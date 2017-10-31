package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteTCPthread extends Thread {
    String[] informacionDistrito;
    MulticastSocket socketMulticast;
    DatagramPacket paqueteRecibido;
    String mensajeRecibido;

    List<Titan> capturados;
    List<Titan> asesinados;
    List<Titan> titanes;

    public ClienteTCPthread(String mensaje, List<Titan> titans, List<Titan> capt, List<Titan> ases){
        informacionDistrito = mensaje.split("/");
        titanes=titans;
        capturados=capt;
        asesinados=ases;
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
                System.out.println("Se recibio el titan multicast:" + mensajeRecibido);
                Titan titan;

                int id = Integer.parseInt(mensajeRecibido.split(" ")[0]);
                String accion = mensajeRecibido.split(" ")[1];
                String culpable = mensajeRecibido.split(" ")[2];

                if(culpable.equals(socketMulticast.getLocalAddress().toString())){
                    if(accion.equals("capturado")){
                        titan = pop1(capturados,id);
                        System.out.println("Capturado: titan "+titan.getNombre()+",id "+titan.getId()+
                                ", tipo "+titan.getTipo());
                    }
                    else{
                        titan = pop1(asesinados,id);
                        System.out.println("Asesinado: titan "+titan.getNombre()+",id "+titan.getId()+
                                ", tipo "+titan.getTipo());

                    }
                }
                else{
                    titan=pop2(titanes,id);
                    System.out.println("Titan "+titan.getNombre()+" "+accion+" por "+culpable);
                }
                System.out.println(mensajeRecibido + " del thread");
                //analizar el mensaje para actualizar las listas de titanes,titanes asesinados,titates capturados
                //falta agregar a la clase en el constructor que reciba estas listas etc.
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    public Titan pop1(List<Titan> lista, int id){
        for(Titan i:lista){
            if(i.getId()==id){
                lista.remove(i);
                return i;
            }
        }
        return null;
    }
    public Titan pop2(List<Titan> lista, int id){
        for(Titan i:lista){
            if(i.getId()==id){
                return i;
            }
        }
        return null;
    }
}
