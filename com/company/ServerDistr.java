package com.company;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerDistr {

    public static void main(String argv[]) throws Exception {
        //ArrayList<Titan> titanes = new ArrayList(); //---> me tiraba problemas al pasarlo a ServerDistrThread por algo de static
                                                       // por eso lo cree denuevo aca dentro, luego no supe inicializarlo
                                                    // por eso puse ArrayList para que no tirara errores
        List<Titan> titanes = new ArrayList();
        List<Titan> capturados = new ArrayList<>();
        List<Titan> asesinados = new ArrayList<>();
        String distrito;
        String nombreTitan;
        String tipoTitan;
        Titan nuevoTitan;
        ServerDistrThread recepcionPetiticones;
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket socketUDP = new DatagramSocket(5000);
        InetAddress IPAddress;
        String ipCentral;
        byte[] data = new byte[256];
        byte[] bitsRecibido = new byte[256];
        DatagramPacket paqueteRecibido = new DatagramPacket(bitsRecibido, 256);
        DatagramPacket paqueteEnviado;
        int puertoMulticast;
        int puerto;
        String ip;
        String IPMulticast;
        MulticastSocket multicastSocket;

        //En un principio el distrito se registra con el servidor central(1 datagrama enviado y 1 recibido)
        //luego se crea un thread encargado de la recepcion de datagramas de peticiones de los clientes y su respuesta correspondiente,
        //tanto en unicast como multicast.
        //Este thread queda a cargo de la creacion de titanes y el envio de alerta multicast sobre este evento.

        //ingreso de informacion del distrito/servidor
        String mensaje = "";
        System.out.print("IP del servidor Central:\n");
        ipCentral = buffer.readLine();
        IPAddress = InetAddress.getByName(ipCentral);
        System.out.print("Nombre de distrito a registrar:\n");
        mensaje = mensaje + buffer.readLine();
        distrito = mensaje;
        System.out.print("IP Multicast:\n");
        IPMulticast = buffer.readLine();
        mensaje = mensaje + "/" + IPMulticast;
        System.out.print("Puerto Multicast:\n");
        puertoMulticast = Integer.parseInt(buffer.readLine());
        mensaje = mensaje + "/" + puertoMulticast;
        System.out.print("IP peticiones:\n");
        ip = buffer.readLine();
        mensaje = mensaje + "/" +ip;
        System.out.print("Puerto peticiones:\n");
        puerto = Integer.parseInt(buffer.readLine());
        mensaje = mensaje + "/" + puerto+"/";
        //envio de la informacion al servidor central para su registro
        data = mensaje.getBytes();
        paqueteEnviado = new DatagramPacket(data, data.length, IPAddress, 5050);
        socketUDP.send(paqueteEnviado);
        //recibir si se registro
        socketUDP.receive(paqueteRecibido);
        String mensajeRecibido = new String(paqueteRecibido.getData());
        System.out.print(mensajeRecibido);
        socketUDP.close();
        //multicast
        socketUDP = new DatagramSocket(puerto);
        multicastSocket = new MulticastSocket();
        //creacion del thread encargado de peticiones
        recepcionPetiticones = new ServerDistrThread(socketUDP, titanes, capturados, asesinados,
                puertoMulticast, IPMulticast, distrito);
        recepcionPetiticones.start();
        while(true) {
            System.out.println("Agregar titan? (agregar)");
            //aqui colocar leer si se agrega un titan, para luego enviarlo por multicast
            if(buffer.readLine().equals("agregar")) {
                System.out.println("Nombre del titan: ");
                nombreTitan = buffer.readLine();
                System.out.println("Tipo del titan: ");
                System.out.println("1) Normal ");
                System.out.println("2) Excentrico ");
                System.out.println("3) Cambiante ");
                System.out.print(">");
                tipoTitan = buffer.readLine();
                if(tipoTitan.equals("1")){
                    tipoTitan = "Normal";
                }else if(tipoTitan.equals("2")){
                    tipoTitan = "Excentrico";
                }else if(tipoTitan.equals("3")){
                    tipoTitan = "Cambiante";
                }
                nuevoTitan = new Titan(nombreTitan, tipoTitan, distrito);
                titanes.add( nuevoTitan );
                mensaje = nuevoTitan.getId()+" "+ "aparece" +" "+nuevoTitan.getNombre()+" "+nuevoTitan.getTipo()+" "+nuevoTitan.getUltimoDistrito()+" ";
                data = mensaje.getBytes();
                paqueteEnviado = new DatagramPacket(data, data.length, InetAddress.getByName(IPMulticast), puertoMulticast);
                multicastSocket.send(paqueteEnviado);
            }
        }
    }
}








