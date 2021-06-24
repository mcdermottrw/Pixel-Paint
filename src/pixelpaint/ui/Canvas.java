package pixelpaint.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Canvas extends GridPane {

    private LinkedList<LinkedList> grid;
    private int gridWidth;
    private int gridHeight;

    /* Constructor - by default */
    public Canvas() {
        this.gridWidth = 150;
        this.gridHeight = 150;
        grid = new LinkedList<>();
    }

    /* Constructor - with parameters */
    public Canvas(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        grid = new LinkedList<>();
    }

    /* Returns the data (the pixels in a list) behind the canvas */
    public LinkedList<LinkedList> getGrid() {
        return grid;
    }

    /* Returns the width of the canvas in terms of pixels when called */
    public int getGridWidth() {
        return gridWidth;
    }

    /* Changes the value of gridWidth to whatever the passed value is */
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    /* Returns the height of the canvas in terms of pixels when called */
    public int getGridHeight() {
        return gridHeight;
    }

    /* Changes the value of gridHeight to whatever the passed value is */
    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    /* Clears the canvas so that it contains all white pixels again */
    public void clear() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Pixel p = (Pixel) getGrid().get(i).get(j);
                p.setColor(Color.WHITE);
            }
        }
    }

    /* Opens an image by scanning all of its pixels and applying that to the canvas */
    public void openImage(Stage stage) {
        // Initialize FileChooser and open the file dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Pictures"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif")
        );
        File file = fileChooser.showOpenDialog(stage);

        // Check to make sure a file was actually chosen
        if (file != null) {
            // Clear the canvas before starting anything
            clear();

            // Turn the chosen file into a JavaFX Image
            Image img = new Image(file.toURI().toString());

            // If the height of the image is greater than the width
            if (img.getHeight() > img.getWidth()) {
                // Get the ratio of the image's dimensions and apply that ratio to resize the image properly
                double multiplyByThis = (img.getWidth()/img.getHeight());
                img = new Image(file.toURI().toString(), 150*multiplyByThis, 150, false, true);
            }
            // If the width of the image is greater than the height
            else if (img.getWidth() > img.getHeight()) {
                // Get the ratio of the image's dimensions and apply that ratio to resize the image properly
                double multiplyByThis = (img.getHeight()/img.getWidth());
                img = new Image(file.toURI().toString(), 150, 150*multiplyByThis, false, true);
            }
            // If the width and height of the image are equal
            else {
                img = new Image(file.toURI().toString(), 150, 150, false, true);
            }

            // Iterate through each pixel within the image
            BufferedImage bImg = SwingFXUtils.fromFXImage(img, null);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    // Gets the color of the pixel and then turns it into a JavaFX Color
                    java.awt.Color c = new java.awt.Color(bImg.getRGB(i, j));
                    Color color = Color.rgb(c.getRed(), c.getGreen(), c.getBlue(), (float)c.getAlpha()/255);

                    // Get the corresponding pixel from the grid and set its color
                    Pixel p = (Pixel) grid.get(i).get(j);
                    p.setColor(color);
                }
            }
        }
    }
    
    /* Save a screenshot of the current canvas and bring up a save dialog option */
    public void saveImage(Stage stage) {
        // Takes a screenshot of a given region
        WritableImage img = snapshot(new SnapshotParameters(), null);

        // Allows the user to choose where to save their image
        FileChooser fileChooser = new FileChooser();

        // Creates extension options PNG, JPEG, and GIF
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG", "*.jpg", ".jpeg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GIF", ".gif"));

        // The title of the pop-up window
        fileChooser.setTitle("Save Image");

        // Opens the save dialog window and saves the image
        File file = fileChooser.showSaveDialog(stage);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
        } catch (IOException e) {
            System.err.println("File could not be saved: " + e);
        }
    }

    /* Returns the dimensions of the canvas as supplied in the constructor */
    @Override
    public String toString() {
        return "Canvas: " + gridWidth + "x" + gridHeight;
    }
}
