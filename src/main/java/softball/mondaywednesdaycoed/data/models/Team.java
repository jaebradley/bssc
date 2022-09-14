package softball.mondaywednesdaycoed.data.models;

import org.jetbrains.annotations.NotNull;

public record Team(
        @NotNull String name,
        @NotNull Captain captain,
        @NotNull Color color
) {
    public enum Color {
        Maroon,
        Neon_Pink,
        Navy,
        Gold,
        Black,
        Light_Blue,
        Neon_Blue,
        Purple,
        Charcoal,
        Vegas_Gold,
        Orange,
        Royal,
    }
}
