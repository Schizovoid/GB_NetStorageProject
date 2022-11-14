package ClientSide;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController {
    @FXML
    AnchorPane anchor;


    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
