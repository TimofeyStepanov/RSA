package com.company.algebra;

import com.company.algebra.residue.RSAMath;
import com.company.algebra.residue.impl.RSAMathImpl;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;


class RSAMathImplTest {
    RSAMath rsaMath = new RSAMathImpl();

    @Test
    void testLegendre() {
        int asnwer = rsaMath.getLegendreSymbol(BigInteger.valueOf(15), BigInteger.valueOf(17));
        assert (asnwer == 1);
    }

    @Test
    void testJacobi() {
        int answer;

        answer = rsaMath.getJacobiSymbol(BigInteger.valueOf(13), BigInteger.valueOf(13));
        assert (answer == 0);

        answer = rsaMath.getJacobiSymbol(BigInteger.valueOf(1), BigInteger.valueOf(1));
        assert (answer == 1);

        answer = rsaMath.getJacobiSymbol(BigInteger.valueOf(17), BigInteger.valueOf(23));
        assert (answer == -1);

        answer = rsaMath.getJacobiSymbol(BigInteger.valueOf(219), BigInteger.valueOf(383));
        assert (answer == 1);

        answer = rsaMath.getLegendreSymbol(BigInteger.valueOf(16), BigInteger.valueOf(59));
        assert (answer == 1);
    }
}