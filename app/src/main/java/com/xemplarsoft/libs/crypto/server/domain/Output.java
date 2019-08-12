package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Output extends OutputOverview {
	private String address;
	private String account;
	private String scriptPubKey;
	private String redeemScript;
	private BigDecimal amount;
	private Integer confirmations;
	private Boolean spendable;

	public Output(){}
	public Output(String txId, Integer vOut, String scriptPubKey, String redeemScript) {
		super(txId, vOut);
		this.scriptPubKey = scriptPubKey;
		this.redeemScript = redeemScript;
	}

	public Output(String address, String account, String scriptPubKey, String redeemScript, 
			BigDecimal amount, Integer confirmations, Boolean spendable) {
		setAddress(address);
		setAccount(account);
		setScriptPubKey(scriptPubKey);
		setRedeemScript(redeemScript);
		setAmount(amount);
		setConfirmations(confirmations);
		setSpendable(spendable);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setTxId(String txId) {
		super.setTxId(txId);
	}
	public void setScriptPubKey(String scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}
	public void setRedeemScript(String redeemScript) {
		this.redeemScript = redeemScript;
	}
	public void setSpendable(Boolean spendable) {
		this.spendable = spendable;
	}
	public void setVOut(Integer vOut) {
		super.setVOut(vOut);
	}

    public String getAddress() {
        return address;
    }
    public String getAccount() {
        return account;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public Integer getConfirmations() {
        return confirmations;
    }
    public Boolean getSpendable() {
        return spendable;
    }
    public String getRedeemScript() {
        return redeemScript;
    }
    public String getScriptPubKey() {
        return scriptPubKey;
    }
}