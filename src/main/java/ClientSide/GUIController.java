package ClientSide;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController {
    protected InputStream in;
    protected OutputStream out;
    protected String nickName;
    @FXML
    AnchorPane anchor;

    public void sendCommand () {

    }
    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
