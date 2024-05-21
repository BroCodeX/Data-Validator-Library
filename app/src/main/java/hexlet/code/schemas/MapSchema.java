package hexlet.code.schemas;


import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        addValidation("sizeof", value -> value == null || value.size() >= size);
        return this;
    }

    public <K, V> MapSchema shape(Map<K, BaseSchema<V>> schemas) {
        addValidation("shape", value -> shapeHandler(value, schemas));
        return this;
    }

    private <K, V> boolean shapeHandler(Map<?, ?> value, Map<K, BaseSchema<V>> schemas) {
        return value.entrySet().stream()
                .allMatch(entry -> {
                    K key = (K) entry.getKey();
                    V val = (V) (entry.getValue() == null ? null : entry.getValue());
                    BaseSchema<V> check = schemas.get(key);
                    return check.isValid(val);
                });
    }
}
