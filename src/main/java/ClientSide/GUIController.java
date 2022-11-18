package ClientSide;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController {
    protected String host;
    protected int port;
    protected InputStream in;
    protected OutputStream out;
    protected String nickName;
    protected File dlFilePath;
    protected File ulFilePath;
    @FXML
    AnchorPane anchor;

    public GUIController(){
        //To-do: первое окно клиентского приложения, позволяющее выбрать куда подключиться.
        //connectToServer();
        this.host = "localhost";//Placeholder
        this.port = 8189;//Placeholder
        try(Socket socket = new Socket(host,port)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Привязанный к кнопке метод, позволяющий клиенту выбрать, куда подключиться, введя желаемые адрес и порт в диалоговом окне.
    private void connectToServer(){

    }

    //Для каждой кнопки, совершающей действие на сервере, создаётся свой метод(ActionEvent). Эти методы включают в себя
    //метод sendCommand, создающий и отправляющий сериализированный объект HandleRequest.

    private void uploadDoc(){

    }
    private void downloadDoc(){

    }
    private void sendCommand () {
    HandleRequest handleRequest = new HandleRequest(nickName);
    }
    private void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
