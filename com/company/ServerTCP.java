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
            distritos =new ArrayList<ArrayList>();
            conexiones = new ArrayList<ServerCentralThread>();
            ServerCentralDistritos threadDistritos = new ServerCentralDistritos(distritos);
            threadDistritos.start();

            while (true) {
                server = new ServerSocket(6000);
                socketBienvenida = server.accept();
                //ServerCentralThread thread1 = new ServerCentralThread(socketBienvenida,distritos);
                //thread1.start();
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