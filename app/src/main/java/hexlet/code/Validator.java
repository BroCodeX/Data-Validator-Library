package hexlet.code;

import hexlet.code.validators.BaseSchema;
import hexlet.code.validators.MapSchema;
import hexlet.code.validators.NumberSchema;
import hexlet.code.validators.StringSchema;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Validator {
    @Getter
    private List<BaseSchema> schemas;

    public Validator() {
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

    public MapSchema map() {
        MapSchema mapSchema = new MapSchema();
        this.schemas.add(mapSchema);
        mapSchema.setValidator(this);
        return mapSchema;
    }

    public void removeSchema(BaseSchema schema) {
        this.schemas.remove(schema);
        try {
            Field field = schema.getClass().getDeclaredField("validator");
            field.setAccessible(true);
            field.set(schema, null);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
