package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public final class Validator {
    @Getter
    private List<BaseSchema<?>> schemas;

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

    public void removeSchema(final BaseSchema<?> schema) {
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
