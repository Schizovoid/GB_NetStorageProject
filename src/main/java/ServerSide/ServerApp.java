package ServerSide;

public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server(8189);
        new Thread (server).start();
    }
}
