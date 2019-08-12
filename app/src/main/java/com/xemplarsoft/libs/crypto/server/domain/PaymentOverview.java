package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.common.Defaults;
import com.xemplarsoft.libs.crypto.server.domain.enums.PaymentCategories;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentOverview extends Entity {

	@JsonProperty("involvesWatchonly")
	public Boolean involvesWatchOnly;
	private String account;
	private String address;
	private PaymentCategories category;
	private BigDecimal amount;
	@JsonProperty("vout")
	private Integer vOut;
	private BigDecimal fee;

	public PaymentOverview(){}

	public PaymentOverview(Boolean involvesWatchOnly, String account, String address,
						   PaymentCategories category, BigDecimal amount, Integer vOut, BigDecimal fee) {
		setInvolvesWatchOnly(involvesWatchOnly);
		setAccount(account);
		setAddress(address);
		setCategory(category);
		setAmount(amount);
		setVOut(vOut);
		setFee(fee);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setInvolvesWatchOnly(Boolean involvesWatchOnly) {
		this.involvesWatchOnly = involvesWatchOnly;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCategory(PaymentCategories category) {
		this.category = category;
	}
	public void setVOut(Integer vOut) {
		this.vOut = vOut;
	}

	public BigDecimal getFee() {
		return fee;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public Boolean getInvolvesWatchOnly() {
		return involvesWatchOnly;
	}
	public Integer getvOut() {
		return vOut;
	}
	public PaymentCategories getCategory() {
		return category;
	}
	public String getAccount() {
		return account;
	}
	public String getAddress() {
		return address;
	}
}