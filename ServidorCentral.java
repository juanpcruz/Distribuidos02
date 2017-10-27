package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

class ServidorCentral {
    public static void main(String args[]) throws Exception {
        int firsttime = 1;
        while (true) {
            ServerSocket socketDeBienvenida = new ServerSocket(4000);
            System.out.println("running... on port: 4000");
            Socket socketCliente = socketDeBienvenida.accept();
            BufferedReader mensajeRecibido = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            DataOutputStream respuesta = new DataOutputStream(socketCliente.getOutputStream());
            String request = mensajeRecibido.readLine();
            String IPCliente = socketCliente.getLocalAddress().toString();
            System.out.println("Dar autorizacion a "+ IPCliente +" ? (y/n)");
            
            socketDeBienvenida.close();
            System.out.println("connection terminated");
        }
    }
}