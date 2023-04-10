package com.company.crypto.algorithm.impl;

import com.company.algebra.prime.PrimeChecker;
import com.company.algebra.prime.PrimeCheckerFabric;
import com.company.algebra.prime.PrimeCheckerType;
import com.company.crypto.algorithm.RSA;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
public final class RSAImpl extends RSA {
    public static RSA getInstance(PrimeCheckerType type, double precision, int primeNumberLength) {
        final double minPrecision = 0.5;
        final double maxPrecision = 1.0;
        if (precision < minPrecision || precision >= maxPrecision) {
            throw new IllegalArgumentException("Wrong precision:" + precision);
        }

        final int minLength = 512;
        if (primeNumberLength < minLength) {
            throw new IllegalArgumentException("Too small length of prime number:" + primeNumberLength);
        }
        return new RSAImpl(type, precision, primeNumberLength);
    }

    private final OpenKeyGenerator openKeyGenerator;
    private OpenKey openKey;
    private PrivateKey privateKey;

    private RSAImpl(PrimeCheckerType type, double precision, int primeNumberLength) {
        this.openKeyGenerator = new OpenKeyGenerator(type, precision, primeNumberLength);
        this.openKey = this.openKeyGenerator.generateOpenKey();
    }

    @Override
    public byte[] encode(byte[] array, BigInteger exponent, BigInteger modulo) {
        Objects.requireNonNull(array);
        if (array.length == 0) {
            return new byte[0];
        }

        BigInteger message = new BigInteger(array);

        return new byte[0];
    }

    @Override
    public byte[] decode(byte[] array) {
        Objects.requireNonNull(array);
        if (array.length == 0) {
            return new byte[0];
        }

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
        private static final int MIN_NUMBER_OF_DIFFERENT_BITS = 256;

        private final PrimeChecker primeChecker;
        private final double precision;
        private final int primeNumberLength;

        public OpenKeyGenerator(PrimeCheckerType type, double precision, int primeNumberLength) {
            this.primeChecker = PrimeCheckerFabric.getInstance(type);
            this.precision = precision;
            this.primeNumberLength = primeNumberLength;
        }

        public OpenKey generateOpenKey() {
            BigInteger p = generateP();
            BigInteger q = generateQ();

            log.info("Generate p:" + p);
            log.info("Generate q:" + q);

            BigInteger n = p.multiply(q);

            return new OpenKey();
        }

        private BigInteger generateP() {
            BitSet pBitSet = new BitSet(primeNumberLength);
            pBitSet.set(0, true);
            pBitSet.set(primeNumberLength - 1, true);
            pBitSet.set(primeNumberLength - 1 - MIN_NUMBER_OF_DIFFERENT_BITS, primeNumberLength - 1);
            return generateRandomEvenDigit(pBitSet);
        }

        private BigInteger generateQ() {
            BitSet qBitSet = new BitSet(primeNumberLength);
            qBitSet.set(0, true);
            qBitSet.set(primeNumberLength - 1, true);
            return generateRandomEvenDigit(qBitSet);
        }

        private BigInteger generateRandomEvenDigit(BitSet bitSet) {
            BigInteger randomEvenDigit;
            do {
                int i = 1;
                for (; i < primeNumberLength - 1 - MIN_NUMBER_OF_DIFFERENT_BITS; i++) {
                    bitSet.set(i, ThreadLocalRandom.current().nextBoolean());
                }

                byte[] bitSetByteArray = bitSet.toByteArray();
                reverseArray(bitSetByteArray);
                randomEvenDigit = new BigInteger(1, bitSetByteArray);
            } while (primeChecker.isPrime(randomEvenDigit, precision));
            return randomEvenDigit;
        }

        private void reverseArray(byte[] array) {
            for (int i = 0; i < array.length / 2; i++) {
                byte tmp = array[i];
                array[i] = array[array.length - 1 - i];
                array[array.length - 1 - i] = tmp;
            }
        }
    }
}
