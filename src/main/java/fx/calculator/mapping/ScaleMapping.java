package fx.calculator.mapping;

import java.util.HashMap;
import java.util.Map;

public class ScaleMapping {
    private final Map<String, Integer> mapping = new HashMap<>();

    public Integer getScale(String ccy) {
        return mapping.getOrDefault(ccy, 2);
    }

    public void putScale(String ccy, Integer scale) {
        mapping.put(ccy, scale);
    }
}
