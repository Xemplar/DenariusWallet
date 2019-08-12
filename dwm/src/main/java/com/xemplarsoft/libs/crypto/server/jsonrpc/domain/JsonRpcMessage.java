package com.xemplarsoft.libs.crypto.server.jsonrpc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class JsonRpcMessage {
	private String jsonrpc;
	private String id;

	public void setID(String id){
		this.id = id;
	}
	public void setJsonrpc(String json){
		this.jsonrpc = json;
	}

	public String getId() {
		return id;
	}
	public String getJsonrpc() {
		return jsonrpc;
	}
}