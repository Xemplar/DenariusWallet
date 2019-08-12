package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ChainStatuses {

	ACTIVE("active"),
	INVALID("invalid"),
	HEADERS_ONLY("headers-only"),
	VALID_HEADERS("valid-headers"),
	VALID_FORK("valid-fork"),
	UNKNOWN("unknown");

	private final String name;
	ChainStatuses(String name){
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static ChainStatuses forName(String name) {
		if (name != null) {
			for (ChainStatuses chainStatus : ChainStatuses.values()) {
				if (name.equals(chainStatus.getName())) {
					return chainStatus;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_CHAINSTATUS_UNSUPPORTED.getDescription());
	}

    public String toString(){
        return name;
    }
}