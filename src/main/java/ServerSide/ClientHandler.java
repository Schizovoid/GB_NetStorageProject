package ServerSide;

import ClientSide.Command;
import enums.CommandType;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientHandler implements Runnable {
    protected InputStream in;
    protected OutputStream out;
    protected Socket clientSocket;
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
        Command command = (Command) objIn.readObject();
        switch (command.getCommandType()){
            case AUTHORIZE -> authoriseClient(command.getSender());
            case DOWNLOAD_FILE -> downloadFileThere(command.getFileName());
            case UPLOAD_FILE -> uploadFileHere(command.getFileName(), command.getFileData());
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
                //TO-DO: подключение к базе данных
                while (clientSocket.isConnected() & !clientSocket.isClosed()){
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
            clientDirectory = Path.of(String.format("src/main/TempStorage/%s/" , name));
            if (!clientDirectory.toFile().exists()) {
                Files.createDirectory(clientDirectory);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Сохранение копии переданного файла на стороне сервера
    public void uploadFileHere(String fileName, byte[] content) {
        try {
            File file = Files.createFile(Path.of((clientDirectory + fileName))).toFile();
            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));
            fileOut.write(content);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void downloadFileThere(String fileName) {
    try(ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(out))){
        File file = (Path.of(String.format(clientDirectory + "/%s", fileName))).toFile();
        if (file.exists()) {
            try (FileInputStream fileIn = new FileInputStream(file)) {
                byte[] content = new byte[(int)file.length()];
                fileIn.read(content);
                Command command = new Command(clientName, CommandType.DOWNLOAD_FILE);
                command.fileData = content;
            objOut.writeObject(command);
            }
        } else {
            throw new RuntimeException("File does not exist.");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    }
