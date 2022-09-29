package softball.mondaywednesdaycoed.pdf.translators.implementations;

import data.models.TwelveHourClockPeriod;
import data.models.UnorderedPair;
import data.serialization.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.data.models.Team;

import java.time.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GameLineTranslator implements softball.mondaywednesdaycoed.pdf.translators.interfaces.GameLineTranslator {
    @NotNull
    private static final Set<String> EXPECTED_DAY_OF_WEEK_VALUES = Set.of("Mon", "Wed");

    @NotNull
    private static final Map<String, String> CITY_NAMES_BY_FIELD_NAME = Map.of(
            "Donnelly", "Cambridge",
            "Murray", "Brighton",
            "Rogers", "Brighton",
            "Library 2", "Woburn",
            "Library 1", "Woburn"
    );

    @NotNull
    private static final Map<String, Game.Location> LOCATIONS_BY_FIELD_NAME = Map.of(
            "Donnelly", Game.Location.Donnelly_Field,
            "Murray", Game.Location.Murray_Field,
            "Rogers", Game.Location.Rogers_Park,
            "Library 2", Game.Location.Library_2,
            "Library 1", Game.Location.Library_1
    );

    @NotNull
    private final ZoneId gameTimeZone;

    @NotNull
    private final Map<String, Team> teamsByCaptainLastName;

    @NotNull
    private final Year seasonStartingYear;

    @NotNull
    private final Deserializer<DayOfWeek> dayOfWeekDeserializer;

    @NotNull
    private final Deserializer<MonthDay> monthDayDeserializer;

    @NotNull
    private final Deserializer<LocalTime> timeDeserializer;

    @NotNull
    private final Deserializer<TwelveHourClockPeriod> periodDeserializer;


    public GameLineTranslator(
            @NotNull final ZoneId gameTimeZone,
            @NotNull final Set<Team> teams,
            @NotNull final Year seasonStartingYear,
            @NotNull final Deserializer<DayOfWeek> dayOfWeekDeserializer,
            @NotNull final Deserializer<MonthDay> monthDayDeserializer,
            @NotNull final Deserializer<LocalTime> timeDeserializer,
            @NotNull final Deserializer<TwelveHourClockPeriod> periodDeserializer
    ) {
        this.gameTimeZone = gameTimeZone;
        this.teamsByCaptainLastName = teams
                .stream()
                .map(t -> Map.entry(t.captain().lastName(), t))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.seasonStartingYear = seasonStartingYear;
        this.dayOfWeekDeserializer = dayOfWeekDeserializer;
        this.monthDayDeserializer = monthDayDeserializer;
        this.timeDeserializer = timeDeserializer;
        this.periodDeserializer = periodDeserializer;
    }

    @Override
    public Optional<Game> translate(@NotNull final String line) {
        final String[] parts = line.trim().split(" ");
        if (8 > parts.length) {
            return Optional.empty();
        }

        if (!EXPECTED_DAY_OF_WEEK_VALUES.contains(parts[0])) {
            return Optional.empty();
        }

        if (!CITY_NAMES_BY_FIELD_NAME.containsKey(parts[2])) {
            return Optional.empty();
        }

        if (!parts[3].equals(CITY_NAMES_BY_FIELD_NAME.get(parts[2]))) {
            return Optional.empty();
        }

        final String fieldName = parts[2];

        final Team firstTeam = teamsByCaptainLastName.get(parts[6]);
        if (null == firstTeam) {
            return Optional.empty();
        }

        final Team secondTeam = teamsByCaptainLastName.get(parts[7]);
        if (null == secondTeam) {
            return Optional.empty();
        }

        final Optional<ZonedDateTime> startTime = dayOfWeekDeserializer.deserialize(parts[0])
                .flatMap(dayOfWeek -> monthDayDeserializer.deserialize(parts[1])
                        .flatMap(monthDay -> timeDeserializer.deserialize(parts[4])
                                .flatMap(time -> periodDeserializer.deserialize(parts[5])
                                        .flatMap(period -> {
                                            final LocalTime updatedTime = period.equals(TwelveHourClockPeriod.PM) ? time.plusHours(12) : time;
                                            final ZonedDateTime datetime = ZonedDateTime.of(
                                                    LocalDate.of(seasonStartingYear.getValue(), monthDay.getMonth(), monthDay.getDayOfMonth()),
                                                    updatedTime,
                                                    gameTimeZone
                                            );
                                            if (datetime.getDayOfWeek().equals(dayOfWeek)) {
                                                return Optional.of(datetime);
                                            }

                                            return Optional.empty();
                                        }))));

        if (startTime.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                new Game(
                        LOCATIONS_BY_FIELD_NAME.get(fieldName),
                        startTime.get(),
                        new UnorderedPair<>(firstTeam, secondTeam)
                )
        );
    }
}
