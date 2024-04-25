package hexlet.code.validators;

import hexlet.code.Validator;


public class StringSchema implements Schema<String> {
    private Validator validator;
    private boolean required = false;
    private int minLength = 0;
    private String contains = "";

    public StringSchema() {
    }

    @Override
    public boolean isValid(String value) {
        if (this.required) {
            return value != null
                    && !value.isEmpty()
                    && value.length() >= this.minLength
                    && value.contains(this.contains);
        } else {
            return falseRequired(this.minLength, this.contains, value);
        }
    }

    public void required(){
        this.required = true;
    }

    public StringSchema minLength(int count) {
        this.minLength = count;
        return this;
    }

    public StringSchema contains(String text) {
        this.contains = text;
        return this;
    }

    private boolean falseRequired(int length, String cText, String value) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return value.contains(cText) && value.length() >= length;
    }
}
