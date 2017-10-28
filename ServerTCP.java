import java.net.*;
import java.io.*;

public class ServerTCP {

    public static void main(String argv[]) {
        //BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        String mensaje = "";
        ServerSocket socket;
        Socket socket_cli;
        DataInputStream in;
        DataOutputStream out;
        try {
            socket = new ServerSocket(6000);
            socket_cli = socket.accept();
            //derivar a thread y lo de a continuacion pasar a thread
            
            out = new DataOutputStream(socket_cli.getOutputStream());
            mensaje = "A cual servidor desea ingresar?\n 1)Europa \n 2)Asia \n 3)U.S West \n 3)U.S East";
            out.writeUTF(mensaje);

            in = new DataInputStream(socket_cli.getInputStream());
            do {
                mensaje = in.readUTF();
                System.out.println(mensaje);
            } while (1>0);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}