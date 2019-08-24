package com.xemplarsoft.dw.medium;

import com.xemplarsoft.Vars;
import com.xemplarsoft.libs.crypto.server.domain.Address;
import com.xemplarsoft.libs.tribus.KeyManager;
import com.xemplarsoft.libs.tribus.KeySet;
import com.xemplarsoft.libs.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.*;

public class DWClientHandler implements Runnable {
    protected static final String privKey = Vars.privKey;
    protected static final Map<Long, SecretKey> keys = new HashMap<>();
    protected static final UIDPool pool = new UIDPool();
    protected volatile boolean isRunning = false;
    protected volatile Socket s;
    protected Thread t;

    protected volatile DWClientListener listener;
    protected volatile BufferedWriter out;
    protected volatile BufferedReader in;

    protected static DWClientManager manager;
    protected static PrivateKey async;
    protected static DBHandler db;

    protected SecretKey sync;
    protected long UID;
    protected String chall;
    protected boolean isReady = false, delete = false;

    static {
        try {
            async = CryptoHandler.loadPrivateKey(privKey);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public DWClientHandler(Socket s, DWClientListener listener){
        this.t = new Thread(this);
        this.s = s;
        this.listener = listener;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public synchronized void start(){
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (Exception e){
            e.printStackTrace();
        }

        isRunning = true;
        t.start();
    }

    public synchronized void stop(){
        isRunning = false;
    }

    public void registerOnNetwork(int step, String response){
        String[] dat = response.split(":");
        if(step == 0){
            System.out.println("INFO: Client Requesting Registration");
            try{
                System.out.println("REGISTER: Sync key decrypting.");
                String[] syncDat = CryptoHandler.decryptMessage(dat[1], async).split(":");
                byte[] decoded = Base64.getDecoder().decode(syncDat[0]);
                sync = new SecretKeySpec(decoded, 0, decoded.length, "AES");
                chall = syncDat[1];

                write(CryptoHandler.encryptMessage(chall, sync));
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("REGISTER: Decryption failed.");
            }
        } else if(step == 1){
            try {
                String message = CryptoHandler.decryptMessage(response, sync);
                if(message.equals("finishmii")){
                    System.out.println("REGISTER: Generated UID");
                    UID = pool.getNewUID();
                    keys.put(UID, sync);
                    write(CryptoHandler.encryptMessage("ACKR:" + UID, sync));
                    isReady = true;

                    ArrayList<String> addresses = new ArrayList<>();
                    DWClientHandler.manager.saveUser(UID, sync, addresses);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void loginOnNetwork(int step, String response){
        String[] dat = response.split(":");
        if(step == 0){
            try {
                System.out.println("LOGIN: user " + dat[1] + " attempting login");
                this.UID = Long.parseLong(dat[1]);
                if(manager.clientExists(UID)){
                    System.out.println("LOGIN: user exists");
                    this.chall = CryptoHandler.generateRandomString(21);
                    this.sync = manager.getSyncKey(UID);
                    if(this.sync == null){
                        write("checkfail");
                        this.delete = true;
                    } else {
                        write("check:" + CryptoHandler.encryptMessage(this.chall, sync));
                    }
                    System.out.println("LOGIN: sending user challenge");
                } else {
                    System.out.println("LOGIN: user doesn't exists.");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(step == 1){
            try {
                String chall = CryptoHandler.decryptMessage(dat[1], sync);
                if(chall.equals(StringUtils.invertString(this.chall))){
                    System.out.println("LOGIN: user successfully logged in");
                    write(CryptoHandler.encryptMessage("ACKL:" + this.UID, sync));
                } else {
                    write("checkfail");
                    System.out.println("LOGIN: user failed login");
                    this.delete = true;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void processCommand(String command){
        System.out.println("Command: " + command);
        String[] dat = command.split(" ");
        if(dat[0].equals("do")) {
            String com = "";
            for(int i = 1; i < dat.length; i++) com += dat[i] + " ";
            listener.dataReceived(UID, com.trim());
        } else if(dat[0].equals("newkey")) {
            if(dat[1].equals("local")){
                newLocalKey(dat[2]);
                encryptAndWrite("Address Received");
            } else if(dat[1].equals("remote")){
                encryptAndWrite(newRemoteKey());
            }
        } else if(dat[0].equals("getbalance")){
            if(dat.length == 1) {
                encryptAndWrite("balance total wallet " + manager.getBalance(UID).toPlainString());
            } else {
                encryptAndWrite("balance total " + dat[1] + " " + manager.getBalance(dat[1]).toPlainString());
            }
        } else if(dat[0].equals("getaddresses")){
            ArrayList<String> addresses = manager.getAddresses(UID);
            String resp = "";
            for(String s : addresses) resp += s + " ";
            encryptAndWrite("addresses " + resp.trim().replace(' ', ':'));
        } else if(dat[0].equals("pay")){
            String address = dat[1];
            BigDecimal amt = new BigDecimal(dat[2]);
            BigDecimal total = amt.add(new BigDecimal("0.00001"));

            BigDecimal balance = manager.getBalance(UID);
            if(balance.compareTo(total) >= 0){
                listener.dataReceived(UID, "pay " + address + " " + amt.toPlainString());
            } else {
                encryptAndWrite("pay failed");
            }
        }
    }

    public void processMessage(String message){
        String[] dat = message.split(":");
        System.out.println("INFO: Message Received");
        if(dat.length == 1){
            try {
                String resp = CryptoHandler.decryptMessage(message, sync);
                if(resp.startsWith("finishmii")) registerOnNetwork(1, message);
                else processCommand(resp);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(dat[0].equals("loadmii")){
            System.out.println("INFO: loadmii Requested");
            registerOnNetwork(0, message);
        } else if(dat[0].equals("logmiiin")){
            System.out.println("INFO: logmiiin Requested");
            loginOnNetwork(0, message);
        } else if(dat[0].equals("verify")){
            loginOnNetwork(1, message);
        }
    }

    public void newLocalKey(String address){
        manager.putAddress(address, UID);
    }

    public String newRemoteKey(){
        KeySet set = KeySet.generateAddress();
        listener.dataReceived(UID, "importprivkey " + set.getWIF());
        newLocalKey(set.getCompressedAddress());
        return set.getCompressedAddress();
    }

    public synchronized void write(String message){
        System.out.println("INFO: Sending Message");
        try {
            out.write(message + "\n");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized void encryptAndWrite(String message){
        try {
            String str = CryptoHandler.encryptMessage(message, sync);
            out.write(str + "\n");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run() { try {
        while (isRunning){
            String line = "";
            while ((line = in.readLine()) != null){
                processMessage(line);
            }
            Thread.sleep(100);
        }
        t.join();
    } catch (SocketException e){
        System.out.println("INFO: Client Disconnected.");
    } catch (Exception e){
        e.printStackTrace();
    }}
}
