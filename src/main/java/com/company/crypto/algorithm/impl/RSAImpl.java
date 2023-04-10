package com.company.crypto.algorithm.impl;

import com.company.algebra.prime.PrimeChecker;
import com.company.algebra.prime.PrimeCheckerFabric;
import com.company.algebra.prime.PrimeCheckerType;
import com.company.crypto.algorithm.RSA;

import java.math.BigInteger;

public final class RSAImpl extends RSA {
    public static RSA getInstance(PrimeCheckerType type, double precision, int primeNumberLength) {
        final double minPrecision = 0.5;
        final double maxPrecision = 1.0;
        if (precision < minPrecision || precision >= maxPrecision) {
            throw new IllegalArgumentException("Wrong precision:" + precision);
        }

        final int minLength = 1024;
        if (primeNumberLength < minLength) {
            throw new IllegalArgumentException("Too small length of prime number:" + primeNumberLength);
        }
        return new RSAImpl(type, precision, primeNumberLength);
    }

    private final OpenKeyGenerator openKeyGenerator;
    private OpenKey openKey;

    private RSAImpl(PrimeCheckerType type, double precision, int primeNumberLength) {
        this.openKeyGenerator = new OpenKeyGenerator(type, precision, primeNumberLength);
        this.openKey = this.openKeyGenerator.generateOpenKey();
    }

    @Override
    public byte[] encode(byte[] array, BigInteger exponent, BigInteger modulo) {
        return new byte[0];
    }

    @Override
    public byte[] decode(byte[] array) {
        return new byte[0];
    }

    @Override
    public void regenerateOpenKey() {
        this.openKey = this.openKeyGenerator.generateOpenKey();
    }

    @Override
    public BigInteger getExponent() {
        return this.openKey.getExponent();
    }

    @Override
    public BigInteger getModulo() {
        return this.openKey.getModulo();
    }

    static class OpenKeyGenerator {
        private final PrimeChecker primeChecker;
        private final double precision;
        private final int primeNumberLength;

        public OpenKeyGenerator(PrimeCheckerType type, double precision, int primeNumberLength) {
            this.primeChecker = PrimeCheckerFabric.getInstance(type);
            this.precision = precision;
            this.primeNumberLength = primeNumberLength;
        }

        public OpenKey generateOpenKey() {
            return new OpenKey();
        }
    }
}
