import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerTCP {

    public static void main(String argv[]) {
        ServerSocket server;
        Socket socketBienvenida;
        ArrayList<ArrayList> distritos;
        try {
            distritos =new ArrayList<ArrayList>();
            ServerCentralDistritos threadDistritos = new ServerCentralDistritos(distritos);
            threadDistritos.start();
            while (true) {
                server = new ServerSocket(6000);
                socketBienvenida = server.accept();
                ServerCentralThread thread1 = new ServerCentralThread(socketBienvenida,distritos);
                thread1.start();
                server.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}