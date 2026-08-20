// assumption - list is always sorted in ascending order
package numberrangesummarizer;

import java.util.*;
import java.util.stream.Collectors;

public class ListGenerator implements NumberRangeSummarizer{
    
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String[] rawNumbers = input.split(",");
        
        List<Integer> numbers = new ArrayList<>();

        for (String rawNum : rawNumbers) {
            // Remove leading and trailing spaces
            String trimmedNum = rawNum.trim();

            if (!trimmedNum.isEmpty()) {
                int parsedNumber = Integer.parseInt(trimmedNum);

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

        // Ensure input is sorted and contains no nulls or duplicates
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
                end = current;
            } else {
                appendRange(sb, start, end);
                sb.append(", ");
                start = end = current;
            }
        }

        appendRange(sb, start, end);

        return sb.toString();
    }

    private void appendRange(StringBuilder sb, int start, int end) {
        if (start == end) {
            sb.append(start);
        } else {
            sb.append(start).append("-").append(end);
        }
    }

}