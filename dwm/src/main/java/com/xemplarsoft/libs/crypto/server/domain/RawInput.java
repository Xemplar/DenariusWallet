package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawInput extends Entity {
	@JsonProperty("txid")
	private String txId;
	@JsonProperty("vout")
	private Integer vOut;
	private SignatureScript scriptSig;
	private String coinbase;
	private Long sequence;

	public RawInput(){}
	public RawInput(String txId, int vOut, SignatureScript scriptSig, String coinbase, long sequence){
		this.txId = txId;
		this.vOut = vOut;
		this.scriptSig = scriptSig;
		this.coinbase = coinbase;
		this.sequence = sequence;
	}
}