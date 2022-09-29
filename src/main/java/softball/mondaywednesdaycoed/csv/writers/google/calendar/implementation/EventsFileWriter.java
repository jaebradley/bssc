package softball.mondaywednesdaycoed.csv.writers.google.calendar.implementation;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.jetbrains.annotations.NotNull;
import softball.mondaywednesdaycoed.data.models.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class EventsFileWriter implements softball.mondaywednesdaycoed.csv.writers.google.calendar.interfaces.EventsFileWriter {
    // From https://support.google.com/calendar/answer/37118?hl=en&co=GENIE.Platform%3DDesktop#zippy=%2Ccreate-or-edit-a-csv-file
    private static final String[] HEADER = new String[]{"Subject", "Start Date", "Start Time", "End Date", "End Time", "All Day Event", "Description", "Location", "Private"};

    private static final Map<Game.Location, String> ADDRESS_BY_LOCATION = Map.of(
            Game.Location.Library_1, "Library Park, Harrison Ave, Woburn, MA 01801",
            Game.Location.Library_2, "Library Park, Harrison Ave, Woburn, MA 01801",
            Game.Location.Rogers_Park, "Rogers Park, 30 Rogers Park Ave, Boston, MA 02135",
            Game.Location.Donnelly_Field, "Donnelly Field, York St & Berkshire St, Cambridge, MA 02141",
            Game.Location.Murray_Field, "Murray Field, 30-32 Lincoln St, Boston, MA 02135"
    );

    @NotNull
    private final ZoneId eventTimeZone;

    public EventsFileWriter(@NotNull final ZoneId eventTimeZone) {
        this.eventTimeZone = eventTimeZone;
    }

    @Override
    public void write(@NotNull final File file, @NotNull final Set<Game> games) {
        try (final FileWriter fileWriter = new FileWriter(file)) {
            final ICSVWriter csvWriter = new CSVWriterBuilder(fileWriter)
                    .withSeparator(',')
                    .build();
            csvWriter.writeNext(HEADER);

            for (final Game game : games) {
                final ZonedDateTime startTime = game.startTime().withZoneSameInstant(eventTimeZone);
                csvWriter.writeNext(new String[]{
                        String.format("%s vs. %s", game.teams().getValue1().name(), game.teams().getValue2().name()),
                        startTime.format(DateTimeFormatter.ISO_DATE),
                        startTime.format(DateTimeFormatter.ISO_LOCAL_TIME),
                        startTime.plusMinutes(75).format(DateTimeFormatter.ISO_DATE),
                        startTime.plusMinutes(75).format(DateTimeFormatter.ISO_LOCAL_TIME),
                        Boolean.FALSE.toString(),
                        "",
                        Optional.ofNullable(ADDRESS_BY_LOCATION.get(game.location())).orElseThrow(),
                        Boolean.FALSE.toString()
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
