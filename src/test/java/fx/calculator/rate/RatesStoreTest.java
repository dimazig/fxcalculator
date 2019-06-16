package fx.calculator.rate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


class RatesStoreTest {


    private RatesStore ratesStore;

    @BeforeEach
    void setUp() {
        ratesStore = new RatesStore();
    }

    @Test
    void shouldReturnRate() {
        ratesStore.putRate("AUDUSD", new BigDecimal("123.45"));
        BigDecimal rate = ratesStore.getRate("AUDUSD");

        assertThat(rate).isEqualTo(new BigDecimal("123.45"));
    }

    @Test
    void shoudReturnNull() {
        ratesStore.putRate("AUDUSD", new BigDecimal("123.45"));
        BigDecimal rate = ratesStore.getRate("USDSAUD");

        assertThat(rate).isNull();
    }
}