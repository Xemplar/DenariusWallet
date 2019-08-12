package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawTransaction extends RawTransactionOverview {
	@JsonProperty("blockhash")
	private String blockHash;
	private Integer confirmations;
	private Long time;
	@JsonProperty("blocktime")
	private Long blockTime;
	private String hex;

	public RawTransaction(){}
	public RawTransaction(String blockHash, int confirmations, long time, long blockTime, String hex){
		this.blockHash = blockHash;
		this.confirmations = confirmations;
		this.time = time;
		this.blockHash = blockHash;
		this.blockTime = blockTime;
		this.hex = hex;
	}
}