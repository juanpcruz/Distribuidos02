package com.company;

import java.net.*;
import java.io.*;
import java.util.List;

public class ClienteTCP {

    public static void main(String argv[]) throws Exception{
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in;
        DataOutputStream out;
        Socket socket;
        String ip;
        String puerto;
        String mensaje = "";

        List<Titan> capturados;
        List<Titan> asesinados;

        try {
            //informacion de servidor central
            System.out.print("Ingrese ip de servidor: \n>");
            ip = buffer.readLine();
            System.out.print("Ingrese puerto de servidor: \n>");
            puerto = buffer.readLine();
            socket = new Socket(ip,Integer.parseInt(puerto));
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            //recibir y mostrar mensaje
            mensaje = in.readUTF();
            System.out.println(mensaje);
            //ingresar servidor deseado y enviar
            System.out.print(">");
            mensaje = buffer.readLine();
            out.writeUTF(mensaje);
            //recibir y mostrar respuesta
            mensaje = in.readUTF();
            System.out.println(mensaje);
            //si es positiva, entra en envio de mensajes
            if(mensaje.equals("Conexion aceptada")) {
                do {
                    System.out.print(">");
                    mensaje = buffer.readLine();
                    out.writeUTF(mensaje);
                } while (!mensaje.startsWith("fin"));
            }
            socket.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}