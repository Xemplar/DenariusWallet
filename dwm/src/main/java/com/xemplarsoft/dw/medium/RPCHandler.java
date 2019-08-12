package com.xemplarsoft.dw.medium;

import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPCImpl;

public class RPCHandler implements Runnable {
    private CryptoLinkRPCImpl link = null;
    public RPCHandler(String rpcuser, String rpcpass){
        try {
            link = new CryptoLinkRPCImpl("127.0.0.1", 32369, rpcuser, rpcpass);
            System.out.println(link.getInfo().getBlocks());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){

    }
}
