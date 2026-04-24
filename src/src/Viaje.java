import java.time.LocalDate;
import java.time.LocalTime;

public class Viaje {
    //Vicente Salinas
    //faltan terminar todos los metodos que dependian de la clase pasaje

    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private Bus bus;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus){
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
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

    //AVANZAR UNA VEZ LA CLASE PASAJE ESTE LISTA

    //public String[][] getAsientos() {}
        //no se puede avanzar sin la clase Pasaje
    //public String[][] getListaPasajeros() {}
        //no se puede avanzar sin la clase Pasaje
    public boolean existeDisponibilidad() {
        //no se puede avanzar sin la clase Pasaje
        return false;
    }
    public int getNroAsientosDisponibles() {
        //no se puede avanzar sin la clase Pasaje
        return 0;
    }
}
