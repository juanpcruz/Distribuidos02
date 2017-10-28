import java.net.*;
import java.io.*;

public class ServerTCP {

    public static void main(String argv[]) {
        ServerSocket server;
        Socket socketBienvenida;
        try {
            while (true) {
                server = new ServerSocket(6000);
                socketBienvenida = server.accept();
                ServerCentralThread thread1 = new ServerCentralThread(socketBienvenida);
                thread1.run();
                server.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}