package hexlet.code.schemas;


import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema sizeof(int size) {
        addValidation("sizeof", value -> value == null || value.size() >= size);
        return this;
    }

    public <T> MapSchema shape(Map<?, BaseSchema<T>> schemas) {
        addValidation("shape", value -> shapeHandler(value, schemas));
        return this;
    }

    private <T> boolean shapeHandler(Map<?, ?> value, Map<?, BaseSchema<T>> schemas) throws RuntimeException {
        return schemas.entrySet().stream()
                .allMatch(entry -> {
                    BaseSchema<T> schema = entry.getValue();
                    T val = (T) (value.get(entry.getKey())) ;
                    return schema.isValid(val);
                });
    }
}
