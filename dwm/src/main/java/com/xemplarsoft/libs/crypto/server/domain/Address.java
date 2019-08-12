package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address extends Entity {

	@JsonProperty("involvesWatchonly")
	private Boolean involvesWatchOnly;
	private String address;
	private String account;
	private BigDecimal amount;
	private Integer confirmations;
	@JsonProperty("txids")
	private List<String> txIds;


	public Address(Boolean involvesWatchOnly, String address, String account, BigDecimal amount, 
			Integer confirmations, List<String> txIds) {
		setInvolvesWatchOnly(involvesWatchOnly);
		setAddress(address);
		setAccount(account);
		setAmount(amount);
		setConfirmations(confirmations);
		setTxIds(txIds);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setInvolvesWatchOnly(Boolean involvesWatchOnly) {
		this.involvesWatchOnly = involvesWatchOnly;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}

	public void setTxIds(List<String> txIds) {
		this.txIds = txIds;
	}
}