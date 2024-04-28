package hexlet.code;

import hexlet.code.validators.BaseSchema;
import hexlet.code.validators.MapSchema;
import hexlet.code.validators.NumberSchema;
import hexlet.code.validators.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class MainTest {
    private StringSchema stringSchema;
    private NumberSchema numberSchema;
    private MapSchema mapSchema;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new Validator();
        stringSchema = validator.string();
        numberSchema = validator.number();
        mapSchema = validator.map();
    }

    @Test
    public void excludeRequiredStringTest() {
        boolean expected = stringSchema.isValid("");
        assertTrue(expected);
        boolean expected2 = stringSchema.isValid(null);
        assertTrue(expected2);
    }

    @Test
    public void includeRequiredStringTest() {
        stringSchema.required();
        boolean expected = stringSchema.isValid(null);
        assertFalse(expected);
        boolean expected2 = stringSchema.isValid("");
        assertFalse(expected2);
        boolean expected3 = stringSchema.isValid("what does the fox say");
        assertTrue(expected3);
        boolean expected4 = stringSchema.isValid("hexlet");
        assertTrue(expected4);
    }

    @Test
    public void minLengthStringTest() {
        stringSchema.required();
        boolean expected = stringSchema.minLength(4).isValid("Hexlet");
        assertTrue(expected);
        boolean expected2 = stringSchema.minLength(10).isValid("Hello where!");
        assertTrue(expected2);
        boolean expected3 = stringSchema.minLength(3).minLength(5).isValid("jambo");
        assertTrue(expected3);
    }

    @Test
    public void containsStringTest() {
        stringSchema.required();
        boolean expected = stringSchema.contains("wh").isValid("what does the fox say");
        assertTrue(expected);
        boolean expected2 = stringSchema.contains("what").isValid("what does the fox say");
        assertTrue(expected2);
        boolean expected3 = stringSchema.contains("whatthe").isValid("what does the fox say");
        assertFalse(expected3);
        boolean expected4 = stringSchema.isValid("what does the fox say");
        assertFalse(expected4);
    }

    @Test
    public void excludeRequiredIntTest() {
        boolean expected = numberSchema.isValid(5);
        assertTrue(expected);
        boolean expected2 = numberSchema.isValid(null);
        assertTrue(expected2);
        boolean expected3 = numberSchema.positive().isValid(null);
        assertTrue(expected3);
    }

    @Test
    public void includeRequiredIntTest() {
        numberSchema.required();
        numberSchema.positive();
        boolean expected = numberSchema.isValid(null);
        assertFalse(expected);
        boolean expected2 = numberSchema.isValid(10);
        assertTrue(expected2);
        boolean expected3 = numberSchema.isValid(-10);
        assertFalse(expected3);
        boolean expected4 = numberSchema.isValid(0);
        assertFalse(expected4);
    }

    @Test
    public void rangeIntTest() {
        numberSchema.range(5, 10);
        boolean expected = numberSchema.isValid(5);
        assertTrue(expected);
        boolean expected2 = numberSchema.isValid(11);
        assertFalse(expected2);
        numberSchema.range(-3, -7);
        boolean expected3 = numberSchema.isValid(-5);
        assertTrue(expected3);
        boolean expected4 = numberSchema.isValid(-23);
        assertFalse(expected4);
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
}
