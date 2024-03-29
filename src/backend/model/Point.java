package backend.model;

public class Point implements Movable {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }


    /**
     * @param target el punto objetivo
     * @return distancia al punto objetivo
     */
    public double distanceTo(Point target){
        return Math.sqrt(Math.pow(x - target.x, 2) +
                Math.pow(y - target.y, 2));
    }
}
