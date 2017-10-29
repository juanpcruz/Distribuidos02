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
        Boolean cambioDistrito;
        MulticastSocket socketMulticast;
        String[] partes;

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
                //datos del distrito
                mensaje = in.readUTF();
                partes = mensaje.split("/");
                System.out.println(mensaje);
                cambioDistrito = false;
                //recepcion de mensajes multicast
                socketMulticast = new MulticastSocket(Integer.parseInt(partes[2]));
                socketMulticast.joinGroup(InetAddress.getByName(partes[1]));
                while (!mensaje.equals("asd")) {
                    byte[] bitsRecibido = new byte[256];
                    DatagramPacket msgPacket = new DatagramPacket(bitsRecibido, bitsRecibido.length);
                    socketMulticast.receive(msgPacket);
                    String mensajeRecibido = new String(msgPacket.getData());
                    System.out.println(mensajeRecibido);
                }
                while (!cambioDistrito) {
                    System.out.println("Lista de acciones:");
                    System.out.println("1) Listar titanes\n2) Cambiar de distrito\n3) Capturar titan\n4) Asesinar titan\n5) Listar titanes capturados\n6) Listar titanes asesinados");
                    //switch con case para cada uno
                    System.out.print(">");
                    mensaje = buffer.readLine();
                    switch (mensaje){
                        case "1":
                            System.out.println("1) Listar titanes)");
                            break;
                        case "2":
                            System.out.println("2) Cambiooo");
                            cambioDistrito = true;
                            break;
                    }
                    out.writeUTF(mensaje);
                }
                if(cambioDistrito){
                    //cambiar, dejar en un while true desde despues de conectar al servidor central
                }
            }
            socket.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}