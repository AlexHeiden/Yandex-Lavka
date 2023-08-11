package ru.yandex.yandexlavka.common_validation_utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TimeValidationUtils {

    public static final DateTimeFormatter DateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public static final DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String TimeRegexp = "(([0,1][0-9])|(2[0-3])):[0-5][0-9]";
    private static final String TimeIntervalRegexp = TimeRegexp + "-" +TimeRegexp;

    private static final int FirstHourIndex = 0;
    private static final int FirstMinuteIndex = 3;
    private static final int SecondHourIndex = 6;
    private static final int SecondMinuteIndex = 9;
    private static final int MinutesInHour = 60;

    public static List<String> validateTimeIntervals(List<String> timeIntervals,
                                                     StringBuilder sb,
                                                     String parameterName) {
        if (timeIntervals == null || timeIntervals.isEmpty()) {
            sb.append("The parameter '")
                    .append(parameterName)
                    .append("' is missing; ");

            return timeIntervals;
        }

        timeIntervals = timeIntervals.stream().map(x -> x.trim()).collect(Collectors.toList());

        StringBuilder timeIntervalsStringBuilder = new StringBuilder();
        for (String timeInterval : timeIntervals) {
            validateTimeIntervalFormat(timeInterval, timeIntervalsStringBuilder);
        }

        if (timeIntervalsStringBuilder.length() != 0) {
            timeIntervalsStringBuilder.delete(timeIntervalsStringBuilder.length() - 2,
                    timeIntervalsStringBuilder.length());
            sb.append("Invalid time format: ")
                    .append(timeIntervalsStringBuilder)
                    .append("; ");

            return timeIntervals;
        }

        validateTimeIntervalIntersections(timeIntervals, timeIntervalsStringBuilder);
        if (timeIntervalsStringBuilder.length() != 0) {
            timeIntervalsStringBuilder.delete(timeIntervalsStringBuilder.length() - 2,
                    timeIntervalsStringBuilder.length());
            sb.append("Intersected time intervals: ")
                    .append(timeIntervalsStringBuilder)
                    .append("; ");
        }

        return timeIntervals;
    }

    //check if matches TimeIntervalRegexp and first time not equal to second time
    private static String validateTimeIntervalFormat(String timeInterval, StringBuilder timeIntervalsStringBuilder) {
        if (!timeInterval.matches(TimeIntervalRegexp)
                || timeInterval.substring(FirstHourIndex, FirstMinuteIndex + 2)
                    .equals(timeInterval.substring(SecondHourIndex, SecondMinuteIndex + 2))) {
            timeIntervalsStringBuilder.append(timeInterval)
                    .append(", ");
        }

        return timeInterval;
    }

    //check if time intervals intersect with each other
    private static List<String> validateTimeIntervalIntersections(List<String> timeIntervals,
                                                                  StringBuilder timeIntervalsStringBuilder) {
        if (timeIntervals.size() == 1) {
            return timeIntervals;
        }

        Collections.sort(timeIntervals);
        List<Integer> firstInterval = parseTimeIntervalToMinutes(timeIntervals.get(0));
        for (int i = 1; i < timeIntervals.size(); i++) {
            List<Integer> secondInterval = parseTimeIntervalToMinutes(timeIntervals.get(i));
            if (areIntervalsIntersected(firstInterval, secondInterval)) {
                timeIntervalsStringBuilder.append(timeIntervals.get(i - 1))
                        .append(" and ")
                        .append(timeIntervals.get(i))
                        .append(", ");
            }

            firstInterval = secondInterval;
        }

        if (timeIntervals.size() != 2) {
            List<Integer> secondInterval = parseTimeIntervalToMinutes(timeIntervals.get(0));
            if (areIntervalsIntersected(firstInterval, secondInterval)) {
                timeIntervalsStringBuilder.append(timeIntervals.get(timeIntervals.size() - 1))
                        .append(" and ")
                        .append(timeIntervals.get(0))
                        .append(", ");
            }
        }

        return timeIntervals;
    }

    private static boolean areIntervalsIntersected(List<Integer> firstInterval,
                                                   List<Integer> secondInterval) {
        int firstStart = firstInterval.get(0);
        int firstEnd = firstInterval.get(1);
        int secondStart = secondInterval.get(0);
        int secondEnd = secondInterval.get(1);

        return (Math.max(firstStart, secondStart) <= Math.min(firstEnd, secondEnd))
                || ((firstEnd > secondStart || secondEnd > firstStart)
                        && Integer.signum(firstStart - firstEnd) != Integer.signum(secondStart - secondEnd))
                || (firstEnd < firstStart && secondEnd < secondStart)
                || (firstEnd == secondStart)
                || (secondEnd == firstStart);
    }

    public static LocalDateTime parseDateTime(String date) throws DateTimeParseException {
        return LocalDateTime.parse(date.trim(), DateTimeFormat);
    }

    public static LocalDate parseDate(String date) throws DateTimeParseException {
        return LocalDate.parse(date.trim(), DateFormat);
    }

    private static List<Integer> parseTimeIntervalToMinutes(String timeInterval) {
        List<Integer> list = new ArrayList<>(2);
        int start = parseSpecificTimeFromTimeInterval(timeInterval, FirstHourIndex, FirstMinuteIndex);
        int end = parseSpecificTimeFromTimeInterval(timeInterval, SecondHourIndex, SecondMinuteIndex);

        list.add(start);
        list.add(end);

        return list;
    }

    private static int parseSpecificTimeFromTimeInterval(String timeInterval, int hourIndex, int minuteIndex) {
        return MinutesInHour * Integer.parseInt(timeInterval.substring(hourIndex, hourIndex + 2))
                + Integer.parseInt(timeInterval.substring(minuteIndex, minuteIndex + 2));
    }
}
