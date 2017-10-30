package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerDistrThread extends Thread{
    String[] informacionDistrito;
    DatagramSocket socketUDP;
    DatagramPacket paqueteRecibido;
    DatagramPacket paqueteEnviado;
    byte[] data = new byte[256];
    String mensajeRecibido;
    String mensajeEnviado;
    List<Titan> titanes;
    int puertoMult;
    String IPMult;


    public ServerDistrThread(DatagramSocket socket, List<Titan> listaTitanes, int puertoMulticast, String ipMulticast){
        socketUDP = socket;
        titanes = listaTitanes;
        puertoMult = puertoMulticast;
        IPMult = ipMulticast;
    }
    public void run() {
        try {
            while(true) {
                //Thread encargado de la recepcion asincronica de datagramas unicast por parte de de clientesTCP
                //Responde por multicast peticiones de asesinato/captura
                socketUDP.receive(paqueteRecibido);
                String mensajeRecibido = new String(paqueteRecibido.getData());
                //si el mensaje es Hola, se debe responder con la lista actual de titanes
                if (mensajeRecibido.equals("Hola")) {
                    mensajeEnviado = "lista de titanes";
                    data = mensajeEnviado.getBytes();
                    paqueteEnviado = new DatagramPacket(data, data.length, paqueteRecibido.getAddress(), paqueteRecibido.getPort());
                    socketUDP.send(paqueteEnviado);
                }
                //Aca si es eliminar, borrar de la lista de titanes(supongo que List es referenciada)
                //luego enviar por multicast info de quien lo mato/captura
                mensajeEnviado = "tuma";
                data = mensajeEnviado.getBytes();
                paqueteEnviado = new DatagramPacket(data, data.length, InetAddress.getByName(IPMult), puertoMult);
                socketUDP.send(paqueteEnviado);
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
