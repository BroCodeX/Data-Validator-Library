package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class MapSchemaTest {
    private MapSchema mapSchema;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new Validator();
        mapSchema = validator.map();
    }

    @Test
    public void requiredTest() {
        assertTrue(mapSchema.isValid(null));
        assertFalse(mapSchema.required().isValid(null));

    }

    @Test
    public void sizeTest() {
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertFalse(mapSchema.sizeof(2).isValid(data));
        data.put("key2", "value2");
        assertTrue(mapSchema.required().isValid(data));
    }

    @Test
    public void shapeStringTest() {
        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        mapSchema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(mapSchema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(mapSchema.isValid(human2));
    }

    @Test
    public void shapeNumTest() {
        Map<Integer, BaseSchema<Integer>> schemas = new HashMap<>();
        schemas.put(5, validator.number().required());
        schemas.put(10, validator.number().positive());
        schemas.put(15, validator.number().required().positive().range(3, 8));

        mapSchema.shape(schemas);

        Map<Integer, Integer> number1 = new HashMap<>();
        number1.put(5, 8);
        number1.put(10, null);
        number1.put(15, 5);
        assertTrue(mapSchema.isValid(number1));

        Map<Integer, Integer> number2 = new HashMap<>();
        number2.put(5, 6);
        number2.put(10, -5);
        number1.put(15, 2);
        assertFalse(mapSchema.isValid(number2));
    }
}
