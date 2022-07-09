package backend.model;

/**
 * Implementa movimiento
 */
public interface Movable {
    /**
     * Desplaza un objeto en el plano
     * @param dx Desplazamiento en x
     * @param dy Desplazamiento en y
     */
    void move(double dx, double dy);
}
