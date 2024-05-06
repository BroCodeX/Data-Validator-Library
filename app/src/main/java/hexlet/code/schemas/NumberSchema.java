package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {
    private int[] range;

    public NumberSchema() {
        super();
    }

    @Override
    public boolean isValid(Integer value) {
        return this.getInternalState().stream()
                .allMatch(field -> stateHandler(field, value));
    }
    public NumberSchema required() {
        this.getInternalState().add("required");
        return this;
    }

    public NumberSchema positive() {
        this.getInternalState().add("positive");
        return this;
    }

    public NumberSchema range(int start, int end) {
        this.range = new int[2];
        this.range[0] = start;
        this.range[1] = end;
        this.getInternalState().add("range");
        return this;
    }

    private boolean stateHandler(String field, Integer value) {
        return switch (field) {
            case "required" -> value != null;
            case "positive" -> value == null || value >= 1;
            case "range" -> rangeHandler(value);
            default -> throw new RuntimeException("There is no settings for the schema");
        };
    }

    private boolean rangeHandler(int value) {
        if (value > 0) {
            return value >= this.range[0] && value <= this.range[1];
        } else {
            return value <= this.range[0] && value >= this.range[1];
        }
    }
}
