import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha) {
        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
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
    //Terminare la relacion de clases dsps
    public void createPasaje(){

    }
}
