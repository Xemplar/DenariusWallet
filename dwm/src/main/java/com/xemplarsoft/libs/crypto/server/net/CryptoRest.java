package com.xemplarsoft.libs.crypto.server.net;

import com.xemplarsoft.libs.crypto.common.HttpRest;
import com.xemplarsoft.libs.crypto.common.NetworkListener;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

/**
 * Created by Rohan on 8/18/2017.
 */
public class CryptoRest extends HttpRest {
    private CryptoLink link;

    public CryptoRest(String pubKey, char[] privKey, char[] enckey, String url, CryptoLink link){
        this(pubKey, privKey, enckey, url);
        this.link = link;
    }

    public CryptoRest(String pubKey, char[] privKey, char[] enckey, String url){
        super(pubKey, privKey, enckey, url);
    }

    public void setLink(CryptoLink link){
        this.link = link;
    }
    public int handlePost(String method, List<NameValuePair> params, NetworkListener.CryptoService listener){
        if(listener instanceof NetworkListener.CryptoClientService) return -5;
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            params.clear();
            if (entity != null) {
                InputStream instream = entity.getContent();
                StringBuffer resp = new StringBuffer();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(instream));
                    String output;

                    while ((output = in.readLine()) != null) {
                        resp.append(output + "\n");
                    }
                    in.close();
                } finally {
                    instream.close();
                }
                if(link.initialized()) {
                    if(method.equals("setpaid")){
                        return 0;
                    } else if(method.equals("requests")){
                        return link.importRequests(resp.toString(), (NetworkListener.ServerRequestListener) listener) ? 0 : -4;
                    } else if(method.equals("setpin")){
                        return 0;
                    } else if(method.equals("remove")){
                        return 0;
                    }
                } else {

                }
            }
        } catch (ProtocolException e) {
            this.e = e;
            return -1;
        } catch (MalformedURLException e) {
            this.e = e;
            return -2;
        } catch (IOException e) {
            this.e = e;
            return -3;
        }

        return -4;
    }
}
