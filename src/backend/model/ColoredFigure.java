package backend.model;

public abstract class ColoredFigure implements Figure{
    private static final String DEFAULT_FILL_COLOR = "#FF0080";
    private static final String DEFAULT_STROKE_COLOR = "#6600A1";
    private static final String SELECTED_STROKE_COLOR = "#FF0000";
    private static final double DEFAULT_STROKE_WEIGHT = 1;

    protected String fillColor = DEFAULT_FILL_COLOR;
    protected String strokeColor = DEFAULT_STROKE_COLOR;
    protected double strokeWeight = DEFAULT_STROKE_WEIGHT;

    public String getStrokeColor(boolean isSelected) {
        return isSelected ? SELECTED_STROKE_COLOR : strokeColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWeight(double strokeWeight) {
        this.strokeWeight = strokeWeight;
    }
}
