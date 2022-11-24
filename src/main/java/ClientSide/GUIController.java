package ClientSide;

import enums.CommandType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController {
    protected String host;
    protected int port;
    protected InputStream in;
    protected OutputStream out;
    protected String nickName;
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    protected CommandType commandType;
    public CommandType getCommandType() {
        return commandType;
    }
    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
    protected File chosenFile;
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

    private void uploadDoc(){
        setCommandType(CommandType.UPLOAD_DOC);
        try(BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(chosenFile));
            ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(out)))
        {
            byte[] bytesRead = new byte[(int)chosenFile.length()];
            bufIn.read(bytesRead);
            HandleRequest handleRequest = new HandleRequest(nickName, commandType);
            handleRequest.fileName = chosenFile.getName();
            handleRequest.fileData = bytesRead;
            objOut.writeObject(handleRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void downloadDoc(){
        setCommandType(CommandType.DOWNLOAD_DOC);
        HandleRequest handleRequest = new HandleRequest(nickName, commandType);
    }
    private void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}
