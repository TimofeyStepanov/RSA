package com.company.crypto.algorithm.impl;

import com.company.algebra.prime.PrimeChecker;
import com.company.algebra.prime.PrimeCheckerFabric;
import com.company.algebra.prime.PrimeCheckerType;
import com.company.crypto.algorithm.RSA;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
public final class RSAImpl extends RSA {
    public static RSA getInstance(PrimeCheckerType type, double precision, int primeNumberLength) {
        final double minPrecision = 0.5;
        final double maxPrecision = 1.0;
        if (precision < minPrecision || precision >= maxPrecision) {
            throw new IllegalArgumentException("Wrong precision:" + precision);
        }

        // TODO
//        final int minLength = 12;
//        if (primeNumberLength < minLength) {
//            throw new IllegalArgumentException("Too small length of prime number:" + primeNumberLength);
//        }
        return new RSAImpl(type, precision, primeNumberLength);
    }

    private final OpenKeyGenerator openKeyGenerator;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;

    private RSAImpl(PrimeCheckerType type, double precision, int primeNumberLength) {
        this.openKeyGenerator = new OpenKeyGenerator(type, precision, primeNumberLength);
        this.openKeyGenerator.generateOpenKey();
        this.openKeyGenerator.generatePrivateKey();
    }

    @Override
    public byte[] encode(byte[] array, BigInteger exponent, BigInteger modulo) {
        Objects.requireNonNull(array);
        if (array.length == 0) {
            return new byte[0];
        }
        return doOperation(Arrays.copyOf(array, array.length), exponent, modulo);
    }

    @Override
    public byte[] decode(byte[] array) {
        Objects.requireNonNull(array);
        if (array.length == 0) {
            return new byte[0];
        }
        return doOperation(Arrays.copyOf(array, array.length), this.d, this.n);
    }

    private byte[] doOperation(byte[] array, BigInteger exponent, BigInteger modulo) {
        reverseArray(array);

        BigInteger message = new BigInteger(array);
        log.info("message:" + message);

        BigInteger newMessage = message.modPow(exponent, modulo);
        log.info("New message:" + newMessage);

        byte[] decodedMessageBytes = newMessage.toByteArray();
        reverseArray(decodedMessageBytes);

        return decodedMessageBytes;
    }

    @Override
    public void regenerateOpenKey() {
        this.openKeyGenerator.generateOpenKey();
        this.openKeyGenerator.generatePrivateKey();
    }

    @Override
    public BigInteger getExponent() {
        return this.e;
    }

    @Override
    public BigInteger getModulo() {
        return this.n;
    }

    private void reverseArray(byte[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            byte tmp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
        }
    }

    class OpenKeyGenerator {
        private final PrimeChecker primeChecker;
        private final double precision;
        private final int primeNumberLength;
        private final int minNumberOfDifferentBits;
        private final Random random = new Random();

        public OpenKeyGenerator(PrimeCheckerType type, double precision, int primeNumberLength) {
            this.primeChecker = PrimeCheckerFabric.getInstance(type);
            this.precision = precision;
            this.primeNumberLength = primeNumberLength;
            this.minNumberOfDifferentBits = primeNumberLength / 100 * 20;
        }


        public void generateOpenKey() {
            RSAImpl.this.p = generateP();
            RSAImpl.this.q = generateQ();
            RSAImpl.this.n = p.multiply(q);

            log.info("Generate p:" + RSAImpl.this.p);
            log.info("Generate q:" + RSAImpl.this.q);

            BigInteger eulerFunctionValue = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            RSAImpl.this.e = generateOpenExponent(eulerFunctionValue);
            log.info("Generate e:" + RSAImpl.this.e);
        }

        private BigInteger generateP() {
            return BigInteger.probablePrime(primeNumberLength, new Random());
//            BitSet pBitSet = new BitSet(primeNumberLength);
//            pBitSet.set(0, true);
//            pBitSet.set(primeNumberLength - 1, true);
//            pBitSet.set(primeNumberLength - 1 - minNumberOfDifferentBits, primeNumberLength - 1);
//            return generateRandomEvenDigitFromBitSet(pBitSet);
        }

        private BigInteger generateQ() {
            return BigInteger.probablePrime(primeNumberLength, new Random());
//            BitSet qBitSet = new BitSet(primeNumberLength);
//            qBitSet.set(0, true);
//            qBitSet.set(primeNumberLength - 1, true);
//            return generateRandomEvenDigitFromBitSet(qBitSet);
        }

        private BigInteger generateRandomEvenDigitFromBitSet(BitSet bitSet) {
            BigInteger randomEvenDigit;
            do {
                int i = 1;
                for (; i < primeNumberLength - 1 - minNumberOfDifferentBits; i++) {
                    bitSet.set(i, ThreadLocalRandom.current().nextBoolean());
                }

                byte[] bitSetByteArray = bitSet.toByteArray();
                reverseArray(bitSetByteArray);
                randomEvenDigit = new BigInteger(1, bitSetByteArray);
            } while (primeChecker.isPrime(randomEvenDigit, precision));
            return randomEvenDigit;
        }

        private BigInteger generateOpenExponent(BigInteger valueOfEulerFunction) {
            BigInteger randomDigit;
            BigInteger gcd;
            do {
                randomDigit = generateRandomDigit(BigInteger.ONE, valueOfEulerFunction);
                gcd = getGCD(randomDigit, valueOfEulerFunction);
            } while (!gcd.equals(BigInteger.ONE));
            return randomDigit;
        }

        private BigInteger getGCD(BigInteger firstDigit, BigInteger secondDigit) {
            if (secondDigit.equals(BigInteger.ZERO)) {
                return firstDigit;
            }
            return getGCD(secondDigit, firstDigit.mod(secondDigit));
        }

        private BigInteger generateRandomDigit(BigInteger minDigit, BigInteger maxDigit) {
            BigInteger randomDigit = new BigInteger(maxDigit.bitLength(), random);
            while (randomDigit.compareTo(minDigit) <= 0 || randomDigit.compareTo(maxDigit) >= 0) {
                randomDigit = new BigInteger(maxDigit.bitLength(), random);
            }
            return randomDigit;
        }


        public void generatePrivateKey() {
            BigInteger eulerFunctionValue = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            //RSAImpl.this.d = RSAImpl.this.e.modInverse(eulerFunctionValue);
            EEATuple eeaTuple = EEA(eulerFunctionValue, RSAImpl.this.e);
            if (RSAImpl.this.e.multiply(eeaTuple.x).mod(eulerFunctionValue).equals(BigInteger.ONE)) {
                RSAImpl.this.d = eeaTuple.x;
            } else {
                RSAImpl.this.d = eeaTuple.y;
            }
            log.info("Generate d:" + RSAImpl.this.d);
        }

        @AllArgsConstructor
        class EEATuple {
            BigInteger d;
            BigInteger x;
            BigInteger y;
        }

        EEATuple EEA(BigInteger a, BigInteger b) {
            if (b.equals(BigInteger.ZERO)) {
                return new EEATuple(a, BigInteger.ONE, BigInteger.ZERO);
            }
            EEATuple eeaTuple = EEA(b, a.mod(b));

            BigInteger d = eeaTuple.d;
            BigInteger x = eeaTuple.x;
            BigInteger y = eeaTuple.y;
            return new EEATuple(d, y, x.subtract(y.multiply(a.divide(b))));
        }
    }
}
