package il.ac.tau.cs.sw1.Trivia;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TriviaTester {
    TriviaGUI gui = new TriviaGUI();
    Method[] mthd = TriviaGUI.class.getMethods();
    Field[] fld = TriviaGUI.class.getDeclaredFields();
    
}
