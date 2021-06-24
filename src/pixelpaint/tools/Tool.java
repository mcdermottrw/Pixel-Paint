package pixelpaint.tools;

import javafx.scene.paint.Color;

public abstract class Tool {

    Color color;
    int radius;

    public Tool() {
        color = Color.BLACK;
        radius = 2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Color: " + color;
    }

}
