import java.net.*;
import java.io.*;
import java.util.*;

public class ServerCentralThread extends Thread {
    Socket socketCliente;
    ArrayList<ArrayList> distritos;
    public ServerCentralThread(Socket socketBienvenida, ArrayList distrito){
        socketCliente = socketBienvenida;
        distritos = distrito;
    }
    public void run(){
        String mensaje = "";
        DataInputStream in;
        DataOutputStream out;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(distritos+"\n");
        try {
            while(true) {
                out = new DataOutputStream(socketCliente.getOutputStream());
                //mensaje = "A cual servidor desea ingresar?\n 1)Europa \n 2)Asia \n 3)U.S West \n 3)U.S East";

                mensaje = "Seleccione servidor a ingresar: \n";
                for(int i=0; i<distritos.size();i++) {
                    mensaje = mensaje + (i + 1) + ") " + distritos.get(i).get(2) + "\n";
                }
                out.writeUTF(mensaje);

                //agregar a la lista multicast del distrito y enviar datos del servidor
                in = new DataInputStream(socketCliente.getInputStream());
                mensaje = in.readUTF();
                System.out.println("Permitir conexion a servidor " +distritos.get(Integer.parseInt(mensaje)-1).get(2)+"?" );
                String respuesta = buffer.readLine();
                if(respuesta.equals("s") || respuesta.equals("S")){
                    System.out.print("asd"+ respuesta);
                    mensaje = "Conexion aceptada";
                    out.writeUTF(mensaje);
                }
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
