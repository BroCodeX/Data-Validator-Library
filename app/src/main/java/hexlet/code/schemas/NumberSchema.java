package hexlet.code.schemas;


public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema positive() {
        addValidation("positive", value -> value == null || value >= 1);
        return this;
    }

    public NumberSchema range(int start, int end) {
        addValidation("range", value -> value == null ||  value >= start && value <= end);
        return this;
    }
}
