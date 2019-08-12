package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment extends PaymentOverview {
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
    @JsonProperty("n_0")
	private String comment;
	private String to;
	@JsonProperty("otheraccount")
	private String otherAccount;

	public Payment(){}
	public Payment(int confirmations, boolean generated, String blockHash, int blockIndex, long blockTime, String txId,
				   List<String> walletConflicts, long time, long timeReceived, String comment, String to, String otherAccount) {
		this.confirmations = confirmations;
		this.generated = generated;
		this.blockHash = blockHash;
		this.blockIndex = blockIndex;
		this.blockTime = blockTime;
		this.txId = txId;
		this.walletConflicts = walletConflicts;
		this.time = time;
		this.timeReceived = timeReceived;
		this.comment = comment;
		this.to = to;
		this.otherAccount = otherAccount;
	}

    public String getTxId() {
        return txId;
    }
    public String getTo() {
        return to;
    }
    public String getBlockHash() {
        return blockHash;
    }
    public String getComment() {
        return comment;
    }
    public Integer getConfirmations() {
        return confirmations;
    }
    public Long getBlockTime() {
        return blockTime;
    }
    public List<String> getWalletConflicts() {
        return walletConflicts;
    }
    public Long getTimeReceived() {
        return timeReceived;
    }
    public Integer getBlockIndex() {
        return blockIndex;
    }
    public Boolean getGenerated() {
        return generated;
    }
    public Long getTime() {
        return time;
    }
    public String getOtherAccount() {
        return otherAccount;
    }
}