package numberrangesummarizer;

import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class ListGeneratorTest {

    private NumberRangeSummarizer summarizer;

    @Before
    public void setUp() {
        summarizer = new ListGenerator();
    }

    @Test
    public void testProvidedSampleInput() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
    }

    @Test
    public void testInputWithSpaces() {
        String input = "1, 3, 6, 7, 8, 12, 13";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1, 3, 6-8, 12-13", result);
    }

    @Test
    public void testUnsortedInput() {
        String input = "10,1,7,8,6,2,3";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1-3, 6-8, 10", result);
    }

    @Test
    public void testDuplicatesInInput() {
        String input = "1,2,2,3,4,6,6,7";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1-4, 6-7", result);
    }

    @Test
    public void testNegativeNumbers() {
        String input = "-5,-4,-3,-1,0,1,5,6";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("-5--3, -1-1, 5-6", result);
    }

    @Test
    public void testSingleNumber() {
        Collection<Integer> input = Collections.singletonList(5);
        assertEquals("5", summarizer.summarizeCollection(input));
    }

    @Test
    public void testEmptyString() {
        Collection<Integer> collected = summarizer.collect("");
        assertTrue(collected.isEmpty());
        assertEquals("", summarizer.summarizeCollection(collected));
    }

    @Test
    public void testNullInput() {
        Collection<Integer> collected = summarizer.collect(null);
        assertTrue(collected.isEmpty());
        assertEquals("", summarizer.summarizeCollection(null));
    }

    @Test(expected = NumberFormatException.class)
    public void testInvalidNumberFormatThrowsException() {
        summarizer.collect("1,2,abc,4");
    }
}
