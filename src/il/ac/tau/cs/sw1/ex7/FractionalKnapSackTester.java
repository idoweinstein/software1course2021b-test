package il.ac.tau.cs.sw1.ex7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

public class FractionalKnapSackTester {
    @Test
    public void testFractionalKnapSack() {
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(10,60);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(20,100);
        FractionalKnapSack.Item s3 = new FractionalKnapSack.Item(30,120);
        FractionalKnapSack s = new FractionalKnapSack(50, Arrays.asList(s3,s1,s2));
        assertEquals("[{weight=10.0, value=60.0}, {weight=20.0, value=100.0}, {weight=20.0, value=120.0}]", s.greedyAlgorithm().toString());
    }

    @Test
    public void testEmptyFractionalKnapSack() {
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(10,60);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(20,100);
        FractionalKnapSack.Item s3 = new FractionalKnapSack.Item(30,120);
        FractionalKnapSack s = new FractionalKnapSack(0, Arrays.asList(s3,s1,s2));
        assertEquals("[]", s.greedyAlgorithm().toString());
    }

    @Test
    public void testNonFullFractionalKnapSack() {
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(10,60);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(20,100);
        FractionalKnapSack.Item s3 = new FractionalKnapSack.Item(30,120);
        FractionalKnapSack s = new FractionalKnapSack(100, Arrays.asList(s3,s1,s2));
        assertEquals("[{weight=10.0, value=60.0}, {weight=20.0, value=100.0}, {weight=30.0, value=120.0}]", s.greedyAlgorithm().toString());
    }

    @Test
    public void testFirstFractionalKnapSack() {
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(12,34.5);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(170,300);
        FractionalKnapSack s = new FractionalKnapSack(5, Arrays.asList(s1,s2));
        assertEquals("[{weight=5.0, value=34.5}]", s.greedyAlgorithm().toString());
    }

    @Test
    public void testTwiceFractionalKnapSack() {
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(12,34.5);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(170,300);
        FractionalKnapSack s = new FractionalKnapSack(5, Arrays.asList(s1,s2));
        assertEquals(s.greedyAlgorithm().toString(), s.greedyAlgorithm().toString());
    }

    @Ignore
    @Test
    public void testWrongSolutionFractionalKnapSack() {
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(12,34.5);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(170,300);
        FractionalKnapSack s = new FractionalKnapSack(200, Arrays.asList(s1,s2));
        assertFalse(s.solution(Arrays.asList(new FractionalKnapSack.Item(200,34.5))));
    }

    @Ignore
    @Test
    public void testOppositeOrderSolutionFractionalKnapSack() {
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(200,34.5);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(200,300);
        FractionalKnapSack s = new FractionalKnapSack(210, Arrays.asList(s1,s2));
        assertTrue(s.solution(Arrays.asList(new FractionalKnapSack.Item(10,300), new FractionalKnapSack.Item(200,34.5))));
        assertTrue(s.solution(Arrays.asList(new FractionalKnapSack.Item(200,300), new FractionalKnapSack.Item(10,34.5))));
    }
}
