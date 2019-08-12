package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignatureScript extends Entity {
	private String asm;
	private String hex;

	public SignatureScript(){}
	public SignatureScript(String asm, String hex){
		this.asm = asm;
		this.hex = hex;
	}

    public void setHex(String hex) {
        this.hex = hex;
    }
    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getAsm() {
        return asm;
    }
    public String getHex() {
        return hex;
    }
}