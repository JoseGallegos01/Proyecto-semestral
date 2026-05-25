package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Bus {
    //Vicente Salinas

    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private List <Viaje> viajes;
    private Empresa empresa;

    public Bus(String patente, int nroAsientos, Empresa empresa) {
        this.patente = patente;
        this.nroAsientos = nroAsientos;
        this.viajes = new ArrayList<>();
    }
    public String getPatente() {
        return patente;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public int getNroAsientos() {
        return nroAsientos;
    }
    public void addViaje(Viaje viaje){
        viajes.add(viaje);
    }
    public Empresa getEmpresa(){
        return empresa;
    }
    public void setEmpresa(Empresa empresa){
        this.empresa=empresa;
    }
}
