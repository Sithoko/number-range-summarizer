package numberrangesummarizer;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Unit tests for ListGenerator covering standard functionality, edge cases, 
 * formatting variations, and unexpected input handling.
 */
public class ListGeneratorTest {

    private NumberRangeSummarizer summarizer;

    @Before
    public void setUp() {
        summarizer = new ListGenerator();
    }

    /**
     * Verifies that the sample input provided in the specification returns
     * the exact expected output string.
     */
    @Test
    public void testProvidedSampleInput() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result);
    }

    /**
     * Ensures leading and trailing whitespace around numbers is trimmed safely.
     */
    @Test
    public void testInputWithSpaces() {
        String input = "1, 3, 6, 7, 8, 12, 13";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1, 3, 6-8, 12-13", result);
    }

    /**
     * Confirms that unsorted input elements are ordered sequentially before summarization.
     */
    @Test
    public void testUnsortedInput() {
        String input = "10,1,7,8,6,2,3";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1-3, 6-8, 10", result);
    }

    /**
     * Verifies that duplicate values are deduplicated and do not break sequence continuity.
     */
    @Test
    public void testDuplicatesInInput() {
        String input = "1,2,2,3,4,6,6,7";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("1-4, 6-7", result);
    }

    /**
     * Checks correct range formatting across boundary transitions with negative numbers.
     */
    @Test
    public void testNegativeNumbers() {
        String input = "-5,-4,-3,-1,0,1,5,6";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);

        assertEquals("-5--3, -1-1, 5-6", result);
    }

    /**
     * Confirms single-element inputs summarize to the isolated number without dashes or extra commas.
     */
    @Test
    public void testSingleNumber() {
        Collection<Integer> input = Collections.singletonList(5);
        assertEquals("5", summarizer.summarizeCollection(input));
    }

    /**
     * Verifies empty string inputs gracefully produce an empty collection and string without errors.
     */
    @Test
    public void testEmptyString() {
        Collection<Integer> collected = summarizer.collect("");
        assertTrue(collected.isEmpty());
        assertEquals("", summarizer.summarizeCollection(collected));
    }

    /**
     * Ensures null inputs are handled defensively without throwing NullPointerExceptions.
     */
    @Test
    public void testNullInput() {
        Collection<Integer> collected = summarizer.collect(null);
        assertTrue(collected.isEmpty());
        assertEquals("", summarizer.summarizeCollection(null));
    }

    /**
     * Expects a NumberFormatException when non-numeric characters are parsed.
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidNumberFormatThrowsException() {
        summarizer.collect("1,2,abc,4");
    }
}
