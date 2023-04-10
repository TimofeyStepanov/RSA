package com.company;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.BitSet;

@Slf4j
public class Main {
    public static void main(String[] args) {
        BigInteger bigInteger = BigInteger.valueOf(0);
        bigInteger = bigInteger.setBit(0);
        bigInteger = bigInteger.setBit(9);
        log.info(bigInteger.toString());

//        BitSet bitSet = new BitSet(100);
//        bitSet.set(0, true);
//        bitSet.set(9, true);
//        BigInteger bigInteger1 = new BigInteger(1, reverse(bitSet.toByteArray()));
//        log.info(bigInteger1.toString());


    }

//    private static byte[] reverse(byte[] array) {
//        for (int i = 0; i < array.length / 2; i++) {
//            byte tmp = array[i];
//            array[i] = array[array.length - 1 - i];
//            array[array.length - 1 - i] = tmp;
//        }
//        return array;
//    }
}
