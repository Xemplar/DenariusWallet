package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawOutput extends Entity {
	private BigDecimal value;
	private Integer n;
	private PubKeyScript scriptPubKey;

	public RawOutput(){}
	public RawOutput(BigDecimal value, Integer n, PubKeyScript scriptPubKey) {
		setValue(value);
		setN(n);
		setScriptPubKey(scriptPubKey);
	}

	public void setValue(BigDecimal value) {
		this.value = value.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public void setScriptPubKey(PubKeyScript scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}
}