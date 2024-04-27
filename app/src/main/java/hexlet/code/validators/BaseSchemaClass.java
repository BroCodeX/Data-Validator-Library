package hexlet.code.validators;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSchemaClass<T> {

    @Getter
    private List<String> internalState;

    public BaseSchemaClass() {
        this.internalState = new ArrayList<>();
    }

    public void required() {
        this.internalState.add("required");
    }

    abstract boolean isValid(T value);
}
