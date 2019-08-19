package com.xemplarsoft.libs.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerPoint {
    public BigInteger x, y;

    public BigIntegerPoint(){
        this(new BigInteger("0", 10), new BigInteger("0", 10));
    }

    public BigIntegerPoint(BigInteger x, BigInteger y){
        this.x = x;
        this.y = y;
    }

    public BigIntegerPoint(int x, int y){
        this.x = new BigInteger(x + "", 10);
        this.y = new BigInteger(y + "", 10);
    }

    public BigIntegerPoint(BigIntegerPoint p){
        this.x = p.x;
        this.y = p.y;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public BigIntegerPoint getLocation(){
        return new BigIntegerPoint(x, y);
    }

    public void setLocation(BigInteger x, BigInteger y){
        this.x = x;
        this.y = y;
    }

    public void setLocation(BigDecimal x, BigDecimal y){
        this.x = x.toBigIntegerExact();
        this.y = y.toBigIntegerExact();
    }

    public void move(BigInteger x, BigInteger y){
        setLocation(x, y);
    }

    public void translate(BigInteger x, BigInteger y){
        this.x = this.x.add(x);
        this.y = this.y.add(y);
    }

    public boolean equals(BigIntegerPoint p){
        return p.x.equals(this.x) && p.y.equals(this.y);
    }

    public BigIntegerPoint scale(BigInteger i){
        BigIntegerPoint ret = new BigIntegerPoint();
        ret.x = this.x.multiply(i);
        ret.y = this.y.multiply(i);

        return ret;
    }

    public BigIntegerPoint scale(int i){
        return this.scale(new BigInteger(i + "", 10));
    }

    public String toString() {
        return getClass().getName() + "[x=" + x.toString() + ",y=" + y.toString() + "]";
    }
}
