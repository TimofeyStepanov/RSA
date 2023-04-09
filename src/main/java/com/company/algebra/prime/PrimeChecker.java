package com.company.algebra.prime;

import java.math.BigInteger;
import java.util.Random;

public abstract class PrimeChecker {
    private final Random random = new Random();


    public abstract boolean isPrime(BigInteger digit, double precision);


    protected int getIterationNumber(double precision) {
        int iterationNumber = -log2(1 - precision);
        return Math.max(iterationNumber, 1);
    }

    private int log2(double n) {
        return (int) (Math.log(n) / Math.log(2));
    }


    protected BigInteger getPositiveRandomDigit(BigInteger maxDigit) {
        BigInteger randomDigit = new BigInteger(maxDigit.bitLength(), random);
        while (randomDigit.compareTo(maxDigit) > 0) {
            randomDigit = new BigInteger(maxDigit.bitLength(), random);
        }
        return randomDigit;
    }
}
