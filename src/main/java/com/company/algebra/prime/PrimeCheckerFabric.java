package com.company.algebra.prime;

import com.company.algebra.prime.impl.FermatPrimeChecker;
import com.company.algebra.prime.impl.MillerRabinPrimeChecker;
import com.company.algebra.prime.impl.SolovayStrassenPrimeChecker;
import com.company.algebra.residue.impl.RSAMath;

import java.util.Map;

public final class PrimeCheckerFabric {
    private static Map<PrimeCheckerType, PrimeChecker> typeAndInstance = Map.of(
            PrimeCheckerType.FERMAT, new FermatPrimeChecker(),
            PrimeCheckerType.MILLER_RABIN, new MillerRabinPrimeChecker(),
            PrimeCheckerType.SOLOVEY_STRASSEN, new SolovayStrassenPrimeChecker(new RSAMath())
    );
    
    public static PrimeChecker getInstance(PrimeCheckerType type) {
        return typeAndInstance.get(type).clone();
    }
}
