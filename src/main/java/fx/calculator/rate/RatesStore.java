package fx.calculator.rate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RatesStore {

    private final Map<String, BigDecimal> rates = new HashMap<>();

    public BigDecimal getRate(String ccyPair) {
        return rates.get(ccyPair);
    }

    public void putRate(String ccyPair, BigDecimal rate) {
        rates.put(ccyPair, rate);
    }

}
