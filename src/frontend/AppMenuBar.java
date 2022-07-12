package frontend;

import javafx.scene.control.*;

import java.util.Optional;

public class AppMenuBar extends MenuBar {

    private static final String FILE = "Archivo";
    private static final String EXIT = "Salir";
    private static final String HELP = "Ayuda";
    private static final String ABOUT = "Acerca De";
    private static final String PAINT = "Paint 4D";
    private static final String EXIT_APP = "Salir de la aplicación";
    private static final String ARE_YOU_SURE = "¿Está seguro que desea salir de la aplicación?";
    private static final String POO = "TPE Final POO Julio 2022";


    public AppMenuBar() {
        Menu file = new Menu(FILE);
        MenuItem exitMenuItem = new MenuItem(EXIT);
        exitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(EXIT);
            alert.setHeaderText(EXIT_APP);
            alert.setContentText(ARE_YOU_SURE);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    System.exit(0);
                }
            }
        });
        file.getItems().add(exitMenuItem);
        Menu help = new Menu(HELP);
        MenuItem aboutMenuItem = new MenuItem(ABOUT);
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ABOUT);
            alert.setHeaderText(PAINT);
            alert.setContentText(POO);
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);
        getMenus().addAll(file, help);
    }

}
