package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema required() {
        addValidation("required", value -> value != null && !value.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        addValidation("minLength", value -> value == null || value.length() >= length);
        return this;
    }

    public StringSchema contains(String text) {
        addValidation("contains", value -> value == null || value.contains(text));
        return this;
    }
}
