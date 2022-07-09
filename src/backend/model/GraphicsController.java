package backend.model;

/**
 * Se encarga de encapsular las funcionalidades para dibujar figuras que puede tener un frontend
 */
public interface GraphicsController {
    /**
     * Dibuja un rectangulo
     * @param topLeft Punto de arriba a la izquierda
     * @param bottomRight Punto de abajo a la derecha
     * @param colorData Datos de color de la figura
     */
    void drawRectangle(Point topLeft, Point bottomRight, ColoredFigure.ColorData colorData);

    /**
     * Dibuja una elipse
     * @param centerPoint Punto central de la elipse
     * @param sMayorAxis Eje mayor
     * @param sMinorAxis Eje menor
     * @param colorData Datos de color de la figura
     */
    void drawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, ColoredFigure.ColorData colorData);

    /**
     * Dibuja un circulo
     * @param centerPoint Punto central del circulo
     * @param radius Radio del circulo
     * @param colorData Datos de color de la figura
     */
    void drawCircle(Point centerPoint, double radius, ColoredFigure.ColorData colorData);
}