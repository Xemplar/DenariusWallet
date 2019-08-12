package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedeemScript extends PubKeyScript {
	private String p2sh;

	public RedeemScript(){}
	public RedeemScript(String p2sh){
		this.p2sh = p2sh;
	}
}