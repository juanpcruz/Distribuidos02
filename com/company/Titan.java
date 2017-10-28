package com.company;

import java.util.Objects;

public class Titan {
    private static int IDcounter=100;
    private int id;
    private String nombre;
    private String tipo;
    private String ultimoDistrito;
    public Titan(String name, String type, Distrito distrito){
        id= IDcounter;
        IDcounter++;
        nombre = name;
        tipo = type;
        ultimoDistrito = distrito.getNombre();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUltimoDistrito() {
        return ultimoDistrito;
    }

    public boolean tipoNormal(){
        if (Objects.equals(tipo,"normal")){
            return true;
        }
        return false;
    }

    public boolean tipoExcentrico(){
        if (Objects.equals(tipo,"excéntrico")){
            return true;
        }
        return false;
    }

    public boolean tipoCambiante(){
        if (Objects.equals(tipo,"cambiante")){
            return true;
        }
        return false;
    }

    public void setUltimoDistrito(Distrito ultimoDistrito) {
        this.ultimoDistrito = ultimoDistrito.getNombre();
    }

    //El codigo de abajo era para probar que el ID fuese estatico

    //public static void main(String[] args) {
    //    Titan eren = new Titan("Eren","tuma");
    //    Titan mata = new Titan("MATA","enfermito del curso");
    //    System.out.println("Titan: "+eren.getNombre()+", ID: "+eren.getId());
    //    System.out.println("Titan: "+mata.getNombre()+", ID: "+mata.getId());
    //}
}
