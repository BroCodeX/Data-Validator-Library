package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {
    private int[] range;

    public NumberSchema() {
        super();
    }

    @Override
    public boolean isValid(Integer value) {
        return this.getInternalState().entrySet().stream()
                .allMatch(entry -> entry.getValue().test(value));
    }

    @Override
    public void addValidation(String rule, Predicate<Integer> predicate) {
        this.getInternalState().put(rule, predicate);
    }


    public NumberSchema required() {
        addValidation("required", Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addValidation("positive", value -> value == null || value >= 1);
        return this;
    }

    public NumberSchema range(int start, int end) {
        this.range = new int[2];
        this.range[0] = start;
        this.range[1] = end;
        addValidation("range", this::rangeHandler);
        return this;
    }

    private boolean rangeHandler(int value) {
        if (value > 0) {
            return value >= this.range[0] && value <= this.range[1];
        } else {
            return value <= this.range[0] && value >= this.range[1];
        }
    }
}
