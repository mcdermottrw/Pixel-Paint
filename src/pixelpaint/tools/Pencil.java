package pixelpaint.tools;

import pixelpaint.ui.Pixel;

public class Pencil extends Tool {

    /* Performs the very basic function of recoloring a pixel */
    public void draw(Pixel pixel) {
        pixel.setColor(color);
    }

}
