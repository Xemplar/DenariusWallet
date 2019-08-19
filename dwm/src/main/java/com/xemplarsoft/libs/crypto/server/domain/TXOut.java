package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TXOut extends Entity{
    private BigDecimal value;
    private int n;
    private TXScriptPubKey scriptPubKey;

    public TXOut(){}
    public TXOut(BigDecimal value, int n, TXScriptPubKey scriptPubKey){
        setValue(value);
        setN(n);
        setScriptPubKey(scriptPubKey);
    }

    public void setValue(BigDecimal value) { this.value = value; }
    public void setN(int n) { this.n = n; }
    public void setScriptPubKey(TXScriptPubKey scriptPubKey) { this.scriptPubKey = scriptPubKey; }

    public BigDecimal getValue() { return value; }
    public int getN() { return n; }
    public TXScriptPubKey getScriptPubKey() { return scriptPubKey; }
}
