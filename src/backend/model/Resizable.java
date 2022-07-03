package backend.model;

public interface Resizable {
    float DEFAULT_PERCENTAGE = 10;

    default void enlarge() { resize(DEFAULT_PERCENTAGE); }
    default void shrink(){ resize(-DEFAULT_PERCENTAGE); }

    void resize(double percentage);
}
