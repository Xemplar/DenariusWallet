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
import java.util.ArrayList;
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

                    case "importprivkey":{
                        System.out.println("INFO: Client " + UID + " added new address.");
                        link.importPrivKey(dat[1], "" + UID, false);
                        break;
                    }

                    case "pay":{
                        String address = dat[1];
                        BigDecimal amt = new BigDecimal(dat[2]);
                        BigDecimal total = amt.add(new BigDecimal("0.00001"));

                        BigDecimal balance = DWClientHandler.manager.getBalance(UID);
                        if(balance.compareTo(total) >= 0){
                            //Double checks can't hurt
                            String txid = link.sendToAddress(address, amt);
                            if(txid != null) {
                                BigDecimal left = new BigDecimal(total.toPlainString());
                                ArrayList<String> addresses = DWClientHandler.manager.getAddresses(UID);
                                int index = 0;
                                while(left.compareTo(new BigDecimal("0.0")) > 0){
                                    BigDecimal balCur = DWClientHandler.manager.getBalance(addresses.get(index));
                                    if(left.compareTo(balCur) <= 0){
                                        DWClientHandler.manager.subBalance(addresses.get(index), left);
                                        left = new BigDecimal("0.0");
                                    } else {
                                        DWClientHandler.manager.subBalance(addresses.get(index), balCur);
                                        total = total.subtract(balCur);
                                    }
                                    index++;
                                }
                                handler.writeToClient(UID, "pay successful " + txid);
                            } else {
                                handler.writeToClient(UID, "pay failed");
                            }
                        } else {
                            handler.writeToClient(UID, "pay failed");
                        }
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
