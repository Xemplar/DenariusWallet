package com.xemplarsoft.libs.crypto.server.net;

import com.xemplarsoft.libs.crypto.common.NetworkListener;
import com.xemplarsoft.libs.crypto.server.NodeProps;
import com.xemplarsoft.libs.crypto.server.domain.Block;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Rohan on 10/18/2017.
 */
public class LiveVarHandler implements Runnable {
    private volatile boolean run = false;
    private final CryptoLink link;
    private final int BLOCK_AVG;

    private final boolean LIVE_LAST_TIME;
    private final boolean LIVE_HEIGHT;
    private final boolean LIVE_AVG_TIME;

    LiveVarHandler(CryptoLink link, Properties props){
        this.link = link;

        LIVE_LAST_TIME = booleanValue(NodeProps.LIVE_LAST_TIME.getValue(props));
        LIVE_HEIGHT = booleanValue(NodeProps.LIVE_HEIGHT.getValue(props));

        int avg;
        try {
            avg = Integer.parseInt(NodeProps.LIVE_AVG_TIME.getValue(props));
        } catch (Exception e){
            avg = -1;
        }
        LIVE_AVG_TIME = avg > 0;
        BLOCK_AVG = avg < 2 ? 2 : avg;

        run = LIVE_AVG_TIME || LIVE_HEIGHT || LIVE_LAST_TIME;
    }

    public void run(){
        long[] timediff = new long[BLOCK_AVG];
        double avgTime = 0;
        long blockHeight = 0, prevHeight = -1;

        for(int i = 0; i < timediff.length; i++){
            timediff[i] = -1;
        }

        blockHeight = link.getBlockCount();
        if(LIVE_AVG_TIME || LIVE_LAST_TIME) {
            for (int i = BLOCK_AVG; i > 1; i--) {
                Block fst = link.getBlockAt(blockHeight - i);
                Block scd = link.getBlockAt(blockHeight - (i + 1));

                timediff[i - 1] = fst.getTime() - scd.getTime();

                System.out.println("  " + i + ": " + fst.getTime() + " - " + scd.getTime() + " = " + timediff[i - 1]);
                avgTime += timediff[i - 1];
            }
            avgTime /= (double) BLOCK_AVG;
            System.out.println("AVG: " + avgTime);
        }
        if(LIVE_HEIGHT) {
            System.out.println("Block Height: " + blockHeight);
        }

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        if(LIVE_LAST_TIME) postVar("lasttime", "" + timediff[0], pairs);
        if(LIVE_AVG_TIME) postVar("avgtime", "" + avgTime, pairs);
        if(LIVE_HEIGHT) postVar("height", "" + blockHeight, pairs);

        while(run){
            avgTime = 0;
            blockHeight = link.getBlockCount();
            if(blockHeight != prevHeight){
                if(LIVE_AVG_TIME) {
                    for (int i = (BLOCK_AVG - 1); i > 0; i--) {
                        timediff[i] = timediff[i - 1];
                    }
                }

                if(LIVE_LAST_TIME || LIVE_AVG_TIME) {
                    Block fst = link.getBlockAt(blockHeight);
                    Block scd = link.getBlockAt(blockHeight - 1);

                    timediff[0] = fst.getTime() - scd.getTime();
                    for (int i = 0; i < BLOCK_AVG; i++) {
                        avgTime += timediff[i];
                    }
                    avgTime /= (double) BLOCK_AVG;
                    System.out.println("AVG: " + avgTime);
                    System.out.println("First 4: " + timediff[0] + ", " + timediff[1] + ", " + timediff[2] + ", " + timediff[3]);
                }

                prevHeight = blockHeight;

                if(LIVE_LAST_TIME) postVar("lasttime", "" + timediff[0], pairs);
                if(LIVE_AVG_TIME) postVar("avgtime", "" + avgTime, pairs);
                if(LIVE_HEIGHT) postVar("height", "" + blockHeight, pairs);
            }
            PaymentHandler.sleep(50);
        }
    }

    private void postVar(String name, String value, List<NameValuePair> pairs){
        pairs.add(new BasicNameValuePair("varname", name));
        pairs.add(new BasicNameValuePair("value", value));
        link.doPost("postvar", pairs, new NetworkListener.CryptoServerService() { });
        pairs.clear();
    }

    public static boolean booleanValue(String string){
        try{
            if(Integer.parseInt(string) > 0) return true;
        } catch (Exception e) { }
        return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("t") ||
                string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("y");
    }
}
