package com.company.algebra;

import java.math.BigInteger;

public interface RSAMath {
    int getLegendreSymbol(BigInteger a, BigInteger p);
    int getJacobiSymbol(BigInteger a, BigInteger p);
}