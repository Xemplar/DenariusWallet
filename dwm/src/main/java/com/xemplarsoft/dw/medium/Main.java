package com.xemplarsoft.dw.medium;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;
import com.xemplarsoft.libs.crypto.server.domain.Entity;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPC;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPCImpl;
import com.xemplarsoft.libs.tribus.Base58;
import com.xemplarsoft.libs.tribus.Tribus;

import java.math.BigDecimal;
import java.util.Base64;

public class Main implements DWClientListener{
    public CryptoLinkRPC link;
    public DWServerHandler handler;
    private Main() throws CommunicationException, CryptocoinException{
        link = new CryptoLinkRPCImpl("127.0.0.1", 32369, "tSJ6GlAlo", "dv2aIvSI7RjkpCtvh5Nr31l");
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
        Tribus tribus = new Tribus();
        Byte[] bytes = (Byte[])tribus.digest("04e891c3b19eec7d6b025bcde8b965f133ed1a2fdfd7acb8bcaefcd842caf16f9ce1db8fe4315532a725dcb6440ea40fa6e80214543e7078a75c86c5b13c842650", 0, 1);
        byte[] bs = new byte[bytes.length];
        int i = 0;
        for(Byte b : bytes) bs[i++] = b;
        System.out.println(Base58.encode(bs));

        if(!isUserAGoat()) return;
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
