package pixelpaint.tools;

import pixelpaint.ui.Pixel;
import pixelpaint.ui.Canvas;

public class Brush extends Tool {
    
    /* Constructor */
    public Brush() {

    }

    /* Draws a circle of pixels wherever the user chooses using the midpoint circle algorithm */
    public void stroke(Pixel pixel, Canvas canvas) {
        Pixel p;

        int xCenter = pixel.getXCoord();
        int yCenter = pixel.getYCoord();

        int r = radius;

        int x = r, y = 0;

        try {
            p = (Pixel) canvas.getGrid().get(x + xCenter).get(y + yCenter);
            p.setColor(color);
        } catch (IndexOutOfBoundsException ignored) {}

        if (r > 0) {
            try {
                p = (Pixel) canvas.getGrid().get(-x + xCenter).get(yCenter);
                p.setColor(color);
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                p = (Pixel) canvas.getGrid().get(xCenter).get(r + yCenter);
                p.setColor(color);
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                p = (Pixel) canvas.getGrid().get(xCenter).get(-r + yCenter);
                p.setColor(color);
            } catch (IndexOutOfBoundsException ignored) {}
        }

        int P = 1 - r;
        while (x > y) {

            y++;

            if (P <= 0)
                P = P + 2 * y + 1;
            else {
                x--;
                P = P + 2 * y - 2 * x + 1;
            }

            if (x < y)
                break;

            try {
                p = (Pixel) canvas.getGrid().get(x + xCenter).get(y + yCenter);
                p.setColor(color);
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                p = (Pixel) canvas.getGrid().get(-x + xCenter).get(y + yCenter);
                p.setColor(color);
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                p = (Pixel) canvas.getGrid().get(x + xCenter).get(-y + yCenter);
                p.setColor(color);
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                p = (Pixel) canvas.getGrid().get(-x + xCenter).get(-y + yCenter);
                p.setColor(color);
            } catch (IndexOutOfBoundsException ignored) {}

            if (x != y) {
                try {
                    p = (Pixel) canvas.getGrid().get(y + xCenter).get(x + yCenter);
                    p.setColor(color);
                } catch (IndexOutOfBoundsException ignored) {}

                try {
                    p = (Pixel) canvas.getGrid().get(-y + xCenter).get(x + yCenter);
                    p.setColor(color);
                } catch (IndexOutOfBoundsException ignored) {}

                try {
                    p = (Pixel) canvas.getGrid().get(y + xCenter).get(-x + yCenter);
                    p.setColor(color);
                } catch (IndexOutOfBoundsException ignored) {}

                try {
                    p = (Pixel) canvas.getGrid().get(-y + xCenter).get(-x + yCenter);
                    p.setColor(color);
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }

        Filler.fill(pixel, canvas, pixel.getColor(), color);
    }

    @Override
    public String toString() {
        return "Radius: " + radius +"\nColor: " + color;
    }
}
