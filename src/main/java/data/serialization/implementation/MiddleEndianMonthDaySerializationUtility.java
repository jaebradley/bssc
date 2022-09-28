package data.serialization.implementation;

import data.serialization.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

/**
 * From https://en.wikipedia.org/w/index.php?title=Date_and_time_notation_in_the_United_States&oldid=1106212765
 * "In the United States, dates are traditionally written in the "month-day-year" order, with neither increasing nor decreasing order of significance. This is called middle endian."
 */

public class MiddleEndianMonthDaySerializationUtility implements Deserializer<MonthDay> {
    @NotNull
    private static final MiddleEndianMonthDaySerializationUtility INSTANCE = new MiddleEndianMonthDaySerializationUtility(DateTimeFormatter.ofPattern("M/d").localizedBy(Locale.ENGLISH));

    @NotNull
    private final DateTimeFormatter formatter;

    private MiddleEndianMonthDaySerializationUtility(@NotNull final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public Optional<MonthDay> deserialize(@NotNull final String serializedValue) {
        final MonthDay monthDay;
        try {
            monthDay = MonthDay.parse(serializedValue, formatter);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }

        return Optional.of(monthDay);
    }

    public static MiddleEndianMonthDaySerializationUtility getInstance() {
        return INSTANCE;
    }
}
