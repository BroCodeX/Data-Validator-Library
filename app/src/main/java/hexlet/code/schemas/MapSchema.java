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

    public <T> MapSchema shape(Map<T, BaseSchema<T>> schemas) {
        addValidation("shape", value -> shapeHandler(value, schemas));
        return this;
    }

    public <T> boolean shapeHandler(Map<?, ?> value, Map<T, BaseSchema<T>> schemas) {
        return value.entrySet().stream()
                .allMatch(entry -> {
                    T key = (T) entry.getKey();
                    T val = (T) (entry.getValue() == null ? null : entry.getValue());
                    BaseSchema<T> check = schemas.get(key);
                    return check.isValid(val);


//                    if (entry.getKey() instanceof String) {
//                        String key = entry.getKey().toString();
//                        String val = entry.getValue() == null ? null : entry.getValue().toString();
//                        StringSchema check = (StringSchema) schemas.get(key);
//                        result = check.isValid(val);
//                    } else if (entry.getKey() instanceof Integer) {
//                        Integer key = (Integer) entry.getKey();
//                        Integer val = entry.getValue() == null ? null : (Integer) entry.getValue();
//                        NumberSchema check = (NumberSchema) schemas.get(key);
//                        result = check.isValid(val);
//                    }
                    //return result;
                });
    }
}
