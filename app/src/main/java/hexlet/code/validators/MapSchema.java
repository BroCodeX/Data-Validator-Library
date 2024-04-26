package hexlet.code.validators;

import java.util.Map;

public class MapSchema<T> implements BaseSchema<Map<T, T>>{
    @Override
    public boolean isValid(Map<T, T> value) {
        return false;
    }
}
