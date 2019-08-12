package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Info extends Entity {
	private String version;
	@JsonProperty("protocolversion")
	private Integer protocolVersion;
	@JsonProperty("walletversion")
	private Integer walletVersion;
	private BigDecimal balance;
	private Integer blocks;
	@JsonProperty("timeoffset")
	private Integer timeOffset;
	private Integer connections;
	private String proxy;
	@JsonProperty("difficulty")
	private Difficulty difficulty;
	private Boolean testnet;
	@JsonProperty("keypoololdest")
	private Long keypoolOldest;
	@JsonProperty("keypoolsize")
	private Integer keypoolSize;
	@JsonProperty("unlocked_until")
	private Long unlockedUntil;
	@JsonProperty("paytxfee")
	private BigDecimal payTxFee;
	@JsonProperty("relayfee")
	private BigDecimal relayFee;
	private String errors;

    public Info(){}
	public Info(String version, Integer protocolVersion, Integer walletVersion, BigDecimal balance,
			Integer blocks, Integer timeOffset, Integer connections, String proxy,
			Difficulty difficulty, Boolean testnet, Long keypoolOldest, Integer keypoolSize,
			Long unlockedUntil, BigDecimal payTxFee, BigDecimal relayFee, String errors) {
		setVersion(version);
		setProtocolVersion(protocolVersion);
		setWalletVersion(walletVersion);
		setBalance(balance);
		setBlocks(blocks);
		setTimeOffset(timeOffset);
		setConnections(connections);
		setProxy(proxy);
		setDifficulty(difficulty);
		setTestnet(testnet);
		setKeypoolOldest(keypoolOldest);
		setKeypoolSize(keypoolSize);
		setUnlockedUntil(unlockedUntil);
		setPayTxFee(payTxFee);
		setRelayFee(relayFee);
		setErrors(errors);
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	public void setPayTxFee(BigDecimal payTxFee) {
		this.payTxFee = payTxFee.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setRelayFee(BigDecimal relayFee) {
		this.relayFee = relayFee.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setProtocolVersion(Integer protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	public void setBlocks(Integer blocks) {
		this.blocks = blocks;
	}
	public void setConnections(Integer connections) {
		this.connections = connections;
	}
	public void setKeypoolOldest(Long keypoolOldest) {
		this.keypoolOldest = keypoolOldest;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	public void setTestnet(Boolean testnet) {
		this.testnet = testnet;
	}
	public void setKeypoolSize(Integer keypoolSize) {
		this.keypoolSize = keypoolSize;
	}
	public void setTimeOffset(Integer timeOffset) {
		this.timeOffset = timeOffset;
	}
	public void setWalletVersion(Integer walletVersion) {
		this.walletVersion = walletVersion;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public void setUnlockedUntil(Long unlockedUntil) {
		this.unlockedUntil = unlockedUntil;
	}

    public BigDecimal getBalance() {
        return balance;
    }
    public Integer getProtocolVersion() {
        return protocolVersion;
    }
    public String getVersion() {
        return version;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }
    public Integer getBlocks() {
        return blocks;
    }
    public BigDecimal getPayTxFee() {
        return payTxFee;
    }
    public Boolean getTestnet() {
        return testnet;
    }
    public BigDecimal getRelayFee() {
        return relayFee;
    }
    public Integer getConnections() {
        return connections;
    }
    public Integer getKeypoolSize() {
        return keypoolSize;
    }
    public Integer getTimeOffset() {
        return timeOffset;
    }
    public Integer getWalletVersion() {
        return walletVersion;
    }
    public Long getKeypoolOldest() {
        return keypoolOldest;
    }
    public Long getUnlockedUntil() {
        return unlockedUntil;
    }
    public String getProxy() {
        return proxy;
    }
    public String getErrors() {
        return errors;
    }
}