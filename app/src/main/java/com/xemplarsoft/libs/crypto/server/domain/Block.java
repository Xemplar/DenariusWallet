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
public class Block extends Entity {
	private String hash;
	private Integer confirmations;
	private Integer size;
	private Integer height;
	private Integer version;
	@JsonProperty("merkleroot")
	private String merkleRoot;
	private List<Transaction> tx;
	private Long time;
	private Long nonce;
	private String bits;
	private BigDecimal difficulty;
    @JsonProperty("chaintrust")
    private String chainTrust;
    @JsonProperty("blocktrust")
    private String blockTrust;
	@JsonProperty("previousblockhash")
	private String previousBlockHash;
	@JsonProperty("nextblockhash")
	private String nextBlockHash;

    public Block(){}
	public Block(String hash, Integer confirmations, Integer size, Integer height, Integer version,
			String merkleRoot, List<Transaction> tx, Long time, Long nonce, String bits,
			BigDecimal difficulty, String chainTrust, String blockTrust, String previousBlockHash, String nextBlockHash) {
		setHash(hash);
		setConfirmations(confirmations);
		setSize(size);
		setHeight(height);
		setVersion(version);
		setMerkleRoot(merkleRoot);
		setTx(tx);
		setTime(time);
		setNonce(nonce);
		setBits(bits);
		setDifficulty(difficulty);
        setChainTrust(chainTrust);
        setBlockTrust(blockTrust);
		setPreviousBlockHash(previousBlockHash);
		setNextBlockHash(nextBlockHash);
	}

	public void setDifficulty(BigDecimal difficulty) {
		this.difficulty = difficulty.setScale(Defaults.DECIMAL_SCALE, Defaults.ROUNDING_MODE);
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}
	public void setBits(String bits) {
		this.bits = bits;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}
	public void setNonce(Long nonce) {
		this.nonce = nonce;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
    public void setChainTrust(String chainTrust) {
        this.chainTrust = chainTrust;
    }
    public void setBlockTrust(String blockTrust) {
        this.blockTrust = blockTrust;
    }
	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash;
	}
	public void setNextBlockHash(String nextBlockHash) {
		this.nextBlockHash = nextBlockHash;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public void setTx(List<Transaction> tx) {
		this.tx = tx;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getTime() {
		return time;
	}
    public Integer getConfirmations() {
        return confirmations;
    }
    public Integer getHeight() {
        return height;
    }
    public String getHash() {
        return hash;
    }
    public Integer getVersion() {
        return version;
    }
    public Integer getSize() {
        return size;
    }
    public BigDecimal getDifficulty() {
        return difficulty;
    }
    public List<Transaction> getTx() {
        return tx;
    }
    public Long getNonce() {
        return nonce;
    }
    public String getBits() {
        return bits;
    }
    public String getBlockTrust() {
        return blockTrust;
    }
    public String getChainTrust() {
        return chainTrust;
    }
    public String getMerkleRoot() {
        return merkleRoot;
    }
    public String getNextBlockHash() {
        return nextBlockHash;
    }
    public String getPreviousBlockHash() {
        return previousBlockHash;
    }
}