package softball.mondaywednesdaycoed.pdf.translators.implementations;

import data.models.UnorderedPair;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.data.models.Team;

import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private static final DateTimeFormatter dd_MM_YYYY_H_mm_A = DateTimeFormatter
            .ofPattern("E M/d/yyyy h:m a")
            .withZone(ZoneId.of("America/New_York"));

    @NotNull
    private final Map<String, Team> teamsByCaptainLastName;

    @NotNull
    private final Year seasonStartingYear;


    public GameLineTranslator(@NotNull final Set<Team> teams, @NotNull final Year seasonStartingYear) {
        this.teamsByCaptainLastName = teams
                .stream()
                .map(t -> Map.entry(t.captain().lastName(), t))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.seasonStartingYear = seasonStartingYear;
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

        final Instant startTime;
        try {
            startTime = dd_MM_YYYY_H_mm_A
                    .parse(
                            parts[0] + " " + parts[1] + "/" + seasonStartingYear.getValue() + " " + parts[4] + " " + parts[5],
                            Instant::from
                    );
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }

        return Optional.of(
                new Game(LOCATIONS_BY_FIELD_NAME.get(fieldName), startTime, new UnorderedPair<>(firstTeam, secondTeam))
        );
    }
}
