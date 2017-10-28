package com.company;

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
    public void run() {
        String mensaje = "";
        DataInputStream in;
        DataOutputStream out;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        try {
            while(true) {
                // enviar informacion de servidores disponibles
                out = new DataOutputStream(socketCliente.getOutputStream());
                mensaje = "Seleccione servidor a ingresar: \n";
                for (int i = 0; i < distritos.size(); i++) {
                    mensaje = mensaje + (i + 1) + ") " + distritos.get(i).get(0) + "\n";
                }
                out.writeUTF(mensaje);
                //recibir respuesta de servidor deseado
                in = new DataInputStream(socketCliente.getInputStream());
                mensaje = in.readUTF();
                //consultar si se otorga permiso de ingreso al servidor
                System.out.println("Permitir conexion a servidor " + distritos.get(Integer.parseInt(mensaje) - 1).get(0) + "?");
                String respuesta = buffer.readLine();
                //si es afirmativo
                if (respuesta.equals("s") || respuesta.equals("S")) {
                    //agregar a la lista multicast del distrito y enviar datos del servidor
                    mensaje = "Conexion aceptada";
                    out.writeUTF(mensaje);
                    //simple recibo de mensajes
                    while (true) {
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                    }
                } else { // si es negativo(falta cerrar la conexion o intentar otro servidor)
                    mensaje = "Conexion negada";
                    out.writeUTF(mensaje);
                }
            }

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
