package hexlet.code.schemas;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSchema<T> {

    @Getter
    private List<String> internalState;

    public BaseSchema() {
        this.internalState = new ArrayList<>();
    }

    public abstract BaseSchema<T> required();

    public abstract boolean isValid(T value);
}
