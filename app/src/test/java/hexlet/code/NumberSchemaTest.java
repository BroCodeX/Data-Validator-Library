package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public final class NumberSchemaTest {
    private NumberSchema numberSchema;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new Validator();
        numberSchema = validator.number();
    }

    @Test
    public void requiredTest() {
        assertTrue(numberSchema.isValid(null));
        numberSchema.required();
        assertFalse(numberSchema.isValid(null));
    }

    @Test
    public void positiveTest() {
        assertTrue(numberSchema.isValid(0));
        numberSchema.positive();
        assertTrue(numberSchema.isValid(10));
        assertFalse(numberSchema.isValid(-10));
        assertTrue(numberSchema.isValid(null));
        assertFalse(numberSchema.isValid(0));
    }

    @Test
    public void rangeTest() {
        numberSchema.range(5, 10);
        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(10));
        numberSchema.range(10, 5);
        assertFalse(numberSchema.isValid(6));
        numberSchema.range(-8, -3);
        assertTrue(numberSchema.isValid(-6));
    }
}
