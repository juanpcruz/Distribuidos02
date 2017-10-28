import java.net.*;
import java.io.*;

public class ServerCentralThread extends Thread {
    Socket socketCliente;
    public ServerCentralThread(Socket socketBienvenida){
        socketCliente = socketBienvenida;
    }
    public void run(){
        String mensaje = "";
        DataInputStream in;
        DataOutputStream out;
        try {
            while(true) {
                out = new DataOutputStream(socketCliente.getOutputStream());
                mensaje = "A cual servidor desea ingresar?\n 1)Europa \n 2)Asia \n 3)U.S West \n 3)U.S East";
                out.writeUTF(mensaje);
                //agregar a la lista multicast del distrito y enviar datos del servidor
                in = new DataInputStream(socketCliente.getInputStream());
                while (true){
                    mensaje = in.readUTF();
                    System.out.println(mensaje);
                }
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
