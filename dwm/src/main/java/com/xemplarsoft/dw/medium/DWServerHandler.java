package com.xemplarsoft.dw.medium;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class DWServerHandler implements Runnable {
    protected volatile ArrayList<DWClientHandler> clients;
    protected volatile boolean isRunning = false;
    protected DWClientListener listener;
    protected ServerSocket server;
    protected Thread t, t2;

    public DWServerHandler(DWClientListener listener){
        this.t = new Thread(this::run);
        this.t2 = new Thread(this::run2);
        this.clients = new ArrayList<>();
        this.listener = listener;
    }

    public synchronized void start(){
        isRunning = true;
        t.start();
        t2.start();
        System.out.println("INFO: Server Starting");
    }

    public synchronized void stop(){
        isRunning = false;
    }

    public void run() {
        Socket socket = null;
        try {
            server = new ServerSocket(24516);
        } catch (Exception e){
            e.printStackTrace();
        }

        while (isRunning){
            try {
                System.out.println("INFO: Waiting for Client");
                socket = server.accept();
                System.out.println("INFO: Client Found, IP: " + socket.getInetAddress().getHostAddress());
            } catch (Exception e){
                e.printStackTrace();
            }
            DWClientHandler handler = new DWClientHandler(socket, listener);
            handler.start();
            clients.add(handler);
            System.out.println("INFO: Client Handler Started");
        }
        try {
            t.join();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run2(){
        while(isRunning){
            DWClientHandler delete = null;
            for(DWClientHandler handle : clients){
                if(handle.delete) delete = handle;
            }

            if(delete != null){
                delete.stop();
                clients.remove(delete);
            }
        }
    }

    public void writeToClient(long UID, String message){
        for(DWClientHandler cli : clients){
            if(cli.UID == UID){
                try {
                    cli.write(CryptoHandler.encryptMessage(message, cli.sync));
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            } else {

            }
        }
    }
}
