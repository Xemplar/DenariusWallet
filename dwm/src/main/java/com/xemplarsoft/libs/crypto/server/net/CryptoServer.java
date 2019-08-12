package com.xemplarsoft.libs.crypto.server.net;
import com.xemplarsoft.libs.crypto.common.NetworkListener;
import com.xemplarsoft.libs.crypto.server.NodeProps;
import com.xemplarsoft.libs.crypto.server.ServerListener;
import com.xemplarsoft.libs.crypto.server.net.domain.Request;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Rohan on 8/20/2017.
 */
public class CryptoServer implements Runnable{
    public static int MIN_CONFIRMS = 3;
    public ServerListener listener;
    public boolean running = false;
    CryptoLink link;
    LiveVarHandler handler;

    public CryptoServer(String cryptoAddress, String webAddress, String user, char[] pass, char[] enckey, String config){
        this.link = new CryptoLink(cryptoAddress, webAddress, user, pass, enckey);
        try {
            Properties nodeConfig = new Properties();
            InputStream is = new BufferedInputStream(new FileInputStream("./" + config));
            nodeConfig.load(is);
            is.close();
            link.initialize(nodeConfig);
            handler = new LiveVarHandler(link, nodeConfig);
        } catch(Exception e){
            e.printStackTrace();
        }
        running = true;
    }

    public CryptoServer(String config, ServerListener listener){
        this.listener = listener;
        try {
            Properties nodeConfig = new Properties();
            InputStream is = new BufferedInputStream(new FileInputStream("./" + config));
            nodeConfig.load(is);
            is.close();

            String address = NodeProps.WALLET_ADDRESS.getValue(nodeConfig);
            String url = NodeProps.DB_URL.getValue(nodeConfig);
            String user = NodeProps.DB_USER.getValue(nodeConfig);
            char[] pass = NodeProps.DB_PASS.getValue(nodeConfig).toCharArray();
            char[] enckey = NodeProps.DB_ENCKEY.getValue(nodeConfig).toCharArray();

            try {
                MIN_CONFIRMS = Integer.parseInt(NodeProps.RPC_CONFIRM.getValue(nodeConfig));
            } catch (Exception e){
                System.err.println("Invalid value set for " + NodeProps.RPC_CONFIRM.getKey());
                System.exit(-1);
            }

            this.link = new CryptoLink(address, url, user, pass, enckey);
            link.initialize(nodeConfig);
            handler = new LiveVarHandler(link, nodeConfig);
        } catch(Exception e){
            e.printStackTrace();
        }
        running = true;
    }

    public void run(){
        Thread liveVar = new Thread(handler);
        liveVar.start();

        while(running){
            searchForRequests();
            setupPaymentHandlers();
            CryptoLink.sleep(1000);
        }
    }

    public volatile List<PaymentHandler> payments = new ArrayList<>();
    public void setupPaymentHandlers(){
        System.out.println("Running Handlers: " + payments.size());
        System.out.println("Request Count: " + requests.size());
        for(Request r : requests){
            payments.add(new PaymentHandler(this, r, new NetworkListener.ServerPaymentListener() {
                public void paymentReceived(PaymentHandler handler, String txID) {
                    System.out.println(handler.getID() + " has paid: " + handler.getAmount());
                    payments.remove(handler);

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id", handler.getID() + ""));
                    params.add(new BasicNameValuePair("txid", txID));

                    CryptoServer.this.link.doPost("setpaid", params, null);
                    CryptoServer.this.listener.onRequestFilled(handler.getID());
                }

                public void paymentCanceled(PaymentHandler handler) {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id", handler.getID() + ""));
                    CryptoServer.this.link.doPost("remove", params, null);

                    payments.remove(handler);
                    CryptoServer.this.listener.onRequestCanceled(handler.getID());
                }
            }));
        }
        requests.clear();

        for(PaymentHandler pay : payments){
            if(pay.hasStarted()) continue;
            Thread t = new Thread(pay);
            t.start();
        }
    }

    public volatile List<Request> requests = new ArrayList<>();
    public volatile List<String> codes = new ArrayList<>();
    public void searchForRequests(){
        if(link.initialized()){
            link.findRequests(new NetworkListener.ServerRequestListener() {
                public void requestsFound(Request[] requests) {
                    for(Request r : requests){
                        boolean found = false;
                        for(int i = 0; i < payments.size(); i++){
                            boolean seen = payments.get(i).hasRequest(r);
                            found |= seen;

                            if(seen && r.filled == -1){
                                payments.get(i).cancel();
                            }
                        }
                        if(!found) {
                            CryptoServer.this.requests.add(r);
                            CryptoServer.this.listener.onRequestReceived(r.id, r.notes, new BigDecimal(r.amount));
                            CryptoServer.this.listener.onRequestUpdate(r.id, "Request");
                            System.out.println("  Request Found: " + r.getId());
                        }
                    }
                }

                public void noRequests() {

                }
            });
        }
    }

    public static void setMinimumConfirms(int confirms){
        MIN_CONFIRMS = confirms;
    }

    public boolean checkCode(String code){
        if(code == null) return false;
        for(String s : codes){
            if(s.equals(code)) return false;
        }
        codes.add(code);
        return true;
    }
}
