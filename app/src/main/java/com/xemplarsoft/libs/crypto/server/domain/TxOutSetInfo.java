package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.common.Defaults;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TxOutSetInfo extends Entity {
	private Integer height;
	@JsonProperty("bestblock")
	private String bestBlock;
	private Long transactions;
	@JsonProperty("txouts")
	private Long txOuts;
	@JsonProperty("bytes_serialized")
	private Long bytesSerialized;
	@JsonProperty("hash_serialized")
	private String hashSerialized;
	@JsonProperty("total_amount")
	private BigDecimal totalAmount;


	public TxOutSetInfo(Integer height, String bestBlock, Long transactions, Long txOuts, 
			Long bytesSerialized, String hashSerialized, BigDecimal totalAmount) {
		setHeight(height);
		setBestBlock(bestBlock);
		setTransactions(transactions);
		setTxOuts(txOuts);
		setBytesSerialized(bytesSerialized);
		setHashSerialized(hashSerialized);
		setTotalAmount(totalAmount);
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public void setBestBlock(String bestBlock) {
		this.bestBlock = bestBlock;
	}
	public void setBytesSerialized(Long bytesSerialized) {
		this.bytesSerialized = bytesSerialized;
	}
	public void setHashSerialized(String hashSerialized) {
		this.hashSerialized = hashSerialized;
	}
	public void setTransactions(Long transactions) {
		this.transactions = transactions;
	}
	public void setTxOuts(Long txOuts) {
		this.txOuts = txOuts;
	}

    public Integer getHeight() {
        return height;
    }
    public Long getBytesSerialized() {
        return bytesSerialized;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public Long getTransactions() {
        return transactions;
    }
    public Long getTxOuts() {
        return txOuts;
    }
    public String getBestBlock() {
        return bestBlock;
    }
    public String getHashSerialized() {
        return hashSerialized;
    }
}