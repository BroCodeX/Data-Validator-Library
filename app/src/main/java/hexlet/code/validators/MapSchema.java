package hexlet.code.validators;

import hexlet.code.Validator;
import lombok.Setter;

import java.util.Map;

public class MapSchema<T> extends BaseSchema<Map<T, T>> {
    private int sizeOf;

    @Setter
    private Validator validator;

    public MapSchema() {
        super();
    }

    public BaseSchema required() {
        this.getInternalState().add("required");
        return this;
    }

    @Override
    public boolean isValid(Map<T, T> value) {
        return this.getInternalState().stream()
                .allMatch(field -> stateHandler(field, value));
    }

    public void sizeof(int size) {
        this.sizeOf = size;
        this.getInternalState().add("sizeof");
    }

    private boolean stateHandler(String field, Map<T, T> value) {
        return switch (field) {
            case "required" -> value != null;
            case "sizeof" -> value.size() >= this.sizeOf;
            default -> false;
        };
    }

    public void shape(Map<T, BaseSchema<T>> schemas) {

    }
}
