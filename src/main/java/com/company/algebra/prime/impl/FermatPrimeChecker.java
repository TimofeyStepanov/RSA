package com.company.algebra.prime.impl;

import com.company.algebra.prime.PrimeChecker;

import java.math.BigInteger;

public class FermatPrimeChecker extends PrimeChecker {
    @Override
    public boolean isPrime(BigInteger digitToCheck, double precision) {
        int n = this.getIterationNumber(precision);
        for (int i = 0; i < n; i++) {
            BigInteger randomDigit = super.getPositiveRandomDigit(digitToCheck);

            BigInteger gcd = gcd(digitToCheck, randomDigit);
            if (!gcd.equals(BigInteger.ONE)) {
                return false;
            }

            BigInteger degree = digitToCheck.subtract(BigInteger.ONE);
            if (!((randomDigit.modPow(degree, randomDigit)).equals(BigInteger.ONE))) {
                return false;
            }
        }
        return true;
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)) {
            return b;
        }
        return gcd(b.mod(a), a);
    }


}
