package il.ac.tau.cs.sw1.ex8.wordsRank;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileTester {
    public static final String INPUT_FOLDER = "./resources/hw8/input";
    public static final String TEST_FOLDER = "./resources/hw8/input/test";
    FileIndex findex = new FileIndex();
    boolean indexed = false;

    private void index() {
        if (!this.indexed) {
            findex.indexDirectory(TEST_FOLDER);
            this.indexed = true;
        }
    }

    @Test
    public void testGetCountInFile() {
        this.index();

        assertDoesNotThrow(() -> assertEquals(3, findex.getCountInFile("bongcloud.txt", "Ke2")));
        assertDoesNotThrow(() -> assertEquals(1, findex.getCountInFile("bongcloud.txt", "e5")));
        assertDoesNotThrow(() -> assertEquals(1, findex.getCountInFile("bongcloud.txt", "E5")));
        assertDoesNotThrow(() -> assertEquals(0, findex.getCountInFile("bongcloud.txt", "0-0-0")));

        assertDoesNotThrow(() -> assertEquals(4, findex.getCountInFile("chrysalis.txt", "beautiful")));

        assertThrows(
            FileIndexException.class,
            () -> findex.getCountInFile(
                "2021b-final-test-answers.txt",
                "יב. קל לראות"
            )
        );
    }

    @Test
    public void testGetRankForWordInFile() {
        this.index();

        assertDoesNotThrow(() -> assertEquals(79, findex.getRankForWordInFile("lorem-ipsum.txt", "explain")));
        assertDoesNotThrow(() -> assertEquals(79, findex.getRankForWordInFile("lorem-ipsum.txt", "explAin")));
        assertDoesNotThrow(() -> assertEquals(1, findex.getRankForWordInFile("lorem-ipsum.txt", "to")));
        assertDoesNotThrow(() -> assertEquals(175, findex.getRankForWordInFile("lorem-ipsum.txt", "simple")));

        assertDoesNotThrow(() -> assertEquals(1402, findex.getRankForWordInFile("bee-movie.txt", "motion-picture-capture")));
        assertDoesNotThrow(() -> assertEquals(3, findex.getRankForWordInFile("bee-movie.txt", "a")));
        assertDoesNotThrow(() -> assertEquals(1713, findex.getRankForWordInFile("bee-movie.txt", "sugar-free")));

        assertDoesNotThrow(() -> assertEquals(1864 + FileIndex.UNRANKED_CONST, findex.getRankForWordInFile("bee-movie.txt", "winnie-the-pooh")));

        assertThrows(
            FileIndexException.class,
            () -> findex.getRankForWordInFile(
                "האיש-הירוק.txt",
                "אני מסיפור אחר"
            )
        );

        assertThrows(
            FileIndexException.class,
            () -> findex.getRankForWordInFile(
                "הילד-הירוק.txt",
                "13"
            )
        );
    }

    @Test
    public void testGetAverageRankForWord() {
        this.index();

        assertFalse(593 == this.findex.getAverageRankForWord("meap"), "Maybe there is a problem with the way you calculated the average.");
        assertEquals(594, this.findex.getAverageRankForWord("meap"));
        assertEquals(43, this.findex.getAverageRankForWord("be"));
        assertEquals(586, this.findex.getAverageRankForWord("welcomed"));
        assertEquals(19, this.findex.getAverageRankForWord("you"));
        assertEquals(557, this.findex.getAverageRankForWord("satan"));
        assertEquals(557, this.findex.getAverageRankForWord("SaTaN"));
        assertEquals(110, this.findex.getAverageRankForWord("because"));
        assertEquals(410, this.findex.getAverageRankForWord("game"));
        assertEquals(11, this.findex.getAverageRankForWord("the"));
    }

    @Test
    public void testGetWordsWithMaxRankSmallerThanK() {
        this.index();

        Set<String> smallerThan100Expected = Arrays.asList(
            "a", "and", "i", "is", "it", "of", "the", "to", "you", "no", "with", "this", "are",
            "we", "but", "so", "that", "all", "be", "he", "do", "in", "can"
        ).stream().collect(Collectors.toSet());
        Set<String> smallerThan100Results = this.findex.getWordsWithMaxRankSmallerThanK(100).stream().collect(Collectors.toSet());
        assertTrue(smallerThan100Expected.equals(smallerThan100Results));

        Set<String> smallerThan36Expected = Collections.emptySet();
        Set<String> smallerThan36Results = this.findex.getWordsWithMaxRankSmallerThanK(36).stream().collect(Collectors.toSet());
        assertTrue(smallerThan36Expected.equals(smallerThan36Results));

        Set<String> smallerThan0Expected = Collections.emptySet();
        Set<String> smallerThan0Results = this.findex.getWordsWithMaxRankSmallerThanK(0).stream().collect(Collectors.toSet());
        assertTrue(smallerThan0Expected.equals(smallerThan0Results));

        List<String> allResults = this.findex.getWordsWithMaxRankSmallerThanK(Integer.MAX_VALUE);

        assertEquals(2012, allResults.size());

        assertTrue(allResults.contains("haphazardly"));
        assertTrue(allResults.contains("dada"));
        assertTrue(allResults.contains("never"));

        assertTrue(allResults.indexOf("down") < allResults.indexOf("dismissal"));
        assertTrue(allResults.indexOf("well") < allResults.indexOf("27-million-year-old"));

        assertFalse(allResults.contains("biefield"));
        assertFalse(allResults.contains("wyoming"));
        assertFalse(allResults.contains("tlaxcala"));
        assertFalse(allResults.contains("molise"));
        assertFalse(allResults.contains("new-zealand"));
    }

    @Test
    public void testGetWordsWithAverageRankSmallerThanK() {
        this.index();

        Set<String> smallerThan12Expected = Arrays.asList("the").stream().collect(Collectors.toSet());
        Set<String> smallerThan12Results = this.findex.getWordsWithAverageRankSmallerThanK(12).stream().collect(Collectors.toSet());
        assertTrue(smallerThan12Expected.equals(smallerThan12Results));

        Set<String> smallerThan11Expected = Collections.emptySet();
        Set<String> smallerThan11Results = this.findex.getWordsWithAverageRankSmallerThanK(1).stream().collect(Collectors.toSet());
        assertTrue(smallerThan11Expected.equals(smallerThan11Results));

        Set<String> smallerThan0Expected = Collections.emptySet();
        Set<String> smallerThan0Results = this.findex.getWordsWithAverageRankSmallerThanK(0).stream().collect(Collectors.toSet());
        assertTrue(smallerThan0Expected.equals(smallerThan0Results));

        List<String> allResults = this.findex.getWordsWithAverageRankSmallerThanK(Integer.MAX_VALUE);

        assertEquals(2012, allResults.size());

        assertFalse(allResults.contains("party"));
        assertTrue(allResults.contains("fit"));
        assertTrue(allResults.indexOf("we") < allResults.indexOf("sports"));

        assertFalse(allResults.contains(""));
    }

    @Test
    public void testGetWordsWithMinRankSmallerThanK() {
        this.index();

        Set<String> smallerThan2Expected = Arrays.asList("i", "ke2", "to", "you").stream().collect(Collectors.toSet());
        Set<String> smallerThan2Results = this.findex.getWordsWithMinRankSmallerThanK(2).stream().collect(Collectors.toSet());
        assertTrue(smallerThan2Expected.equals(smallerThan2Results));

        Set<String> smallerThan1Expected = Collections.emptySet();
        Set<String> smallerThan1Results = this.findex.getWordsWithMinRankSmallerThanK(1).stream().collect(Collectors.toSet());
        assertTrue(smallerThan1Expected.equals(smallerThan1Results));

        Set<String> smallerThan0Expected = Collections.emptySet();
        Set<String> smallerThan0Results = this.findex.getWordsWithMinRankSmallerThanK(0).stream().collect(Collectors.toSet());
        assertTrue(smallerThan0Expected.equals(smallerThan0Results));

        List<String> allResults = this.findex.getWordsWithMinRankSmallerThanK(Integer.MAX_VALUE);

        assertEquals(2012, allResults.size());

        assertFalse(allResults.contains("baba"));
        assertTrue(allResults.contains("is"));
        assertTrue(allResults.contains("you"));

        assertFalse(allResults.contains(""));
    }

    @Test
    public void testTeachingAssistantsTest() {
        FileIndex fIndex = new FileIndex();
        fIndex.indexDirectory(INPUT_FOLDER);

        assertDoesNotThrow(() -> assertEquals(5, fIndex.getCountInFile("rocky1.txt", "Rocky")));
        assertDoesNotThrow(() -> assertEquals(1, fIndex.getRankForWordInFile("rocky3.txt", "and")));

        assertDoesNotThrow(() -> {
            int unknownWordRankInFile1 = fIndex.getRankForWordInFile("rocky1.txt", "revolution");
            int unknownWordRankInFile2 = fIndex.getRankForWordInFile("rocky2.txt", "revolution");

            assertEquals(fIndex.getRankForWordInFile("rocky1.txt", "doctor"), unknownWordRankInFile1);
            assertTrue(unknownWordRankInFile1 < unknownWordRankInFile2);
        });

        assertDoesNotThrow(() -> {
            assertTrue(fIndex.getRankForWordInFile("rocky3.txt", "revolution")
                        - fIndex.getRankForWordInFile("rocky3.txt", "doctor") >= FileIndex.UNRANKED_CONST);
        });

        assertThrows(FileIndexException.class, () -> fIndex.getRankForWordInFile("help.txt", "rocky"));
        assertDoesNotThrow(() -> assertEquals(Math.round((1+4+2)/3.), fIndex.getAverageRankForWord("rocky")));
        //in rocky1.txt: rank = 1, in rocky2.txt: rank = 4, in rocky3.txt: rank = 2

        List<String> topByMin = fIndex.getWordsWithMinRankSmallerThanK(2);
        //RankedWord [word=his, ranksForFile={rocky2.txt=1, rocky1.txt=23, rocky3.txt=31}, average=18, min=1, max=31]
        //RankedWord [word=rocky, ranksForFile={rocky2.txt=4, rocky1.txt=1, rocky3.txt=2}, average=2, min=1, max=4]
        //RankedWord [word=and, ranksForFile={rocky2.txt=2, rocky1.txt=11, rocky3.txt=1}, average=5, min=1, max=11]
        assertTrue(topByMin.contains("rocky"));
        assertTrue(topByMin.contains("and"));
        assertTrue(topByMin.contains("his"));
        assertTrue(topByMin.size() == 3);

        //highest rank for rocky is 4
        List<String> topByMax = fIndex.getWordsWithMaxRankSmallerThanK(6);
        assertTrue(topByMax.get(0).equals("rocky"));
    }
}