package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerCentralDistritos extends Thread  {
    ArrayList<ArrayList> distritos;
    DatagramSocket socket;
    byte[] mensaje;
    DatagramPacket paqueteRecibido;
    public ServerCentralDistritos(ArrayList distrito){
        //distritos sin servidor (solo de prueba de listado)
        distritos = distrito;
        ArrayList distritos1 = new ArrayList();
        distritos1.add("nombre");
        distritos1.add("ip");
        distritos1.add("puerto");
        distritos1.add("puertoB");

        distritos.add(distritos1);

        ArrayList distrito2 = new ArrayList();
        distrito2.add("nombre2");
        distrito2.add("ip2");
        distrito2.add("puerto2");
        distritos1.add("puertoB2");

        distritos.add(distrito2);
    }
    public void run() {
        try {
            socket = new DatagramSocket(5050);
            mensaje = new byte[256];
            paqueteRecibido = new DatagramPacket(mensaje, 256);
            while(true) {
                //en espera de recibir datagramas
                try {
                    socket.receive(paqueteRecibido);
                } catch (SocketException a) {
                    System.err.println(a.getMessage());
                }
                //traducir datagrama a string y luego obtener informacion de distrito a registrar
                String mensajeRecibido = new String(paqueteRecibido.getData());
                System.out.print(paqueteRecibido.getAddress()+" dice: "+mensajeRecibido+"\n");
                String[] partes = mensajeRecibido.split("/");
                ArrayList nuevoDistrito = new ArrayList();
                nuevoDistrito.add(partes[0]);
                nuevoDistrito.add(partes[1]);
                nuevoDistrito.add(partes[2]);
                nuevoDistrito.add(partes[3]);
                distritos.add(nuevoDistrito);
                //enviar de vuelta confirmando registro exitoso
            }
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
