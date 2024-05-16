package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class StringSchemaTest {
    private StringSchema stringSchema;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new Validator();
        stringSchema = validator.string();
    }

    @Test
    public void requiredTest() {
        assertTrue(stringSchema.isValid(""));
        assertTrue(stringSchema.isValid(null));
        stringSchema.required();
        assertFalse(stringSchema.isValid(null));
        assertFalse(stringSchema.isValid(""));
    }

    @Test
    public void minLengthStringTest() {
        stringSchema.minLength(10);
        assertTrue(stringSchema.isValid("Hello where!"));
        assertFalse(stringSchema.isValid("jam"));
        assertTrue(stringSchema.isValid(null));
    }

    @Test
    public void containsStringTest() {
        stringSchema.contains("wh");
        assertTrue(stringSchema.isValid("what does the fox say"));
        assertFalse(stringSchema.isValid("watt does the fox say"));
        assertTrue(stringSchema.isValid(null));
    }
}
