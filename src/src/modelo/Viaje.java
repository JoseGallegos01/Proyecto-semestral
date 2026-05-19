package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.LocalDateTime;



public class Viaje {
    //Vicente Salinas

    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private Bus bus;
    private ArrayList<Pasaje>pasajes;
    private int duracion;
    private Auxiliar auxiliar;
    private ArrayList<Conductor>conductores;
    private Terminal terminalSalida;
    private Terminal terminalLlegada;


    public Viaje(LocalDate fecha, LocalTime hora, int precio,int duracion, Bus bus,Auxiliar auxiliar,ArrayList<Conductor>conductores,Terminal terminalSalida,Terminal terminalLlegada){
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.duracion=duracion;
        this.bus = bus;
        this.auxiliar=auxiliar;
        this.conductores=conductores;
        this.terminalSalida=terminalSalida;
        this.terminalLlegada=terminalLlegada;
        this.pasajes=new ArrayList<>();
        bus.addViaje(this);
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public LocalTime getHora() {
        return hora;
    }
    public int getPrecio() {
        return precio;
    }

    public void setPrecio(){
        this.precio = precio;
    }
    public Bus getBus(){
        return bus;
    }
    public void setBus(Bus bus){
        this.bus = bus;
    }
    public int getDuracion(){return duracion;}

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }
    public ArrayList<Conductor>getConductores(){return conductores ;}
    public Terminal getTerminalSalida(){
        return terminalSalida;
    }
    public Terminal getTerminalLlegada(){
        return terminalLlegada;
    }
    public LocalDateTime getFechaHoraTermino(){
        return fecha.atTime(hora).plusMinutes(duracion);
    }

    public void addPasaje(Pasaje pasaje){
        pasajes.add(pasaje);
    }
    public void addConductor(Conductor conductor){
        conductores.add(conductor);

    }
    public Tripulante[]getTripulantes(){
        Tripulante []tripulantes=new Tripulante[conductores.size()+1];
        tripulantes[0]=auxiliar;
        for (int i =0; i<conductores.size();i++){
            tripulantes[i+1]=conductores.get(i);

        }
        return tripulantes;
    }
    public String [][]getAsientos(){
    int total =bus.getNroAsientos();
    String [][] asientos = new String [total][2];

    for (int i =0; i<total; i++){
        asientos[i][0]=(i+1)+"";
        asientos[i][1]="Libre";
    }
    for (Pasaje p:pasajes){
        int asiento =p.getAsiento();
        asientos[asiento-1][1]="Ocupado";
    }
    return asientos;

    }
    public String [][]getListaPasajeros() {
        String[][] lista = new String[pasajes.size()][4];

        for (int i = 0; i < pasajes.size(); i++) {
            Pasajero p = pasajes.get(i).getPasajero();
            lista[i][0] = p.getIdPersona().toString();
            lista[i][1] = p.getNombreCompleto().toString();
            lista[i][2] = String.valueOf(p.getNomContacto());
            lista[i][3] = p.getFonoContacto();
        }
        return lista;
    }
    public boolean existeDisponibilidad(int nroAsientos){
    return getnroAsientosDisponibles()>=nroAsientos;
    }
    public int getnroAsientosDisponibles(){
        return bus.getNroAsientos()-pasajes.size();
    }
    public Venta[]getVentas(){
        ArrayList<Venta>ventasUnicas=new ArrayList<>();
        for (Pasaje p : pasajes){
            Venta venta =p.getVenta();
            if(!ventasUnicas.contains(venta)){
                ventasUnicas.add(venta);
            }
        }
        return ventasUnicas.toArray(new Venta[0]);
    }
}






