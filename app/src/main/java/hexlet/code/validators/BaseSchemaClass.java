package hexlet.code.validators;

import hexlet.code.Validator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSchemaClass<T> {
    private boolean required;

    @Getter
    private List<String> internalState;

    public BaseSchemaClass() {
        this.internalState = new ArrayList<>();
    }

    public void required() {
        this.required = true;
        this.internalState.add("required");
    }

    abstract boolean isValid(T value);
}
