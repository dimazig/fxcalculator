package fx.calculator;

import fx.calculator.mapping.BaseCurrencyNotFoundException;
import fx.calculator.rate.FxConverter;
import fx.calculator.rate.RateNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CmdProcessor {

    private final Pattern pattern = Pattern.compile("^([A-Z]{3}) ([0-9]*\\.?[0-9]*) in ([A-Z]{3})$");

    private final FxConverter fxConverter;

    public CmdProcessor(FxConverter fxConverter) {
        this.fxConverter = fxConverter;
    }


    public void start() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        while (true) {

            prompt();
            String command = reader.readLine();
            if ("exit".equalsIgnoreCase(command) || "quit".equalsIgnoreCase(command)) {
                return;
            }

            Matcher matcher = pattern.matcher(command);
            if (!matcher.matches()) {
                System.out.println("Wrong input");
                continue;
            }

            String ccyFrom = matcher.group(1);
            BigDecimal amount = new BigDecimal(matcher.group(2));
            String ccyTo = matcher.group(3);

            try {
                BigDecimal result = fxConverter.convert(ccyFrom, ccyTo, amount);
                System.out.println(String.format("%1$s %2$s = %3$s %4$s", amount, ccyFrom, ccyTo, result));
            } catch (RateNotFoundException e) {
                System.out.println("Exchange rate not found for this currency pair");
            }


        }
    }

    private void prompt() {
        System.out.println("\nPlease input: <CCY> <AMOUNT> in <CCY>");
        System.out.print("%>");
    }


}
