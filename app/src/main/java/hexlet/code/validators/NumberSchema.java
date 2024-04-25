package hexlet.code.validators;

import hexlet.code.Validator;
import lombok.Setter;


public class NumberSchema implements BaseSchema<Integer> {
    @Setter
    private Validator validator;

    private boolean required = false;
    private boolean positive;
    private int[] range;

    public NumberSchema() {
    }

    @Override
    public boolean isValid(Integer value) {
        if (this.required) {
            return value != null
                    && value >= 1
                    && value >= this.range[0] && value <= this.range[1];
        } else {
            return falseRequired(this.minLength, this.contains, value);
        }
    }

    public void required(){
        this.required = true;
    }

    public NumberSchema positive() {
        this.positive = true;
        return this;
    }

    public NumberSchema range(int start, int end) {
        this.range = new int[2];
        this.range[0] = start;
        this.range[1] = end;
        return this;
    }

    private boolean falseRequired(int length, String cText, String value) {
        if (value == null || !positive) {
            return true;
        }
        return value.contains(cText) && value.length() >= length;
    }
}
