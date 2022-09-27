package softball.mondaywednesdaycoed.pdf.readers.implementation;

import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.pdf.translators.interfaces.GameLineTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

public class ScheduleReader implements softball.mondaywednesdaycoed.pdf.readers.interfaces.ScheduleReader {
    private static final String SCHEDULE_LINE_BEFORE = "Day Date Field City Time Team Team Div Changes ";

    @NotNull
    private final GameLineTranslator gameLineTranslator;

    public ScheduleReader(
            @NotNull final GameLineTranslator gameLineTranslator
    ) {
        this.gameLineTranslator = gameLineTranslator;
    }

    @Override
    public void read(@NotNull final Reader pdf, @NotNull final Consumer<Game> gamesConsumer) {
        try (final BufferedReader bufferedReader = new BufferedReader(pdf)) {
            String line;
            boolean hasGameLineBeenSeen = false;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(SCHEDULE_LINE_BEFORE)) {
                    hasGameLineBeenSeen = true;
                } else if (hasGameLineBeenSeen) {
                    try {
                        gameLineTranslator.translate(line).ifPresent(gamesConsumer);
                    } catch (RuntimeException e) {
                        // expected
                    }
                }
            }
        } catch (IOException e) {
            // TODO: @jbradley convert to checked exception
            throw new RuntimeException("unexpected");
        }
    }
}
