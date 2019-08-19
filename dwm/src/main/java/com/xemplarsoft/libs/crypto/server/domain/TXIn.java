package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TXIn extends Entity {
    private String txid;
    private String coinbase;
    private int vout;
    private TXScriptSig scriptSig;
    private long sequence;

    public TXIn(){}
    public TXIn(String coinbase, long sequence){
        setCoinbase(coinbase);
        setSequence(sequence);
    }
    public TXIn(String txid, int vout, TXScriptSig scriptSig, long sequence){
        setTxid(txid);
        setVout(vout);
        setScriptSig(scriptSig);
        setSequence(sequence);
    }

    public void setTxid(String txid) { this.txid = txid; }
    public void setCoinbase(String coinbase) { this.coinbase = coinbase; }
    public void setVout(int vout) { this.vout = vout; }
    public void setScriptSig(TXScriptSig scriptSig) { this.scriptSig = scriptSig; }
    public void setSequence(long sequence) { this.sequence = sequence; }

    public String getTxid() { return txid; }
    public String getCoinbase() { return coinbase; }
    public int getVout() { return vout; }
    public TXScriptSig getScriptSig() { return scriptSig; }
    public long getSequence() { return sequence; }
}
