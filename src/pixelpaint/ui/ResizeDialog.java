package pixelpaint.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

public class ResizeDialog extends GridPane {

    private int gridWidth;
    private int gridHeight;
    private Button btnSubmit;

    public ResizeDialog() {
        this.getStyleClass().add("resize-dialog");
        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);

        Label lblTitle = new Label("Resize Canvas");
        lblTitle.getStyleClass().add("resize-dialog-label");
        add(lblTitle, 0, 0, 2, 1);

        Line decor = new Line(0, 0, 250, 0);
        decor.getStyleClass().add("resize-dialog-line");
        add(decor, 0, 1, 2, 1);

        Label lblWidth = new Label("Width: ");
        lblWidth.getStyleClass().add("resize-dialog-label");
        add(lblWidth, 0, 2);

        TextField txtWidth = new TextField();
        txtWidth.getStyleClass().add("resize-dialog-textfield");
        txtWidth.setOnKeyPressed(e -> {
            try {
                gridWidth = Integer.parseInt(txtWidth.getText());
            } catch (Exception ignored) {
                txtWidth.clear();
            }
        });
        add(txtWidth, 1, 2);

        Label lblHeight = new Label("Height: ");
        lblHeight.getStyleClass().add("resize-dialog-label");
        add(lblHeight, 0, 3);

        TextField txtHeight = new TextField();
        txtHeight.getStyleClass().add("resize-dialog-textfield");
        txtHeight.setOnKeyPressed(e -> {
            try {
                gridHeight = Integer.parseInt(txtWidth.getText());
            } catch (Exception ignored) {
                txtHeight.clear();
            }
        });
        add(txtHeight, 1, 3);

        btnSubmit = new Button("Submit");
        btnSubmit.getStyleClass().add("resize-dialog-button");
        setHalignment(btnSubmit, HPos.RIGHT);
        add(btnSubmit, 1, 4);
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public Button getBtnSubmit() {
        return btnSubmit;
    }

}
