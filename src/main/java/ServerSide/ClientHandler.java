package ServerSide;

import ClientSide.HandleRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientHandler implements Runnable {
    protected InputStream in;
    protected OutputStream out;
    protected Socket clientSocket;
    protected boolean isStopped = false;
    public boolean getStopped() {
        return isStopped;
    }
    protected String clientName;
    protected Path clientDirectory;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.clientSocket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Десериализируются входящие запросы с клиента, даются дальнейшие команды в зависимости от типа переданой команды
    public void handle() {
    try (ObjectInputStream objIn = new ObjectInputStream(in)){
        HandleRequest handleRequest = (HandleRequest) objIn.readObject();
        switch (handleRequest.getCommandType()){
            case AUTHORISE -> authoriseClient(handleRequest.getSender());
            case DOWNLOAD_DOC -> downloadDocThere();
            case UPLOAD_DOC -> uploadDocHere();
        }
    } catch (IOException | ClassNotFoundException e){
        e.printStackTrace();
    }
    }
        @Override
        public void run() {
            try {
                this.in = clientSocket.getInputStream();
                this.out = clientSocket.getOutputStream();
                //TO-DO: подключение к базе данных и авторизация пользователя
                while (!this.getStopped()){
                    //Обработка поступающих запросов с клиента
                    handle();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public void authoriseClient(String name) {
        try {
            //TO-DO: логика авторизации клиента на стороне сервера и БД

            //Для пользователя создаётся его корневая папка
            clientName = name;
            clientDirectory = Path.of(String.format("src/main/TempStorage/%s" , name));
            Files.createDirectory(clientDirectory);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void uploadDocHere() {

    }
    public void downloadDocThere() {

    }
    }
