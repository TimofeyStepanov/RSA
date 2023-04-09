package com.company.algebra.prime.impl;

import com.company.algebra.prime.PrimeChecker;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class FermatPrimeCheckerTest {
    PrimeChecker primeChecker = new FermatPrimeChecker();


    @Test
    void checkKramer() {
        //assert(fermatPrimeChecker.isPrime(BigInteger.valueOf(1729), 0.5));
    }

    @Test
    void checkNotPrime() {
        assert (!primeChecker.isPrime(BigInteger.valueOf(43040357), 0.9999));
        assert (!primeChecker.isPrime(BigInteger.valueOf(43040358), 0.9999));
        assert (!primeChecker.isPrime(BigInteger.valueOf(4), 0.9999));
    }

    @Test
    void checkPrime() {
        assert (primeChecker.isPrime(BigInteger.valueOf(997), 0.99999));
        assert (primeChecker.isPrime(BigInteger.valueOf(109), 0.99999));
        assert (primeChecker.isPrime(BigInteger.valueOf(6379), 0.99999));
        assert (primeChecker.isPrime(BigInteger.valueOf(3), 0.99999));
    }
}