package pixelpaint.tools;

import pixelpaint.ui.Pixel;
import pixelpaint.ui.Canvas;
import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.Queue;

public class Filler extends Tool {

    /* Constructor */
    public Filler() {

    }

    /* Non-Recursive flood fill algorithm */
    public void fill(Pixel pixel, Canvas canvas, Color prev) {
        // If prev color is equal to next color, return
        if (prev == color) return;

        // If the color of pixel is not equal to the prev color, return
        if (pixel.getColor() != prev) return;

        // Set the color of our pixel to the replacement color
        pixel.setColor(color);

        Queue q = new LinkedList<Pixel>();
        q.add(pixel);

        Pixel p;
        Pixel testPixel;

        // While the queue is not empty, take a pixel out and check to see if all pixels surrounding it are
        // not the previous color. If a pixel is found to be the previous color, change it to the next color
        // and add it to the queue so that that pixel can be checked
        while (!q.isEmpty()) {
            p = (Pixel) q.poll();

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord() + 1).get(p.getYCoord());
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord() - 1).get(p.getYCoord());
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord()).get(p.getYCoord() + 1);
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord()).get(p.getYCoord() - 1);
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

        }

    }

    /* Non-Recursive flood fill algorithm but STATIC now */
    public static void fill(Pixel pixel, Canvas canvas, Color prev, Color color) {
        // If prev color is equal to next color, return
        if (prev == color) return;

        // If the color of pixel is not equal to the prev color, return
        if (pixel.getColor() != prev) return;

        // Set the color of our pixel to the replacement color
        pixel.setColor(color);

        Queue q = new LinkedList<Pixel>();
        q.add(pixel);

        Pixel p;
        Pixel testPixel;

        // While the queue is not empty, take a pixel out and check to see if all pixels surrounding it are
        // not the previous color. If a pixel is found to be the previous color, change it to the next color
        // and add it to the queue so that that pixel can be checked
        while (!q.isEmpty()) {
            p = (Pixel) q.poll();

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord() + 1).get(p.getYCoord());
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord() - 1).get(p.getYCoord());
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord()).get(p.getYCoord() + 1);
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

            try {
                testPixel = (Pixel) canvas.getGrid().get(p.getXCoord()).get(p.getYCoord() - 1);
                if (testPixel.getColor() == prev) {
                    testPixel.setColor(color);
                    q.add(testPixel);
                }
            } catch (IndexOutOfBoundsException ignored) {}

        }

    }

}
