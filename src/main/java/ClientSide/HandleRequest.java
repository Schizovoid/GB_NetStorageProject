package ClientSide;

import enums.CommandType;

import java.io.File;
import java.io.Serializable;

public class HandleRequest implements Serializable {

    //Имя отправителя. Совпадает с ником пользователя в приложении. Используется для создания корневой папки для пользователя.
    protected String sender;
    //Тип команды, передаваемый для исполнения ClientHandler. Зависит от того, какую кнопку нажимает пользователь.
    protected CommandType commandType;
    /*Путь, указывающий конечную точку загрузки файла (либо на сервер,либо на устройство/соединение пользователя).
    Пользователь может оставить это поле по умолчанию (файл загружается в его корневую папку), либо в указанную
    директорию. */
    protected File destinationFilePath;
    //Название файла, с которым работает приложение.
    protected String fileName;
    //Массив данных, загружаемый или скачиваемый приложением из файла.
    byte[] fileData;

public HandleRequest (String sender, CommandType commandType) {
this.sender = sender;
this.commandType = commandType;
}
    public CommandType getCommandType() {
        return commandType;
    }
    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
    public String getSender() {
        return sender;
    }
}
