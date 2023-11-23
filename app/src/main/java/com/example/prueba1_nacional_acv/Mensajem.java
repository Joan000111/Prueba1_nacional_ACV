package com.example.prueba1_nacional_acv;

public class Mensajem {
    private static String idmensaje;
    private String contenido;

    public Mensajem()
    {
        this.idmensaje="";
        this.contenido="";
    }

    public Mensajem( String idmensaje, String contenido)
    {
        this.idmensaje=idmensaje;
        this.contenido=contenido;
    }

    public static String getIdMensaje() { return idmensaje;
    }

    public void setIdMensaje(String idmensaje) {
        this.idmensaje = idmensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Contenido = " + contenido;
        /*
        return "Mensaje{" +
                "idmensaje='" + idmensaje + '\'' +
                ", contenido='" + contenido + '\'' +
                '}';*/
    }

}
