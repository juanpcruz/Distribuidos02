package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerCentralDistritos extends Thread  {
    ArrayList<ArrayList> distritos;
    DatagramSocket socketUDP;
    byte[] mensaje;
    String mensaje2;
    byte[] data = new byte[256];
    DatagramPacket paqueteRecibido;
    DatagramPacket paqueteEnviado;
    public ServerCentralDistritos(ArrayList distrito){
        distritos = distrito;

    }
    public void run() {
        try {
            socketUDP = new DatagramSocket(5050);
            mensaje = new byte[256];
            paqueteRecibido = new DatagramPacket(mensaje, 256);
            while(true) {
                //en espera de recibir datagramas
                try {
                    socketUDP.receive(paqueteRecibido);
                    //traducir datagrama a string y luego obtener informacion de distrito a registrar
                    String mensajeRecibido = new String(paqueteRecibido.getData());
                    String[] partes = mensajeRecibido.split("/");
                    System.out.println("[Servidor Central] Se esta agregando el servidor/distrito: ");
                    System.out.println("Nombre: "+partes[0]);
                    System.out.println("IP multicast: "+partes[1]);
                    System.out.println("Puerto multicast: "+partes[2]);
                    System.out.println("IP peticiones: "+partes[3]);
                    System.out.println("Puerto peticiones: "+partes[4]);
                    ArrayList nuevoDistrito = new ArrayList();
                    nuevoDistrito.add(partes[0]);
                    nuevoDistrito.add(partes[1]);
                    nuevoDistrito.add(partes[2]);
                    nuevoDistrito.add(partes[3]);
                    nuevoDistrito.add(partes[4]);
                    distritos.add(nuevoDistrito);
                    //enviar de vuelta confirmando registro exitoso
                    mensaje2 = "Se a registrado exitosamente el distrito";
                    data = mensaje2.getBytes();
                    paqueteEnviado = new DatagramPacket(data, data.length, paqueteRecibido.getAddress(), paqueteRecibido.getPort());
                    socketUDP.send(paqueteEnviado);
                } catch (SocketException a) {

                }

            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
