package il.ac.tau.cs.sw1.ex9.riddles;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import il.ac.tau.cs.sw1.ex9.TesterUtil;
import il.ac.tau.cs.sw1.ex9.riddles.first.C1;
import il.ac.tau.cs.sw1.ex9.riddles.second.C2;
import il.ac.tau.cs.sw1.ex9.riddles.third.C3;
import il.ac.tau.cs.sw1.ex9.riddles.forth.C4;

public class RiddlesTester {
    private static final String[] text = 
        ("I got two strong arms "
        + "Blessings of babylon "
        + "Time to carry on and try "
        + "For sins and false alarms "
        + "So to america the brave "
        + "Wise men save "

        + "Near a tree by a river "
        + "There's a hole in the ground "
        + "Where an old man of aran "
        + "Goes around and around "
        + "And his mind is a beacon "
        + "In the veil of the night "
        + "For a strange kind of fashion "
        + "There's a wrong and a right "
        + "But he'll never, never fight over you").split(" ");

    private static final List<Consumer<String[]>> riddles = Arrays.asList(
        C1::main,
        C2::main,
        C3::main,
        C4::main
    );

    private static Stream<Arguments> provider() {
        List<Arguments> list = new ArrayList<>();
        for (int i = 0; i < text.length; ++i) {
            String[] array = Arrays.copyOf(text, i + 1);
            for (int j = 0; j < riddles.size(); ++j) {
                list.add(Arguments.of(riddles.get(j), array, String.format("C%d.main", j + 1)));
            }
        }
        return list.stream();
    }

    @ParameterizedTest
    @MethodSource("provider")
    public void testRiddles(Consumer<String[]> riddle, String[] args, String name) {
        String output = TesterUtil.testOutput(riddle, args, name);
        assertTrue("success!\n".equals(output.replaceAll("\r\n", "\n")), 
            String.format("Expected \"success!\", but instead %s(%s) printed %s",
            name,
            Arrays.toString(args),
            "".equals(output) ? "nothing" : String.format("the following output:%n%s", output))
        );
    }
}
