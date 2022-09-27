package softball.mondaywednesdaycoed.pdf.translators.implementations;

import junit.framework.TestCase;
import softball.mondaywednesdaycoed.data.models.Captain;
import softball.mondaywednesdaycoed.data.models.Team;

import java.util.Optional;

public class TeamLineTranslatorTest extends TestCase {

    public void test() {
        assertEquals(
                Optional.of(
                        new Team(
                                "Dirt Devils",
                                new Captain("Jake", "Aldrich"),
                                Team.Color.Maroon
                        )
                ),
                new TeamLineTranslator().translate("1 Jake Aldrich Dirt Devils Maroon 1 0 1 4")
        );
    }
}