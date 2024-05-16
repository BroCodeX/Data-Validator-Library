package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addValidation("positive", value -> value == null || value >= 1);
        return this;
    }

    public NumberSchema range(int start, int end) {
        addValidation("range", value -> rangeHandler(value, start, end));
        return this;
    }

    private boolean rangeHandler(Integer value, int start, int end) {
        if (value == null) {
            return true;
        }
        if (value > 0) {
            return value >= start && value <= end;
        } else {
            return value <= start && value >= end;
        }
    }
}
