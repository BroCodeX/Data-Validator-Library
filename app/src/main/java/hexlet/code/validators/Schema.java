package hexlet.code.validators;

public interface Schema<T> {
    boolean isValid(T value);
}
