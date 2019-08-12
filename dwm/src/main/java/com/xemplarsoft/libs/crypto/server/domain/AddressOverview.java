package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xemplarsoft.libs.crypto.common.Defaults;
import com.xemplarsoft.libs.crypto.server.jsonrpc.deserialization.AddressOverviewDeserializer;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = AddressOverviewDeserializer.class)
public class AddressOverview extends Entity {

	private String address;
	private BigDecimal balance;
	private String account;

	public AddressOverview() {}
	public AddressOverview(String address, BigDecimal balance, String account) {
		setAddress(address);
		setBalance(balance);
		setAccount(account);
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}