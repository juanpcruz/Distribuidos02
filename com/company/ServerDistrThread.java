package com.company;

import java.net.*;
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
    List<Titan> capturados;
    List<Titan> asesinados;
    int puertoMult;
    String IPMult;
    String distrito;
    MulticastSocket multicastSocket;


    public ServerDistrThread(DatagramSocket socket, List<Titan> listaTitanes, List<Titan> capt, List<Titan> ases,
                             int puertoMulticast, String ipMulticast, String dist){
        socketUDP = socket;
        titanes = listaTitanes;
        puertoMult = puertoMulticast;
        IPMult = ipMulticast;
        distrito = dist;
        capturados=capt;
        asesinados=ases;
    }
    public void run() {
        try {
            data = new byte[256];
            paqueteRecibido = new DatagramPacket(data, 256);
            multicastSocket = new MulticastSocket();
            while(true) {
                //Thread encargado de la recepcion asincronica de datagramas unicast por parte de de clientesTCP
                //Responde por multicast peticiones de asesinato/captura
                System.out.println("Esperando paquete en thread ...");
                socketUDP.receive(paqueteRecibido);
                String mensajeRecibido = new String(paqueteRecibido.getData());
                System.out.println("Se recibio el mensaje: " +mensajeRecibido +"/");
                //si el mensaje es Hola, se debe responder con la lista actual de titanes
                if (mensajeRecibido.split(" ")[0].equals("Hola")) {
                    System.out.println("Se esta enviando titanes a "+ paqueteRecibido.getAddress()+ paqueteRecibido.getPort());
                    mensajeEnviado = superJoin(titanes);
                    data = mensajeEnviado.getBytes();
                    paqueteEnviado = new DatagramPacket(data, data.length, paqueteRecibido.getAddress(), paqueteRecibido.getPort());
                    socketUDP.send(paqueteEnviado);
                    continue;
                }
                else {
                    //Mensaje: Accion + Id del Titan
                    String accion = mensajeRecibido.split(" ")[0];
                    int Id = Integer.parseInt(mensajeRecibido.split(" ")[1]);
                    //Accion de captura
                    if (accion.equals("Capturar")){
                        Titan capturado = captura(Id);
                        if(capturado!=null){
                            mensajeEnviado = capturado.getId()+" capturado "+
                                    paqueteRecibido.getAddress().toString()+" ";
                            capturados.add(capturado);
                        }
                    }
                    //Accion de asesinato
                    if (accion.equals("Asesinar")){
                        Titan asesinado = muerte(Id);
                        if(asesinado!=null){
                            mensajeEnviado = asesinado.getId()+" asesinado "+
                                    socketUDP.getRemoteSocketAddress().toString()+" ";
                            asesinados.add(asesinado);
                        }
                    }
                    data = mensajeEnviado.getBytes();
                    paqueteEnviado = new DatagramPacket(data, data.length, InetAddress.getByName(IPMult), puertoMult);
                    multicastSocket.send(paqueteEnviado);
                }
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    public Titan captura(int ID) {
        for (Titan i : titanes) {
            if (i.getId() == ID && (i.tipoNormal() || i.tipoCambiante())) {
                i.setUltimoDistrito(distrito);
                titanes.remove(i);
                return i;
            }
        }
        return null;
    }
    public Titan muerte(int ID) {
        for (Titan i : titanes) {
            if (i.getId() == ID && (i.tipoNormal() || i.tipoExcentrico())) {
                i.setUltimoDistrito(distrito);
                titanes.remove(i);
                return i;
            }
        }
        return null;
    }
    public String superJoin(List<Titan> lista){
        String resultado = "";

        for(Titan i:lista){
            resultado = resultado.concat(i.getId()+"/"+i.getNombre()+"/"+i.getTipo()+"/"+i.getUltimoDistrito()+"#");
        }
        return resultado+"$";
    }
}
