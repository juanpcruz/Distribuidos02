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
    ArrayList<Titan> titanes;

    public ClienteTCPthread(String mensaje, ArrayList<Titan> titans, List<Titan> capt, List<Titan> ases){
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
                Titan titan;

                int id = Integer.parseInt(mensajeRecibido.split(" ")[0]);
                String accion = mensajeRecibido.split(" ")[1];
                if(accion.equals("aparece")){
                    System.out.println("Aparece titan "+mensajeRecibido.split(" ")[2]+ " del tipo "+mensajeRecibido.split(" ")[3]);
                    titanes.add(new Titan(mensajeRecibido.split(" ")[2], mensajeRecibido.split(" ")[3],mensajeRecibido.split(" ")[4]));
                    System.out.print(">");
                }else {
                    String culpable = mensajeRecibido.split(" ")[2];
                    System.out.println(culpable);
                    System.out.println(InetAddress.getLocalHost().getHostAddress());

                    if (culpable.equals(InetAddress.getLocalHost().getHostAddress())) {
                        if (accion.equals("capturado")) {
                            titan = pop1(capturados, id);
                            System.out.println("Capturado: titan " + titan.getNombre() + ",id " + titan.getId() +
                                    ", tipo " + titan.getTipo());
                        } else {
                            titan = pop1(asesinados, id);
                            System.out.println("Asesinado: titan " + titan.getNombre() + ",id " + titan.getId() +
                                    ", tipo " + titan.getTipo());

                        }
                    } else {
                        titan = pop2(titanes, id);
                        System.out.println("Titan " + titan.getNombre() + " " + accion + " por " + culpable);
                    }
                }
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
    public void cerrar(){
        this.socketMulticast.close();
    }
    public ArrayList<Titan> superUnpack(String mensaje){
        String[] split = mensaje.split("#");
        String[] split2;
        ArrayList<Titan> resultado = new ArrayList<>();
        for(String i:split){
            if(i.startsWith("$")){
                break;
            }
            split2=i.split("/");
            Titan aux = new Titan(split2[1],split2[2],split2[3]);
            aux.setId(Integer.parseInt(split2[0]));
            aux.setUltimoDistrito(split2[3]);
            resultado.add(aux);
        }
        return resultado;
    }
}
