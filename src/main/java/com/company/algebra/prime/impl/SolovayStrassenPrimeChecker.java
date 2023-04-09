package com.company.algebra.prime.impl;

import com.company.algebra.prime.PrimeChecker;
import com.company.algebra.residue.RSAMath;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@RequiredArgsConstructor
public class SolovayStrassenPrimeChecker extends PrimeChecker {
    private final RSAMath rsaMath;

    @Override
    protected boolean isNotPrime(BigInteger digitToCheck, double precision) {
        BigInteger randomDigit = super.getPositiveRandomDigit(BigInteger.TWO, digitToCheck.subtract(BigInteger.ONE));

        BigInteger gcd = super.gcd(digitToCheck, randomDigit);
        if (!gcd.equals(BigInteger.ONE)) {
            return true;
        }

        BigInteger degree = digitToCheck.subtract(BigInteger.ONE).divide(BigInteger.TWO);
        BigInteger raisedToADegreeModulo = randomDigit.modPow(degree, digitToCheck);

        BigInteger jacobiSymbol = BigInteger.valueOf(rsaMath.getJacobiSymbol(randomDigit, digitToCheck));
        BigInteger jacobiSymbolForCompare = jacobiSymbol.add(digitToCheck).mod(digitToCheck);
        return !raisedToADegreeModulo.equals(jacobiSymbolForCompare);
    }
}
