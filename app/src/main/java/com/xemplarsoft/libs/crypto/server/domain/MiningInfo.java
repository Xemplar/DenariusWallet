package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xemplarsoft.libs.crypto.server.domain.enums.ChainTypes;

import java.math.BigInteger;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiningInfo extends Entity {
	private Integer blocks;
	@JsonProperty("currentblocksize")
	private Integer currentBlockSize;
	@JsonProperty("currentblocktx")
	private Integer currentBlockTx;
	private Difficulty difficulty;
	private String errors;
	@JsonProperty("genproclimit")
	private Integer genProcLimit;
	@JsonProperty("networkhashps")
	private BigInteger networkHashPs;
	@JsonProperty("pooledtx")
	private Integer pooledTx;
	private Boolean testnet;
	private ChainTypes chain;
	private Boolean generate;
	@JsonProperty("hashespersec")
	private Long hashesPerSec;

	public MiningInfo(){}
	public MiningInfo(Integer blocks, Integer currentBlockSize, Integer currentBlockTx, 
			Difficulty difficulty, String errors, Integer genProcLimit, BigInteger networkHashPs,
			Integer pooledTx, Boolean testnet, ChainTypes chain, Boolean generate, 
			Long hashesPerSec) {
		setBlocks(blocks);
		setCurrentBlockSize(currentBlockSize);
		setCurrentBlockTx(currentBlockTx);
		setDifficulty(difficulty);
		setErrors(errors);
		setGenProcLimit(genProcLimit);
		setNetworkHashPs(networkHashPs);
		setPooledTx(pooledTx);
		setTestnet(testnet);
		setChain(chain);
		setGenerate(generate);
		setHashesPerSec(hashesPerSec);
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	public void setBlocks(Integer blocks) {
		this.blocks = blocks;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public void setCurrentBlockSize(Integer currentBlockSize) {
		this.currentBlockSize = currentBlockSize;
	}
	public void setTestnet(Boolean testnet) {
		this.testnet = testnet;
	}
	public void setCurrentBlockTx(Integer currentBlockTx) {
		this.currentBlockTx = currentBlockTx;
	}
	public void setGenProcLimit(Integer genProcLimit) {
		this.genProcLimit = genProcLimit;
	}
	public void setChain(ChainTypes chain) {
		this.chain = chain;
	}
	public void setGenerate(Boolean generate) {
		this.generate = generate;
	}
	public void setHashesPerSec(Long hashesPerSec) {
		this.hashesPerSec = hashesPerSec;
	}
	public void setNetworkHashPs(BigInteger networkHashPs) {
		this.networkHashPs = networkHashPs;
	}
	public void setPooledTx(Integer pooledTx) {
		this.pooledTx = pooledTx;
	}

	public Integer getBlocks() {
		return blocks;
	}
	public Difficulty getDifficulty() {
		return difficulty;
	}
	public String getErrors() {
		return errors;
	}
	public Integer getCurrentBlockSize() {
		return currentBlockSize;
	}
	public Integer getCurrentBlockTx() {
		return currentBlockTx;
	}
	public Boolean getTestnet() {
		return testnet;
	}
	public BigInteger getNetworkHashPs() {
		return networkHashPs;
	}
	public Boolean getGenerate() {
		return generate;
	}
	public ChainTypes getChain() {
		return chain;
	}
	public Integer getGenProcLimit() {
		return genProcLimit;
	}
	public Integer getPooledTx() {
		return pooledTx;
	}
	public Long getHashesPerSec() {
		return hashesPerSec;
	}
}