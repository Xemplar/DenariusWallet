package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum SigHashTypes {

	ALL("ALL"),
	NONE("NONE"),
	SINGLE("SINGLE"),
	ALL_ACP("ALL|ANYONECANPAY"),
	NONE_ACP("NONE|ANYONECANPAY"),
	SINGLE_ACP("SINGLE|ANYONECANPAY");
	
	private final String name;
	SigHashTypes(String name){
		this.name = name;
	}

	
	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static SigHashTypes forName(String name) {
		if (name != null) {
			for (SigHashTypes sigHashType : SigHashTypes.values()) {
				if (name.equals(sigHashType.getName())) {
					return sigHashType;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_SIGHASHTYPE_UNSUPPORTED.getDescription());
	}

	public String toString(){
		return name;
	}
}