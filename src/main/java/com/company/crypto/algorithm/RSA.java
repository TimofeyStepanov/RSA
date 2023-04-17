package com.company.crypto.algorithm;

import com.company.crypto.algorithm.exception.DangerOfHastadAttackException;
import com.company.crypto.db.DBForEncodedMessages;

import java.math.BigInteger;

public abstract class RSA {
    protected DBForEncodedMessages dataBase;

    protected RSA(DBForEncodedMessages dataBase) {
        this.dataBase = dataBase;
    }

    public abstract byte[] encode(byte[] array, BigInteger exponent, BigInteger modulo) throws DangerOfHastadAttackException;
    public abstract byte[] decode(byte[] array);

    public abstract void regenerateOpenKey();

    public  abstract BigInteger getExponent();
    public  abstract BigInteger getModulo();
}
