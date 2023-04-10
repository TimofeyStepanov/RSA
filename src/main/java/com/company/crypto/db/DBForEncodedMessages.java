package com.company.crypto.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;

public interface DBForEncodedMessages {
    @EqualsAndHashCode
    @Data
    class OpenKey {
        private final BigInteger exponent;
        private final BigInteger modulo;
    }

    void save(BigInteger message, OpenKey openKey);
    public BigInteger getNumberOfMessageWithSameExponentAndDifferentModulo(BigInteger message, BigInteger exponent);
}
