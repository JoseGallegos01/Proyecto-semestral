import java.util.ArrayList;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();
    ArrayList<Viaje> viajes = new ArrayList<>();
    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){
        if (findCliente(id) == null) {
            clientes.add(new Cliente(id, nom, email));
            findCliente(id).setTelefono(fono);
            return true;
        }
        return false;
    }
    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nombreContacto,
                                  String fonoContacto){
        if (findPasajero(id) == null) {
            pasajeros.add(new Pasajero(id, nom, nombreContacto, fono));
            findPasajero(id).setTelefono(fono);
            return true;
        }
        return false;
    }
    public boolean createBus(String patente, String marca, String modelo, int NroAsientos){
        if (findBus(patente) == null) {
            buses.add(new Bus(patente, NroAsientos));
            findBus(patente).setMarca(marca);
            findBus(patente).setModelo(modelo);
            return true;
        }
        return false;
    }


    private Cliente findCliente(IdPersona id){
        for (Cliente c : clientes){
            if (c.getIdPersona().equals(id)){
                return c;
            }
        }
        return null;
    }

    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento){
        for (Venta v : ventas){
            if (v.getIdDocumento().equals(idDocumento) && v.getTipo().equals(tipoDocumento)){
                return v;
            }
        }
        return null;
    }
    private Bus findBus(String patente){
        for (Bus b : buses){
            if (b.getPatente().equals(patente)){
                return b;
            }
        }
        return null;
    }
    private Viaje findViaje(String fecha, String hora, String patenteBus){
        for (Viaje v : viajes){
            if (v.getHora().equals(hora) && v.getFecha().equals(fecha) && v.getBus().equals(patenteBus)){
                return v;
            }
        }
        return null;
    }
    private Pasajero findPasajero(IdPersona idPersona){
        for (Pasajero p : pasajeros){
            if (p.getIdPersona().equals(idPersona)){
                return p;
            }
        }
        return null;
    }
}
