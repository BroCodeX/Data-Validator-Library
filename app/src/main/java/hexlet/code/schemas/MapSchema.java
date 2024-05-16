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

    public void shape(Map<?, ?> schemas) {
        addValidation("shape", value -> shapeHandler(value, schemas));
    }

    public boolean shapeHandler(Map<?, ?> value, Map<?, ?> schemas) {
        return value.entrySet().stream()
                .allMatch(entry -> {
                    boolean result = false;
                    if (entry.getKey() instanceof String) {
                        String key = entry.getKey().toString();
                        String val = entry.getValue() == null ? null : entry.getValue().toString();
                        StringSchema check = (StringSchema) schemas.get(key);
                        result = check.isValid(val);
                    } else if (entry.getKey() instanceof Integer) {
                        Integer key = (Integer) entry.getKey();
                        Integer val = entry.getValue() == null ? null : (Integer) entry.getValue();
                        NumberSchema check = (NumberSchema) schemas.get(key);
                        result = check.isValid(val);
                    }
                    return result;
                });
    }
}
