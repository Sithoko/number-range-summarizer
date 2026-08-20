package numberrangesummarizer;

import java.util.*;
import java.util.stream.*;

/**
 * Summarizes collections of integers into condensed range representations.
 */
public class ListGenerator implements NumberRangeSummarizer {

    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String[] rawNumbers = input.split(",");
        List<Integer> numbers = new ArrayList<>();

        for (String rawNum : rawNumbers) {
            String trimmedNum = rawNum.trim();

            // Ignore blank tokens caused by trailing or multiple consecutive commas
            if (!trimmedNum.isEmpty()) {
                int parsedNumber = Integer.parseInt(trimmedNum);
                
                // Deduplicate during collection to keep subsequent range checking linear
                if (!numbers.contains(parsedNumber)) {
                    numbers.add(parsedNumber);
                }
            }
        }

        Collections.sort(numbers);
        return numbers;
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Clean input defensively in case an unparsed collection is passed directly
        List<Integer> sortedList = input.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        if (sortedList.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int start = sortedList.get(0);
        int end = start;

        for (int i = 1; i < sortedList.size(); i++) {
            int current = sortedList.get(i);

            if (current == end + 1) {
                // Number continues the active sequence
                end = current;
            } else {
                // Break in sequence; commit active range and step forward
                appendRange(sb, start, end);
                sb.append(", ");
                start = end = current;
            }
        }

        // Commit the final tracked range
        appendRange(sb, start, end);

        return sb.toString();
    }

    /**
     * Formats ranges as "start-end" for sequential blocks or "start" for isolated numbers.
     */
    private void appendRange(StringBuilder sb, int start, int end) {
        if (start == end) {
            sb.append(start);
        } else {
            sb.append(start).append("-").append(end);
        }
    }
}
