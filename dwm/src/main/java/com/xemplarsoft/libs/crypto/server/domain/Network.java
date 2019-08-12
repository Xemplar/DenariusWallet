package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xemplarsoft.libs.crypto.server.domain.enums.NetworkTypes;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Network extends Entity {
	private NetworkTypes name;
	private Boolean limited;
	private Boolean reachable;
	private String proxy;

	public Network(){}
	public Network(NetworkTypes name, boolean limited, boolean reachable, String proxy){
		this.name = name;
		this.limited = limited;
		this.reachable = reachable;
		this.proxy = proxy;
	}
}