package fx.calculator.rate;

import fx.calculator.mapping.BaseCurrencyMapping;
import fx.calculator.mapping.ScaleMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FxConverterTest {

    private FxConverter fxConverter;
    private BaseCurrencyMapping baseCurrencyMapping;
    private RatesStore ratesStore;
    private ScaleMapping scaleMapping;

    @BeforeEach
    void setUp() {
        baseCurrencyMapping = new BaseCurrencyMapping();
        ratesStore = new RatesStore();
        scaleMapping = new ScaleMapping();
        fxConverter = new FxConverter(baseCurrencyMapping, ratesStore, scaleMapping);
    }

    @Test
    void shouldThrowOnRateNotFound() {
        assertThrows(RateNotFoundException.class, () -> {
            fxConverter.convert("EUR", "USD", new BigDecimal("123.45"));
        });
    }

    @Test
    void shouldConvertDirectly() throws RateNotFoundException {
        ratesStore.putRate("EURUSD", new BigDecimal("1.12"));
        BigDecimal result = fxConverter.convert("EUR", "USD", new BigDecimal("100"));

        assertThat(result).isEqualTo(new BigDecimal("112.00"));
    }

    @Test
    void shouldConvertInverted() throws RateNotFoundException {
        ratesStore.putRate("EURUSD", new BigDecimal("1.12"));
        BigDecimal result = fxConverter.convert("USD", "EUR", new BigDecimal("100"));

        assertThat(result).isEqualTo(new BigDecimal("89.29"));
    }

    @Test
    void shouldConvertWithCrossRateWithUSDBase() throws RateNotFoundException {

        ratesStore.putRate("EURUSD", new BigDecimal("1.12"));
        ratesStore.putRate("USDAUD", new BigDecimal("1.45"));
        BigDecimal result = fxConverter.convert("AUD", "EUR", new BigDecimal("100"));

        assertThat(result).isEqualTo(new BigDecimal("61.58"));
    }


    @Test
    void shouldConvertWithCrossRateWithViaEUR() throws RateNotFoundException {

        baseCurrencyMapping.putBaseCurrency("CZK", "NOK", "EUR");
        ratesStore.putRate("EURCZK", new BigDecimal("25.60"));
        ratesStore.putRate("EURNOK", new BigDecimal("9.80"));
        BigDecimal result = fxConverter.convert("CZK", "NOK", new BigDecimal("100"));

        assertThat(result).isEqualTo(new BigDecimal("38.28"));
    }


    @Test
    void shouldConvertWithScale() throws RateNotFoundException {
        scaleMapping.putScale("JPY", 0);

        ratesStore.putRate("USDJPY", new BigDecimal("108.56"));
        BigDecimal result = fxConverter.convert("USD", "JPY", new BigDecimal("10"));

        assertThat(result).isEqualTo(new BigDecimal("1086"));
    }
}