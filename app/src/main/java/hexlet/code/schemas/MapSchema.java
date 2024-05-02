package hexlet.code.schemas;

import hexlet.code.Validator;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    private int sizeOf;
    private Map<String, StringSchema> stringSchemas = new HashMap<>();
    private Map<Integer, NumberSchema> numberSchemas = new HashMap<>();

    @Setter
    private Validator validator;

    public MapSchema() {
        super();
    }

    public MapSchema required() {
        this.getInternalState().add("required");
        return this;
    }

    @Override
    public boolean isValid(Map<?, ?> value) {
        return this.getInternalState().stream()
                .allMatch(field -> stateHandler(field, value));
    }

    public MapSchema sizeof(int size) {
        this.sizeOf = size;
        this.getInternalState().add("sizeof");
        return this;
    }

    private boolean stateHandler(String field, Map<?, ?> value) {
        return switch (field) {
            case "required" -> value != null;
            case "sizeof" -> value.size() >= this.sizeOf;
            case "shape" -> shapeHandler(value);
            default -> throw new RuntimeException("There is no settings for the schema");
        };
    }

    public void shape(Map<?, ?> schemas) {
        this.getInternalState().add("shape");
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
                        Map<String, String> valueString = new HashMap<>();
                        valueString.put(entry.getKey().toString(),
                                entry.getValue() == null ? null : entry.getValue().toString());
                        result = shapeStringHandler(valueString);
                    } else if (entry.getKey() instanceof Integer) {
                        Map<Integer, Integer> valueInt = new HashMap<>();
                        valueInt.put((Integer) entry.getKey(),
                                entry.getValue() == null ? null : (Integer) entry.getValue());
                        result = shapeIntegerHandler(valueInt);
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
