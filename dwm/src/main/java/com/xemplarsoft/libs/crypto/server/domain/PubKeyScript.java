package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xemplarsoft.libs.crypto.server.domain.enums.ScriptTypes;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubKeyScript extends SignatureScript {
	private Integer reqSigs;
	private ScriptTypes type;
	private List<String> addresses;

	public PubKeyScript(){}
	public PubKeyScript(int reqSigs, ScriptTypes type, List<String> addresses){
		this.reqSigs = reqSigs;
		this.type = type;
		this.addresses = addresses;
	}
}