package com.xemplarsoft.libs.crypto.server.jsonrpc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRpcRequest<T> extends JsonRpcMessage {
	private String method;
	private List<T> params;

	public JsonRpcRequest(){}
	public JsonRpcRequest(String method, List<T> params){
		this.method = method;
		this.params = params;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public void setParams(List<T> params) {
		this.params = params;
	}

	public String getMethod() {
		return method;
	}
	public List<T> getParams() {
		return params;
	}
}