package com.xemplarsoft.dw.medium;

import com.xemplarsoft.libs.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.*;

public class DWClientHandler implements Runnable {
    protected static final String privKey = "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";
    protected static final Map<Long, SecretKey> keys = new HashMap<>();
    protected static final UIDPool pool = new UIDPool();
    protected volatile boolean isRunning = false;
    protected volatile Socket s;
    protected Thread t;

    protected volatile DWClientListener listener;
    protected volatile BufferedWriter out;
    protected volatile BufferedReader in;

    protected static PrivateKey async;
    protected static DWClientManager manager;

    protected SecretKey sync;
    protected long UID;
    protected String chall;
    protected boolean isReady = false, delete = false;

    static {
        try {
            async = CryptoHandler.loadPrivateKey(privKey);
            manager = new DWClientManager();
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
        listener.dataReceived(UID, command);
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

    public synchronized void write(String message){
        System.out.println("INFO: Sending Message");
        try {
            out.write(message + "\n");
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
