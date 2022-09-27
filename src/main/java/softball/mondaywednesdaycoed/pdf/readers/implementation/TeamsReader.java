package softball.mondaywednesdaycoed.pdf.readers.implementation;

import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Team;
import softball.mondaywednesdaycoed.pdf.translators.interfaces.TeamLineTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

public class TeamsReader implements softball.mondaywednesdaycoed.pdf.readers.interfaces.TeamsReader {
    private static final String TEAM_LINE_BEFORE = "Seed Tm First Last Team Name Color W L T PTS ";
    private static final String SCHEDULE_LINE_BEFORE = "Day Date Field City Time Team Team Div Changes ";

    @NotNull
    private final TeamLineTranslator teamLineTranslator;

    public TeamsReader(@NotNull final TeamLineTranslator teamLineTranslator) {
        this.teamLineTranslator = teamLineTranslator;
    }

    @Override
    public void read(@NotNull final Reader pdf, @NotNull final Consumer<Team> teamConsumer) {
        try (final BufferedReader bufferedReader = new BufferedReader(pdf)) {
            String line;
            boolean hasTeamLineBeenSeen = false;
            boolean hasGameLineBeenSeen = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(TEAM_LINE_BEFORE)) {
                    hasTeamLineBeenSeen = true;
                } else if (line.equals(SCHEDULE_LINE_BEFORE)) {
                    hasGameLineBeenSeen = true;
                } else if (hasTeamLineBeenSeen && !hasGameLineBeenSeen) {
                    try {
                        teamLineTranslator.translate(line).ifPresent(teamConsumer);
                    } catch (RuntimeException e) {
                        // expected
                    }
                } else if (hasTeamLineBeenSeen && hasGameLineBeenSeen) {
                    break;
                }
            }
        } catch (IOException e) {
            // TODO: @jbradley convert to checked exception
            throw new RuntimeException("unexpected");
        }
    }
}
