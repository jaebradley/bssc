package softball.mondaywednesdaycoed.data.models;

import data.models.UnorderedPair;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

public record Game(
        @NotNull Location location,
        @NotNull ZonedDateTime startTime,
        @NotNull UnorderedPair<Team> teams
) {

    public enum Location {
        Murray_Field,
        Rogers_Park,
        Donnelly_Field,
        Library_1,
        Library_2
    }
}
