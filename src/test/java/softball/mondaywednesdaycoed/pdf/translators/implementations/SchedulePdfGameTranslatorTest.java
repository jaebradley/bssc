package softball.mondaywednesdaycoed.pdf.translators.implementations;

import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.junit.Test;
import softball.mondaywednesdaycoed.data.models.Game;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class SchedulePdfGameTranslatorTest {

    @Test
    public void test() {
        try (final InputStream inputStream = getClass().getResourceAsStream("/softball/mondaywednesdaycoed/FALL Mon-Wed 2022 Schedule.pdf")) {
            final PDFParser parser = new PDFParser(new RandomAccessReadBuffer(inputStream));
            final PDDocument document = parser.parse();
            final Set<Game> games = new HashSet<>();
            new SchedulePdfGameTranslator(new PDFTextStripper()).translate(document, games::add);
            Assert.assertFalse(games.isEmpty());
        } catch (IOException | SchedulePdfGameTranslator.UnableToParsePage e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}