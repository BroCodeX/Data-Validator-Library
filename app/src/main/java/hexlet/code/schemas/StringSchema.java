package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {
    private int minLength;
    private String contains;

    @Override
    public boolean isValid(String value) {
        return this.getInternalState().entrySet().stream()
                .allMatch(entry -> entry.getValue().test(value));
    }

    @Override
    public void addValidation(String rule, Predicate<String> predicate) {
        this.getInternalState().put(rule, predicate);
    }

    public StringSchema required() {
        addValidation("required", value -> value != null && !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        addValidation("minLength", value -> value == null || value.length() >= this.minLength);
        return this;
    }

    public StringSchema contains(String text) {
        this.contains = text;
        addValidation("contains", value -> value == null || value.contains(this.contains));
        return this;
    }
}
