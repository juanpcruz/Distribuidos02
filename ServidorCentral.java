package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

class socketThread extends Thread {
    private Socket socketCliente;
    socketThread(Socket sock) {
        socketCliente=sock;
    }
    public void run() {
        try {
            BufferedReader mensajeRecibido = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            DataOutputStream respuesta = new DataOutputStream(socketCliente.getOutputStream());
            String request = mensajeRecibido.readLine();
            String IPCliente = socketCliente.getLocalAddress().toString();
            System.out.println("Dar autorizacion a " + IPCliente + " ? (y/n)");
            BufferedReader inputConsola = new BufferedReader(new InputStreamReader(System.in));
            String autorizacion = inputConsola.readLine();
            if(Objects.equals(autorizacion,"y")){
                //COSAS HERMOSAS
            }
            else socketCliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServidorCentral {

    public static void main(String args[]) throws Exception {
        int firsttime = 1;
        while (true) {
            ServerSocket socketDeBienvenida = new ServerSocket(4000);
            System.out.println("running... on port: 4000");
            socketThread thread = new socketThread(socketDeBienvenida.accept());
            thread.start();
            socketDeBienvenida.close();
        }
    }
}