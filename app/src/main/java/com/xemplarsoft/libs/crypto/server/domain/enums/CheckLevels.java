package com.xemplarsoft.libs.crypto.server.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xemplarsoft.libs.crypto.common.Errors;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum CheckLevels {
	LEVEL_0(0),
	LEVEL_1(1),
	LEVEL_2(2),
	LEVEL_3(3),
	LEVEL_4(4);

	private final Integer identifier;
	CheckLevels(int identifier){
		this.identifier = identifier;
	}


	@JsonValue
	public Integer getIdentifier() {
		return identifier;
	}

	@JsonCreator
	public static CheckLevels forIdentifier(Integer identifier) {
		if (identifier != null) {
			for (CheckLevels checkLevel : CheckLevels.values()) {
				if (identifier.equals(checkLevel.getIdentifier())) {
					return checkLevel;
				}
			}
		}
		throw new IllegalArgumentException(Errors.ARGS_CRYPTO_CHECKLEVEL_UNSUPPORTED.getDescription());
	}	
}