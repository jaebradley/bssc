package softball.mondaywednesdaycoed.pdf.readers.interfaces;

import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Team;

import java.io.Reader;
import java.util.function.Consumer;

public interface TeamsReader {
    void read(@NotNull Reader pdf, @NotNull Consumer<Team> teamConsumer);
}
