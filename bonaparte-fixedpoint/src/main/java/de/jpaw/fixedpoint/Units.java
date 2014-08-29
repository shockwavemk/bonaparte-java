package de.jpaw.fixedpoint;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Units extends FixedPointBase {
    private static final long serialVersionUID = -3073254135663195283L;
    public static final int DECIMALS = 0;
    public static final long UNIT_MANTISSA = 1;
    public static final Units ZERO = new Units(0);
    public static final Units ONE = new Units(UNIT_MANTISSA);
    
    public Units(long mantissa) {
        super(mantissa);
    }

    public static Units of(long mantissa) {
        return ZERO.newInstanceOf(mantissa);
    }

    // This is certainly not be the most efficient implementation, as it involves the construction of up to 2 new BigDecimals
    // TODO: replace it by a zero GC version
    public static Units of(BigDecimal number) {
        return of(number.setScale(DECIMALS, RoundingMode.UNNECESSARY).longValue());
    }

    @Override
    public Units newInstanceOf(long mantissa) {
        // caching checks...
        if (mantissa == 0)
            return ZERO;
        if (mantissa == UNIT_MANTISSA)
            return ONE;
        if (mantissa == getMantissa())
            return this;
        return new Units(mantissa);
    }

    @Override
    public int getDecimals() {
        return DECIMALS;
    }

    @Override
    public Units getZero() {
        return ZERO;
    }

    @Override
    public Units getUnit() {
        return ONE;
    }

    @Override
    public long getUnitAsLong() {
        return UNIT_MANTISSA;
    }
}
