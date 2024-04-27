package hexlet.code.validators;


public class StringSchema extends BaseSchemaClass<String> {
    private int minLength;
    private String contains;

    public StringSchema() {
        super();
    }

    @Override
    public boolean isValid(String value) {
        return this.getInternalState().stream()
                .allMatch(field -> stateHandler(field, value));
    }

    public void required() {
        super.required();
    }

    public StringSchema minLength(int count) {
        this.minLength = count;
        this.getInternalState().add("minLength");
        return this;
    }

    public StringSchema contains(String text) {
        this.contains = text;
        this.getInternalState().add("contains");
        return this;
    }

    private boolean stateHandler(String field, String value) {
        return switch (field) {
            case "required" -> value != null && !value.isEmpty();
            case "contains" -> value.contains(this.contains);
            case "minLength" -> value.length() >= this.minLength;
            default -> false;
        };
    }
}
