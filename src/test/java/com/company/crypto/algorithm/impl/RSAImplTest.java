package com.company.crypto.algorithm.impl;

import com.company.algebra.prime.PrimeCheckerType;
import com.company.crypto.algorithm.RSA;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;


@Slf4j
class RSAImplTest {
    RSA rsa = RSAImpl.getInstance(PrimeCheckerType.MILLER_RABIN, 0.99999999, 32);

    @Test
    void testMock() {
        byte[] message = {5};
        log.info("Message to encode:" + message[0]);

        byte[] encoded = rsa.encode(message, rsa.getExponent(), rsa.getModulo());
        log.info("Encoded message:" + encoded[0]);

        byte[] decoded = rsa.decode(encoded);
        log.info("Decoded message:" + decoded[0]);

        assert (message[0] == decoded[0]);
    }

    @Test
    void testString() {
        String toEncode = "Hi!";

        byte[] message = toEncode.getBytes(StandardCharsets.UTF_8);
        byte[] encoded = rsa.encode(message, rsa.getExponent(), rsa.getModulo());
        byte[] decoded = rsa.decode(encoded);

        String decodedStr = new String(decoded);

        assert (toEncode.equals(decodedStr));
    }

    @Test
    void testString1() {
        String toEncode = "1201!";

        byte[] message = toEncode.getBytes(StandardCharsets.UTF_8);
        byte[] encoded = rsa.encode(message, rsa.getExponent(), rsa.getModulo());
        byte[] decoded = rsa.decode(encoded);

        String decodedStr = new String(decoded);

        assert (toEncode.equals(decodedStr));
    }
}