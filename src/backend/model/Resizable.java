package backend.model;

/**
 * Implementa los reajustes de tamaño
 */
public interface Resizable {
    float DEFAULT_PERCENTAGE = 10;

    /**
     * Aumenta las dimensiones el porcentaje por defecto
     */
    default void enlarge() { resize(DEFAULT_PERCENTAGE); }
    /**
     * Disminuye las dimensiones el porcentaje por defecto
     */
    default void shrink(){ resize(-DEFAULT_PERCENTAGE); }

    /**
     * @param percentage Porcentaje original
     * @return Porcentaje correspondiente a la operacion inversa
     */
    default double inversePercentage(double percentage){
        return 100.0 * ((100 / (100.0 + percentage)) - 1);
    }

    /**
     * Operacion inversa de enlarge
     */
    default void inverseEnlarge() { resize(inversePercentage(DEFAULT_PERCENTAGE)); }

    /**
     * Operacion inversa de shrink
     */
    default void inverseShrink(){ resize(inversePercentage(-DEFAULT_PERCENTAGE)); }


    /**
     * Modifica las dimensiones segun el porcentaje recibido
     * @param percentage El porcentaje
     */
    void resize(double percentage);
}
