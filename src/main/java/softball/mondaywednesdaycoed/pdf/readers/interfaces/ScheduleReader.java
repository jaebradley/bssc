package softball.mondaywednesdaycoed.pdf.readers.interfaces;

import softball.mondaywednesdaycoed.data.models.Game;

import java.io.Reader;
import java.util.function.Consumer;

public interface ScheduleReader {
    void read(Reader pdf, Consumer<Game> gamesConsumer);
}
