package fx.calculator.rate;

import java.math.BigDecimal;

    public class RateConversionOperand {

    private BigDecimal multiplier = BigDecimal.ONE;
    private BigDecimal divisor = BigDecimal.ONE;

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public BigDecimal getDivisor() {
        return divisor;
    }


    public void appendToMultiplier(BigDecimal value) {
        multiplier = multiplier.multiply(value);
    }

    public void appendToDivisor(BigDecimal value) {
        divisor = divisor.multiply(value);
    }
}
