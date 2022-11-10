package ServerSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    InputStream in;
    OutputStream out;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        @Override
        public void run() {

        }

    }
