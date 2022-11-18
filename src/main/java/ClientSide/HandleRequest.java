package ClientSide;

import java.io.File;
import java.io.Serializable;

public class HandleRequest implements Serializable {
protected String sender; //Имя отправителя. Совпадает с ником пользователя в приложении.
                         // Используется для создания корневой папки для пользователя.
protected String typeOfCommand; //Тип команды, передаваемый для исполнения ClientHandler.
                                // Зависит от того, какую кнопку нажимает пользователь.
protected File dlFilePath; //Путь, передаваемый приложению, указывающий откуда оно загружается (либо с сервера,
                           //либо с устройства/соединения пользователя).
protected File ulFilePath; //Путь, указывающий куда загружается файл (либо на сервер, либо на устройство/соединение пользователя).

public HandleRequest (String sender) {

}
}
