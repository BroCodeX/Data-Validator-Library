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
    public void mapTests() {
        assertTrue(mapSchema.isValid(null));

        mapSchema.required();
        assertFalse(mapSchema.isValid(null));

        assertTrue(mapSchema.isValid(new HashMap<>()));

        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertTrue(mapSchema.isValid(data));

        mapSchema.sizeof(2);

        assertFalse(mapSchema.isValid(data));
        data.put("key2", "value2");
        assertTrue(mapSchema.isValid(data));
    }

    @Test
    public void mapIncludeTests() {
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

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(mapSchema.isValid(human3));
    }

    @Test
    public void mapIncludeTestsNumber() {
        Map<Integer, BaseSchema<Integer>> schemas = new HashMap<>();
        schemas.put(5, validator.number().required());
        schemas.put(10, validator.number().positive());
        schemas.put(15, validator.number().required().positive().range(3, 8));

        mapSchema.shape(schemas);

        Map<Integer, Integer> number1 = new HashMap<>();
        number1.put(5, 8);
        number1.put(10, 15);
        assertTrue(mapSchema.isValid(number1));

        Map<Integer, Integer> number2 = new HashMap<>();
        number2.put(5, 15);
        number2.put(10, null);
        assertTrue(mapSchema.isValid(number2));

        Map<Integer, Integer> number3 = new HashMap<>();
        number3.put(5, 6);
        number3.put(10, -5);
        assertFalse(mapSchema.isValid(number3));

        Map<Integer, Integer> number4 = new HashMap<>();
        number4.put(15, 5);
        assertTrue(mapSchema.isValid(number4));
    }
}
