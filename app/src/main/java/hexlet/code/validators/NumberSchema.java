package hexlet.code.validators;

import java.util.List;


public class NumberSchema extends BaseSchemaClass<Integer> {
    private boolean positive;
    private int[] range;

    public NumberSchema() {
        super();
    }

    @Override
    public boolean isValid(Integer value) {
        return this.getInternalState().stream()
                .allMatch(field -> stateHandler(field, value));
    }
    public void required() {
        super.required();
    }

    public NumberSchema positive() {
        this.positive = true;
        this.getInternalState().add("positive");
        return this;
    }

    public NumberSchema range(int start, int end) {
        this.range = new int[2];
        this.range[0] = start;
        this.range[1] = end;
        this.getInternalState().add("range");
        return this;
    }

    private boolean stateHandler(String field, Integer value) {
        return switch (field) {
            case "required" -> value != null;
            case "positive" -> value == null || value >= 1;
            case "range" -> rangeHandler(value);
            default -> false;
        };
    }

    private boolean rangeHandler(int value) {
        if (value > 0) {
            return value >= this.range[0] && value <= this.range[1];
        } else {
            return value <= this.range[0] && value >= this.range[1];
        }
    }
}
