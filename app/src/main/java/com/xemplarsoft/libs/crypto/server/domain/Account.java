package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account extends Entity {

	@JsonProperty("involvesWatchonly")
	private Boolean involvesWatchOnly;
	private String account;
	private BigDecimal amount;
	private Integer confirmations;

	public Account(Boolean involvesWatchOnly, String account, BigDecimal amount, Integer confirmations) {
		setInvolvesWatchOnly(involvesWatchOnly);
		setAccount(account);
		setAmount(amount);
		setConfirmations(confirmations);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setInvolvesWatchOnly(Boolean involvesWatchOnly) {
		this.involvesWatchOnly = involvesWatchOnly;
	}

	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}