package il.ac.tau.cs.sw1.ex9;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

public class TesterUtil {
    public static String testOutput(Consumer<String[]> function, String[] args, String name) {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        final PrintStream originalErr = System.err;

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        function.accept(args);

        String errOutput = errContent.toString();
        assert "".equals(errOutput) : String.format("%s unexpectedly printed to System.err:%n%s", name, errContent);

        String output = outContent.toString();

        System.setOut(originalOut);
        System.setErr(originalErr);

        return output;
    }
    
}