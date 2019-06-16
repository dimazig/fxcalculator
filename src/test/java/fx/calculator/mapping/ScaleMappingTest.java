package fx.calculator.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScaleMappingTest {

    private ScaleMapping scaleMapping;

    @BeforeEach
    void setUp() {
        scaleMapping = new ScaleMapping();
    }

    @Test
    void shouldReturn2OnEmptyMapping() {
        Integer scale = scaleMapping.getScale("USD");
        assertThat(scale).isEqualTo(2);
    }

    @Test
    void shouldReturn0() {
        scaleMapping.putScale("JPY", 0);
        Integer scale = scaleMapping.getScale("JPY");
        assertThat(scale).isEqualTo(0);
    }
}