package data.serialization.implementation;

import data.models.TwelveHourClockPeriod;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class TwelveHourClockPeriodSerializationUtility extends EnumeratedValuesSerializationUtility<TwelveHourClockPeriod> {
    @NotNull
    private static final TwelveHourClockPeriodSerializationUtility INSTANCE = new TwelveHourClockPeriodSerializationUtility(
            new EnumMap<>(
                    Map.of(TwelveHourClockPeriod.AM, "AM", TwelveHourClockPeriod.PM, "PM")
            )
    );

    private TwelveHourClockPeriodSerializationUtility(@NotNull final EnumMap<TwelveHourClockPeriod, String> serializedValuesByEnumeratedValue) {
        super(serializedValuesByEnumeratedValue);
    }

    public static TwelveHourClockPeriodSerializationUtility getInstance() {
        return INSTANCE;
    }
}
