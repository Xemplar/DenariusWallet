package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TXScriptPubKey extends Entity{
    private String asm;
    private int reqSigs;
    private String type;
    private List<String> addresses;

    public TXScriptPubKey(){}
    public TXScriptPubKey(String asm, int reqSigs, String type, List<String> addresses){
        setAsm(asm);
        setReqSigs(reqSigs);
        setType(type);
        setAddresses(addresses);
    }

    public void setAsm(String asm) { this.asm = asm; }
    public void setReqSigs(int reqSigs) { this.reqSigs = reqSigs; }
    public void setType(String type) { this.type = type; }
    public void setAddresses(List<String> addresses) { this.addresses = addresses; }

    public int getReqSigs() { return reqSigs; }
    public String getType() { return type; }
    public List<String> getAddresses() { return addresses; }
    public String getAsm() { return asm; }

}
