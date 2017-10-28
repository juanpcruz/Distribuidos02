import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerDistr {
    public static void main(String argv[]) throws Exception{
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] data = new byte[256];
        String mensaje = "";
        System.out.print("Nombre de distrito a registrar:\n");
        mensaje = mensaje + buffer.readLine();
        System.out.print("IP de distrito a registrar:\n");
        mensaje = mensaje + "/" + buffer.readLine();
        System.out.print("Puerto de distrito a registrar:\n");
        mensaje = mensaje + "/" + buffer.readLine();
        System.out.print("Puerto de multicast de distrito a registrar:\n");
        mensaje = mensaje + "/" + buffer.readLine();
        data = mensaje.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 5050);

        clientSocket.send(sendPacket);
        clientSocket.close();
    }
}
