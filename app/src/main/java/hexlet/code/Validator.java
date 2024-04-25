package hexlet.code;

import hexlet.code.validators.BaseSchema;
//import hexlet.code.validators.NumberSchema;
import hexlet.code.validators.NumberSchema;
import hexlet.code.validators.StringSchema;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Validator {
    @Getter
    private List<BaseSchema> schemas;

    Validator() {
        this.schemas = new ArrayList<>();
    }

    public StringSchema string() {
        StringSchema stringSchema = new StringSchema();
        this.schemas.add(stringSchema);
        stringSchema.setValidator(this);
        return stringSchema;
    }

    public NumberSchema number() {
        NumberSchema numberSchema = new NumberSchema();
        this.schemas.add(numberSchema);
        numberSchema.setValidator(this);
        return numberSchema;
    }

    public void removeSchema(BaseSchema schema) {
        try {
            Field field = schema.getClass().getDeclaredField("validator");
            field.setAccessible(true);
            field.set(schema, null);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        this.schemas.remove(schema);
    }

}
