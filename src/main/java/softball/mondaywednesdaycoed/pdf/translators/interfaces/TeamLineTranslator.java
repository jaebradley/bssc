package softball.mondaywednesdaycoed.pdf.translators.interfaces;

import softball.mondaywednesdaycoed.data.models.Team;

import java.util.Optional;

public interface TeamLineTranslator {
    Optional<Team> translate(final String line);
}
