package com.xemplarsoft.dw.medium;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xemplarsoft.Vars;
import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;
import com.xemplarsoft.libs.crypto.server.domain.Entity;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPC;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPCImpl;

import java.math.BigDecimal;
import java.util.Base64;

public class Main implements DWClientListener{
    public CryptoLinkRPC link;
    public DWServerHandler handler;
    private Main() throws CommunicationException, CryptocoinException{
        link = new CryptoLinkRPCImpl("127.0.0.1", 32369, Vars.rpcuser, Vars.rpcpass);
        DWClientHandler.db = new DBHandler();
        DWClientHandler.manager = new DWClientManager(link, DWClientHandler.db);
        DWClientHandler.manager.start();

        handler = new DWServerHandler(this);
        handler.start();
    }


    public void dataReceived(long UID, String data) {
        String[] dat = data.trim().split(" ");

        try {
            if(dat.length == 1) {
                switch (data) {
                    case "getinfo":
                        handler.writeToClient(UID, "Info#" + Base64.getEncoder().encodeToString(serialize(link.getInfo(), false).getBytes()));
                        break;
                    case "getblockcount":
                        handler.writeToClient(UID, "" + link.getBlockCount());
                        break;
                }
            } else {
                switch (dat[0]){
                    case "getReceivedByAddress":{
                        handler.writeToClient(UID, "" + link.getReceivedByAddress(dat[1]));
                        break;
                    }

                    case "sendtoaddress":{
                        String d = link.sendToAddress(dat[1], new BigDecimal(dat[2]));
                        System.out.println("PERFORMED: " + d);
                        handler.writeToClient(UID, d);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
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

    public static void main(String[] args){
        try {
            new Main();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isUserAGoat(){
        return false;
    }
}
