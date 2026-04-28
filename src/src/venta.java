import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
    ArrayList<Pasaje> pasajes;
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha) {
        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.pasajes = new ArrayList<>();


    }
    public String getIdDocumento() {
        return idDocumento;
    }
    public TipoDocumento getTipo() {
        return tipo;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public Cliente getCliente(){
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero){
        Pasaje pasaje = new Pasaje(asiento, this, viaje, pasajero);
        pasajes.add(pasaje);
        viaje.addPasaje(pasaje);
    }
    public Pasaje[] getPasajes(){
        return pasajes.toArray(new Pasaje[0]);
    }
    public int getMonto(){
        int monto = 0;
        for (Pasaje p : pasajes){
            monto += p.getViaje().getPrecio();
        }
        return monto;
    }
}