import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();
    ArrayList<Viaje> viajes = new ArrayList<>();
    ArrayList<Pasaje> pasajes = new ArrayList<>();
    ArrayList<Nombre> nombres = new ArrayList<>();
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
        if (findBus(patente) != null) {
            buses.add(new Bus(patente, NroAsientos));
            findBus(patente).setMarca(marca);
            findBus(patente).setModelo(modelo);
            return true;
        }
        return false;
    }
    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patenteBus){
        if (findViaje(fecha.toString(), hora.toString(), patenteBus) == null){
            viajes.add((new Viaje(fecha, hora, precio, findBus(patenteBus))));
            return true;
        }
        return false;
    }
    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){
        if (findCliente(idCliente) == null) return false;
        if (findVenta(idDoc, tipo) == null){
            ventas.add(new Venta(idDoc, tipo, fechaVenta));
            return true;
        }
        return false;
    }
    public String[][] getHorariosDisponibles(LocalDate fechaViaje){
        int cantidadHorariosDisponibles = 0;
        for (Viaje viaje : viajes){
            if (viaje.getFecha().isEqual(fechaViaje)){
                cantidadHorariosDisponibles++;
            }
        }
        String[][] horarios = new String[cantidadHorariosDisponibles][4];
        int i = 0;
        for (Viaje viaje : viajes){
            if (viaje.getFecha().isEqual(fechaViaje)){

            }
        }
        return horarios;
    }

    public String[][] listAsientosDelViaje(LocalDate fecha, LocalTime hora, String patenteBus){
        //tengo que implementar la manera de marcar los ocupados
        if (findViaje(fecha.toString(), hora.toString(), patenteBus) != null){
            int cantidadasientos = findViaje(fecha.toString(), hora.toString(), patenteBus).getBus().getNroAsientos();
            int contador = 0;
            String[][] asientos = new String[cantidadasientos][4];
            for (int i = 0; i < cantidadasientos; i++){
                if (contador<=cantidadasientos) asientos[i][0] = String.valueOf(contador++);
                else asientos[i][0] = "";
                if (contador<=cantidadasientos) asientos[i][1] = String.valueOf(contador++);
                else asientos[i][1] = "";
                if (contador<=cantidadasientos) asientos[i][3] = String.valueOf(contador++);
                else asientos[i][3] = "";
                if (contador<=cantidadasientos) asientos[i][2] = String.valueOf(contador++);
                else asientos[i][2] = "";
            }
            return asientos;
        }
        return new String[0][0];
    }

    public int getMontoVenta(String idDocumento, TipoDocumento tipoDocumento){
        int monto = 0;
        for (Pasaje pasaje : pasajes){

        }
    }

    public String getNombrePasajero(IdPersona idPasajero){
        if (findPasajero(idPasajero)!=null){

        }
        return null;
    }

    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patenteBus, int asiento, IdPersona idPasajero){

    }

    //falta la relacion de clase de venta
    public String[][] listVentas(){

    }

    public String[][] listViajes(){
        int contador = 0;
        String[][] listaViajes = new String[viajes.size()][5];
        for (Viaje v : viajes){
            listaViajes[contador][0] = v.getFecha().toString();
            listaViajes[contador][1] = v.getHora().toString();
            listaViajes[contador][2] = "";
            listaViajes[contador][3] = v.getAsientos().toString();
            listaViajes[contador][4] = v.getBus().getPatente();
            contador++;
        }
        return listaViajes;
    }


    //tengo que esperar a que el metodo de la clase en viaje este listo para finalizarlo
    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patenteBus){
        if (findViaje(fecha.toString(), hora.toString(), patenteBus) != null) {
            findViaje(fecha.toString(), hora.toString(), patenteBus).getListaPasajeros();
        }
        String[][] pasajeros = new String[0][0];
        return pasajeros;
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
            if (v.getHora().toString().equals(hora) && v.getFecha().toString().equals(fecha) && v.getBus().toString().equals(patenteBus)){
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
