package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) throws IOException {
	// write your code here
        BufferedReader inputConsola = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("[Cliente] Ingresar IP Servidor Central:");
        System.out.print(">");
        String Ip = inputConsola.readLine();
        System.out.println("[Cliente] Ingresar Puerto Servidor Central:");
        System.out.print(">");
        int Puerto = Integer.parseInt(inputConsola.readLine());

        Socket clientSocket = new Socket("localhost",Puerto);

        System.out.println("[Cliente] Introducir Distrito:");
        System.out.print(">");
        String Distrito = inputConsola.readLine();

        DataOutputStream mensaje = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader respuestaRecibida = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        mensaje.writeBytes(Distrito);
        clientSocket.close();

    }
}
