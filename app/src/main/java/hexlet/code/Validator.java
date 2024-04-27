package hexlet.code;

import hexlet.code.validators.BaseSchemaClass;
import hexlet.code.validators.MapSchema;
import hexlet.code.validators.NumberSchema;
import hexlet.code.validators.StringSchema;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Validator {
    private List<BaseSchemaClass> schemas;

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

    public MapSchema mapSchema() {
        MapSchema mapSchema = new MapSchema();
        this.schemas.add(mapSchema);
        mapSchema.setValidator(this);
        return mapSchema;
    }

    public void removeSchema(BaseSchemaClass schemaClass) {
        this.schemas.remove(schemaClass);
        try {
            Field field = schemaClass.getClass().getDeclaredField("validator");
            field.setAccessible(true);
            field.set(schemaClass, null);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
