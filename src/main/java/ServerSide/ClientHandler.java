package ServerSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    protected InputStream in;
    protected OutputStream out;
    protected Socket clientSocket;
    protected boolean isStopped = false;
    public boolean getStopped() {
        return isStopped;
    }
    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.clientSocket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (IOException e) {
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

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
