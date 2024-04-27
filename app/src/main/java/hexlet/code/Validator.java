package hexlet.code;

import hexlet.code.validators.MapSchema;
import hexlet.code.validators.NumberSchema;
import hexlet.code.validators.StringSchema;


public class Validator {
    public StringSchema string() {
        return new StringSchema();
    }

    public NumberSchema number() {
        return new NumberSchema();
    }

    public MapSchema mapSchema() {
        return new MapSchema();
    }

}
