package softball.mondaywednesdaycoed.interfaces.pdf.translators;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.interfaces.pdf.implementations.SchedulePdfGameTranslator;

import java.util.function.Consumer;

public interface GameTranslator {
    void translate(@NotNull PDDocument document, @NotNull Consumer<Game> gameConsumer) throws SchedulePdfGameTranslator.UnableToParsePage;
}
