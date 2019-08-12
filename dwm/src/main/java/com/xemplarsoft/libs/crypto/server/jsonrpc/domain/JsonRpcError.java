package com.xemplarsoft.libs.crypto.server.jsonrpc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRpcError {
	private Integer code;
	private String message;

	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}