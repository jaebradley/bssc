package softball.mondaywednesdaycoed.pdf.translators.implementations;

import softball.mondaywednesdaycoed.data.models.Team;

import java.util.Optional;

public class TeamLineTranslator implements softball.mondaywednesdaycoed.pdf.translators.interfaces.TeamLineTranslator {
    @Override
    public Optional<Team> translate(String line) {
        return Optional.empty();
    }
}
