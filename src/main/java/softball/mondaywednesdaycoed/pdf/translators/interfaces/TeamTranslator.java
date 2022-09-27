package softball.mondaywednesdaycoed.pdf.translators.interfaces;

import softball.mondaywednesdaycoed.data.models.Team;

public interface TeamTranslator {
    class UnableToTranslateTeam extends Exception {
    }

    Team translate(String line) throws UnableToTranslateTeam;
}
