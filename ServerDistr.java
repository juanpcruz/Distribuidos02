package com.company;

import java.util.List;

public class ServerDistr {
    private Distrito distrito;
    private List<Titan> titanes;
    private List<Titan> capturados;
    private List<Titan> asesinados;

    public void captura(int ID) {
        for (Titan i : titanes) {
            if (i.getId() == ID && (i.tipoNormal() || i.tipoCambiante())) {
                i.setUltimoDistrito(distrito);
                capturados.add(i);
                titanes.remove(i);
            }
        }
    }

    public void muerte(int ID) {
        for (Titan i : titanes) {
            if (i.getId() == ID && (i.tipoNormal() || i.tipoExcentrico())) {
                i.setUltimoDistrito(distrito);
                asesinados.add(i);
                titanes.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        //codigo
        //Hay que poner la llamada a cada uno de los metodos en base al mensaje que se reciba
    }
}