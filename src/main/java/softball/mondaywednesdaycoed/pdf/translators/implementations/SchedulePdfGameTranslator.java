package softball.mondaywednesdaycoed.pdf.translators.implementations;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.pdf.readers.interfaces.ScheduleReader;
import softball.mondaywednesdaycoed.pdf.translators.interfaces.GameTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.function.Consumer;

public class SchedulePdfGameTranslator implements GameTranslator {
    private final PDFTextStripper documentParser;
    private final ScheduleReader scheduleReader;

    public SchedulePdfGameTranslator(
            @NotNull final PDFTextStripper documentParser,
            @NotNull final ScheduleReader scheduleReader
    ) {
        this.documentParser = documentParser;
        this.scheduleReader = scheduleReader;
    }

    @Override
    public void translate(@NotNull PDDocument document, @NotNull Consumer<Game> gameConsumer) throws UnableToParsePage {
        try (final StringWriter outputStreamWriter = new StringWriter()) {
            documentParser.writeText(document, outputStreamWriter);
            scheduleReader.read(
                    new BufferedReader(new StringReader(outputStreamWriter.toString())),
                    gameConsumer
            );
        } catch (IOException e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    public static class UnableToParsePage extends Exception {
    }
}
