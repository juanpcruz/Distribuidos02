package com.company;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerTCP {

    public static void main(String argv[]) throws Exception{
        ServerSocket server;
        Socket socketBienvenida;
        ArrayList<ArrayList> distritos;
        ArrayList<ServerCentralThread> conexiones;
        try {
            //arreglo con informacion de servidores-distrito---> cada elemento es un arreglo con la info
            distritos =new ArrayList<ArrayList>();
            //arreglo con cada thread encargado de conexion tcp de servidor central y cliente
            conexiones = new ArrayList<ServerCentralThread>();
            //ejecucion de thread nuevo para encargarse de registrar distritos
            ServerCentralDistritos threadDistritos = new ServerCentralDistritos(distritos);
            threadDistritos.start();

            while (true) {
                server = new ServerSocket(6000);
                socketBienvenida = server.accept();
                //ejecutar y agregar thread a la lista de estos
                conexiones.add(0,new ServerCentralThread(socketBienvenida,distritos));
                conexiones.get(0).start();
                server.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}