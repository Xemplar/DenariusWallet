package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum ScriptTypes {
	
	PUB_KEY("pubkey"),
	PUB_KEY_HASH("pubkeyhash"),
	SCRIPT_HASH("scripthash"),
	MULTISIG("multisig"),
	NULL_DATA("nulldata"),
	NONSTANDARD("nonstandard");
	
	private final String name;
	ScriptTypes(String name){
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}	

	@JsonCreator
	public static ScriptTypes forName(String name) {
		if (name != null) {
			for (ScriptTypes scriptType : ScriptTypes.values()) {
				if (name.equals(scriptType.getName())) {
					return scriptType;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_SCRIPTTYPE_UNSUPPORTED.getDescription());
	}

	public String toString(){
		return name;
	}
}