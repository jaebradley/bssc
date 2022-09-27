package softball.mondaywednesdaycoed.pdf.translators.interfaces;

import softball.mondaywednesdaycoed.data.models.Game;

import java.util.Optional;

public interface GameLineTranslator {
    Optional<Game> translate(String line);
}
