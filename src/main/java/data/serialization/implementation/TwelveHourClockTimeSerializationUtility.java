package data.serialization.implementation;

import data.serialization.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

public class TwelveHourClockTimeSerializationUtility implements Deserializer<LocalTime> {
    // TODO: Restrict this to 12 hours somehow
    private static final TwelveHourClockTimeSerializationUtility INSTANCE = new TwelveHourClockTimeSerializationUtility(DateTimeFormatter.ofPattern("k:mm").localizedBy(Locale.ENGLISH));

    @NotNull
    private final DateTimeFormatter formatter;

    private TwelveHourClockTimeSerializationUtility(@NotNull final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public Optional<LocalTime> deserialize(@NotNull final String serializedValue) {
        final LocalTime value;
        try {
            value = LocalTime.parse(serializedValue, formatter);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    public static TwelveHourClockTimeSerializationUtility getInstance() {
        return INSTANCE;
    }
}
