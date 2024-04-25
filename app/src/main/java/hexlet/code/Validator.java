package hexlet.code;

import hexlet.code.validators.Schema;
import hexlet.code.validators.StringSchema;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Validator {
    @Getter
    private List<Schema> schemas;

    Validator() {
        this.schemas = new ArrayList<>();
    }

    public StringSchema string() {
        StringSchema stringSchema = new StringSchema();
        this.schemas.add(stringSchema);
        try {
            Field field = stringSchema.getClass().getDeclaredField("validator");
            field.setAccessible(true);
            field.set(stringSchema, this);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return stringSchema;
    }

    public void removeSchema(Schema schema) {
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
