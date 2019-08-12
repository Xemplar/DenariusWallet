package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawTransactionOverview extends Entity {
	@JsonProperty("txid")
	private String txId;
	private Integer version;
	@JsonProperty("locktime")
	private Long lockTime;
	@JsonProperty("vin")
	private List<RawInput> vIn;
	@JsonProperty("vout")
	private List<RawOutput> vOut;

	public RawTransactionOverview(){}
	public RawTransactionOverview(String txId, int version, long lockTime, List<RawInput> vIn, List<RawOutput> vOut){
		this.txId = txId;
		this.version = version;
		this.lockTime = lockTime;
		this.vIn = vIn;
		this.vOut = vOut;
	}
}