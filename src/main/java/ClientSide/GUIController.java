package ClientSide;

import enums.CommandType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GUIController {
    protected String host;
    protected int port;
    protected Socket socket;
    protected InputStream in;
    protected OutputStream out;
    protected String nickName;
    protected boolean isStopped = false;
    public boolean getStopped() {
        return isStopped;
    }
    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }
    protected File chosenFile;
    @FXML
    AnchorPane anchor;

    public GUIController() {
        this.host = "localhost";//Placeholder
        this.port = 8189;//Placeholder
        try (Socket socket = new Socket(host, port)) {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(in));
            while (!this.getStopped()) {
                Command command = (Command) objIn.readObject();
                switch (command.getCommandType()) {
                    case RECEIVE_FILE -> receiveFile(command.getFileName(), command.getFileData());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //Привязанный к кнопке метод, позволяющий клиенту выбрать, куда подключиться, введя желаемые адрес и порт в диалоговом окне.
    private void connectToServer(){

    }
    //Привязанный к кнопке метод, позволяющий авторизироваться.
    private void authorize(){

    }
    //TO-DO При вызове методов uploadDoc и downloadDoc появляется окно с просьбой ожидать.
    private void uploadFile(){
        //Получение пути до файла, который нужно загрузить.
        //chosenFile = ...
        try(BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(chosenFile));
            ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(out)))
        {
            byte[] bytesRead = new byte[(int)chosenFile.length()];
            bufIn.read(bytesRead);
            Command command = new Command(nickName, CommandType.UPLOAD_FILE);
            command.fileName = chosenFile.getName();
            command.fileData = bytesRead;
            objOut.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void downloadFile(){
        Command command = new Command(nickName, CommandType.DOWNLOAD_FILE);
        //Получение пути до документа, на который указывает пользователь
        //chosenFile = ...
        command.fileName = chosenFile.getName();
        try(ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(out))){
        objOut.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void receiveFile(String fileName, byte[] content){
        try {
            String home = System.getProperty("user.home");
            File file = Files.createFile(Path.of((home + "/Downloads/" + fileName))).toFile();
            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));
            fileOut.write(content);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void closeApplication(ActionEvent actionEvent) {
        this.setStopped(true);
        Platform.exit();
    }
}
