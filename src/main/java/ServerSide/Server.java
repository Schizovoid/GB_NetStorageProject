package ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    protected int port = 8189;
    protected boolean isStopped = false;
    public boolean getStopped() {
        return isStopped;
    }
    protected Thread serverThread;

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }
    protected InputStream in;
    protected OutputStream out;

    /*
    Многопоточный IO сервер:
    один поток serverThread ожидает подключения через .accept(),
    создаёт поток ClientHandler для подключившегося пользователя,
    затем возвращается к ожиданию подключения.
     */
public Server(int port) {
    this.port = port;
}
    @Override
    public void run() {
        synchronized(this){
            this.serverThread = Thread.currentThread();
        }
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            // TO-DO Подключение сервера к базе данных.
            while (!this.isStopped) {
                Socket clientSocket = serverSocket.accept();
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
                new Thread(new ClientHandler(this, clientSocket)).start();
                if (getStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong server-side!");
        } finally {
        shutdown();
        }
    }
    public void shutdown() {
    setStopped(true);
    try {
        in.close();
        out.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}


