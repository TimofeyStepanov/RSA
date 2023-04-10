package com.company.crypto.algorithm.impl;

import com.company.algebra.prime.PrimeCheckerType;
import com.company.crypto.algorithm.RSA;
import org.junit.jupiter.api.Test;


class RSAImplTest {
    RSA rsa = RSAImpl.getInstance(PrimeCheckerType.MILLER_RABIN, 0.99999999, 512);

    @Test
    void test() {

    }
}