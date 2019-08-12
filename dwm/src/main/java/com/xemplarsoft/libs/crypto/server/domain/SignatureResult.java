package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignatureResult extends Entity {
	private String hex;
	private Boolean complete;

	public SignatureResult(){}
	public SignatureResult(String hex, boolean complete){
		this.hex = hex;
		this.complete = complete;
	}
}