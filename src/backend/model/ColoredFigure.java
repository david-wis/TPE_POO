package backend.model;

public abstract class ColoredFigure implements Figure{
    public static final String SELECTED_STROKE_COLOR = "#FF0000";
    private ColorData colorData;

    public ColoredFigure(ColorData colorData) {
        this.colorData = colorData;
    }

    public String getFillColor() { return colorData.fillColor; }
    public double getStrokeWeight() { return colorData.strokeWeight; }

    public String getStrokeColor(boolean isSelected) {
        return isSelected ? SELECTED_STROKE_COLOR : colorData.strokeColor;
    }

    public void setFillColor(String fillColor) {
        this.colorData = new ColorData(fillColor, colorData.strokeColor, colorData.strokeWeight);
    }

    public void setStrokeColor(String strokeColor){
        this.colorData = new ColorData(colorData.fillColor, strokeColor, colorData.strokeWeight);
    }

    public void setStrokeWeight(double strokeWeight){
        this.colorData = new ColorData(colorData.fillColor, colorData.strokeColor, strokeWeight);
    }

    public static class ColorData {
        private final String fillColor, strokeColor;
        private final double strokeWeight;

        public ColorData(String fillColor, String strokeColor, double strokeWeight) {
            this.fillColor = fillColor;
            this.strokeColor = strokeColor;
            this.strokeWeight = strokeWeight;
        }
    }
}
