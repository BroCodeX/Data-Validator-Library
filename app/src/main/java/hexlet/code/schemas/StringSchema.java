package hexlet.code.schemas;


import hexlet.code.Validator;
import lombok.Setter;

public class StringSchema extends BaseSchema<String> {
    private int minLength;
    private String contains;

    @Setter
    private Validator validator;

    public StringSchema() {
        super();
    }

    @Override
    public boolean isValid(String value) {
        return this.getInternalState().stream()
                .allMatch(field -> stateHandler(field, value));
    }

    public StringSchema required() {
        this.getInternalState().add("required");
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        this.getInternalState().add("minLength");
        return this;
    }

    public StringSchema contains(String text) {
        this.contains = text;
        this.getInternalState().add("contains");
        return this;
    }

    private boolean stateHandler(String field, String value) {
        return switch (field) {
            case "required" -> value != null && !value.isEmpty();
            case "contains" -> value.contains(this.contains);
            case "minLength" -> value.length() >= this.minLength;
            default -> throw new RuntimeException("There is no settings for the schema");
        };
    }
}
