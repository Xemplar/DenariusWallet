package com.xemplarsoft.dw;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xemplarsoft.dw.medium.CryptoHandler;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.PublicKey;
import android.util.Base64;
import com.xemplarsoft.libs.crypto.server.domain.Entity;

import java.util.Random;

public class WalletManager implements Runnable{
    private static String pubkey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA35Q/9plE4tlJqEb+6UrNWMWM6C0j5Ru/oSTRYJ0+eIZHHpJbubZY55BdnU2diMTVnUBydzLvIRKbQtWkHCdsZRsE15dUcSJWplD5Z7pHiFrmd4qCq+rhosRbBRzBS6cYjSabPEBCuHfdp0xDMfTpUTDhaStIAkqMI7tMn+hQTNBOqZ8rXrW1KwoXBZQ1PkEHuVyrYEM1QSBzEA19C+49YMCabT6x35K7cT3qSpUOQ/MeCOArgv1KC8IeQdfV+poPq1d7NxcZw68t4G22EL40hoz05+2M2AW4S0SfD34igbDpDUFaNB6jJKq1YHLTF1Sy+9sJeM7jFd7KNMJsPvhBeOgL7Yt8vtmxR7l6AP7RxRZ3kjCJdkolpXMoXBHAC1DYaYWzIYjA5A9ypvtKdCtRbmF+MUAFyWo7tEp+pFrxiPnD0/4xMruw9FRh9mLTj7pc6sFpFc5CBlBVK1KBXgxubtx0pEwbcYblj9j2N3CC4p3pPgpv8so0f+wY32xXEi9Tm3x6Y2Vmh5jbg8wX4P2q+4RHJEvOhpZvd4TN5AWn3MVGtTTEYeB3D1G+Eq7+URspw11Ra3o3p2XsKXZbSXnSopzW4JBDC/NVgl+0Y6ErhNEjfqvr0odnQToItOer3KKvJtLwMAXSVVCMyTeBL13CyZ1EYLxWuvC7Sdpcj/h53wcCAwEAAQ==";
    protected PublicKey async;
    protected SecretKey sync;
    protected String chall;
    protected long UID;

    protected Socket socket;
    protected BufferedReader reader;
    protected BufferedWriter writer;

    protected boolean isReady = false;

    public WalletManager(){
        try {
            async = CryptoHandler.loadPublicKey(pubkey);
            socket = new Socket("127.0.0.1", 24516);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void registerOnNetwork(int step, String response){
        if(step == 0){
            System.out.println("INFO: Attempting to register on network.");
            chall = generateRandomString(20);
            String message = "loadmii:";
            sync = CryptoHandler.generateAES();
            String syncDat = "";
            try {
                syncDat = CryptoHandler.encryptMessage(new String(Base64.encode(sync.getEncoded(), 0)) + ":chall" + chall, async);
                write(message + syncDat);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(step == 1){
            try {
                System.out.println("REGISTER: Checking Challenge.");
                String dat = CryptoHandler.decryptMessage(response, sync);
                if(dat.equals("chall" + chall)){
                    System.out.println("REGISTER: Challenge Correct");
                    write(CryptoHandler.encryptMessage("finishmii", sync));
                } else {
                    System.out.println("REGISTER: Challenge Incorrect");
                    registerOnNetwork(0, "");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(step == 2){
            try {
                String dat = CryptoHandler.decryptMessage(response, sync);
                if(dat.startsWith("ACKR:")){
                    UID = Long.parseLong(dat.split(":")[1]);
                    isReady = true;
                    System.out.println("REGISTER: UID " + UID + " Received");
                } else {
                    System.out.println("INFO: Server Error -1");
                    registerOnNetwork(0, "");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void write(String message){
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void encryptAndWrite(String message){
        try {
            String str = CryptoHandler.encryptMessage(message, sync);
            writer.write(str + "\n");
            writer.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loginToNetwork(int step, String response){
        if(step == 0){
            try {
                write("logmiiin:" + UID);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(step == 1){
            try {
                String[] dat = response.split(":");
                String random = CryptoHandler.decryptMessage(dat[1], sync);
                String modnar = "";
                for(int i = random.length() - 1; i >= 0; i--) {
                    modnar = modnar + random.charAt(i);
                }
                write("verify:" + CryptoHandler.encryptMessage(modnar, sync));
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(step == 2){
            try{
                String dat = CryptoHandler.decryptMessage(response, sync);
                if(dat.startsWith("ACKL:")){
                    if(UID == Long.parseLong(dat.split(":")[1])){
                        isReady = true;
                    } else {
                        write("loginerr");
                    }
                } else {
                    loginToNetwork(0, "");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isReady() {
        return isReady;
    }

    public String generateRandomString(int length){
        String choices = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String dat = "";

        Random rand = new Random();
        for(int i = 0; i < length; i++){
            int r = rand.nextInt(choices.length());
            dat += choices.charAt(r);
        }

        return dat;
    }

    public void processMessage(String message){
        System.out.println("INFO: Message Received");
        String[] dat = message.split(":");
        if(dat.length == 1){
            try{
                String data = CryptoHandler.decryptMessage(message, sync);
                if(data.startsWith("chall")){
                    registerOnNetwork(1, message);
                } else if(data.startsWith("ACKR")){
                    registerOnNetwork(2, message);
                } else if(data.startsWith("ACKL")){
                    loginToNetwork(2, message);
                } else {
                    processCommand(data);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(dat[0].equals("check")){
            loginToNetwork(1, message);
        }
    }

    public void processCommand(String command){
        System.out.println("COMMAND: " + command);
        String[] dat = command.split("#");
        if(dat.length > 1){
            try {
                Class c = Class.forName("com.xemplarsoft.libs.crypto.server.domain." + dat[0]);
                String data = new String(Base64.decode(dat[1], 0));
                Entity info = (Entity) deserialize(data, c);
                System.out.println(serialize(info, true));
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {

        }
    }

    public static String serialize(Object obj, boolean pretty) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            if (pretty) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }

            return mapper.writeValueAsString(obj);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Entity deserialize(String str, Class<? extends Entity> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Entity obj = mapper.readValue(str, clazz);

            return obj;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void run() {
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        registerOnNetwork(0, "");

        while(socket.isConnected()){
            try {
                String line = reader.readLine();
                if(line != null){
                    processMessage(line);
                }
                Thread.sleep(100);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
