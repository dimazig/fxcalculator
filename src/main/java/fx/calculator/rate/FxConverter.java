package fx.calculator.rate;

import fx.calculator.mapping.BaseCurrencyMapping;
import fx.calculator.mapping.ScaleMapping;

import java.math.BigDecimal;
import java.util.Objects;

public class FxConverter {
    private final BaseCurrencyMapping baseCurrencyMapping;
    private final RatesStore ratesStore;
    private final ScaleMapping scaleMapping;

    public FxConverter(BaseCurrencyMapping baseCurrencyMapping, RatesStore ratesStore,
                       ScaleMapping scaleMapping) {
        this.baseCurrencyMapping = baseCurrencyMapping;
        this.ratesStore = ratesStore;
        this.scaleMapping = scaleMapping;
    }


    public BigDecimal convert(String ccyFrom, String ccyTo, BigDecimal amount) throws RateNotFoundException {
        if (Objects.equals(ccyFrom, ccyTo)) {
            return amount;
        }

        RateConversionOperand rateConversionOperand = new RateConversionOperand();
        populate(rateConversionOperand, ccyFrom, ccyTo);

        Integer scale = scaleMapping.getScale(ccyTo);
        return amount.multiply(rateConversionOperand.getMultiplier())
                .divide(rateConversionOperand.getDivisor(), scale, BigDecimal.ROUND_HALF_UP);


    }

    private void populate(RateConversionOperand rateConversionOperand, String ccyFrom, String ccyTo) throws RateNotFoundException {

        if (populateRate(rateConversionOperand, ccyFrom, ccyTo)) {
            return;
        }

        String baseCurrency = baseCurrencyMapping.getBaseCurrency(ccyFrom, ccyTo);
        if (Objects.equals(ccyFrom, baseCurrency) || Objects.equals(ccyTo, baseCurrency)) {
            throw new RateNotFoundException();
        }

        if (!populateRate(rateConversionOperand, ccyFrom, baseCurrency)) {
            populate(rateConversionOperand, ccyFrom, baseCurrency);
        }

        if (!populateRate(rateConversionOperand, baseCurrency, ccyTo)) {
            populate(rateConversionOperand, baseCurrency, ccyTo);
        }

    }

    private boolean populateRate(RateConversionOperand rateConversionOperand, String ccyFrom, String ccyTo) {
        BigDecimal rate = ratesStore.getRate(ccyFrom + ccyTo);
        if (rate != null) {
            rateConversionOperand.appendToMultiplier(rate);
            return true;
        }

        rate = ratesStore.getRate(ccyTo + ccyFrom);
        if (rate != null) {
            rateConversionOperand.appendToDivisor(rate);
            return true;
        }
        return false;
    }

}
