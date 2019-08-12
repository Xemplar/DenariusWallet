package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkAddress extends Entity {
	private String address;
	private Integer port;
	private Integer score;

	public NetworkAddress(){}
	public NetworkAddress(String address, int port, int score){
		this.address = address;
		this.port = port;
		this.score = score;
	}
}