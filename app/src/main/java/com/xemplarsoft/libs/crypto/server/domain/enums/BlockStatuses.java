package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum BlockStatuses {

	ACCEPTED("null"),
	DUPLICATE("duplicate"),
	DUPLICATE_INVALID("duplicate-invalid"),
	DUPLICATE_INCONCLUSIVE("duplicate-inconclusive"),
	INCONCLUSIVE("inconclusive"),
	REJECTED("rejected");

	private final String name;

	BlockStatuses(String name){
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static BlockStatuses forName(String name) {
		if (name == null) {
			return ACCEPTED;
		} else {
			for (BlockStatuses blockStatus : BlockStatuses.values()) {
				if (name.equals(blockStatus.getName())) {
					return blockStatus;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_BLOCKSTATUS_UNSUPPORTED.getDescription());
	}

	public String toString(){
		return name;
	}
}