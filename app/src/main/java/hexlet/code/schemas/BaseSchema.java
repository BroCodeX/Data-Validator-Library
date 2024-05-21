package hexlet.code.schemas;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    private Map<String, Predicate<T>> internalState;

    public BaseSchema() {
        this.internalState = new HashMap<>();
    }

    public final boolean isValid(T value) {
        return this.internalState.entrySet().stream()
                .allMatch(entry -> entry.getValue().test(value));
    }

    public final void addValidation(String rule, Predicate<T> predicate) {
        this.internalState.put(rule, predicate);
    }

    /**
     * Subclasses can override this method to provide additional behavior.
     *
     * @return BaseSchema instance
     */
    public BaseSchema<T> required() {
        addValidation("required", Objects::nonNull);
        return this;
    }
}
