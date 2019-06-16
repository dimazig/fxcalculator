package fx.calculator.mapping;

import java.util.HashMap;
import java.util.Map;

public class BaseCurrencyMapping {

    private final String DEFAULT_BASE_CCY = "USD";

    private final Map<CurrencyPairKey, String> mapping = new HashMap<>();

    public String getBaseCurrency(String ccy1, String ccy2) {
        String baseCcy = mapping.get(new CurrencyPairKey(ccy1, ccy2));
        return baseCcy == null ? DEFAULT_BASE_CCY : baseCcy;
    }

    public void putBaseCurrency(String ccy1, String ccy2, String baseCcy) {
        mapping.put(new CurrencyPairKey(ccy1, ccy2), baseCcy);
    }
}
