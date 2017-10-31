package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
    Distrito distrito;


    public ServerDistrThread(DatagramSocket socket, List<Titan> listaTitanes, List<Titan> capt, List<Titan> ases,
                             int puertoMulticast, String ipMulticast, Distrito dist){
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
            System.out.println("Inicio de thread encargado de escuchar peticiones");
            data = new byte[256];
            paqueteRecibido = new DatagramPacket(data, 256);
            while(true) {
                //Thread encargado de la recepcion asincronica de datagramas unicast por parte de de clientesTCP
                //Responde por multicast peticiones de asesinato/captura
                System.out.println("Esperando paquete en thread ...");
                socketUDP.receive(paqueteRecibido);
                String mensajeRecibido = new String(paqueteRecibido.getData());
                System.out.println("Se recibio el mensaje: " +mensajeRecibido +"/");
                //si el mensaje es Hola, se debe responder con la lista actual de titanes
                if (mensajeRecibido.split(" ")[0].equals("Hola")) {
                    System.out.print("asdlll");
                    mensajeEnviado = superJoin(titanes);
                    data = mensajeEnviado.getBytes();
                    System.out.print("asd");
                    paqueteEnviado = new DatagramPacket(data, data.length, paqueteRecibido.getAddress(), paqueteRecibido.getPort());
                    System.out.print("asdqwe");
                    socketUDP.send(paqueteEnviado);
                    continue;
                }
                else {
                    System.out.print("asdjhjhjhj");
                    //Mensaje: Accion + Id del Titan
                    String accion = mensajeRecibido.split(" ")[0];
                    int Id = Integer.parseInt(mensajeRecibido.split(" ")[1]);
                    //Accion de captura
                    if (accion.equals("Capturar")){
                        Titan capturado = captura(Id);
                        if(capturado!=null){
                            mensajeEnviado = capturado.getId()+" capturado "+
                                    socketUDP.getRemoteSocketAddress().toString()+" ";
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
                }
                //Aca si es eliminar, borrar de la lista de titanes(supongo que List es referenciada)
                //luego enviar por multicast info de quien lo mato/captura
                //mensajeEnviado = "tuma";
                data = mensajeEnviado.getBytes();
                paqueteEnviado = new DatagramPacket(data, data.length, InetAddress.getByName(IPMult), puertoMult);
                socketUDP.send(paqueteEnviado);
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
