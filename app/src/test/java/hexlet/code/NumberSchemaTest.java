package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class NumberSchemaTest {
    private NumberSchema numberSchema;
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        validator = new Validator();
        numberSchema = validator.number();
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
}
