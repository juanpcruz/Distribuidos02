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
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] data = new byte[256];
        //ingreso de informacion del distrito/servidor
        String mensaje = "";
        System.out.print("Nombre de distrito a registrar:\n");
        mensaje = mensaje + buffer.readLine();
        System.out.print("IP de distrito a registrar:\n");
        mensaje = mensaje + "/" + buffer.readLine();
        System.out.print("Puerto de distrito a registrar:\n");
        mensaje = mensaje + "/" + buffer.readLine();
        System.out.print("Puerto de multicast de distrito a registrar:\n");
        mensaje = mensaje + "/" + buffer.readLine();
        //envio de la informacion al servidor central para su registro
        data = mensaje.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 5050);
        clientSocket.send(sendPacket);
        clientSocket.close();
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








