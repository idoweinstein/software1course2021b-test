package il.ac.tau.cs.sw1.ex7;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class CoinsTester {
    @Test
    public void testCoins() {
        assertEquals(Arrays.asList(1), (new Coins(1)).greedyAlgorithm());
        assertEquals(Arrays.asList(10,5,2,2), (new Coins(19)).greedyAlgorithm());
        assertEquals(Arrays.asList(10,10,10), (new Coins(30)).greedyAlgorithm());
        assertEquals(Arrays.asList(10,10,10,5,1), (new Coins(36)).greedyAlgorithm());
    }

    @Test
    public void testEmptyCoins() {
        assertEquals(Arrays.asList(), (new Coins(0)).greedyAlgorithm());
    }
}
