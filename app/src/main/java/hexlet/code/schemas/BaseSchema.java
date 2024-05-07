package hexlet.code.schemas;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    @Getter
    private Map<String, Predicate<T>> internalState;

    public BaseSchema() {
        this.internalState = new HashMap<>();
    }

    public abstract boolean isValid(T value);

    public abstract void addValidation(String rule, Predicate<T> predicate);
}
