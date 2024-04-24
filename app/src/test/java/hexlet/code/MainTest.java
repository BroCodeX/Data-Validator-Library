package hexlet.code;

import hexlet.code.validators.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {
    private StringSchema stringSchema;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new Validator();
        stringSchema = validator.string();
    }

    @Test
    public void excludeRequiredTest() {
        boolean expected = stringSchema.isValid("");
        assertTrue(expected);
        boolean expected2 = stringSchema.isValid(null);
        assertTrue(expected2);
    }

    @Test
    public void includeRequiredTest() {
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
    public void minLengthTest() {
        stringSchema.required();
        boolean expected = stringSchema.minLength(4).isValid("Hexlet");
        assertTrue(expected);
        boolean expected2 = stringSchema.minLength(10).isValid("Hello where!");
        assertTrue(expected2);
        boolean expected3 = stringSchema.minLength(3).minLength(5).isValid("jambo");
        assertTrue(expected3);
    }

    @Test
    public void containsTest() {
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
}
