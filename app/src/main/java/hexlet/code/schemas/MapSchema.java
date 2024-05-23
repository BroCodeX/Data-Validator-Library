package hexlet.code.schemas;


import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema sizeof(int size) {
        addValidation("sizeof", value -> value == null || value.size() >= size);
        return this;
    }

    public <T> MapSchema shape(Map<String, BaseSchema<T>> schemas) {
        addValidation("shape", value -> schemas.entrySet().stream()
                .allMatch(entry -> {
                    BaseSchema<T> schema = entry.getValue();
                    T val = (T) (value.get(entry.getKey()));
                    return schema.isValid(val);
                }
    ));
        return this;
    }
}
