package pixelpaint.tools;

import pixelpaint.ui.Pixel;
import pixelpaint.ui.Canvas;

import java.util.Random;

public class Spray extends Tool {

    /* Constructor */
    public Spray() {

    }

    /* Paints the canvas in a "spray paint-like" way */
    public void spray(Pixel pixel, Canvas canvas) {
        // Radius represents how far pixels can be created from the original pixel
        int radius = this.radius+1;

        // Volume represents the maximum volume of pixels that can be created per function call
        int volume = radius*2;

        // Random object
        Random r = new Random();

        // How many pixels (RANGE: 3 to volume)
        for (int i = 0; i < r.nextInt(volume) + 3; i++) {
            // Random x and y coordinates in range (RANGE: radius to -radius)
            int x = r.nextInt(radius + radius) - radius;
            int y = r.nextInt(radius + radius) - radius;

            // Plant the random pixel
            try {
                Pixel p = (Pixel) canvas.getGrid().get(pixel.getXCoord() + x).get(pixel.getYCoord() + y);
                // If the color of the pixel being planted is already the target color or
                // it's desaturated counterpart set the color of the pixel to the target color.
                // Otherwise, set the color of the pixel to a desaturated version of the target color
                if (p.getColor().equals(color.desaturate()) || p.getColor().equals(color)) {
                    p.setColor(color);
                }
                else {
                    p.setColor(color.desaturate());
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }

    }

    @Override
    public String toString() {
        return "Color: " + color + "\nRadius: " + radius;
    }

}
