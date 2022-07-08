package backend.model;

public abstract class ColoredFigure extends DrawableFigure {
    public static final String SELECTED_STROKE_COLOR = "#FF0000";
    private ColorData colorData;

    public ColoredFigure(ColorData colorData, GraphicsController graphicsController) {
        super(graphicsController);
        this.colorData = colorData;
    }

    public String getFillColor() { return colorData.fillColor; }
    public double getStrokeWeight() { return colorData.strokeWeight; }

    public String getStrokeColor(boolean isSelected) {
        return isSelected ? SELECTED_STROKE_COLOR : colorData.strokeColor;
    }

    public void setColorData(ColorData colorData) {
        this.colorData = colorData;
    }

    public ColorData getColorData() {
        return colorData;
    }

    public static class ColorData {
        private final String fillColor, strokeColor;
        private final double strokeWeight;

        public ColorData(String fillColor, String strokeColor, double strokeWeight) {
            this.fillColor = fillColor;
            this.strokeColor = strokeColor;
            this.strokeWeight = strokeWeight;
        }

        public String getFillColor() {
            return fillColor;
        }

        public String getStrokeColor() {
            return strokeColor;
        }

        public double getStrokeWeight() {
            return strokeWeight;
        }
    }
}
