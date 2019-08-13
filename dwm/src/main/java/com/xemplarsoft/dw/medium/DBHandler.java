package com.xemplarsoft.dw.medium;

import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPC;

public class DBHandler implements Runnable{
    protected CryptoLinkRPC rpc;
    protected Thread t;

    protected long lastBlock;

    public volatile boolean isRunning = false;

    public DBHandler(CryptoLinkRPC rpc){
        this.rpc = rpc;
        this.t = new Thread(this);
    }

    public void run(){

        while (isRunning){
            try {
                long latest = rpc.getBlockCount();
                if(latest != lastBlock){

                } else {
                    Thread.sleep(100);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
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
}
