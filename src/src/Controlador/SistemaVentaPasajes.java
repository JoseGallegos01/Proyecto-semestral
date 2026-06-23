package Controlador;

import Excepciones.SVPException;
import Modelo.*;
import Persistencia.PersistenciaClase;
import Vista.UISVP;
import utilidades.*;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentaPasajes implements Serializable {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();
    ArrayList<Viaje> viajes = new ArrayList<>();
    static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    ControladorEmpresas ce = ControladorEmpresas.getInstance();

    public static SistemaVentaPasajes instance = null;

    public static SistemaVentaPasajes getInstance() {
        if (instance == null) {
            instance = new SistemaVentaPasajes();
        }
        return instance;
    }

    public void createCliente(IdPersona id, Nombre nom, String fono, String email) {
        if (findCliente(id).isPresent()) throw new SVPException("Ya existe cliente con el id indicado");
        if (findCliente(id).isEmpty()) {
            clientes.add(new Cliente(id, nom, email));
            findCliente(id).get().setTelefono(fono);
        }
    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nombreContacto,
                               String fonoContacto) {
        if (findPasajero(id).isPresent())
            throw new SVPException("Ya existe pasajero con el id indicado");
        pasajeros.add(new Pasajero(id, nom, nombreContacto, fono));
        findPasajero(id).get().setFonoContacto(fonoContacto);

    }

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patenteBus, IdPersona[] tripulantes, String[] nomComunas) {
        if (findViaje(fecha, hora, patenteBus).isPresent()) throw new SVPException("Ya existe viaje con fecha, hora y patente de bus indicados");
        if (ce.findBus(patenteBus).isEmpty()) throw new SVPException("No existe bus con la patente indicada");
        if (ce.findTerminalPorComuna(nomComunas[0]).isEmpty()) throw new SVPException("No existe terminal de salida en la comuna indicada");
        if (ce.findTerminalPorComuna(nomComunas[1]).isEmpty()) throw new SVPException("No existe terminal de llegada en la comuna indicada");
        Terminal terminalSalida = ce.findTerminalPorComuna(nomComunas[0]).get();
        Terminal terminalLlegada = ce.findTerminalPorComuna(nomComunas[1]).get();
        Auxiliar auxiliarViaje = ce.findAuxiliar(tripulantes[0], ce.findBus(patenteBus).get().getEmpresa().getRut()).get();
        ArrayList<Conductor> conductores = new ArrayList<>();
        for (int i = 1; i < tripulantes.length; i++) {
            Optional<Conductor> c = ce.findConductor(tripulantes[i], ce.findBus(patenteBus).get().getEmpresa().getRut());
            conductores.add(c.get());
        }
        viajes.add(new Viaje(fecha, hora, precio, duracion, ce.findBus(patenteBus).get(), auxiliarViaje, conductores, terminalSalida, terminalLlegada));
    }

    public void iniciaVenta(String idDoc, TipoDocumento tipo,
                            LocalDate fechaVenta, IdPersona idCliente, LocalDate fechaViaje, String comunaLLegada, String comunaSalida, int nroPasajeros) {
        if (findVenta(idDoc, tipo).isPresent())
            throw new SVPException("Ya existe venta con el id y tipo de documento indicados");
        if (findCliente(idCliente).isEmpty())
            throw new SVPException("No existe cliente con el id indicado");
        if (viajes.stream().filter(viaje -> viaje.getFecha().isEqual(fechaViaje)).findFirst().isEmpty()) throw new SVPException("No existen viajes con la fecha indicada");
        if (ce.findTerminalPorComuna(comunaLLegada).isEmpty()) throw new SVPException("No existe terminal en la comuna para el destino");
        if (ce.findTerminalPorComuna(comunaSalida).isEmpty()) throw new SVPException("No existe terminal en la comuna de origen");
        if (viajes.stream().filter(viaje -> viaje.existeDisponibilidad(nroPasajeros)).findFirst().isEmpty()) throw new SVPException("No hay disponibilidad para la cantidad de pasajeros que se desea comprar pasaje");
        Optional<Cliente> clienteVenta = findCliente(idCliente);
        ventas.add(new Venta(idDoc, tipo, fechaVenta, clienteVenta.get()));
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje, String comunaSalida, String comunaLlegada, int nroPasajeros) {
        int cantidadHorariosDisponibles = 0;
        if (ce.findTerminalPorComuna(comunaLlegada).isEmpty()) throw new SVPException("No hay terminal en la comuna de destino indicada");
        if (ce.findTerminalPorComuna(comunaSalida).isEmpty()) throw new SVPException("No hay terminal en la comuna de origen indicada");
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().isEqual(fechaViaje) && viaje.existeDisponibilidad(nroPasajeros)) {
                cantidadHorariosDisponibles++;
            }
        }
        String[][] horarios = new String[cantidadHorariosDisponibles][4];
        int i = 0;
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().isEqual(fechaViaje) && viaje.existeDisponibilidad(nroPasajeros)) {
                horarios[i][0] = viaje.getBus().getPatente();
                horarios[i][1] = viaje.getHora().toString();
                horarios[i][2] = String.valueOf(viaje.getPrecio());
                horarios[i][3] = String.valueOf(viaje.getnroAsientosDisponibles());
                i++;
            }
        }
        return horarios;
    }

    public String[][] listAsientosDelViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        if (findViaje(fecha, hora, patenteBus).isPresent()) {
            int cantidadasientos = findViaje(fecha, hora, patenteBus).get().getBus().getNroAsientos();
            int contador = 1;
            int filas = (int) Math.ceil(cantidadasientos / 4.0);
            //saque eso de arriba de https://www.w3schools.com/java/ref_math_ceil.asp buscando como hacer lo de las filas
            String[][] asientos = new String[filas][4];
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < 4; j++) {

                    if (contador <= cantidadasientos) {
                        asientos[i][j] = String.valueOf(contador);
                    } else {
                        asientos[i][j] = "";
                    }

                    contador++;
                }
                }
            return asientos;
        }
        return new String[0][0];
    }

    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipoDocumento) {
        if (findVenta(idDocumento, tipoDocumento).isEmpty()) return Optional.empty();
        return Optional.of(findVenta(idDocumento, tipoDocumento).get().getMonto());
    }

    public String getNombrePasajero(IdPersona idPasajero) {
        if (findPasajero(idPasajero).isEmpty()) return "";
        return findPasajero(idPasajero).get().getNombreCompleto().toString();
    }

    public void vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patenteBus, int asiento, IdPersona idPasajero, TipoDocumento tipo) {
        Optional<Viaje> viajeVenta = findViaje(fecha, hora, patenteBus);
        Optional<Venta> ventaViaje = findVenta(idDoc, tipo);
        Optional<Pasajero> pasajeroVenta = findPasajero(idPasajero);
        if (viajeVenta.isEmpty())
            throw new SVPException("No existe viaje con la fecha, hora y patente de bus indicados");
        if (findPasajero(idPasajero).isEmpty())
            throw new SVPException("No existe pasajero con el id indicado");
        if (!viajeVenta.get().existeDisponibilidad(viajeVenta.get().getnroAsientosDisponibles())) throw new SVPException("No hay disponibilidad");
        ventaViaje.get().createPasaje(asiento, viajeVenta.get(), pasajeroVenta.get());
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo){
        Optional<Venta> venta = findVenta(idDoc, tipo);
        if(venta.isEmpty()){
            throw new SVPException("No existe venta con el id y tipo indicado");
        }
        if(!venta.get().pagaMonto()){
            throw new SVPException("El pago ya fue realizado");
        }
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo, long nroTarjeta){
        Optional<Venta> venta = findVenta(idDoc, tipo);
        if(venta.isEmpty()){
            throw new SVPException("No existe venta con el id y tipo indicado");
        }
        if(!venta.get().pagaMonto(nroTarjeta)){
            throw new SVPException("El pago ya fue realizado");
        }
    }

    public String[][] listVentas() {
        if (ventas.isEmpty()) return new String[0][0];
        String[][] listaVentas = new String[ventas.size()][7];
        for (int i = 0; i < listaVentas.length; i++) {
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

    public String[][] listViajes() {
        int contador = 0;
        String[][] listaViajes = new String[viajes.size()][5];
        for (Viaje listaViaje : viajes) {
            listaViajes[contador][0] = LocalDate.parse(listaViaje.getFecha().toString()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            listaViajes[contador][1] = listaViaje.getHora().toString();
            listaViajes[contador][2] = String.valueOf(listaViaje.getPrecio());
            listaViajes[contador][3] = String.valueOf(listaViaje.getnroAsientosDisponibles());
            listaViajes[contador][4] = listaViaje.getBus().getPatente();
            contador++;
        }
        return listaViajes;
    }


    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patenteBus) {
        Optional<Viaje> viajeListarPasajeros = findViaje(fecha, hora, patenteBus);
        if (viajeListarPasajeros.isEmpty()) throw new SVPException("No existe viaje con la fecha, hora y patente de bus indicados");
        if (viajeListarPasajeros.get().getListaPasajeros().length == 0) throw new SVPException("No hay pasajeros para el viaje");
        return viajeListarPasajeros.get().getListaPasajeros();
    }

    public void readDatosIniciales() {
        Object[] listaDatos = PersistenciaClase.getInstance().readDatosIniciales();
        ArrayList<Object> objetosDeControladorEmpresas = new ArrayList<>();
        for (Object l : listaDatos) {
            if (l instanceof Cliente) clientes.add((Cliente) l);
            if (l instanceof Viaje) viajes.add((Viaje) l);
            if (l instanceof Pasajero) pasajeros.add((Pasajero) l);

            if (l instanceof Terminal) objetosDeControladorEmpresas.add((Terminal) l);
            if (l instanceof Empresa)  objetosDeControladorEmpresas.add((Empresa) l);
            if (l instanceof Bus) objetosDeControladorEmpresas.add((Bus) l);
            if (l instanceof Tripulante) objetosDeControladorEmpresas.add((Tripulante) l);
            if (l instanceof Rut) objetosDeControladorEmpresas.add((Rut) l);
        }
        ce.setDatosIniciales(objetosDeControladorEmpresas.toArray(new Object[0]));
    }

    public void saveDatosSistema(){
        ControladorEmpresas  ControladorEmpresas = Controlador.ControladorEmpresas.getInstance();
        System.out.println("se guardo el controlador");
        Object[] controladores = {this, ce};
        PersistenciaClase.getInstance().saveControladores(controladores);
    }

    public void readDatosSistema() throws FileNotFoundException {
        try {
            Object[] controladores = PersistenciaClase.getInstance().readControladores();
            UISVP.getInstance().setControladores(controladores);
            for (Object c : controladores) {
                if (c instanceof SistemaVentaPasajes) {
                    instance = (SistemaVentaPasajes) c;
                    System.out.println("1");
                }
                if (c instanceof ControladorEmpresas) {
                    ControladorEmpresas.loadControlador((ControladorEmpresas) c);
                    System.out.println("2");
                }
            }
        }catch (SVPException SVPException) {
            throw new SVPException("No se encontro el controlador");
        }
    }

    public void generatePasajesVenta(String idDocumento, TipoDocumento tipo){
        if (findVenta(idDocumento, tipo).isEmpty()) throw new SVPException("No existe el venta con el identificador " + idDocumento);
        Pasaje[] pasajes = findVenta(idDocumento, tipo).get().getPasajes();
        String nombreArchivo = idDocumento + tipo.toString().toLowerCase() + ".txt";
        try {
            PersistenciaClase.getInstance().savePasajesDeVenta(pasajes, nombreArchivo);
        }catch (SVPException e) {
            throw new SVPException(e.getMessage());
        }
    }

    private Optional<Cliente> findCliente(IdPersona id) {
        Optional<Cliente> clienteEncontrado = clientes.stream().filter(c -> c.getIdPersona().equals(id)).findFirst();
        return clienteEncontrado;
//        for (Cliente c : clientes) {
//            if (c.getIdPersona().equals(id)) {
//                return Optional.of(c);
//            }
//        }
//        return Optional.empty();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        Optional<Venta> ventaEncontrada = ventas.stream().
                filter(v -> v.getIdDocumento().equals(idDocumento)).findFirst().
                filter(venta -> venta.getTipo().equals(tipoDocumento));
        return ventaEncontrada;
//        for (Venta v : ventas) {
//            if (v.getIdDocumento().equals(idDocumento) && v.getTipo().equals(tipoDocumento)) {
//                return Optional.of(v);
//            }
//        }
//        return Optional.empty();
    }

    private Optional<Bus> findBus(String patente) {
        Optional<Bus> busEncontrado = buses.stream().filter(b -> b.getPatente().equals(patente)).findFirst();
        return busEncontrado;
//        for (Bus b : buses) {
//            if (b.getPatente().equals(patente)) {
//                return Optional.of(b);
//            }
//        }
//        return Optional.empty();
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
//        Optional<Viaje> viajeOptional = viajes.stream()
//                .filter(viaje -> viaje.getFecha()
//                        .equals(LocalDate.parse(fecha, formatterDate)))
//                .filter(viaje -> viaje.getHora()
//                        .equals(LocalTime.parse(hora, formatterTime)))
//                .filter(viaje -> viaje.getBus().equals(findBus(patenteBus).get())).findFirst();

        Optional<Viaje> viajeOptional = viajes.stream().filter(viaje -> viaje.getFecha().equals(fecha))
                .filter(viaje -> viaje.getHora().equals(hora)).filter(viaje ->
                        viaje.getBus().getPatente().equals(patenteBus)).findFirst();
        return viajeOptional;
//        for (Viaje v : viajes) {
//            if (v.getFecha().toString().equals(fecha)
//                    && v.getHora().toString().equals(hora)
//                    && v.getBus().getPatente().equals(patenteBus)) {
//                return Optional.of(v);
//            }
//        }
//        return Optional.empty();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        Optional<Pasajero> pasajeroEncontrado = pasajeros.stream()
                .filter(pasajero -> pasajero
                        .getIdPersona().equals(idPersona)).findAny();
        return pasajeroEncontrado;
        //        for (Pasajero p : pasajeros) {
//            if (p.getIdPersona().equals(idPersona)) {
//                return Optional.of(p);
//            }
//        }
//        return Optional.empty();
    }
}