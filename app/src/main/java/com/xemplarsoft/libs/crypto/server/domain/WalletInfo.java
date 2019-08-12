package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletInfo extends Entity {

	@JsonProperty("walletversion")
	private Integer walletVersion;
	private BigDecimal balance;
	@JsonProperty("txcount")
	private Integer txCount;
	@JsonProperty("keypoololdest")
	private Long keypoolOldest;
	@JsonProperty("keypoolsize")
	private Integer keypoolSize;
	@JsonProperty("unlocked_until")
	private Long unlockedUntil;


	public WalletInfo(Integer walletVersion, BigDecimal balance, Integer txCount, Long keypoolOldest,
			Integer keypoolSize, Long unlockedUntil) {
		setWalletVersion(walletVersion);
		setBalance(balance);
		setTxCount(txCount);
		setKeypoolOldest(keypoolOldest);
		setKeypoolSize(keypoolSize);
		setUnlockedUntil(unlockedUntil);
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setWalletVersion(Integer walletVersion) {
		this.walletVersion = walletVersion;
	}

	public void setKeypoolOldest(Long keypoolOldest) {
		this.keypoolOldest = keypoolOldest;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
	}

	public void setUnlockedUntil(Long unlockedUntil) {
		this.unlockedUntil = unlockedUntil;
	}

	public void setKeypoolSize(Integer keypoolSize) {
		this.keypoolSize = keypoolSize;
	}
}