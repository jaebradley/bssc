package softball.mondaywednesdaycoed.interfaces.pdf.implementations;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.interfaces.pdf.translators.GameTranslator;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class SchedulePdfGameTranslator implements GameTranslator {
    private final PDFTextStripperByArea documentParser;

    public SchedulePdfGameTranslator(@NotNull final PDFTextStripperByArea documentParser) {
        this.documentParser = documentParser;
    }

    @Override
    public void translate(@NotNull PDDocument document, @NotNull Consumer<Game> gameConsumer) throws UnableToParsePage {
        final PDPageTree pages = document.getPages();
        for (final PDPage page : pages) {
            try {
                documentParser.extractRegions(page);
            } catch (IOException e) {
                throw new UnableToParsePage();
            }
            final List<String> regionNames = documentParser.getRegions();
        }
    }

    public static class UnableToParsePage extends Exception {
    }
}
