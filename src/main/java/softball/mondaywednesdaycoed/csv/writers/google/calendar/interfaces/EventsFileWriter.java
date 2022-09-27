package softball.mondaywednesdaycoed.csv.writers.google.calendar.interfaces;

import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;

import java.io.File;
import java.util.Set;

public interface EventsFileWriter {
    void write(@NotNull File file, @NotNull Set<Game> games);
}
