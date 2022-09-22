package softball.mondaywednesdaycoed.pdf.translators.implementations;

import softball.mondaywednesdaycoed.data.models.Captain;
import softball.mondaywednesdaycoed.data.models.Team;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamLineTranslator implements softball.mondaywednesdaycoed.pdf.translators.interfaces.TeamLineTranslator {
    private static final Map<String, Team.Color> colorsByName = Stream.of(
            Map.entry("Maroon", Team.Color.Maroon),
            Map.entry("Neon Pink", Team.Color.Neon_Pink)
    ).collect(
            Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
            )
    );

    @Override
    public Optional<Team> translate(final String line) {
        // TODO: refactor all of this
        int startingIndex = 0;
        final String firstName;
        {
            Optional<Integer> nameStartIndex = Optional.empty();
            Optional<Integer> nameEndIndex = Optional.empty();
            for (int captainFirstNameIndex = 0; captainFirstNameIndex < line.length(); captainFirstNameIndex += 1) {
                final char currentCharacter = line.charAt(captainFirstNameIndex);
                if (Character.isAlphabetic(currentCharacter)) {
                    if (nameStartIndex.isEmpty()) {
                        nameStartIndex = Optional.of(captainFirstNameIndex);
                    }
                }

                if (!Character.isAlphabetic(currentCharacter) && nameStartIndex.isPresent()) {
                    nameEndIndex = Optional.of(captainFirstNameIndex);
                    startingIndex = captainFirstNameIndex;
                    break;
                }
            }

            if (nameStartIndex.isEmpty() || nameEndIndex.isEmpty()) {
                throw new RuntimeException();
            }

            firstName = line.substring(nameStartIndex.get(), nameEndIndex.get());
        }

        final String lastName;
        {
            Optional<Integer> nameStartIndex = Optional.empty();
            Optional<Integer> nameEndIndex = Optional.empty();
            for (int captainFirstNameIndex = startingIndex; captainFirstNameIndex < line.length(); captainFirstNameIndex += 1) {
                final char currentCharacter = line.charAt(captainFirstNameIndex);
                if (Character.isAlphabetic(currentCharacter)) {
                    if (nameStartIndex.isEmpty()) {
                        nameStartIndex = Optional.of(captainFirstNameIndex);
                    }
                }

                if (!Character.isAlphabetic(currentCharacter) && nameStartIndex.isPresent()) {
                    nameEndIndex = Optional.of(captainFirstNameIndex);
                    startingIndex = captainFirstNameIndex;
                    break;
                }
            }

            if (nameStartIndex.isEmpty() || nameEndIndex.isEmpty()) {
                throw new RuntimeException();
            }

            lastName = line.substring(nameStartIndex.get(), nameEndIndex.get());
        }

        int endingIndex = line.length() - 1;
        final Team.Color color;
        {
            Optional<Integer> colorEndIndex = Optional.empty();

            for (int colorIndex = endingIndex; colorIndex > startingIndex; colorIndex -= 1) {
                final char currentCharacter = line.charAt(colorIndex);
                if (Character.isAlphabetic(currentCharacter)) {
                    if (colorEndIndex.isEmpty()) {
                        colorEndIndex = Optional.of(colorIndex);
                        break;
                    }
                }
            }

            if (colorEndIndex.isEmpty()) {
                throw new RuntimeException();
            }

            final Optional<Integer> finalColorEndIndex = colorEndIndex;
            color = colorsByName
                    .entrySet()
                    .stream()
                    .filter(e -> e.getKey().equals(line.substring(finalColorEndIndex.get() - e.getKey().length() + 1, 1 + finalColorEndIndex.get())))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElseThrow();

            endingIndex = colorsByName
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().equals(color))
                    .map(Map.Entry::getKey)
                    .map(String::length)
                    .map(l -> finalColorEndIndex.get() - l)
                    .findFirst()
                    .orElseThrow();
        }

        if (startingIndex >= endingIndex) {
            throw new RuntimeException();
        }

        final String teamName = line.substring(startingIndex, endingIndex).trim();

        return Optional.of(
                new Team(
                        teamName,
                        new Captain(firstName, lastName),
                        color
                )
        );
    }
}
