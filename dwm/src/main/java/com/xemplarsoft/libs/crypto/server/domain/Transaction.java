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
public class Transaction extends Entity {
	private BigDecimal amount;
	private BigDecimal fee;
	private Integer confirmations;
	private Boolean generated;
	@JsonProperty("blockhash")
	private String blockHash;
	@JsonProperty("blockindex")
	private Integer blockIndex;
	@JsonProperty("blocktime")
	private Long blockTime;
	@JsonProperty("txid")
	private String txId;
	@JsonProperty("walletconflicts")
	private List<String> walletConflicts;
	private Long time;
	@JsonProperty("timereceived")
	private Long timeReceived;
    @JsonProperty("n_x")
	private String comment;
	private String address;
	private List<PaymentOverview> details;
	private String hex;
	private List<TXOut> vout;
	private List<TXIn> vin;

	public Transaction(){}
	public Transaction(String txid){
		setTxId(txid);
	}
	public Transaction(BigDecimal amount, BigDecimal fee, Integer confirmations, Boolean generated, 
			String blockHash, Integer blockIndex, Long blockTime, String txId, 
			List<String> walletConflicts, Long time, Long timeReceived, String comment, String address,
			List<PaymentOverview> details, String hex, List<TXOut> vout, List<TXIn> vin) {
		setAmount(amount);
		setFee(fee);
		setConfirmations(confirmations);
		setGenerated(generated);
		setBlockHash(blockHash);
		setBlockIndex(blockIndex);
		setBlockTime(blockTime);
		setTxId(txId);
		setWalletConflicts(walletConflicts);
		setTime(time);
		setTimeReceived(timeReceived);
		setComment(comment);
		setAddress(address);
		setDetails(details);
		setHex(hex);
		setVout(vout);
		setVin(vin);
	}

	public void setAmount(BigDecimal amount) { this.amount = amount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE); }
	public void setFee(BigDecimal fee) {
		this.fee = fee.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public void setBlockIndex(Integer blockIndex) {
		this.blockIndex = blockIndex;
	}
	public void setBlockTime(Long blockTime) {
		this.blockTime = blockTime;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setDetails(List<PaymentOverview> details) {
		this.details = details;
	}
	public void setGenerated(Boolean generated) {
		this.generated = generated;
	}
	public void setHex(String hex) {
		this.hex = hex;
	}
	public void setTimeReceived(Long timeReceived) {
		this.timeReceived = timeReceived;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setWalletConflicts(List<String> walletConflicts) {
		this.walletConflicts = walletConflicts;
	}
	public void setVout(List<TXOut> vout) { this.vout = vout; }
	public void setVin(List<TXIn> vin) { this.vin = vin; }

    public BigDecimal getAmount() {
        return amount;
    }
    public BigDecimal getFee() {
        return fee;
    }
    public Long getTime() {
        return time;
    }
    public String getHex() {
        return hex;
    }
    public Boolean getGenerated() {
        return generated;
    }
    public Integer getBlockIndex() {
        return blockIndex;
    }
    public Integer getConfirmations() {
        return confirmations;
    }
    public List<PaymentOverview> getDetails() {
        return details;
    }
    public List<String> getWalletConflicts() {
        return walletConflicts;
    }
    public List<TXOut> getVout() { return vout; }
    public List<TXIn> getVin() { return vin; }
    public Long getBlockTime() {
        return blockTime;
    }
    public Long getTimeReceived() {
        return timeReceived;
    }
    public String getBlockHash() {
        return blockHash;
    }
    public String getComment() {
        return comment;
    }
    public String getAddress() {
        return address;
    }
    public String getTxId() {
        return txId;
    }
}