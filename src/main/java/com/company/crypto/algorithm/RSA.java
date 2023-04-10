package com.company.crypto.algorithm;

import java.math.BigInteger;

public interface RSA {
    byte[] encode(byte[] array, BigInteger exponent, BigInteger modulo);
    byte[] decode(byte[] array);

    void regenerateOpenKey();

    BigInteger getExponent();
    BigInteger getModulo();
}
