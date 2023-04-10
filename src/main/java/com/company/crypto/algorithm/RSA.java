package com.company.crypto.algorithm;

import com.company.algebra.prime.PrimeChecker;
import com.company.algebra.prime.PrimeCheckerFabric;
import com.company.algebra.prime.PrimeCheckerType;
import lombok.Data;

import java.math.BigInteger;

public abstract class RSA {
    @Data
    protected static class OpenKey {
        private BigInteger exponent;
        private BigInteger modulo;
    }

    @Data
    protected static class PrivateKey {
        private BigInteger p;
        private BigInteger q;
        private BigInteger n;
        private BigInteger d;
    }

    public abstract byte[] encode(byte[] array, BigInteger exponent, BigInteger modulo);
    public abstract byte[] decode(byte[] array);

    public abstract void regenerateOpenKey();

    public  abstract BigInteger getExponent();
    public  abstract BigInteger getModulo();
}
