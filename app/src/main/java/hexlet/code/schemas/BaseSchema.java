package hexlet.code.schemas;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    private Map<String, Predicate<T>> internalState;

    public BaseSchema() {
        this.internalState = new HashMap<>();
    }

    public boolean isValid(T value) {
        return this.internalState.entrySet().stream()
                .allMatch(entry -> entry.getValue().test(value));
    }

    public void addValidation(String rule, Predicate<T> predicate) {
        this.internalState.put(rule, predicate);
    }
}
