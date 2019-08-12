package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.server.domain.enums.ChainStatuses;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tip extends Entity {
	private Integer height;
	private String hash;
	@JsonProperty("branchlen")
	private Integer branchLen;
	private ChainStatuses status;

	public Tip(){}
	public Tip(int height, String hash, int branchLen, ChainStatuses status){
		this.height = height;
		this.hash = hash;
		this.branchLen = branchLen;
		this.status = status;
	}
}