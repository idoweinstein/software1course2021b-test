package riddles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import il.ac.tau.cs.sw1.ex9.TesterUtil;

public class RiddlesTester {
  private static final String EXPECTED = String.format(
      "success!%n"
    + "success!%n"
    + "success!%n"
    + "success!%n"
    + "success!%n"
    + "success!%n"
    + "success!%n");

  @SuppressWarnings("unchecked")
  @Test
  public void testTeachingAssistantsTest() {
    String output = TesterUtil.testOutput(Riddle::main, new String[]{ }, "Riddle.main");
    assertEquals(EXPECTED, output);
  }
}
