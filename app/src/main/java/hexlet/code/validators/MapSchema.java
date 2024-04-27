package hexlet.code.validators;

import hexlet.code.Validator;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapSchema extends BaseSchemaClass<Map<Object, Object>>{
    @Setter
    private Validator validator;
    private int sizeOf;
    private boolean required;

    List<String> internalState;

    public MapSchema() {
        this.internalState = new ArrayList<>();
    }

    public void required() {
        this.required = true;
        this.internalState.add("required");
    }

    public void sizeOf(int size) {

    }

    @Override
    public boolean isValid(Map<Object, Object> value) {
        return false;
    }
}
