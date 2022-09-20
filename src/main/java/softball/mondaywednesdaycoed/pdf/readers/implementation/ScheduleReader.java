package softball.mondaywednesdaycoed.pdf.readers.implementation;

import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

public class ScheduleReader implements softball.mondaywednesdaycoed.pdf.readers.interfaces.ScheduleReader {
    private static final String TEAM_LINE_BEFORE = "Seed Tm First Last Team Name Color W L T PTS ";
    private static final String SCHEDULE_LINE_BEFORE = "Day Date Field City Time Team Team Div Changes ";

    @Override
    public void read(@NotNull final Reader pdf, @NotNull final Consumer<Game> gamesConsumer) {
        try (final BufferedReader bufferedReader = new BufferedReader(pdf)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                assert null != line;
            }
        } catch (IOException e) {
            // TODO: @jbradley convert to checked exception
            throw new RuntimeException("unexpected");
        }
    }
}
