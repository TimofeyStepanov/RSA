package com.company.crypto.algorithm.impl;

import com.company.algebra.prime.PrimeCheckerType;
import com.company.crypto.algorithm.RSA;
import com.company.crypto.algorithm.exception.DangerOfHastadAttackException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
class RSAImplTest {
    RSA rsa = RSAImpl.getInstance(PrimeCheckerType.SOLOVEY_STRASSEN, 0.99999999, 1024);

    @Test
    void testMock() throws DangerOfHastadAttackException {
        byte[] message = {5};
        for (int i = 0; i < 100; i++) {
            log.info("Message to encode:" + message[0]);

            byte[] encoded = rsa.encode(message, rsa.getExponent(), rsa.getModulo());
            log.info("Encoded message:" + encoded[0]);

            byte[] decoded = rsa.decode(encoded);
            log.info("Decoded message:" + decoded[0]);

            if (message[0] != decoded[0]) {
                assert (false);
                return;
            }
        }
        assert (true);
    }

    @Test
    void testString() throws DangerOfHastadAttackException {
        String toEncode = "Hello world!";

        for (int i = 0; i < 1000; i++) {
            byte[] message = toEncode.getBytes(StandardCharsets.UTF_8);
            byte[] encoded = rsa.encode(message, rsa.getExponent(), rsa.getModulo());
            byte[] decoded = rsa.decode(encoded);

            String decodedStr = new String(decoded);

            if (!toEncode.equals(decodedStr)) {
                assert (false);
                return;
            }
        }
        assert (true);
    }

    @Test
    void testString1() throws DangerOfHastadAttackException {
        String toEncode = "My name is Timofey Stepanov. I'm 20 years old!";

        for (int i = 0; i < 1000; i++) {
            byte[] message = toEncode.getBytes(StandardCharsets.UTF_8);
            byte[] encoded = rsa.encode(message, rsa.getExponent(), rsa.getModulo());
            byte[] decoded = rsa.decode(encoded);

            String decodedStr = new String(decoded);

            if (!toEncode.equals(decodedStr)) {
                assert (false);
                return;
            }
        }
        assert (true);
    }

    @Test
    void testRandomStr() throws DangerOfHastadAttackException {
        // последний байт не может равняться нулю
        for (int i = 0; i < 1000; i++) {
            if (i % 1000 == 0) {
                rsa.regenerateOpenKey();
            }

            int randomDigit = Math.abs(ThreadLocalRandom.current().nextInt()) % 32;
            byte[] message = new byte[randomDigit];
            for (int j = 0; j < message.length; j++) {
                byte randomByte = (byte) ThreadLocalRandom.current().nextInt();
                if (randomByte == 0) {
                    randomByte++;
                }
                message[j] = randomByte;
            }

            byte[] encoded = rsa.encode(message, rsa.getExponent(), rsa.getModulo());
            byte[] decoded = rsa.decode(encoded);

            if (!Arrays.equals(message, decoded)) {
                assert (false);
                return;
            }
        }
        assert (true);
    }
}