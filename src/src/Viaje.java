import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;



public class Viaje {
    //Vicente Salinas

    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private Bus bus;
    private ArrayList<Pasaje>pasajes;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus){
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
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
    public void addPasaje(Pasaje pasaje){
        pasajes.add(pasaje);
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
    public boolean existeDisponibilidad(){
    return getnroAsientosDisponibles()>0;
    }
    public int getnroAsientosDisponibles(){
        return bus.getNroAsientos()-pasajes.size();
    }
}






