package Controlador;

import Modelo.PagoEfectivo;
import Excepciones.SistemaVentaPasajesException;
import Modelo.*;
import Persistencia.PersistenciaClase;
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
    ArrayList<Empresa> empresas;
    ArrayList<Modelo.Terminal> terminales;
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
        if (findCliente(id).isPresent()) throw new SistemaVentaPasajesException("Ya existe cliente con el id indicado");
        if (findCliente(id).isEmpty()) {
            clientes.add(new Cliente(id, nom, email));
            findCliente(id).get().setTelefono(fono);
        }
    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nombreContacto,
                               String fonoContacto) {
        if (findPasajero(id).isPresent())
            throw new SistemaVentaPasajesException("Ya existe pasajero con el id indicado");
        pasajeros.add(new Pasajero(id, nom, nombreContacto, fono));
        findPasajero(id).get().setFonoContacto(fonoContacto);

    }

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patenteBus, IdPersona[] tripulantes, String[] nomComunas) {
        if (findViaje(fecha.toString(), hora.toString(), patenteBus).isPresent())
            throw new SistemaVentaPasajesException("Ya existe viaje con fecha, hora y patente de bus indicados");
        if (ce.findBus(patenteBus).isEmpty())
            throw new SistemaVentaPasajesException("No existe bus con la patente indicada");
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

    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        if (findVenta(idDoc, tipo).isPresent())
            throw new SistemaVentaPasajesException("Ya existe venta con el id y tipo de documento indicados");
        if (findCliente(idCliente).isEmpty())
            throw new SistemaVentaPasajesException("No existe cliente con el id indicado");

        Optional<Cliente> clienteVenta = findCliente(idCliente);
        ventas.add(new Venta(idDoc, tipo, fechaVenta, clienteVenta.get()));
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje) {
        int cantidadHorariosDisponibles = 0;
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().isEqual(fechaViaje)) {
                cantidadHorariosDisponibles++;
            }
        }
        String[][] horarios = new String[cantidadHorariosDisponibles][4];
        int i = 0;
        for (Viaje viaje : viajes) {
            if (viaje.getFecha().isEqual(fechaViaje)) {
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
        if (findViaje(fecha.toString(), hora.toString(), patenteBus).isPresent()) {
            int cantidadasientos = findViaje(fecha.toString(), hora.toString(), patenteBus).get().getBus().getNroAsientos();
            int contador = 0;
            cantidadasientos = (int) Math.ceil(cantidadasientos / 4.0);
            //saque eso de arriba de https://www.w3schools.com/java/ref_math_ceil.asp buscando como hacer lo de las filas
            String[][] asientos = new String[cantidadasientos][4];
            for (int i = 0; i < cantidadasientos; i++) {
                if (contador <= cantidadasientos) asientos[i][0] = String.valueOf(contador++);
                else asientos[i][0] = "";
                if (contador <= cantidadasientos) asientos[i][1] = String.valueOf(contador++);
                else asientos[i][1] = "";
                if (contador <= cantidadasientos) asientos[i][3] = String.valueOf(contador++);
                else asientos[i][3] = "";
                if (contador <= cantidadasientos) asientos[i][2] = String.valueOf(contador++);
                else asientos[i][2] = "";
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
        if (findPasajero(idPasajero).isEmpty()) return null;
        return findPasajero(idPasajero).get().getNombreCompleto().toString();
    }

    public void vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patenteBus, int asiento, IdPersona idPasajero, TipoDocumento tipo) {
        Optional<Viaje> viajeVenta = findViaje(fecha.toString(), hora.toString(), patenteBus);
        Optional<Venta> ventaViaje = findVenta(idDoc, tipo);
        Optional<Pasajero> pasajeroVenta = findPasajero(idPasajero);
        if (viajeVenta.isEmpty())
            throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
        if (findPasajero(idPasajero).isEmpty())
            throw new SistemaVentaPasajesException("No existe pasajero con el id indicado");
        if (!viajeVenta.get().existeDisponibilidad(viajeVenta.get().getnroAsientosDisponibles())) throw new SistemaVentaPasajesException("No hay disponibilidad");
        ventaViaje.get().createPasaje(asiento, viajeVenta.get(), pasajeroVenta.get());
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo){
        Optional<Venta> venta = findVenta(idDoc, tipo);
        if(venta.isEmpty()){
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo indicado");
        }
        if(!venta.get().pagaMonto()){
            throw new SistemaVentaPasajesException("El pago ya fue realizado");
        }
    }

    public void pagaVenta(String idDoc, TipoDocumento tipo, long nroTarjeta){
        Optional<Venta> venta = findVenta(idDoc, tipo);
        if(venta.isEmpty()){
            throw new SistemaVentaPasajesException("No existe venta con el id y tipo indicado");
        }
        if(!venta.get().pagaMonto(nroTarjeta)){
            throw new SistemaVentaPasajesException("El pago ya fue realizado");
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
        Optional<Viaje> viajeListarPasajeros = findViaje(fecha.toString(), hora.toString(), patenteBus);
        if (viajeListarPasajeros.isPresent()) return viajeListarPasajeros.get().getListaPasajeros();
        else throw new SistemaVentaPasajesException("No existe viaje con la fecha, hora y patente de bus indicados");
    }

    public void readDatosIniciales(){
        Object[] listaDatos = PersistenciaClase.getInstance().readDatosIniciales();
        for (Object l : listaDatos) {
            if (l instanceof Cliente) clientes.add((Cliente) l);
            if (l instanceof Viaje) viajes.add((Viaje) l);
            if (l instanceof Pasajero) pasajeros.add((Pasajero) l);
            if (l instanceof Bus) buses.add((Bus) l);
            if (l instanceof Empresa) empresas.add((Empresa) l);
        }
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
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException("No se encontro el controlador");
        }
    }

    private Optional<Cliente> findCliente(IdPersona id) {
        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDocumento) && v.getTipo().equals(tipoDocumento)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    private Optional<Bus> findBus(String patente) {
        for (Bus b : buses) {
            if (b.getPatente().equals(patente)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    private Optional<Viaje> findViaje(String fecha, String hora, String patenteBus) {
        for (Viaje v : viajes) {
            if (v.getFecha().toString().equals(fecha)
                    && v.getHora().toString().equals(hora)
                    && v.getBus().getPatente().equals(patenteBus)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(idPersona)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}