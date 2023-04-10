package com.company.crypto.algorithm;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

public abstract class RSA {
//    @Data
//    protected static class OpenKey {
//        private final BigInteger exponent;
//        private final BigInteger modulo;
//    }
//
//    @Data
//    protected static class PrivateKey {
//        private final BigInteger p;
//        private final BigInteger q;
//        private final BigInteger n;
//        private final BigInteger d;
//    }

    public abstract byte[] encode(byte[] array, BigInteger exponent, BigInteger modulo);
    public abstract byte[] decode(byte[] array);

    public abstract void regenerateOpenKey();

    public  abstract BigInteger getExponent();
    public  abstract BigInteger getModulo();
}
