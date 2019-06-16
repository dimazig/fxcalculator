package fx.calculator.mapping;

import java.util.Objects;

public class CurrencyPairKey {


    private final String ccy1;
    private final String ccy2;

    public CurrencyPairKey(String ccy1, String ccy2) {
        this.ccy1 = ccy1;
        this.ccy2 = ccy2;
    }

    @Override
    public int hashCode() {
        return ccy1.hashCode() ^ ccy2.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPairKey that = (CurrencyPairKey) o;
        return Objects.equals(ccy1, that.ccy1) &&
                Objects.equals(ccy2, that.ccy2) || Objects.equals(ccy1, that.ccy2) &&
                Objects.equals(ccy2, that.ccy1);
    }
}
