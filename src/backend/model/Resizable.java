package backend.model;

public interface Resizable {
    float DEFAULT_PERCENTAGE = 10;

    default void enlarge() { resize(DEFAULT_PERCENTAGE); }
    default void shrink(){ resize(-DEFAULT_PERCENTAGE); }

    default double inversePercentage(double percentage){
        return 100.0 * ((100 / (100.0 + percentage)) - 1);
    }

    default void inverseEnlarge() { resize(inversePercentage(DEFAULT_PERCENTAGE)); }
    default void inverseShrink(){ resize(inversePercentage(-DEFAULT_PERCENTAGE)); }

    void resize(double percentage);
}
