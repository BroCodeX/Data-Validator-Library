package hexlet.code.validators;

import hexlet.code.Validator;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class NumberSchema implements BaseSchema<Integer> {
    @Setter
    private Validator validator;

    List<String> internalState;

    private boolean required = false;
    private boolean positive;
    private int[] range;

    public NumberSchema() {
        this.internalState = new ArrayList<>();
    }

    @Override
    public boolean isValid(Integer value) {
        return this.internalState.stream()
                .allMatch(field -> stateHandler(field, value));
    }

    public void required() {
        this.required = true;
        this.internalState.add("required");
    }

    public NumberSchema positive() {
        this.positive = true;
        this.internalState.add("positive");
        return this;
    }

    public NumberSchema range(int start, int end) {
        this.range = new int[2];
        this.range[0] = start;
        this.range[1] = end;
        this.internalState.add("range");
        return this;
    }

    private boolean falseRequired(int length, String cText, String value) {
        if (value == null || !positive) {
            return true;
        }
        return value.contains(cText) && value.length() >= length;
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
