package com.xemplarsoft.libs.crypto.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Rohan on 8/16/2017.
 */
public class Difficulty extends Entity{
    @JsonProperty("proof-of-work")
    private BigDecimal PoW;
    @JsonProperty("proof-of-stake")
    private BigDecimal PoS;
    @JsonProperty("search-interval")
    private Integer search;

    public Difficulty(){}
    public Difficulty(BigDecimal PoW, BigDecimal PoS, int search){
        this.PoS = PoS;
        this.PoW = PoW;
        this.search = search;
    }

    @JsonIgnore
    public BigDecimal getPoS() {
        return PoS;
    }
    @JsonIgnore
    public BigDecimal getPoW() {
        return PoW;
    }

    @JsonIgnore
    public void setPoS(BigDecimal poS) {
        PoS = poS;
    }
    @JsonIgnore
    public void setPoW(BigDecimal poW) {
        PoW = poW;
    }
}
