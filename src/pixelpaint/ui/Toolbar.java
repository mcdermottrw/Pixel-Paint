package pixelpaint.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import pixelpaint.enums.Theme;
import java.util.LinkedList;

public class Toolbar extends VBox {

    // Color & radius selectors
    private ColorPicker colorpicker;
    private ComboBox ddlRadius;

    // Color information displays
    private Label lblHex;
    private Label lblRed;
    private Label lblGreen;
    private Label lblBlue;

    // Buttons used to switch tools + linked list of all tool buttons
    private LinkedList<Button> toolButtons;
    private Button btnPencil;
    private Button btnBrush;
    private Button btnSpray;
    private Button btnEraser;
    private Button btnFill;

    private Label lblPosition;

    /* Constructor - Build GUI */
    public Toolbar() {
        // VBox settings and border styling for cleanliness
        this.setSpacing(5);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.getStyleClass().add("toolbar");
        
	    // Adds the brush label and line below it for [ A E S T H E T I C ]
        Label lblBrush = new Label("Brush");
        lblBrush.getStyleClass().add("toolbar-label");
        getChildren().add(lblBrush);

        Line l1 = new Line(0, 0, 100, 0);
        l1.getStyleClass().add("toolbar-line");
        getChildren().add(l1);
        
	    // Adds spacing between buttons and things
        Region r1 = new Region();
        r1.setPadding(new Insets(1, 0, 1, 0));
        getChildren().add(r1);
        
        // Allows the user to choose the color they paint with
        colorpicker = new ColorPicker();
        colorpicker.getStyleClass().add("toolbar-colorpicker");
        colorpicker.setPrefWidth(42);
		colorpicker.setPrefHeight(26.5);
        
        // Allows the user to choose their brush size
        ddlRadius = new ComboBox();
        ddlRadius.getStyleClass().add("toolbar-combobox");
        ddlRadius.getItems().addAll(
            "1",
            "2",
            "3"
        );
        ddlRadius.getSelectionModel().select(1);

        // Contains general color and size selecter
        HBox hbxBrush = new HBox();
        hbxBrush.setSpacing(5);
        hbxBrush.getChildren().addAll(colorpicker, ddlRadius);
        getChildren().add(hbxBrush);

        lblHex = new Label("Hex: #000000");
        lblHex.getStyleClass().add("toolbar-label");
        lblHex.setPadding(new Insets(5, 0, 0, 5));

        lblRed = new Label("R: 0");
        lblRed.getStyleClass().add("toolbar-label");
        lblRed.setPadding(new Insets(0, 0, 0, 5));

        lblGreen = new Label("G: 0");
        lblGreen.getStyleClass().add("toolbar-label");
        lblGreen.setPadding(new Insets(0, 0, 0, 5));

        lblBlue = new Label("B: 0");
        lblBlue.getStyleClass().add("toolbar-label");
        lblBlue.setPadding(new Insets(0, 0, 0, 5));

        getChildren().addAll(lblHex, lblRed, lblGreen, lblBlue);

	    // Adds spacing between buttons and things
        Region r2 = new Region();
        r2.setPadding(new Insets(1, 0, 1, 0));
        getChildren().add(r2);
        
	    // Adds the tools label and line below it for [ A E S T H E T I C ]
        Label lblTools = new Label("Tools");
        lblTools.getStyleClass().add("toolbar-label");
        getChildren().add(lblTools);

        Line l2 = new Line(0, 0, 100, 0);
        l2.getStyleClass().add("toolbar-line");
        getChildren().add(l2);

	    // Adds spacing between buttons and things
        Region r3 = new Region();
        r3.setPadding(new Insets(1, 0, 1, 0));
        getChildren().add(r3);

        btnPencil = new Button("Pencil");
        btnPencil.getStyleClass().add("toolbar-button");
        btnPencil.setPrefWidth(100);
        getChildren().add(btnPencil);

        // Allows the user to toggle the brush
        btnBrush = new Button("Brush");
        btnBrush.getStyleClass().add("toolbar-button");
        btnBrush.setPrefWidth(100);
        getChildren().add(btnBrush);
		
	    // Allows the user to toggle the eraser
        btnEraser = new Button("Eraser");
        btnEraser.getStyleClass().add("toolbar-button");
        btnEraser.setPrefWidth(100);
        getChildren().add(btnEraser);

        // Allows the user to toggle the spray can
        btnSpray = new Button("Spray");
        btnSpray.getStyleClass().add("toolbar-button");
        btnSpray.setPrefWidth(100);
        getChildren().add(btnSpray);
        
        // Allows the user to toggle the fill function
        btnFill = new Button("Fill");
        btnFill.getStyleClass().add("toolbar-button");
        btnFill.setPrefWidth(100);
        getChildren().add(btnFill);

        // Add all tool buttons to the array
        toolButtons = new LinkedList<>();
        toolButtons.add(btnPencil);
        toolButtons.add(btnBrush);
        toolButtons.add(btnEraser);
        toolButtons.add(btnSpray);
        toolButtons.add(btnFill);

	    // Adds spacing between buttons and things
        Region r4 = new Region();
        r4.setPadding(new Insets(1, 0, 1, 0));
        getChildren().add(r4);
    }

    /* Sets up the color information and colorpicker whenever a new color is chosen */
	public void setColorInfo(Color color) {
	    // Turn JavaFX Color object into unformatted hex string
        String hex = Integer.toHexString(color.hashCode()).toUpperCase();

		// Format the hex string
        String hexf;
		switch(hex.length()) {
            case 2:
                hexf = "000000";
                break;
            case 3:
                hexf = String.format("00000%s", hex.substring(0, 1));
                break;
            case 4:
                hexf = String.format("0000%s", hex.substring(0, 2));
                break;
            case 5:
                hexf = String.format("000%s", hex.substring(0, 3));
                break;
            case 6:
                hexf = String.format("00%s", hex.substring(0, 4));
                break;
            case 7:
                hexf = String.format("0%s", hex.substring(0, 5));
                break;
            default:
                hexf = hex.substring(0, 6);
		}

        // Set information labels
        lblHex.setText("Hex: #" + hexf);
        lblRed.setText("R: " + String.format("%.0f", color.getRed()*255));
        lblGreen.setText("G: " + String.format("%.0f", color.getGreen()*255));
        lblBlue.setText("B: " + String.format("%.0f", color.getBlue()*255));

		// Set the color of the colorpicker to the chosen color using the hex code
		colorpicker.setStyle("-fx-background-color: #" + hexf + ";");
	}

	/* Highlights the button chosen and resets all other buttons */
	public void chooseNewTool(Button button, Theme theme) {
        for (Button b : toolButtons) {
            if (b != button) {
                b.setStyle(null);
            } else {
                switch(theme) {
                    case LIGHT:
                        b.setStyle("-fx-background-color: #99CFE5");
                        break;
                    case DARK:
                        b.setStyle("-fx-background-color: #00BEBE;");
                        break;
                    case NIGHT:
                        b.setStyle("-fx-background-color: #0096C9;");
                        break;
                }
            }
        }
    }

	/* Brush Settings */
    public ColorPicker getColorPicker() {
        return colorpicker;
    }
    
    public ComboBox getDdlRadius() {
        return ddlRadius;
    }

    /* Tool Buttons */
    public Button getBtnPencil() {
        return btnPencil;
    }

    public Button getBtnBrush() {
	    return btnBrush;
    }
    
    public Button getBtnEraser() {
        return btnEraser;
    }
	
    public Button getBtnSpray() {
        return btnSpray;
    }
    
    public Button getBtnFill() {
        return btnFill;
    }

    /* Position Label */
    public Label getLblPosition() {
        return lblPosition;
    }

}
