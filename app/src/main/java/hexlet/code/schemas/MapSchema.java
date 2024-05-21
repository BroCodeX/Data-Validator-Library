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

//    !!! Вариант со значениями
    private <K, V> boolean shapeHandler(Map<?, ?> value, Map<K, BaseSchema<V>> schemas) throws RuntimeException {
        return value.entrySet().stream()
                .allMatch(entry -> {
                    K key = (K) entry.getKey();
                    V val = (V) (entry.getValue());
                    BaseSchema<V> check = schemas.get(key);
                    return check.isValid(val);
                });
    }

//    !!! Вариант с проверкой через схему
//    private <T> boolean shapeHandler(Map<?, ?> value, Map<?, BaseSchema<T>> schemas) throws RuntimeException {
//        return schemas.entrySet().stream()
//                .allMatch(entry -> {
//                    BaseSchema<T> schema = entry.getValue();
//                    T val = (T) (value.get(entry.getKey())) ;
//                    return schema.isValid(val);
//                });
//    }
}
