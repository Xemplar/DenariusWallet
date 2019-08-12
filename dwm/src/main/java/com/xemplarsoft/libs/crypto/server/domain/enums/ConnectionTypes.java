package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ConnectionTypes {

	FALSE("false"),
	INBOUND("inbound"),
	OUTBOUND("outbound");

	private final String name;
	ConnectionTypes(String name){
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static ConnectionTypes forName(String name) {
		if (name != null) {
			for (ConnectionTypes connectionType : ConnectionTypes.values()) {
				if (name.equals(connectionType.getName())) {
					return connectionType;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_CONNECTIONTYPE_UNSUPPORTED.getDescription());
	}

	public String toString(){
		return name;
	}
}