package softball.mondaywednesdaycoed.data.models;

import org.jetbrains.annotations.NotNull;

public record Captain(
        @NotNull String firstName,
        @NotNull String lastName
) {
}
