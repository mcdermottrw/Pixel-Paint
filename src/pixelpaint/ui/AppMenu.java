package pixelpaint.ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class AppMenu extends MenuBar {

    private Menu fileMenu;
    private Menu editMenu;
    private Menu themeMenu;

    public AppMenu() {

        // Set a style class for the MenuBar
        getStyleClass().add("app-menu");

        //--------------------------------------//
        //          F I L E    M E N U          //
        //--------------------------------------//

        fileMenu = new Menu("File");

        // New - Index 0
        MenuItem fileMenu1 = new MenuItem("New");
        fileMenu1.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        // Open - Index 1
        MenuItem fileMenu2 = new MenuItem("Open");
        fileMenu2.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        // Save - Index 2
        MenuItem fileMenu3 = new MenuItem("Save");
        fileMenu3.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        // Exit - Index 3
        MenuItem fileMenu4 = new MenuItem("Exit");
        fileMenu4.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        fileMenu.getItems().addAll(fileMenu1, fileMenu2, fileMenu3, fileMenu4);

        this.getMenus().add(fileMenu);

        //--------------------------------------//
        //          E D I T    M E N U          //
        //--------------------------------------//

        editMenu = new Menu("Edit");

        // Undo - Index 0
        MenuItem editMenu1 = new MenuItem("Undo");
        editMenu1.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));

        // Redo - Index 1
        MenuItem editMenu2 = new MenuItem("Redo");
        editMenu2.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));

        // Resize - Index 2
        MenuItem editMenu3 = new MenuItem("Resize");
        editMenu3.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));

        editMenu.getItems().addAll(editMenu1, editMenu2, editMenu3);

        this.getMenus().add(editMenu);

        //----------------------------------------//
        //          T H E M E    M E N U          //
        //----------------------------------------//

        themeMenu = new Menu("Theme");

        // Light - Index 0
        MenuItem themeMenu1 = new MenuItem("Light");

        // Dark - Index 1
        MenuItem themeMenu2 = new MenuItem("Dark");

        // Night - Index 2
        MenuItem themeMenu3 = new MenuItem("Night");

        themeMenu.getItems().addAll(themeMenu1, themeMenu2, themeMenu3);

        this.getMenus().add(themeMenu);
    }

    public Menu getFileMenu() {
        return fileMenu;
    }

    public Menu getEditMenu() {
        return editMenu;
    }

    public Menu getThemeMenu() {
        return themeMenu;
    }
}
