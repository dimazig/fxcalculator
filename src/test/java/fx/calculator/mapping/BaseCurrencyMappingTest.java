package fx.calculator.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BaseCurrencyMappingTest {

    private BaseCurrencyMapping mapping;

    @BeforeEach
    void setUp() {
        mapping = new BaseCurrencyMapping();
    }

    @Test
    void shouldReturnUsdOnEmptyMapping() {
        String baseCurrency = mapping.getBaseCurrency("AUD", "EUR");
        assertThat(baseCurrency).isEqualTo("USD");
    }


    @Test
    void shouldReturnEur() {
        mapping.putBaseCurrency("KRW", "NOK", "EUR");
        String baseCurrency = mapping.getBaseCurrency("KRW", "NOK");
        assertThat(baseCurrency).isEqualTo("EUR");

        baseCurrency = mapping.getBaseCurrency("NOK", "KRW");
        assertThat(baseCurrency).isEqualTo("EUR");
    }
}