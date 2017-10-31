package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteTCP {
    //static ArrayList<Titan> titanesDistrito;
    public static void main(String argv[]) throws Exception{
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream in;
        DataOutputStream out;
        Socket socketTCP;
        DatagramSocket socketUDP;
        DatagramPacket paqueteRecibido;
        DatagramPacket paqueteEnviado;
        byte[] data ;
        String ip;
        String puerto;
        String mensaje = "";
        Boolean cambioDistrito;
        ClienteTCPthread recepcionMulticast;
        String[] informacionDistrito;
        String titan;

        ArrayList<Titan> titanesDistrito = new ArrayList<>(); //lista "sincronizada" con la lista de titanes del distrito
        ArrayList<Titan> titanesDistritoAux = new ArrayList<>();
        List<Titan> capturados = new ArrayList<>();
        List<Titan> asesinados = new ArrayList<>();

        try {
            //Primero establece una conexion tcp con el servidor central, de donde obtendra la informacion del distrito escogido.
            //Luego creara un thread dedicado a la recepcion de datagramas multicast relacionados al distrito.
            //Este thread (luego de la creacion del otro) enviara un datagrama de "saludo" al distrito, el que respondera con la lista
            //actual de titanes.
            //Este thread queda a cargo de leer acciones del usuario y enviar datagramas/flujoTCP relacionado a esta(sincronicas con
            // ServerDistrThread y ServerCentralThread respectivamente).
            //(Si se solicita capturar/asesinar titan, la respuesta se obtendra de un multicast)

            //informacion de servidor central
            System.out.print("Ingrese ip de servidor: \n>");
            ip = buffer.readLine();
            System.out.print("Ingrese puerto de servidor: \n>");
            puerto = buffer.readLine();
            while (true) {
                socketTCP = new Socket(ip, Integer.parseInt(puerto));
                out = new DataOutputStream(socketTCP.getOutputStream());
                in = new DataInputStream(socketTCP.getInputStream());
                //recibir y mostrar mensaje
                mensaje = in.readUTF();
                System.out.println(mensaje);
                //ingresar servidor deseado y enviar
                System.out.print(">");
                mensaje = buffer.readLine();
                out.writeUTF(mensaje);
                //recibir y mostrar respuesta
                mensaje = in.readUTF();
                System.out.println(mensaje);
                //si es positiva, entra en envio de mensajes
                if (mensaje.equals("Conexion aceptada")) {
                    //datos del distrito
                    mensaje = in.readUTF();
                    //creacion de thread encargado de recibir los datagramas en multicast
                    recepcionMulticast = new ClienteTCPthread(mensaje, titanesDistrito, capturados, asesinados);
                    recepcionMulticast.start();
                    //envio del mensaje de saludo
                    socketUDP = new DatagramSocket(4200);
                    data = new byte[256];
                    paqueteRecibido = new DatagramPacket(data, 256);
                    informacionDistrito = mensaje.split("/");
                    data = "Hola ".getBytes();
                    paqueteEnviado = new DatagramPacket(data, data.length, InetAddress.getByName(informacionDistrito[3]), Integer.parseInt(informacionDistrito[4]));
                    socketUDP.send(paqueteEnviado);
                    //recepcion de la lista de titanes
                    socketUDP.receive(paqueteRecibido);
                    String mensajeRecibido = new String(paqueteRecibido.getData());
                    //titanesDistrito = superUnpack(mensajeRecibido);
                    titanesDistritoAux = recepcionMulticast.superUnpack(mensajeRecibido);
                    for (Titan i : titanesDistritoAux) {
                        titanesDistrito.add(i);
                    }

                    cambioDistrito = false;
                    System.out.println("Lista de acciones:");
                    System.out.println("1) Listar titanes\n2) Cambiar de distrito\n3) Capturar titan\n4) " +
                            "Asesinar titan\n5) Listar titanes capturados\n6) Listar titanes asesinados");
                    System.out.println("Ingrese el número de la acción.");

                    while (!cambioDistrito) {
                        System.out.print(">");
                        mensaje = buffer.readLine();
                        switch (mensaje) {
                            case "1":
                                for (Titan i : titanesDistrito) {
                                    System.out.println("Titan: " + i.getNombre() + ", Tipo: " + i.getTipo() + " ID: " + i.getId());
                                }
                                break;
                            case "2":
                                //cambiar, dejar en un while true desde conectar al servidor central
                                //para que reenvie los servidores (como si se reconectara)
                                cambioDistrito = true;
                                break;
                            case "3":
                                System.out.print("Ingrese ID de titan a capturar:\n>");
                                titan = buffer.readLine();
                                mensaje = "Capturar " + titan + " ";
                                data = mensaje.getBytes();
                                paqueteEnviado = new DatagramPacket(data, data.length, InetAddress.getByName(informacionDistrito[3]),
                                        Integer.parseInt(informacionDistrito[4]));
                                socketUDP.send(paqueteEnviado);
                                break;
                            case "4":
                                System.out.print("Ingrese ID de titan a asesinar:\n>");
                                titan = buffer.readLine();
                                mensaje = "Asesinar " + titan + " ";
                                data = mensaje.getBytes();
                                paqueteEnviado = new DatagramPacket(data, data.length, InetAddress.getByName(informacionDistrito[3]),
                                        Integer.parseInt(informacionDistrito[4]));
                                socketUDP.send(paqueteEnviado);
                                break;
                            case "5":
                                for (Titan i : capturados) {
                                    System.out.println("Titan: " + i.getNombre() + ", Tipo: " + i.getTipo());
                                }
                                break;
                            case "6":
                                for (Titan i : asesinados) {
                                    System.out.println("Titan: " + i.getNombre() + ", Tipo: " + i.getTipo());
                                }
                                break;
                        }
                    }
                    socketUDP.close();
                    recepcionMulticast.cerrar();
                }
                socketTCP.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static ArrayList<Titan> superUnpack(String mensaje){
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