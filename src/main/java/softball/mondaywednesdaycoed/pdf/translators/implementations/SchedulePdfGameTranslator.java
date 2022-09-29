package softball.mondaywednesdaycoed.pdf.translators.implementations;

import data.serialization.implementation.AbbreviatedDayOfWeekSerializationUtility;
import data.serialization.implementation.MiddleEndianMonthDaySerializationUtility;
import data.serialization.implementation.TwelveHourClockPeriodSerializationUtility;
import data.serialization.implementation.TwelveHourClockTimeSerializationUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.data.models.Team;
import softball.mondaywednesdaycoed.pdf.readers.implementation.ScheduleReader;
import softball.mondaywednesdaycoed.pdf.readers.implementation.TeamsReader;
import softball.mondaywednesdaycoed.pdf.translators.interfaces.GameTranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.Year;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SchedulePdfGameTranslator implements GameTranslator {
    private final PDFTextStripper documentParser;

    public SchedulePdfGameTranslator(
            @NotNull final PDFTextStripper documentParser
    ) {
        this.documentParser = documentParser;
    }

    @Override
    public void translate(@NotNull PDDocument document, @NotNull Consumer<Game> gameConsumer) throws UnableToParsePage {
        try (final StringWriter outputStreamWriter = new StringWriter()) {
            documentParser.writeText(document, outputStreamWriter);
            final Set<Team> teams = new HashSet<>();
            final TeamsReader teamsReader = new TeamsReader(
                    new TeamLineTranslator()
            );
            teamsReader.read(
                    new BufferedReader(new StringReader(outputStreamWriter.toString())),
                    teams::add
            );

            final ScheduleReader scheduleReader = new ScheduleReader(
                    new GameLineTranslator(
                            ZoneId.of("America/New_York"),
                            teams,
                            Year.of(2022),
                            AbbreviatedDayOfWeekSerializationUtility.getInstance(),
                            MiddleEndianMonthDaySerializationUtility.getInstance(),
                            TwelveHourClockTimeSerializationUtility.getInstance(),
                            TwelveHourClockPeriodSerializationUtility.getInstance())
            );
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
