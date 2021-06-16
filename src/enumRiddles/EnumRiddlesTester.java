package enumRiddles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import il.ac.tau.cs.sw1.ex9.TesterUtil;

public class EnumRiddlesTester {
  private static final String EXPECTED_DAYTEST = 
    "MONDAY (1), next is TUESDAY\n"
    + "TUESDAY (2), next is WEDNESDAY\n"
    + "WEDNESDAY (3), next is THURSDAY\n"
    + "THURSDAY (4), next is FRIDAY\n"
    + "FRIDAY (5), next is SATURDAY\n"
    + "SATURDAY (6), next is SUNDAY\n"
    + "SUNDAY (7), next is MONDAY\n";
  private static Consumer<String[]> dayTestMain = DayTest::main;

  private static final String EXPECTED_TLGIHTTEST = 
    "RED: 30 seconds, next is GREEN\n"
    + "AMBER: 10 seconds, next is RED\n"
    + "GREEN: 30 seconds, next is AMBER\n";
  private static Consumer<String[]> tlightTestMain = TLightTest::main;
  
  @SuppressWarnings("unused")
  private static Stream<Arguments> testProvider() {
    return Stream.of(
      Arguments.of(dayTestMain, new String[] {}, "DayTest.main", EXPECTED_DAYTEST),
      Arguments.of(tlightTestMain, new String[] {}, "TLightTest.main", EXPECTED_TLGIHTTEST)
    );
  }

  @SuppressWarnings("unchecked")
  @ParameterizedTest
  @MethodSource({
    "testProvider"
  })
  public void testTeachingAssistantsTest(Consumer<String[]> function, String[] params, String name, String expected) {
    String output = TesterUtil.testOutput(function, params, name);
    assertEquals(expected, output);
  }
}
