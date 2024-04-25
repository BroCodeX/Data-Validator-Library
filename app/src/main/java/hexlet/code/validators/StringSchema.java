package hexlet.code.validators;

import hexlet.code.Validator;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class StringSchema implements BaseSchema<String> {
    @Setter
    private Validator validator;

    List<String> internalState;

    private boolean required;
    private int minLength;
    private String contains;

    public StringSchema() {
        this.internalState = new ArrayList<>();
    }

    @Override
    public boolean isValid(String value) {
        return this.internalState.stream()
                .allMatch(field -> stateHandler(field, value));
    }

    public void required() {
        this.required = true;
        this.internalState.add("required");
    }

    public StringSchema minLength(int count) {
        this.minLength = count;
        this.internalState.add("minLength");
        return this;
    }

    public StringSchema contains(String text) {
        this.contains = text;
        this.internalState.add("contains");
        return this;
    }

    private boolean stateHandler(String field, String value) {
        return switch (field) {
            case "required" -> value != null && !value.isEmpty();
            case "contains" -> value.contains(this.contains);
            case "minLength" -> value.length() >= this.minLength;
            default -> false;
        };
    }
}
