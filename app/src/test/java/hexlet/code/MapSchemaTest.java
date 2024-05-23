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
        mapSchema.required();
        assertFalse(mapSchema.isValid(null));

    }

    @Test
    public void sizeTest() {
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        mapSchema.sizeof(2);
        assertFalse(mapSchema.isValid(data));
        data.put("key2", "value2");
        assertTrue(mapSchema.isValid(data));
        assertTrue(mapSchema.isValid(null));
    }

    @Test
    public void shapeStringTest() {
        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string());
        schemas.put("lastName", validator.string().minLength(2));

        mapSchema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(mapSchema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertTrue(mapSchema.isValid(human2));
    }

    @Test
    public void shapeNumTest() {
        Map<String, BaseSchema<Integer>> schemas = new HashMap<>();
        schemas.put("first", validator.number());
        schemas.put("second", validator.number().positive());
        schemas.put("third", validator.number().positive().range(3, 8));

        mapSchema.shape(schemas);

        Map<String, Integer> number1 = new HashMap<>();
        number1.put("first", 8);
        number1.put("second", null);
        number1.put("third", 5);
        assertTrue(mapSchema.isValid(number1));

        Map<String, Integer> number2 = new HashMap<>();
        number2.put("first", 6);
        number2.put("second", -5);
        number1.put("third", 2);
        assertFalse(mapSchema.isValid(number2));
    }
}
