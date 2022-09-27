package softball.mondaywednesdaycoed.pdf.translators.interfaces;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.pdf.translators.implementations.SchedulePdfGameTranslator;

import java.util.function.Consumer;

public interface GameTranslator {
    void translate(@NotNull PDDocument document, @NotNull Consumer<Game> gameConsumer) throws SchedulePdfGameTranslator.UnableToParsePage;
}
