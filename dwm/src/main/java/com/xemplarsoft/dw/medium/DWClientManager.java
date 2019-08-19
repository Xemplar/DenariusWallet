package com.xemplarsoft.dw.medium;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class DWClientManager implements Runnable{
    // DB VAR IDENT VALUES
    public static final String KEY_LAST_BLOCK = "LASTBLOCK";
    // END DB VARS
    protected CryptoLinkRPC rpc;
    protected Thread t;
    protected long lastBlock = 2295408;
    public volatile boolean isRunning = false;

    private ObjectMapper mapper;
    private DBHandler handler;

    public DWClientManager(CryptoLinkRPC rpc, DBHandler handler){
        this.rpc = rpc;
        this.t = new Thread(this);
        this.mapper = new ObjectMapper();
        this.handler = handler;
    }

    public void run(){
        while (isRunning){
            try {
                long latest = rpc.getBlockCount();
                if(latest > lastBlock){
                    String hash = rpc.getBlockHash(++lastBlock);
                    Block b = rpc.getBlock(hash);
                    System.out.println("DWClientManager: START BLOCK: " + b.getHeight());
                    ArrayList<Transaction> txs = new ArrayList<>(b.getTx());
                    int i = 0;
                    for(Transaction t : txs) {
                        Transaction tx = rpc.getTransaction(t.getTxId());
                        if (tx.getVin().get(0).getCoinbase() != null){
                            System.out.println("TX["+i+"]  COINBASE: " + tx.getVin().get(0).getCoinbase());
                            for (TXOut out : tx.getVout()) {
                                List<String> addresses = out.getScriptPubKey().getAddresses();
                                if(addresses == null){
                                    System.out.println("TX[" + i + "]  DWClientManager: Generated TX");
                                } else {
                                    System.out.println("TX[" + i + "]  DWClientManager: TX Out Address " + addresses.get(0) + ", Amount: " + out.getValue());
                                }
                            }
                        } else {
                            System.out.println("TX["+i+"]  TXID: " + tx.getTxId());
                            String address = "";
                            boolean stake = false;
                            BigDecimal stakeAmount = new BigDecimal("0.0");

                            // Process TX outputs
                            for (TXOut out : tx.getVout()) {
                                List<String> addresses = out.getScriptPubKey().getAddresses();
                                if(addresses == null){
                                    System.out.println("TX[" + i + "]  DWClientManager: Stake TX");
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
                                    System.out.println("TX[" + i + "]  DWClientManager: TX Out Address " + addresses.get(0) + ", Amount: " + out.getValue());
                                    if(addresses.get(0).equals(address)) stakeAmount = stakeAmount.add(out.getValue());
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
                                        System.out.println("TX[" + i + "]  DWClientManager: Stake TX");
                                    } else {
                                        System.out.println("TX[" + i + "]  DWClientManager: TX In Address " + addresses.get(0) + ", Amount: " + out.getValue());
                                    }
                                }
                            }
                            if(stake) System.out.println("DWClientManager: " + address + " staked " + stakeAmount);
                        }
                        i++;
                    }
                } else {
                    Thread.sleep(1000);
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

    public synchronized void start(){
        isRunning = true;
        t.start();
        System.out.println("INFO: Server Starting");
    }

    public synchronized void stop(){
        isRunning = false;
    }

    public ArrayList<String> getAddresses(long UID){
        try {
            ResultSet res = handler.executeQuery("SELECT `ADDRESSES` FROM USERS WHERE UID=" + UID);
            if(res.next()){
                String dat = res.getString(1);
                ArrayList<String> addresses = mapper.readValue(dat, new TypeReference<ArrayList<String>>(){});
                return addresses;
            }
            res.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean clientExists(long UID){
        try {
            ResultSet res = handler.executeQuery("SELECT `UID` FROM USERS WHERE UID=" + UID);
            long test = 0L;
            if(res.next()){
                test = res.getLong("UID");
            }
            res.close();

            return UID == test;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public SecretKey getSyncKey(long UID){
        try {
            ResultSet res = handler.executeQuery("SELECT `ENCKEY` FROM USERS WHERE UID=" + UID);
            String key = "";
            if(res.next()){
                key = res.getString("ENCKEY");
            }
            res.close();

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
            String addrs = mapper.writeValueAsString(addresses);

            String sql = "INSERT INTO USERS (UID,ADDRESSES,EMAIL,ENCKEY) " +
                    "VALUES (" + UID + ", '" + addrs + "', '', '" + key + "');";
            handler.executeUpdate(sql);
        } catch (SQLException e){
            if(e.getMessage().contains("USERS")) {
                handler.initalize();
                saveUser(UID, sync, addresses);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
