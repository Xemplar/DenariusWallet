package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TXScriptSig extends Entity{
    private String asm;
    private String hex;

    public TXScriptSig(){}
    public TXScriptSig(String asm, String hex){
        setAsm(asm);
        setHex(hex);
    }

    public void setAsm(String asm) { this.asm = asm; }
    public void setHex(String hex) { this.hex = hex; }

    public String getAsm() { return asm; }
    public String getHex() { return hex; }
}
