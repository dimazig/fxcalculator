package fx.calculator;

import fx.calculator.mapping.BaseCurrencyMapping;
import fx.calculator.mapping.ScaleMapping;
import fx.calculator.rate.FxConverter;
import fx.calculator.rate.RatesStore;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

public class Initializer {

    private final BaseCurrencyMapping baseCurrencyMapping = new BaseCurrencyMapping();
    private final RatesStore ratesStore = new RatesStore();
    private final ScaleMapping scaleMapping = new ScaleMapping();
    private FxConverter fxConverter;
    private CmdProcessor cmdProcessor;

    private final String baseCcyFile;
    private final String ratesFile;
    private final String scalesFile;

    public Initializer(String baseCcyFile, String ratesFile, String scalesFile) {
        this.baseCcyFile = baseCcyFile;
        this.ratesFile = ratesFile;
        this.scalesFile = scalesFile;
    }

    public void init() throws Exception {

        initBaseCurrencyMapping();
        initRates();
        initScales();

        fxConverter = new FxConverter(baseCurrencyMapping, ratesStore, scaleMapping);
        cmdProcessor = new CmdProcessor(fxConverter);
    }

    public FxConverter getFxConverter() {
        return fxConverter;
    }

    public CmdProcessor getCmdProcessor() {
        return cmdProcessor;
    }

    private void initBaseCurrencyMapping() throws IOException {
        try (FileReader reader = new FileReader(baseCcyFile)) {
            Properties properties = new Properties();
            properties.load(reader);

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String ccyPair = (String) entry.getKey();
                if (ccyPair.length() != 6 && !ccyPair.toUpperCase().equals(ccyPair)) {
                    throw new IllegalStateException("Base Currency loading failed. Currency pair should have 6 symbols and be upper case: " + ccyPair);
                }
                String baseCcy = (String) entry.getValue();
                if (baseCcy.length() != 3 && !baseCcy.toUpperCase().equals(baseCcy)) {
                    throw new IllegalStateException("Base Currency loading failed. Base currency should have 3 symbols and be upper case: " + baseCcy);
                }

                baseCurrencyMapping.putBaseCurrency(ccyPair.substring(0, 3), ccyPair.substring(3), baseCcy);

            }
        }
    }


    private void initRates() throws IOException {
        try (FileReader reader = new FileReader(ratesFile)) {
            Properties properties = new Properties();
            properties.load(reader);

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String ccyPair = (String) entry.getKey();
                if (ccyPair.length() != 6 && !ccyPair.toUpperCase().equals(ccyPair)) {
                    throw new IllegalStateException("Rate loading failed. Currency pair should have 6 symbols and be upper case: " + ccyPair);
                }

                try {
                    BigDecimal rate = new BigDecimal((String) entry.getValue());
                    ratesStore.putRate(ccyPair, rate);
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Rate loading failed. Rate is not a number: " + entry.getValue());
                }

            }
        }
    }

    private void initScales() throws IOException{
        try (FileReader reader = new FileReader(scalesFile)) {
            Properties properties = new Properties();
            properties.load(reader);

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String ccy = (String) entry.getKey();
                if (ccy.length() != 3 && !ccy.toUpperCase().equals(ccy)) {
                    throw new IllegalStateException("Scale loading failed. Currency should have 3 symbols and be upper case: " + ccy);
                }

                try {
                    Integer scale = new Integer((String) entry.getValue());
                    scaleMapping.putScale(ccy, scale);
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Rate loading failed. Rate is not a number: " + entry.getValue());
                }

            }
        }
    }


}
