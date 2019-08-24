package com.xemplarsoft.dw.medium;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xemplarsoft.libs.crypto.server.domain.Block;
import com.xemplarsoft.libs.crypto.server.domain.TXIn;
import com.xemplarsoft.libs.crypto.server.domain.TXOut;
import com.xemplarsoft.libs.crypto.server.domain.Transaction;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPC;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


public class DWClientManager implements Runnable{
    // DB VAR IDENT VALUES
    public static final String KEY_LAST_BLOCK = "LASTBLOCK";
    // END DB VARS
    protected CryptoLinkRPC rpc;
    protected Thread t;
    protected long lastBlock = 2296112;
    public volatile boolean isRunning = false;

    private ObjectMapper mapper;
    private DBHandler handler;
    private DWServerHandler server;

    public DWClientManager(CryptoLinkRPC rpc, DBHandler handler){
        this.rpc = rpc;
        this.t = new Thread(this);
        this.mapper = new ObjectMapper();
        this.handler = handler;
    }

    public void setServerHandler(DWServerHandler handler){
        this.server = handler;
    }

    public void run(){
        lastBlock = Long.parseLong(handler.getValue(KEY_LAST_BLOCK));
        lastBlock++;
        boolean written = false;
        while (isRunning){
            try {
                long latest = rpc.getBlockCount();
                if(latest > lastBlock){
                    written = false;
                    String hash = rpc.getBlockHash(++lastBlock);
                    Block b = rpc.getBlock(hash);
                    System.out.println("DWClientManager: Syncing block " + b.getHeight());
                    ArrayList<Transaction> txs = new ArrayList<>(b.getTx());
                    int i = 0;
                    for(Transaction t : txs) {
                        Transaction tx = rpc.getTransaction(t.getTxId());
                        if (tx.getVin().get(0).getCoinbase() != null){
                            //System.out.println("TX["+i+"]  COINBASE: " + tx.getVin().get(0).getCoinbase());
                            for (TXOut out : tx.getVout()) {
                                List<String> addresses = out.getScriptPubKey().getAddresses();
                                if(addresses == null){
                                    //System.out.println("TX[" + i + "]  DWClientManager: Generated TX");
                                } else {
                                    //System.out.println("TX[" + i + "]  DWClientManager: TX Out Address " + addresses.get(0) + ", Amount: " + out.getValue());
                                }
                            }
                        } else {
                            //System.out.println("TX["+i+"]  TXID: " + tx.getTxId());
                            String address = "";
                            boolean stake = false;
                            BigDecimal stakeAmount = new BigDecimal("0.0");

                            // Process TX outputs
                            for (TXOut out : tx.getVout()) {
                                List<String> addresses = out.getScriptPubKey().getAddresses();
                                if(addresses == null){
                                    //System.out.println("TX[" + i + "]  DWClientManager: Stake TX");
                                    stake = true;

                                    TXIn in = tx.getVin().get(0);
                                    Transaction txin = rpc.getTransaction(in.getTxid());
                                    int vout = in.getVout();
                                    for (TXOut o : txin.getVout()) {
                                        if(o.getN() == vout){
                                            address = o.getScriptPubKey().getAddresses().get(0);
                                            stakeAmount = stakeAmount.subtract(o.getValue());
                                            break;
                                        }
                                    }
                                } else {
                                    //System.out.println("TX[" + i + "]  DWClientManager: TX Out Address " + addresses.get(0) + ", Amount: " + out.getValue());
                                    if(addresses.get(0).equals(address) && stake){
                                        stakeAmount = stakeAmount.add(out.getValue());
                                    } else if(hasAddress(addresses.get(0))){
                                        System.out.println("DWClientManager: " + addresses.get(0) + " received " + out.getValue().toPlainString() + "D");
                                        addBalance(addresses.get(0), out.getValue());

                                        long UID = getUIDofAddress(addresses.get(0));
                                        writeToClient(UID, "balance received " + addresses.get(0) + " " + out.getValue().toPlainString());
                                    }
                                }
                            }

                            // Process TX inputs
                            for (TXIn in : tx.getVin()) {
                                Transaction txin = rpc.getTransaction(in.getTxid());
                                int vout = in.getVout();
                                for (TXOut out : txin.getVout()) {
                                    if(out.getN() != vout) continue;
                                    List<String> addresses = out.getScriptPubKey().getAddresses();
                                    if(addresses == null){
                                        //System.out.println("TX[" + i + "]  DWClientManager: Stake TX");
                                    } else {
                                        //System.out.println("TX[" + i + "]  DWClientManager: TX In Address " + addresses.get(0) + ", Amount: " + out.getValue());
                                    }
                                }
                            }
                            if(stake){
                                if(hasAddress(address)){
                                    addBalance(address, stakeAmount);
                                    long UID = getUIDofAddress(address);
                                    writeToClient(UID, "balance staked " + address + " " + stakeAmount.toPlainString());
                                }
                            }
                        }
                        i++;
                    }
                } else {
                    Thread.sleep(1000);
                    if(!written) {
                        handler.putValue(KEY_LAST_BLOCK, lastBlock + "");
                        System.out.println("DWClientManager: Finished to block " + lastBlock);
                    }
                    written = true;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        try {
            t.join();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void writeToClient(long UID, String message){
        server.writeToClient(UID, message);
    }

    public synchronized void start(){
        isRunning = true;
        t.start();
        //System.out.println("INFO: Server Starting");
    }

    public synchronized void stop(){
        isRunning = false;
    }

    public boolean hasAddress(String address){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `ADDRESS` FROM `ADDRESSES` WHERE `ADDRESS`='" + address + "'");
            String test = "";
            if(res.next()){
                test = res.getString("ADDRESS");
            }
            res.close();
            query.close();
            return address.equals(test);
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public BigDecimal addBalance(String address, BigDecimal amount){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `BALANCE` FROM `ADDRESSES` WHERE `ADDRESS`='" + address + "'");
            BigDecimal current = null;
            if(res.next()){
                current = amount.add(new BigDecimal(res.getString("BALANCE")));
                handler.executeUpdate("UPDATE `ADDRESSES` set BALANCE='" + current.toPlainString() + "' WHERE `ADDRESS`='" + address + "'");
            }
            res.close();
            query.close();
            return current;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal subBalance(String address, BigDecimal amount){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `BALANCE` FROM `ADDRESSES` WHERE `ADDRESS`='" + address + "'");
            BigDecimal current = null;
            if(res.next()){
                current = (new BigDecimal(res.getString("BALANCE"))).subtract(amount);
                handler.executeUpdate("UPDATE `ADDRESSES` set BALANCE='" + current.toPlainString() + "' WHERE `ADDRESS`='" + address + "'");
            }
            res.close();
            query.close();
            return current;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getBalance(String address){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `BALANCE` FROM `ADDRESSES` WHERE `ADDRESS`='" + address + "'");
            BigDecimal current = null;
            if(res.next()){
                current = new BigDecimal(res.getString("BALANCE"));
            }
            res.close();
            query.close();
            return current;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public long getUIDofAddress(String address){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `UID` FROM `ADDRESSES` WHERE `ADDRESS`='" + address + "'");
            long current = 0;
            if(res.next()){
                current = Long.parseLong(res.getString("UID"));
            }
            res.close();
            query.close();
            return current;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public BigDecimal getBalance(Long UID){
        try {
            BigDecimal ret = new BigDecimal("0.0");
            ArrayList<String> dat = getAddresses(UID);
            for(String address : dat){
                ret = ret.add(getBalance(address));
            }

            return ret;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean putAddress(String address, long UID){
        try {
            Statement query = handler.getStatement();
            handler.executeUpdate("INSERT INTO `ADDRESSES` (UID, ADDRESS, BALANCE) VALUES ('" + UID + "','" + address + "','0.0')");
            ResultSet res = query.executeQuery("SELECT `ADDRESSES` FROM USERS WHERE UID='" + UID + "'");
            if(res.next()){
                String[] dat = res.getString("ADDRESSES").split(":");

                String put = "";
                for(String s : dat) put += s + ":";
                put += address;

                handler.executeUpdate("UPDATE `USERS` set ADDRESSES='" + put + "' WHERE `UID`='" + UID + "'");
            }
            res.close();
            query.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getAddresses(long UID){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `ADDRESSES` FROM USERS WHERE UID='" + UID + "'");
            if(res.next()){
                String[] dat = res.getString("ADDRESSES").split(":");
                ArrayList<String> addresses = new ArrayList<>();
                Collections.addAll(addresses, dat);
                return addresses;
            }
            res.close();
            query.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean clientExists(long UID){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `UID` FROM USERS WHERE UID='" + UID + "'");
            long test = 0L;
            if(res.next()){
                test = res.getLong("UID");
            }
            res.close();
            query.close();

            return UID == test;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public SecretKey getSyncKey(long UID){
        try {
            Statement query = handler.getStatement();
            ResultSet res = query.executeQuery("SELECT `ENCKEY` FROM USERS WHERE UID='" + UID + "'");
            String key = "";
            if(res.next()){
                key = res.getString("ENCKEY");
            }
            res.close();
            query.close();

            byte[] dat = Base64.getDecoder().decode(key);
            return new SecretKeySpec(dat, 0, dat.length, "AES");
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void saveUser(long UID, SecretKey sync, ArrayList<String> addresses){
        try {
            String key = Base64.getEncoder().encodeToString(sync.getEncoded());
            String addrs = "";
            for(String s : addresses) addrs += ":";
            if(addrs.length() > 0) addrs = addrs.substring(0, addrs.length() - 2);

            String sql = "INSERT INTO USERS (UID,ADDRESSES,EMAIL,ENCKEY) " +
                    "VALUES (" + UID + ", '" + addrs + "', '', '" + key + "');";
            handler.executeUpdate(sql);
        } catch (SQLException e){
            if(e.getMessage().contains("USERS")) {
                handler.initialize();
                saveUser(UID, sync, addresses);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
