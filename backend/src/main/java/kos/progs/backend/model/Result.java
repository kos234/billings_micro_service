package kos.progs.backend.model;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Result<T> permits Result.Success, Result.Failure {

    record Success<T>(T value) implements Result<T> {
        @Override
        public boolean isSuccess() { return true; }

        @Override
        public T get() {
            return value;
        }
    }

    record Failure<T>(String code, String message) implements Result<T> {
        @Override
        public boolean isSuccess() { return false; }

        @Override
        public T get() {
            throw new IllegalStateException("Cannot get value from Failure: " + message);
        }
    }

    boolean isSuccess();
    T get();

    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Result<T> failure(String code, String message) {
        return new Failure<>(code, message);
    }


    default boolean isFailure() {
        return !isSuccess();
    }

    default Result<T> onFailure(Consumer<Failure<T>> consumer) {
        if (this instanceof Failure<T> f) {
            consumer.accept(f);
        }
        return this;
    }

    default Result<T> onSuccess(Consumer<T> consumer) {
        if (this instanceof Success<T> s) {
            consumer.accept(s.value());
        }
        return this;
    }

    default <R> Result<R> map(Function<? super T, ? extends R> mapper) {
        return this instanceof Success<T> s
                ? success(mapper.apply(s.value()))
                : new Failure<>(error().code(), error().message()); // важно: сохранить ошибку!
    }

    default <R> Result<R> flatMap(Function<? super T, ? extends Result<R>> mapper) {
        return this instanceof Success<T> s
                ? mapper.apply(s.value())
                : new Failure<>(error().code(), error().message());
    }

    // Вспомогательный метод: получить Failure (только если это Failure)
    default Failure<T> error() {
        if (this instanceof Failure<T> f) {
            return f;
        }
        throw new IllegalStateException("Result is Success, no error available");
    }

    // Получить значение или бросить исключение
    default T getOrElseThrow() {
        return get(); // Success вернёт value, Failure — бросит
    }

    // Получить значение или вернуть по умолчанию
    default T orElse(T other) {
        return this instanceof Success<T> s ? s.value() : other;
    }

    // Получить значение или вычислить по умолчанию (lazy)
    default T orElseGet(Supplier<? extends T> supplier) {
        return this instanceof Success<T> s ? s.value() : supplier.get();
    }

    // Вернуть Optional (удобно для интеграции с Stream API)
    default java.util.Optional<T> toOptional() {
        return this instanceof Success<T> s
                ? java.util.Optional.of(s.value())
                : java.util.Optional.empty();
    }
}
