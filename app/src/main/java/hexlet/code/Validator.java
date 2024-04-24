package hexlet.code;

import hexlet.code.validators.Schema;
import hexlet.code.validators.StringSchema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Validator {
    @Getter
    private List<Schema> schemas;

    Validator() {
        this.schemas = new ArrayList<>();
    }

    public StringSchema string() {
        StringSchema stringSchema = new StringSchema();
        this.schemas.add(stringSchema);
        return stringSchema;
    }

    public void removeSchema(Schema schema) {
        this.schemas.remove(schema);
    }

}
