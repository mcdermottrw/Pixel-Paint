package pixelpaint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pixelpaint.enums.Mode;
import pixelpaint.enums.Theme;
import pixelpaint.tools.*;
import pixelpaint.ui.*;
import pixelpaint.ui.Canvas;
import java.util.LinkedList;

// MAIN
public class Manager extends Application {

    // UI Components
    private BorderPane root;
    private Canvas canvas;
    private Toolbar toolbar;
    private AppMenu appMenu;
    private InfoBar infoBar;
    private ResizeDialog resizeDialog;
    private Scene mainScene;
    private Scene resizeScene;

    // Tools
    private Pencil pencil;
    private Brush brush;
    private Eraser eraser;
    private Spray spray;
    private Filler filler;

    // Other
    private Mode mode = Mode.PENCIL;
    private Theme theme = Theme.LIGHT;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        /* INITIALIZE USER INTERFACE ----------------------------------------- */

        // Create a BorderPane to hold all elements together
        root = new BorderPane();

        // Create canvas and place it into a ScrollPane so that the whole
        // canvas can still be viewed via scrolling even when the user makes
        // the window smaller. Place the ScrollPane in the middle of the window
        canvas = new Canvas();
        buildCanvas();
        ScrollPane scrCanvas = new ScrollPane(canvas);
        scrCanvas.getStyleClass().add("scrollpane-canvas");
        root.setCenter(scrCanvas);

        // Create toolbar and place it in the right side of the BorderPane
        toolbar = new Toolbar();
        root.setRight(toolbar);

        // Create appmenu and place it on the top of the BorderPane like
        // a traditional menu
        appMenu = new AppMenu();
        root.setTop(appMenu);

        // TODO: Write comment here!
        infoBar = new InfoBar(canvas);
        root.setBottom(infoBar);

        // Create the resize dialog box for whenever the user wants to resize
        // the canvas
        resizeDialog = new ResizeDialog();

        // Create the Scene object which will hold our main window (this refers
        // to the root BorderPane which contains our canvas, toolbar, and menu)
        mainScene = new Scene(root);
        mainScene.getStylesheets().add("light-theme.css");

        // Create the Scene object which will hold our resize dialog box
        resizeScene = new Scene(resizeDialog);
        resizeScene.getStylesheets().add("light-theme.css");

        // Register all UI control action events so that they execute the commands
        // that they should
        registerEvents(stage);

        // Specify a maximum width and height for the stage so that the user cannot
        // make the application too large
        stage.setMaxHeight(1080);
        stage.setMaxWidth(1920);

        // Specify a minimum width and ehight for the stage so that the user cannot
        // make the application too small
        stage.setMinHeight(480);
        stage.setMinWidth(720);

        // Makes the stage set it's default size to fit all the ui elements inside
        // of it as specified
        stage.sizeToScene();

        // Sets the title for the top bar of the window
        stage.setTitle("Pixel Paint");

        // Sets the image for the top bar of the window
        stage.getIcons().add(new Image("file:icon.png"));

        // Places the mainScene inside of the stage so it is displayed when we show
        // the stage
        stage.setScene(mainScene);

        // Print a nice little message for the java.exe users
        System.out.println(
            "|=========================|\n" +
            "| WELCOME TO PIXEL PAINT! |\n" +
            "|=========================|\n"
        );

        // Finally, display the user interface :)
        stage.show();

        /* INITIALIZE TOOLS -------------------------------------------------- */

        pencil = new Pencil();
        brush = new Brush();
        eraser = new Eraser();
        spray = new Spray();
        filler = new Filler();

        /* SET DEFAULTS ------------------------------------------------------ */

        // Activate the pencil button on the toolbar by default
        toolbar.chooseNewTool(toolbar.getBtnPencil(), theme);

        // Make the color of the colorpicker match the activeColor by default
        toolbar.getColorPicker().setValue(Color.BLACK);

        // Deactivate the radius drop down list by default
        toolbar.getDdlRadius().setDisable(true);
    }

    /* ----------------------------------------------------------------
     * registerEvents
     * -> Sets up the action events that determine what code will
     *    execute when a given user interface control is used...
     * Input: Stage stage
     * ----------------------------------------------------------------
     */
    private void registerEvents(Stage stage) {
        canvas.setOnScroll(e -> {
            if (e.isControlDown()) {
                canvasZoom(e);
            }
        });

        /* Action events for the top menu */
        // Pointers to provide enhanced readability
        MenuItem mniNew = appMenu.getFileMenu().getItems().get(0);
        MenuItem mniOpen = appMenu.getFileMenu().getItems().get(1);
        MenuItem mniSave = appMenu.getFileMenu().getItems().get(2);
        MenuItem mniExit = appMenu.getFileMenu().getItems().get(3);

        MenuItem mniUndo = appMenu.getEditMenu().getItems().get(0);
        MenuItem mniRedo = appMenu.getEditMenu().getItems().get(1);
        MenuItem mniResize = appMenu.getEditMenu().getItems().get(2);

        MenuItem mniLight = appMenu.getThemeMenu().getItems().get(0);
        MenuItem mniDark = appMenu.getThemeMenu().getItems().get(1);
        MenuItem mniNight = appMenu.getThemeMenu().getItems().get(2);

        // Clears the canvas when the "New" option is pressed in the menu
        mniNew.setOnAction(e -> {
            canvas.clear();
        });

        // Allows the user to choose an image when the "Open" option is
        // pressed in the menu
        mniOpen.setOnAction(e -> {
            canvas.openImage(stage);
        });

        // Allows the user to save their image as a image file when the
        // "Save" option is pressed in the menu
        mniSave.setOnAction(e -> {
            canvas.saveImage(stage);
        });

        // Exits the application when the "Exit" option is pressed in the menu
        mniExit.setOnAction(e -> {
            System.exit(0);
        });

        // Resizes the canvas' dimensions when the "Resize option is pressed
        // in the menu
        mniResize.setOnAction(e -> {
            stage.setScene(resizeScene);
            stage.setResizable(false);
            resizeDialog.getBtnSubmit().setOnAction(o -> {
                resizeCanvas();
                stage.setScene(mainScene);
                stage.sizeToScene();
                stage.setResizable(true);
            });
        });

        // Switches to the light theme when the "Light" option is pressed in the
        // menu
        mniLight.setOnAction(e -> {
            changeAppTheme(Theme.LIGHT);
        });

        // Switches to the dark theme when the "Dark" option is pressed in the
        // menu
        mniDark.setOnAction(e -> {
            changeAppTheme(Theme.DARK);
        });

        // Switches to the night theme when the "Night" option is pressed in
        // the menu
        mniNight.setOnAction(e -> {
            changeAppTheme(Theme.NIGHT);
        });

        /* Action events for the toolbar */
        // Pointers to provide enhanced readability
        ColorPicker colorpicker = toolbar.getColorPicker();
        ComboBox ddlRadius = toolbar.getDdlRadius();
        Button btnPencil = toolbar.getBtnPencil();
        Button btnBrush = toolbar.getBtnBrush();
        Button btnEraser = toolbar.getBtnEraser();
        Button btnSpray = toolbar.getBtnSpray();
        Button btnFill = toolbar.getBtnFill();

        // Sets the activeColor to the color chosen by the user and displays the
        // info that goes along with the chosen color
        colorpicker.setOnAction(e -> {
            Color color = colorpicker.getValue();
            changeToolColor(color);
        });

        // Sets the enum StrokeWidth for the brush, eraser, and spray depending
        // upon which option is chosen in the drop down list
        ddlRadius.setOnAction(e -> {
            int radius = ddlRadius.getSelectionModel().getSelectedIndex()+1;
            changeToolRadius(radius);
        });

        // Restyle the pencil button on the toolbar to display that the
        // pencil is currently active and set the enum mode to PENCIL
        btnPencil.setOnAction(e -> {
            changeTool(Mode.PENCIL, btnPencil, pencil);
        });

        // Restyle the brush button on the toolbar to display that the
        // brush is currently active and set the enum mode to BRUSH
        btnBrush.setOnAction(e -> {
            changeTool(Mode.BRUSH, btnBrush, brush);
        });

        // Restyle the eraser button on the toolbar to display that the
        // eraser is currently active and set the enum mode to ERASER
        btnEraser.setOnAction(e -> {
            changeTool(Mode.ERASER, btnEraser, eraser);
        });

        // Restyle the spray button on the toolbar to display that the
        // spray is currently active and set the enum mode to SPRAY
        btnSpray.setOnAction(e -> {
            changeTool(Mode.SPRAY, btnSpray, spray);
        });

        // Restyle the fill button on the toolbar to display that the
        // fill is currently active and set the enum mode to FILL
        btnFill.setOnAction(e -> {
            changeTool(Mode.FILLER, btnFill, filler);
        });
    }

    /* ----------------------------------------------------------------
     * registerPixelEvents
     * -> Sets up the OnMouseEntered (while ctrl is down) and the
     *    OnMouseClicked events for all pixels within the canvas...
     * Input: Pixel pixel
     * ----------------------------------------------------------------
     */
    private void registerPixelEvents(Pixel pixel) {
        // Set the commands for the given pixel whenever the mouse goes over it
        // while the control key is pressed
        pixel.setOnMouseEntered(e -> {
            infoBar.setLblPosition(pixel);
            if (e.isShiftDown()) {
                switch(mode) {
                    case PENCIL:
                        pencil.draw(pixel);
                        break;
                    case BRUSH:
                        brush.stroke(pixel, canvas);
                        break;
                    case ERASER:
                        eraser.erase(pixel, canvas);
                        break;
                    case SPRAY:
                        spray.spray(pixel, canvas);
                        break;
                    case FILLER:
                        filler.fill(pixel, canvas, pixel.getColor());
                        break;
                    default:
                        break;
                }
            }
        });

        // Set the commands for the given pixel whenever the mouse clicks on it
        pixel.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                changeToolColor(pixel.getColor());
                return;
            }
            switch(mode) {
                case PENCIL:
                    pencil.draw(pixel);
                    break;
                case BRUSH:
                    brush.stroke(pixel, canvas);
                    break;
                case ERASER:
                    eraser.erase(pixel, canvas);
                    break;
                case SPRAY:
                    spray.spray(pixel, canvas);
                    break;
                case FILLER:
                    filler.fill(pixel, canvas, pixel.getColor());
                    break;
                default:
                    break;
            }
        });
    }

    /* ----------------------------------------------------------------
     * buildCanvas
     * -> Uses a two-dimensional array in order to lay out pixels in
     *    the GridPane canvas (ui) and the LinkedList canvas (data)...
     * ----------------------------------------------------------------
     */
    private void buildCanvas() {
        /*  In the two-dimensional loop, i represents rows while
         *  j represents columns as shown in this example:
         *
         *                        j
         *          ------------------------------
         *          |                            |
         *          |                            |
         *          |                            |
         *        i |                            |
         *          |                            |
         *          |                            |
         *          |                            |
         *          ------------------------------
         *
         */

        // For each iteration of i, a LinkedList is added to our Canvas' data
        // grid. These LinkedLists inside of our Canvas' LinkedList act as rows
        for (int i = 0; i < canvas.getGridWidth(); i++) {
            canvas.getGrid().add(new LinkedList<>());
            // For each iteration of j, fill the recently added LinkedList with
            // a row of pixels
            for (int j = 0; j < canvas.getGridHeight(); j++) {
                // Creates a new Pixel object and registers its action events so that
                // they have abilities
                Pixel pixel = new Pixel(i, j);
                registerPixelEvents(pixel);
                // Add the new Pixel object to both the ui (GridPane) and the data list
                // (LinkedList inside a LinkedList).
                canvas.getGrid().get(i).add(pixel);
                canvas.add(pixel, i, j);
            }
        }


    }

    /* ----------------------------------------------------------------
     * resizeCanvas
     * -> This method resizes both the ui (GridPane) and data list
     *    (LinkedList) to whatever dimensions the user enters into the
     *    resize dialog box while preserving what is already on the
     *    canvas
     * ----------------------------------------------------------------
     */
    private void resizeCanvas() {
        // Get the user-specified height and width from the resizeDialog
        // text boxes
        int height = resizeDialog.getGridHeight();
        int width = resizeDialog.getGridWidth();

        //
        infoBar.setLblDimensions(height, width);

        // Sets new width and height for the canvas object
        canvas.setGridWidth(width);
        canvas.setGridHeight(height);

        // Make copy of list so that it can be implemented into our resized
        // list and then clear both the ui list and data list
        LinkedList<LinkedList> temp = (LinkedList<LinkedList>) canvas.getGrid().clone();
        canvas.getGrid().clear();
        canvas.getChildren().clear();

        // Create a new, resized grid while still preserving what we can from
        // the old grid. Do this by attempting to grab a pixel from the old list
        // based on our i and j values (coordinates within the grid). If the program
        // throws IndexOutOfBoundsException this means the resize is larger than
        // the old grid and we will place a plain, white pixel there by default
        Pixel p;
        // Two-dimensional array where i corresponds to rows and j to columns
        for (int i = 0; i < width; i++) {
            canvas.getGrid().add(new LinkedList<>());
            for (int j = 0; j < height; j++) {
                try {
                    // Try to grab a pixel from the old list using our i and j values
                    // (coordinates) and plant that pixel in our new grid (ui and data list)
                    p = (Pixel) temp.get(i).get(j);
                    canvas.getGrid().get(i).add(p);
                    canvas.add(p, i, j);
                } catch (IndexOutOfBoundsException ignored) {
                    // If the i and j coordinates are out of the bounds of the old list
                    // create a new plain pixel, register its events and place it in
                    // the new grid (ui and data list)
                    p = new Pixel(i, j);
                    registerPixelEvents((p));
                    canvas.getGrid().get(i).add(p);
                    canvas.add(p, i, j);
                }
            }
        }
    }

    /* ----------------------------------------------------------------
     * changeAppTheme
     * -> This method switches the theme of the ui depending on which
     *    option the user chooses in the menu bar
     * ----------------------------------------------------------------
     */
    private void changeAppTheme(Theme t) {
        // Change the Theme enum to indicate which theme is currently chosen
        theme = t;

        // Switch stylesheets for both the main window and the resize dialog
        // depending upon which theme is chosen
        switch(theme) {
            case LIGHT:
                root.getStylesheets().clear();
                root.getStylesheets().add("light-theme.css");
                resizeDialog.getStylesheets().clear();
                resizeDialog.getStylesheets().add("light-theme.css");
                break;
            case DARK:
                root.getStylesheets().clear();
                root.getStylesheets().add("dark-theme.css");
                resizeDialog.getStylesheets().clear();
                resizeDialog.getStylesheets().add("dark-theme.css");
                break;
            case NIGHT:
                root.getStylesheets().clear();
                root.getStylesheets().add("night-theme.css");
                resizeDialog.getStylesheets().clear();
                resizeDialog.getStylesheets().add("night-theme.css");
                break;
            default:
                break;
        }

        // Set the active toolbar button to match the new theme
        switch(mode) {
            case PENCIL:
                toolbar.chooseNewTool(toolbar.getBtnPencil(), theme);
                break;
            case BRUSH:
                toolbar.chooseNewTool(toolbar.getBtnBrush(), theme);
                break;
            case ERASER:
                toolbar.chooseNewTool(toolbar.getBtnEraser(), theme);
                break;
            case SPRAY:
                toolbar.chooseNewTool(toolbar.getBtnSpray(), theme);
                break;
            case FILLER:
                toolbar.chooseNewTool(toolbar.getBtnFill(), theme);
                break;
            default:
                break;
        }
    }

    /* ----------------------------------------------------------------
     * changeAppTheme
     * -> This method switches between different tools depending on
     *    what the user chooses from the toolbar. There are many steps
     *    to switching the tool:
     *    1. Set the mode enum to reflect what tool was chosen
     *    2. Make the button that's chosen appear highlighted (active)
     *    3. Set the color information on the toolbar to match the
     *       color of the tool the user has chosen
     *    4. Turn the ColorPicker and ComboBox on/off depending upon
     *       which tool is chosen
     * ----------------------------------------------------------------
     */
    private void changeTool(Mode m, Button b, Tool p) {
        // Switch the Mode enum to indicate which tool has been chosen
        mode = m;
        toolbar.chooseNewTool(b, theme);
        toolbar.setColorInfo(p.getColor());

        switch(mode) {
            case PENCIL:
            case FILLER:
                toolbar.getDdlRadius().setDisable(true);
                toolbar.getColorPicker().setDisable(false);
                break;
            case BRUSH:
            case SPRAY:
                toolbar.getColorPicker().setDisable(false);
                toolbar.getDdlRadius().setDisable(false);
                toolbar.getDdlRadius().getSelectionModel().select(p.getRadius()-1);
                break;
            case ERASER:
                toolbar.getColorPicker().setDisable(true);
                toolbar.getDdlRadius().setDisable(false);
                toolbar.getDdlRadius().getSelectionModel().select(p.getRadius()-1);
                break;
        }
    }

   /* ----------------------------------------------------------------
    * changeToolColor
    * -> This method switches the color of the currently chosen tool
    *    and sets the color information on the toolbar
    * -----------------------------------------------------------------
    */
    private void changeToolColor(Color color) {
        // Set the color information on the toolbar
        toolbar.setColorInfo(color);

        // Set the color of the tool depending on the current mode
        switch(mode) {
            case PENCIL:
                pencil.setColor(color);
                break;
            case BRUSH:
                brush.setColor(color);
                break;
            case SPRAY:
                spray.setColor(color);
                break;
            case FILLER:
                filler.setColor(color);
                break;
            default:
                break;
        }
    }

    /* ----------------------------------------------------------------
    *  changeToolRadius
    *  -> This method switches the radius of the currently chosen tool
    *  ----------------------------------------------------------------
    */
    private void changeToolRadius(int radius) {
        switch(mode) {
            case BRUSH:
                brush.setRadius(radius);
                break;
            case ERASER:
                eraser.setRadius(radius);
                break;
            case SPRAY:
                spray.setRadius(radius);
                break;
            default:
                break;
        }
    }

    private void canvasZoom(ScrollEvent e) {
        for (int i = 0; i < canvas.getGridWidth(); i++) {
            for (int j = 0; j < canvas.getGridHeight(); j++) {
                Pixel p = (Pixel) canvas.getGrid().get(i).get(j);
                if (e.getDeltaY() > 0 && p.getHeight() > 1) {
                    p.setWidth(p.getWidth()-1);
                    p.setHeight(p.getHeight()-1);
                }
                if (e.getDeltaY() < 0 && p.getHeight() < 25) {
                    p.setWidth(p.getWidth()+1);
                    p.setHeight(p.getHeight()+1);
                }
                infoBar.setLblPixelSize(p);
            }
        }
    }

}
