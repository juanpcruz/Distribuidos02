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
                    System.out.print(paqueteRecibido.getAddress()+" dice: "+mensajeRecibido+"\n");
                    String[] partes = mensajeRecibido.split("/");
                    ArrayList nuevoDistrito = new ArrayList();
                    nuevoDistrito.add(partes[0]);
                    nuevoDistrito.add(partes[1]);
                    nuevoDistrito.add(partes[2]);
                    nuevoDistrito.add(partes[3]);
                    nuevoDistrito.add(partes[4]);
                    System.out.print("Agregando distrito \n");
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
