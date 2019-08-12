package com.xemplarsoft.dw.medium;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;
import com.xemplarsoft.libs.crypto.server.domain.Entity;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPC;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPCImpl;

import java.util.Base64;

public class Main implements DWClientListener{
    public CryptoLinkRPC link;
    public DWServerHandler handler;
    private Main() throws CommunicationException, CryptocoinException{
        link = new CryptoLinkRPCImpl("127.0.0.1", 32369, "xxx", "xxx");
        handler = new DWServerHandler(this);
        handler.start();
    }


    public void dataReceived(long UID, String data) {
        String[] dat = data.split(" ");

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
