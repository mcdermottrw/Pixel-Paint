package pixelpaint.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.text.DecimalFormat;

public class InfoBar extends HBox {

    private Label lblPosition;
    private Label lblDimensions;
    private Label lblPixelSize;

    public InfoBar(Canvas canvas) {
        // Add style class to this HBox
        this.getStyleClass().add("infobar");
        this.setSpacing(5);
        this.setPadding(new Insets(5, 5, 5, 5));

        lblPosition = new Label("0, 0");
        lblPosition.setPadding(new Insets(0, 0, 0, 20));
        lblPosition.setPrefWidth(80);
        lblPosition.getStyleClass().add("infobar-label");
        getChildren().add(lblPosition);

        Label lblSeperator1 = new Label("|");
        lblSeperator1.getStyleClass().add("infobar-label");
        lblSeperator1.setPadding(new Insets(0, 20, 0, 20));
        getChildren().add(lblSeperator1);

        lblDimensions = new Label(canvas.getGridWidth() + "x" + canvas.getGridHeight());
        lblDimensions.setPrefWidth(80);
        lblDimensions.getStyleClass().add("infobar-label");
        getChildren().add(lblDimensions);

        Label lblSeperator2 = new Label("|");
        lblSeperator2.getStyleClass().add("infobar-label");
        lblSeperator2.setPadding(new Insets(0, 20, 0, 20));
        getChildren().add(lblSeperator2);

        lblPixelSize = new Label("5");
        lblPixelSize.setPrefWidth(80);
        lblPixelSize.getStyleClass().add("infobar-label");
        getChildren().add(lblPixelSize);
    }

    public Label getLblPosition() {
        return lblPosition;
    }

    public void setLblPosition(Pixel p) {
        lblPosition.setText((p.getXCoord()+1) + ", " + (p.getYCoord()+1));
    }

    public Label getLblDimensions() {
        return lblDimensions;
    }

    public void setLblDimensions(int height, int width) {
        lblDimensions.setText(height + "x" + width);
    }

    public Label getLblPixelSize() {
        return lblPixelSize;
    }

    public void setLblPixelSize(Pixel p) {
        DecimalFormat df = new DecimalFormat("#");
        lblPixelSize.setText(df.format(p.getHeight()));
    }

}
