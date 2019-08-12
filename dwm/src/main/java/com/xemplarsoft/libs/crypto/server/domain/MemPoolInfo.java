package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemPoolInfo extends Entity {
	private Integer size;
	private Long bytes;

	public MemPoolInfo(){}
	public MemPoolInfo(int size, long bytes){
		this.size = size;
		this.bytes = bytes;
	}
}