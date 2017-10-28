import java.net.*;
import java.io.*;

public class ClienteTCP {

    public static void main(String argv[]) {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in;
        DataOutputStream out;
        Socket socket;
        String ip;
        String puerto;
        String mensaje = "";
        try {
            System.out.print("Ingrese ip de servidor: \n>");
            ip = buffer.readLine();
            System.out.print("Ingrese puerto de servidor: \n>");
            puerto = buffer.readLine();

            socket = new Socket(ip,Integer.parseInt(puerto));
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            mensaje = in.readUTF();
            System.out.println(mensaje);
            do {
                System.out.print(">");
                mensaje = buffer.readLine();
                out.writeUTF(mensaje);
            } while (!mensaje.startsWith("fin"));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}