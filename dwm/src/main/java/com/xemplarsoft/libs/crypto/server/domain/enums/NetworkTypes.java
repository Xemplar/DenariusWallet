package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum NetworkTypes {

	IPV4("ipv4"),
	IPV6("ipv6"),
	ONION("onion");

	private final String name;
	NetworkTypes(String name){
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static NetworkTypes forName(String name) {
		if (name != null) {
			for (NetworkTypes networkType : NetworkTypes.values()) {
				if (name.equals(networkType.getName())) {
					return networkType;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_NETWORKTYPE_UNSUPPORTED.getDescription());
	}

	public String toString(){
		return name;
	}
}