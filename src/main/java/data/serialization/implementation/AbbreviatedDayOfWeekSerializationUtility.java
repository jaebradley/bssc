package data.serialization.implementation;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class AbbreviatedDayOfWeekSerializationUtility extends EnumeratedValuesSerializationUtility<DayOfWeek> {

    private static final AbbreviatedDayOfWeekSerializationUtility INSTANCE = new AbbreviatedDayOfWeekSerializationUtility(
            new EnumMap<DayOfWeek, String>(
                    Arrays.stream(DayOfWeek.values())
                            .sorted(Comparator.comparingInt(Enum::ordinal))
                            .map(d -> Map.entry(d, d.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)))
                            .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue))
            )
    );

    private AbbreviatedDayOfWeekSerializationUtility(@NotNull final EnumMap<DayOfWeek, String> valuesBySerialization) {
        super(valuesBySerialization);
    }

    public static AbbreviatedDayOfWeekSerializationUtility getInstance() {
        return INSTANCE;
    }
}
