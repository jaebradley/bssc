package softball.mondaywednesdaycoed.pdf.readers.interfaces;

import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;

import java.io.Reader;
import java.util.function.Consumer;

public interface ScheduleReader {
    void read(@NotNull Reader pdf, @NotNull Consumer<Game> gamesConsumer);
}
