package com.company;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerDistr {
    private Distrito distrito;
    private List<Titan> titanes;
    public static void main(String argv[]) throws Exception {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket(5000);
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] data = new byte[256];
        byte[] bitsRecibido = new byte[256];
        DatagramPacket paqueteRecibido = new DatagramPacket(bitsRecibido, 256);
        int puertoMulticast;
        int puerto;
        String IPMulticast;
        //ingreso de informacion del distrito/servidor
        String mensaje = "";
        System.out.print("Nombre de distrito a registrar:\n");
        mensaje = mensaje + buffer.readLine();
        System.out.print("IP Multicast:\n");
        IPMulticast = buffer.readLine();
        mensaje = mensaje + "/" + IPMulticast;
        System.out.print("Puerto Multicast:\n");
        puertoMulticast = Integer.parseInt(buffer.readLine());
        mensaje = mensaje + "/" + puertoMulticast;
        System.out.print("IP peticiones:\n");
        mensaje = mensaje + "/" + buffer.readLine();
        System.out.print("Puerto peticiones:\n");
        puerto = Integer.parseInt(buffer.readLine());
        mensaje = mensaje + "/" + puerto;
        //envio de la informacion al servidor central para su registro
        data = mensaje.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 5050);
        clientSocket.send(sendPacket);
        //recibir si se registro
        clientSocket.receive(paqueteRecibido);
        String mensajeRecibido = new String(paqueteRecibido.getData());
        System.out.print(mensajeRecibido);
        clientSocket.close();
        //envio mensajes multicast
        mensaje ="multicast";
        clientSocket = new DatagramSocket();
        data = mensaje.getBytes();
        while(true) {
            sendPacket = new DatagramPacket(data, data.length, InetAddress.getByName(IPMulticast), puertoMulticast);
            clientSocket.send(sendPacket);
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
}








