package backend.model;

public interface Figure extends Movable, Resizable {
    boolean pointBelongs(Point eventPoint);
    void draw(boolean isSelected);
    String getName();
}
