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
public class MemPoolTransaction extends Entity {

	private String txId;
	private Integer size;
	private BigDecimal fee;
	private Long time;
	private Integer height;
	@JsonProperty("startingpriority")
	private BigDecimal startingPriority;
	@JsonProperty("currentpriority")
	private BigDecimal currentPriority;
	private List<String> depends;


	public MemPoolTransaction(String txId, Integer size, BigDecimal fee, Long time, Integer height,
			BigDecimal startingPriority, BigDecimal currentPriority, List<String> depends) {
		setTxId(txId);
		setSize(size);
		setFee(fee);
		setTime(time);
		setHeight(height);
		setStartingPriority(startingPriority);
		setCurrentPriority(currentPriority);
		setDepends(depends);
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setStartingPriority(BigDecimal startingPriority) {
		this.startingPriority = startingPriority.setScale(Defaults.DECIMAL_SCALE, 
				Defaults.ROUNDING_MODE);
	}

	public void setCurrentPriority(BigDecimal currentPriority) {
		this.currentPriority = currentPriority.setScale(Defaults.DECIMAL_SCALE, 
				Defaults.ROUNDING_MODE);
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setDepends(List<String> depends) {
		this.depends = depends;
	}
}