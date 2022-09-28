package data.serialization.implementation;

import data.serialization.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class EnumeratedValuesSerializationUtility<T extends Enum<T>> implements Deserializer<T> {
    @NotNull
    private final Map<String, T> valuesBySerialization;

    public EnumeratedValuesSerializationUtility(@NotNull final EnumMap<T, String> serializedValuesByEnumeratedValue) {
        this.valuesBySerialization = serializedValuesByEnumeratedValue.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    @Override
    public Optional<T> deserialize(@NotNull final String serializedValue) {
        return Optional.ofNullable(valuesBySerialization.get(serializedValue));
    }
}
