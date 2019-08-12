package com.xemplarsoft.libs.crypto.server.net;

import com.xemplarsoft.libs.crypto.server.CommunicationException;
import com.xemplarsoft.libs.crypto.server.CryptocoinException;
import com.xemplarsoft.libs.crypto.common.NetworkListener;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPC;
import com.xemplarsoft.libs.crypto.server.link.CryptoLinkRPCImpl;
import com.xemplarsoft.libs.crypto.server.domain.Block;
import com.xemplarsoft.libs.crypto.server.domain.Payment;
import com.xemplarsoft.libs.crypto.server.domain.Transaction;
import com.xemplarsoft.libs.crypto.server.jsonrpc.JsonMapper;
import com.xemplarsoft.libs.crypto.server.net.domain.Request;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Rohan on 8/18/2017.
 */
public class CryptoLink {
    private static int MIN_CONFIRMS = 3;
    private final JsonMapper mapper;
    private final String address;

    private CryptoLinkRPC cli;
    private CryptoRest rest;

    public CryptoLink(String address, String webURL, String pubKey, char[] privKey, char[] enckey, String rpcHost, int rpcPort, String rpcUser, String rpuPass) throws CryptocoinException, CommunicationException {
        this(address, webURL, pubKey, privKey, enckey);

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient http = HttpClients.custom().setConnectionManager(cm).build();

        cli = new CryptoLinkRPCImpl(http, rpcHost, rpcPort, rpcUser, rpuPass);
    }
    public CryptoLink(String address, String webURL, String pubKey, char[] privKey, char[] enckey, Properties props) throws IOException, CryptocoinException, CommunicationException{
        this(address, webURL, pubKey, privKey, enckey);

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient http = HttpClients.custom().setConnectionManager(cm).build();

        cli = new CryptoLinkRPCImpl(http, props);
    }
    public CryptoLink(String address, String webURL, String pubKey, char[] privKey, char[] enckey){
        this.address = address;

        rest = new CryptoRest(pubKey, privKey, enckey, webURL, this);
        mapper = new JsonMapper();
    }

    public void setWebServerURL(String remote){
        this.rest.setURL(remote);
    }
    public void initialize(Properties props) throws IOException, CommunicationException, CryptocoinException{
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient http = HttpClients.custom().setConnectionManager(cm).build();

        cli = new CryptoLinkRPCImpl(http, props);
    }

    public boolean initialized(){
        return cli != null && rest != null;
    }

    //Net Commands
    public int findRequests(NetworkListener.ServerRequestListener listener){
        List<NameValuePair> params = new ArrayList<>();
        return rest.doPost("requests", params, listener);
    }
    boolean importRequests(String response, NetworkListener.ServerRequestListener listener){
        try {
            System.out.println("Finding requests: " + response);
            List<Request> requests = mapper.mapToList(response, Request.class);
            Request[] ret = new Request[requests.size()];
            ret = requests.toArray(ret);
            listener.requestsFound(ret);
        } catch(Exception e){
            listener.noRequests();
            return false;
        }
        return true;
    }

    //Raw Commands
    List<String> getPayTX(String payID, int start, int stop){
        List<String> ret = new ArrayList<String>();
        try{
            for(int i = start; i <= stop; i++){
                Block current = getBlockAt(i);
                List<Transaction> transactions = current.getTx();
                for(Transaction tx : transactions){
                    try{
                        Transaction p = cli.getTransaction(tx.getTxId());
                        String note = p.getComment();
                        if(note == null) continue;
                        System.out.println("  Received: " + note);
                        System.out.println("  Required: " + payID);
                        if(payID.trim().equals(note.trim())){
                            ret.add(p.getTxId());
                            System.out.println("Block added");
                        }
                        System.out.println("  GG      : " + payID.trim().equals(note.trim()) + "\n");
                    } finally { }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    int getBlockCount(){
        try {
            return cli.getBlockCount();
        } catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }
    Transaction getTransaction(String txID){
        try{
            return cli.getTransaction(txID);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    Block getBlockAt(int height){
        try {
            return (Block)cli.getBlock(cli.getBlockHash(height), true);
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public Block getBlockByHash(String hash){
        try {
            return (Block)cli.getBlock(hash, true);
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public Payment getLastPayment(){
        try{
            return cli.listTransactions("*", 1).get(0);
        } catch(Exception e){
            return null;
        }
    }
    public int doPost(String method, List<NameValuePair> params, NetworkListener.CryptoServerService listener){
        return rest.doPost(method, params, listener);
    }
    public static void sleep(long mills){
        try{
            Thread.sleep(mills);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
