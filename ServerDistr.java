package com.company;

import java.util.List;

public class ServerDistr {
    private Distrito distrito;
    private List<Titan> titanes;

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

    public static void main(String[] args) {
        //codigo
        //Hay que poner la llamada a cada uno de los metodos en base al mensaje que se reciba
    }
}