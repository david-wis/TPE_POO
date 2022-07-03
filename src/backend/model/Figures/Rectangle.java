package backend.model.Figures;

import backend.model.Figure;
import backend.model.Point;

public abstract class Rectangle implements Figure{
    protected final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean pointBelongs(Point eventPoint) {
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }

    @Override
    public void move(double dx, double dy) {
        topLeft.move(dx, dy);
        bottomRight.move(dx, dy);
    }

    @Override
    public void resize(double percentage) {
        double dx = (bottomRight.getX() - topLeft.getX()) * percentage / 100;
        double dy = (topLeft.getY() - bottomRight.getY()) * percentage / 100;
        topLeft.move(-dx/2, dy/2);
        bottomRight.move(dx/2, -dy/2);
    }
}
