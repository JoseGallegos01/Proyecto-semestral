import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();
    ArrayList<Viaje> viajes = new ArrayList<>();
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
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
            findPasajero(id).setFonoContacto(fonoContacto);
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
    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patenteBus){
        if (findBus(patenteBus) != null) {
            if (findViaje(fecha.toString(), hora.toString(), patenteBus) == null) {
                viajes.add(new Viaje(fecha, hora, precio, findBus(patenteBus)));
                return true;
            }
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
    //metodo incompleto
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
                horarios[i][0] = viaje.getBus().getPatente();
                horarios[i][1] = viaje.getHora().toString();
                horarios[i][2] = String.valueOf(viaje.getPrecio());
                horarios[i][3] = String.valueOf(viaje.getnroAsientosDisponibles());
                i++;
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
        if (findVenta(idDocumento, tipoDocumento)==null) return 0;
        return findVenta(idDocumento, tipoDocumento).getMonto();
    }

    public String getNombrePasajero(IdPersona idPasajero){
        if (findPasajero(idPasajero) == null) return null;
        return findPasajero(idPasajero).getNombreCompleto().toString();
    }

    //metodo incompleto
    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patenteBus, int asiento, IdPersona idPasajero, TipoDocumento tipo) {
        Viaje viajeVenta = findViaje(fecha.toString(), hora.toString(), patenteBus);
        Venta ventaViaje = findVenta(idDoc, tipo);
        Pasajero pasajeroVenta = findPasajero(idPasajero);
        if (viajeVenta == null) return false;
        if (findPasajero(idPasajero) == null) return false;
        if (viajeVenta.getnroAsientosDisponibles() == 0) return false;
        ventaViaje.createPasaje(asiento, viajeVenta, pasajeroVenta);
        return true;
    }

    public String[][] listVentas(){
        if (ventas.isEmpty()) return  new String[0][0];
        String[][] listaVentas = new String[ventas.size()][7];
        for (int i = 0; i < listaVentas.length; i++){
            listaVentas[i][0] = ventas.get(i).getIdDocumento();
            listaVentas[i][1] = ventas.get(i).getTipo().name();
            listaVentas[i][2] = ventas.get(i).getFecha().toString();
            listaVentas[i][3] = ventas.get(i).getCliente().getIdPersona().toString();
            listaVentas[i][4] = ventas.get(i).getCliente().getNombreCompleto().toString();
            listaVentas[i][5] = String.valueOf(ventas.get(i).getPasajes().length);
            listaVentas[i][6] = "$" + ventas.get(i).getMonto();
        }
        return listaVentas;
    }

    public String[][] listViajes(){
        int contador = 0;
        String[][] listaViajes = new String[viajes.size()][5];
        for (Viaje v : viajes){
            listaViajes[contador][0] = v.getFecha().toString();
            listaViajes[contador][1] = v.getHora().toString();
            listaViajes[contador][2] = String.valueOf(v.getPrecio());
            listaViajes[contador][3] = String.valueOf(v.getnroAsientosDisponibles());
            listaViajes[contador][4] = v.getBus().getPatente();
            contador++;
        }
        return listaViajes;
    }


    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patenteBus){
        Viaje viajeListarPasajeros =  findViaje(fecha.toString(), hora.toString(), patenteBus);
        if (viajeListarPasajeros == null) return new String[0][0];
        else return viajeListarPasajeros.getListaPasajeros();
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
            if (v.getFecha().toString().equals(fecha)
                    && v.getHora().toString().equals(hora)
                    && v.getBus().getPatente().equals(patenteBus)){
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
