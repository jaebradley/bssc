package softball.mondaywednesdaycoed.pdf.translators.implementations;

import data.models.UnorderedPair;
import data.serialization.implementation.AbbreviatedDayOfWeekSerializationUtility;
import data.serialization.implementation.MiddleEndianMonthDaySerializationUtility;
import data.serialization.implementation.TwelveHourClockPeriodSerializationUtility;
import data.serialization.implementation.TwelveHourClockTimeSerializationUtility;
import junit.framework.TestCase;
import softball.mondaywednesdaycoed.data.models.Captain;
import softball.mondaywednesdaycoed.data.models.Game;
import softball.mondaywednesdaycoed.data.models.Team;

import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class GameLineTranslatorTest extends TestCase {

    public void test() {
        assertEquals(
                new Game(
                        Game.Location.Donnelly_Field,
                        Instant.parse("2022-09-12T18:45:00.00-04:00").atZone(ZoneId.of("America/New_York")),
                        new UnorderedPair<>(
                                new Team("See Ya!", new Captain("Eric", "Maus"), Team.Color.Neon_Pink),
                                new Team("Ranger Danger", new Captain("Jae", "Bradley"), Team.Color.Gold)
                        )
                ),
                new GameLineTranslator(
                        ZoneId.of("America/New_York"),
                        Set.of(
                                new Team("See Ya!", new Captain("Eric", "Maus"), Team.Color.Neon_Pink),
                                new Team("Ranger Danger", new Captain("Jae", "Bradley"), Team.Color.Gold)
                        ),
                        Year.of(2022),
                        AbbreviatedDayOfWeekSerializationUtility.getInstance(),
                        MiddleEndianMonthDaySerializationUtility.getInstance(),
                        TwelveHourClockTimeSerializationUtility.getInstance(),
                        TwelveHourClockPeriodSerializationUtility.getInstance()
                ).translate(
                        "Mon 9/12 Donnelly Cambridge 6:45 PM Maus Bradley A/B "
                ).orElseThrow()
        );
    }
}