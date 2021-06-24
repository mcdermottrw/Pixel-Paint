package pixelpaint.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pixel extends Rectangle {

    private final int xCoord;
    private final int yCoord;
    private Color color;
    
    /* Constructor */
    public Pixel(int xCoord, int yCoord) {
        // Set fields
        this.xCoord = xCoord;
        this.yCoord = yCoord;

        // Set attributes
        setColor(Color.WHITE);
        setWidth(5);
        setHeight(5);
        getStyleClass().add("pixel");
    }
    
    /* Returns the X coordinate of the pixel (based on location in canvas) */
    public int getXCoord() {
        return xCoord;
    }
    
    /* Returns the Y coordinate for the pixel (based on location in canvas) */
    public int getYCoord() {
        return yCoord;
    }
    
    /* Returns the current color of the pixel as a JavaFX Color object */
    public Color getColor() {
        return color;
    }
    
    /* Sets both of color of the pixel in terms of data and on the interface */
    public void setColor(Color color) {
        this.color = color;
        this.setFill(color);
    }
    
    /* Returns the position and color of the printed pixel */
    @Override
    public String toString() {
        return "Position: " + xCoord + "x " + yCoord + "y / Color: " + getColor().toString();
    }
}
