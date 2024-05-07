package hexlet.code.schemas;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    private int sizeOf;
    private final Map<String, StringSchema> stringSchemas = new HashMap<>();
    private final Map<Integer, NumberSchema> numberSchemas = new HashMap<>();

    public MapSchema() {
        super();
    }

    public MapSchema required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    @Override
    public boolean isValid(Map<?, ?> value) {
        return this.getInternalState().entrySet().stream()
                .allMatch(field -> field.getValue().test(value));
    }

    @Override
    public void addValidation(String rule, Predicate<Map<?, ?>> predicate) {
        this.getInternalState().put(rule, predicate);
    }


    public MapSchema sizeof(int size) {
        this.sizeOf = size;
        addValidation("sizeof", value -> value.size() >= this.sizeOf);
        return this;
    }

    public void shape(Map<?, ?> schemas) {
        addValidation("shape", this::shapeHandler);
        schemas.forEach((key, value) -> {
            if (key instanceof String) {
                this.stringSchemas.put(key.toString(), (StringSchema) value);
            } else if (key instanceof Integer) {
                this.numberSchemas.put((Integer) key, (NumberSchema) value);
            } else {
                throw new RuntimeException("Unknown instance");
            }
        });
    }


    public boolean shapeHandler(Map<?, ?> value) {
        return value.entrySet().stream()
                .allMatch(entry -> {
                    boolean result = false;
                    if (entry.getKey() instanceof String) {
                        Map<String, String> stringInstance = new HashMap<>();
                        stringInstance.put(entry.getKey().toString(),
                                entry.getValue() == null ? null : entry.getValue().toString());
                        result = shapeStringHandler(stringInstance);
                    } else if (entry.getKey() instanceof Integer) {
                        Map<Integer, Integer> integerInstance = new HashMap<>();
                        integerInstance.put((Integer) entry.getKey(),
                                entry.getValue() == null ? null : (Integer) entry.getValue());
                        result = shapeIntegerHandler(integerInstance);
                    }
                    return result;
                });
    }

    public boolean shapeStringHandler(Map<String, String> value) {
        for (Map.Entry<String, String> entry : value.entrySet()) {
            String key = entry.getKey();
            StringSchema check = this.stringSchemas.getOrDefault(key, null);
            return check.isValid(entry.getValue());
        }
        return true;
    }

    public boolean shapeIntegerHandler(Map<Integer, Integer> value) {
        for (Map.Entry<Integer, Integer> entry : value.entrySet()) {
            Integer key = entry.getKey();
            NumberSchema check = this.numberSchemas.getOrDefault(key, null);
            return check.isValid(entry.getValue());
        }
        return true;
    }
}
