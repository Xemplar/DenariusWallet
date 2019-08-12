package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ChainTypes {

	MAINNET("main"),
	TESTNET("test"),
	REGTEST("regtest");

	private final String name;
	ChainTypes(String name){
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static ChainTypes forName(String name) {
		if (name != null) {
			for (ChainTypes chainType : ChainTypes.values()) {
				if (name.equals(chainType.getName())) {
					return chainType;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_CHAINTYPE_UNSUPPORTED.getDescription());
	}

	public String toString(){
		return name;
	}
}