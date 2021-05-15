package il.ac.tau.cs.sw1.ex8.histogram;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

public class HistogramTester {
    @Test
    public void testAddGetItem() {
        /* Add String value one time */
        final String stringValue = "Chrysalis";
        HashMapHistogram<String> stringHist = new HashMapHistogram<>();
        assertEquals(0, stringHist.getCountForItem(stringValue));
        stringHist.addItem(stringValue);
        assertEquals(1, stringHist.getCountForItem(stringValue));

        /* Add an int value 5 times and another int value 10 times */
        HashMapHistogram<Integer> intHist = new HashMapHistogram<>();
        final int[][] intValues = new int[][]{{1337, 5}, {0x1337, 10}};
        for (int i = 0; i < intValues.length; ++i) {
            for (int j = 0; j < intValues[i][1]; ++j) {
                intHist.addItem(intValues[i][0]);
            } 
        }

        for (int i = 0; i < intValues.length; ++i) {
            assertEquals(intValues[i][1], intHist.getCountForItem(intValues[i][0]));
        }
    }

    @Test
    public void testAddRemoveItem() {
        /* Add int item 42 times */
        final int intValue = Integer.reverseBytes(0x7F454C46);
        HashMapHistogram<Integer> intHist = new HashMapHistogram<>();
        for (int i = 0; i < 42; ++i) {
            intHist.addItem(intValue);
        }

        /* Remove int item 42 times */
        for (int i = 0; i < 42; ++i) {
            assertDoesNotThrow(
                () -> intHist.removeItem(intValue), 
                String.format("Remove item threw exception unexpectedly in iteration #%d", i)
            );
        }

        /* Remove item when it does not exist 2 times */
        assertThrows(
            "Remove item did not throw exception when no items left (first time)",
            IllegalItemException.class, () -> intHist.removeItem(intValue)
        );
        assertThrows(
            "Remove item did not throw exception when no items left (second time)",
            IllegalItemException.class, () -> intHist.removeItem(intValue));

        /* Remove non existent item */
        final int nonExistentIntValue = Integer.reverseBytes(0x50450000);
        assertThrows(
            "Remove non existent item did not throw exception when no items left (first time)",
            IllegalItemException.class, () -> intHist.removeItem(nonExistentIntValue));
        assertThrows(
            "Remove non existent item did not throw exception when no items left (second time)",
            IllegalItemException.class, () -> intHist.removeItem(nonExistentIntValue));
    }

    @Test
    public void testAddItemsKTimes() {
        final String stringValue = "למה שש מפחד משבע?";
        HashMapHistogram<String> stringHist = new HashMapHistogram<>();

        /* Add String value 7 times */
        assertDoesNotThrow(() -> stringHist.addItemKTimes(stringValue, 7));
        assertEquals(7, stringHist.getCountForItem(stringValue));

        /* Add String value 8 more times */
        assertDoesNotThrow(() -> stringHist.addItemKTimes(stringValue, 8));
        assertEquals(7 + 8, stringHist.getCountForItem(stringValue));

        /* Add String value 9 more times */
        assertDoesNotThrow(() -> stringHist.addItemKTimes(stringValue, 9));
        assertEquals(7 + 8 + 9, stringHist.getCountForItem(stringValue));

        /* Add String value -5 times */
        assertThrows(
            "addItemKTimes did not throw exception with a negative k value",
            IllegalKValueException.class, () -> stringHist.addItemKTimes(stringValue, -5)
        );

        /* Add another String value 0 times */
        final String nonExistentStringValue = "כל המספרים, מה הם מספרים?";
        assertThrows(
            "addItemKTimes did not throw exception with k = 0",
            IllegalKValueException.class, () -> stringHist.addItemKTimes(nonExistentStringValue, 0)
        );
    }

    @Test
    public void testRemoveItemsKTimes() {
        HashMapHistogram<String> stringHist = new HashMapHistogram<>();

        final String stringValue1 = "Botswana";
        final String stringValue2 = "Ghana";

        final int kValue1 = 2394071;
        final int kValue2 = 31653431;

        assertDoesNotThrow(() -> stringHist.addItemKTimes(stringValue1, kValue1));
        assertDoesNotThrow(() -> stringHist.addItemKTimes(stringValue2, kValue2));

        final String nonExistentStringValue = "Petah Tikva";
        assertThrows(
            "removeItemKTimes of non existent value did not throw an exception",
            IllegalItemException.class, () -> stringHist.removeItemKTimes(nonExistentStringValue, 6)
        );

        assertThrows(
            "removeItemKTimes did not throw exception with k = 0",
            IllegalKValueException.class, () -> stringHist.removeItemKTimes(stringValue2, 0)
        );

        assertDoesNotThrow(() -> stringHist.removeItemKTimes(stringValue2, kValue1));
        assertEquals(kValue2 - kValue1, stringHist.getCountForItem(stringValue2));
        assertEquals(kValue1, stringHist.getCountForItem(stringValue1));

        assertDoesNotThrow(() -> stringHist.addItemKTimes(stringValue2, kValue1 * 2));
        assertDoesNotThrow(() -> stringHist.removeItemKTimes(stringValue2, kValue1));
        assertEquals(kValue2, stringHist.getCountForItem(stringValue2));
    }

    @Test
    public void testAddAll() {
        HashMapHistogram<Boolean> boolHist = new HashMapHistogram<>();
        List<Boolean> values = 
            ("00000000001100010000000000110101000000000011000000000000001101000000000000110110000000000010001100000000001"
            + "000000000010111100000000001011101101100000101110101010000010111011111000000000010000000000101110100000000"
            + "010111100001000001011101010100000101111010000000000000100000000001011101110000000101110100110000010111010"
            + "001000001011110100000000000001000000000010111010000000001011101010100000000001000000000010111011100000001"
            + "011101010000000101111010000000010111100010000001011101100100000101111010010000000000100000000001011101000"
            + "100000101111000010000010111100100000001011110100000000101110110010000010111010100000000000011111100000000"
            + "000010100000010111010000000001011101011000000000001000000000010111010100000001011101000100000101110110010"
            + "000010111010000000001011101010100000000001000000000010111011100000001011110000100000101111001000000010111"
            + "101000000001011101100100000101110110010000010111101010000000000010000000000101110111100000010111010011000"
            + "001011110001000000101110110010000000000100000000001011101010000000101110101110000010111011001000001011101"
            + "100100000101110111010000000000100000000001011101010100000101111010000000010111100100000001011101010100000"
            + "101110100000000010111010100000000000010000000000101111010100000010111010101000001011101101100000101110110"
            + "010000000000101110000000000010000000000101111001000000010111010000000001011110011100000101110110010000010"
            + "111100000000001011101001000000000001000000000010111101010000001011101010100000101110110110000010111011001"
            + "000000000010111000000000000010100000010111100111000001011110100100000101110101000000000000100000000001011"
            + "101110000000101110110010000000000100000000001011101110000000101110101110000010111101001000001011101010100"
            + "000101110100010000000000100000000001011110001000000101110111000000000000100000000001011101000100000101111"
            + "000100000010111011100000000000010000000000101110101110000010111011001000001011101100100000101110111010000"
            + "000000100000000001011101100100000101110101010000010111101010000001011110100000000000001000000000010111011"
            + "110000001011110100000000101111000100000010111011001000001011110100100000000001011000000000000100000000001"
            + "011101010100000101110100010000010111100100000001011110100000000101110110000000000000100000000001011110001"
            + "000000101110111000000000000100000000001011101000100000101111000100000010111011100000000000010000000000101"
            + "110101110000010111011001000001011101100100000101110111010000000000100000000001011101100100000101110101010"
            + "000010111101010000001011110100000000000001000000000010111100100000001011101100000000101111001000000010111"
            + "01100000000101110111110000000000101110").chars().mapToObj(c -> c == '1').collect(Collectors.toList());

        boolHist.addAll(values);
        assertEquals(1691, boolHist.getCountForItem(false));
        assertEquals(869, boolHist.getCountForItem(true));

        boolHist.addAll(Arrays.asList(false, false, true, false, true, true, true, false));
        assertEquals(1695, boolHist.getCountForItem(false));
        assertEquals(873, boolHist.getCountForItem(true));
    }

    @Test
    public void testClear() {
        HashMapHistogram<String> hist = new HashMapHistogram<>();
        hist.addAll(Arrays.asList("To be fair, you have to have a very high IQ to understand Rick and Morty".split(" ")));
        assertEquals(2, hist.getCountForItem("to"));

        hist.clear();
        assertEquals(0, hist.getCountForItem("to"));
        assertEquals(0, hist.getCountForItem("IQ"));
        assertEquals(0, hist.getCountForItem("fair"));
    }

    @Test
    public void testGetItemSet() {
        HashMapHistogram<Integer> hist = new HashMapHistogram<>();
        hist.addAll(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8));
        Set<Integer> items = hist.getItemsSet();
        assertTrue(new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9)).equals(items));
        
        hist.addAll(Arrays.asList(4, 6, 2, 6, 4, 3, 3, 8, 3, 2, 7, 9, 5, 0));
        items = hist.getItemsSet();
        assertTrue(new HashSet<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9)).equals(items));
    }

    @Test
    public void testUpdate() {
        HashMapHistogram<Double> hist1 = new HashMapHistogram<>();
        hist1.addAll(Arrays.asList(95.97, 97.73, 91.11, 87.59, 89.58, 0.0));

        HashMapHistogram<Double> hist2 = new HashMapHistogram<>();
        hist2.addAll(Arrays.asList(97.59, 88.58, 85.36, 88.08, 89.84, 93.88, 0.0));
        
        Set<Double> items1 = hist1.getItemsSet();
        hist1.update(hist1);
        assertEquals(2, hist1.getCountForItem(91.11));
        assertTrue(items1.equals(hist1.getItemsSet()));

        Set<Double> items2 = hist2.getItemsSet();
        hist2.update(hist1);
        Set<Double> items3 = new HashSet<Double>();
        items3.addAll(items1);
        items3.addAll(items2);
        assertEquals(3, hist2.getCountForItem(0.0));
        assertEquals(2, hist2.getCountForItem(95.97));
        assertEquals(1, hist2.getCountForItem(88.58));
        assertTrue(items3.equals(hist2.getItemsSet()));
        
        HashMapHistogram<Double> hist3 = new HashMapHistogram<>();
        Set<Double> items4 = hist1.getItemsSet();
        hist1.update(hist3);
        assertTrue(items4.equals(hist1.getItemsSet()));
    }

    @Test
    public void testIterator() {
        HashMapHistogram<Integer> hist = new HashMapHistogram<>();
        /*
         * 0: 8 times
         * 3: 6 times
         * 1: 4 times
         * 2: 4 times
         * 6: 4 times
         * 4: 2 times
         * 5: 2 times
         * 7: 1 time
         * 9: 1 times
         */
        List<Integer> items = Arrays.asList(
            1, 2, 1, 2, 1, 2, 1, 2,
            3, 3, 4, 3, 3, 4, 3, 3,
            0, 0, 0, 0, 0, 0, 0, 0,
            9, 7, 5, 6, 5, 6, 6, 6
        );
        hist.addAll(items);

        List<Integer> expected = Arrays.asList(0, 3, 1, 2, 6, 4, 5, 7, 9);
        List<Integer> result = new ArrayList<>();
        hist.iterator().forEachRemaining(result::add);
        assertTrue(expected.equals(result));
    }

    @Test
    public void testTeachingAssistantsTest() {
        List<Integer> intLst = Arrays.asList(1, 2, 1, 2, 3, 4, 3, 1);
		IHistogram<Integer> intHist = new HashMapHistogram<>();
		for (int i : intLst) {
			intHist.addItem(i);
		}
        assertEquals(3, intHist.getCountForItem(1));
        assertEquals(0, intHist.getCountForItem(5));

		Iterator<Integer> intHistIt = intHist.iterator();
		List<Integer> tmpList = new ArrayList<Integer>();
		while (intHistIt.hasNext()) {
			tmpList.add(intHistIt.next());
		}
        assertEquals(1, tmpList.get(0));
        assertEquals(4, tmpList.size());

		IHistogram<String> stringHist = new HashMapHistogram<>();
		IHistogram<String> anotherHist = new HashMapHistogram<>();
        assertDoesNotThrow(() -> stringHist.addItemKTimes("bb", 5));
        assertDoesNotThrow(() -> stringHist.addItemKTimes("aa", 5));

		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("de");
        assertEquals(3, stringHist.getCountForItem("abc"));

        assertThrows(IllegalItemException.class, () -> stringHist.removeItem("abba"));

        assertDoesNotThrow(() -> stringHist.removeItemKTimes("de", 2));
        assertThrows(IllegalKValueException.class, () -> stringHist.removeItemKTimes("abc", -3));

        assertEquals(2, stringHist.getCountForItem("de"));
        assertDoesNotThrow(() -> stringHist.addItemKTimes("de", 2));

		Iterator<String> it = stringHist.iterator();
		/*
		 * the order of the returned items should be: "aa", "bb", "de", "abc" aa
		 * " and "bb" both appear 5 times, so in this case we sort by the
		 * natural order of the elements "aa" and "bb". This is why "aa" should
		 * appear before "bb"
		 */
        assertTrue(it.next().equals("aa"));
        assertTrue(it.next().equals("bb"));
        assertTrue(it.next().equals("de"));

		anotherHist.addAll(Arrays.asList("abc", "ff"));
		anotherHist.update(stringHist);
        assertEquals(4, anotherHist.getCountForItem("abc"));

        assertEquals(4, anotherHist.getCountForItem("de"));
    }
}
