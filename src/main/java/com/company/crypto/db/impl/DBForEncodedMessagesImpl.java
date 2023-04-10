package com.company.crypto.db.impl;

import com.company.crypto.db.DBForEncodedMessages;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DBForEncodedMessagesImpl implements DBForEncodedMessages {
    private final Map<BigInteger, Map<BigInteger, Set<BigInteger>>> messageAndItsExponentAndModules = new HashMap<>();

    @Override
    public void save(BigInteger message, OpenKey openKey) {
        messageAndItsExponentAndModules.putIfAbsent(message, new HashMap<>());
        Map<BigInteger, Set<BigInteger>> exponentAndModules = messageAndItsExponentAndModules.get(message);

        exponentAndModules.putIfAbsent(openKey.getExponent(), new HashSet<>());
        Set<BigInteger> modules = exponentAndModules.get(openKey.getExponent());

        modules.add(openKey.getModulo());
    }

    @Override
    public BigInteger getNumberOfMessageWithSameExponentAndDifferentModulo(BigInteger message, BigInteger exponent) {
        if (!messageAndItsExponentAndModules.containsKey(message)) {
            return BigInteger.ZERO;
        }

        Map<BigInteger, Set<BigInteger>> exponentAndModules = messageAndItsExponentAndModules.get(message);
        if (!exponentAndModules.containsKey(exponent)) {
            return BigInteger.ZERO;
        }

        Set<BigInteger> modules = exponentAndModules.get(exponent);
        return BigInteger.valueOf(modules.size());
    }
}
