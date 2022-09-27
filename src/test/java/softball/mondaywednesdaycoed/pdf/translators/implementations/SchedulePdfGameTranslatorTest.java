package softball.mondaywednesdaycoed.pdf.translators.implementations;

import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.junit.Test;
import softball.mondaywednesdaycoed.csv.writers.google.calendar.implementation.EventsFileWriter;
import softball.mondaywednesdaycoed.data.models.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SchedulePdfGameTranslatorTest {

    @Test
    public void test() {
        try (final InputStream inputStream = getClass().getResourceAsStream("/softball/mondaywednesdaycoed/FALL Mon-Wed 2022 Schedule.pdf")) {
            final PDFParser parser = new PDFParser(new RandomAccessReadBuffer(inputStream));
            final PDDocument document = parser.parse();
            final Set<Game> games = new HashSet<>();
            new SchedulePdfGameTranslator(new PDFTextStripper()).translate(document, games::add);
            Assert.assertFalse(games.isEmpty());

            final File eventsFile = new File("/tmp/events.csv");
            eventsFile.delete();
            final boolean createdNewFile = eventsFile.createNewFile();
            if (!createdNewFile) {
                throw new RuntimeException("unexpected");
            }

            final EventsFileWriter eventsFileWriter = new EventsFileWriter(ZoneId.of("America/New_York"));
            eventsFileWriter.write(eventsFile, games.stream().filter(g -> g.teams().getValue1().name().equals("Ranger Danger") || g.teams().getValue2().name().equals("Ranger Danger")).collect(Collectors.toSet()));
        } catch (IOException | SchedulePdfGameTranslator.UnableToParsePage e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}