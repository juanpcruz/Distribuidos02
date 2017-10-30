package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClienteTCPthread extends Thread {
    String[] informacionDistrito;
    MulticastSocket socketMulticast;
    DatagramPacket paqueteRecibido;
    String mensajeRecibido;
    public ClienteTCPthread(String mensaje){
        informacionDistrito = mensaje.split("/");
    }
    public void run() {
        try {
            //Thread encargado de la recepcion asincronica de datagramas multicast por parte del distrito(ServerDistr)
            socketMulticast = new MulticastSocket(Integer.parseInt(informacionDistrito[2]));
            socketMulticast.joinGroup(InetAddress.getByName(informacionDistrito[1]));
            while(true) {
                byte[] bitsRecibido = new byte[256];
                paqueteRecibido = new DatagramPacket(bitsRecibido, bitsRecibido.length);
                socketMulticast.receive(paqueteRecibido);
                mensajeRecibido = new String(paqueteRecibido.getData());
                System.out.println(mensajeRecibido + " del thread");
                //analizar el mensaje para actualizar las listas de titanes,titanes asesinados,titates capturados
                //falta agregar a la clase en el constructor que reciba estas listas etc.
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
