package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MultiSigAddress extends Entity {
	private String address;
	private String redeemScript;

	public MultiSigAddress(){}
	public MultiSigAddress(String address, String redeemScript){
		this.address = address;
		this.redeemScript = redeemScript;
	}
}