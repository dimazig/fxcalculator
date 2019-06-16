package fx.calculator;

public class Main {
    public static void main(String[] args) throws Exception {


        Initializer initializer = new Initializer("baseccy.properties", "rates.properties", "scales.properties");

        initializer.init();
        CmdProcessor cmdProcessor = initializer.getCmdProcessor();
        cmdProcessor.start();
    }
}
