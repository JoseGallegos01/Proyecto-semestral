package Controlador;

import modelo.*;
import utilidades.*;
import Vista.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();
    ArrayList<Viaje> viajes = new ArrayList<>();
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){
        if (findCliente(id).isEmpty()) {
            clientes.add(new Cliente(id, nom, email));
            findCliente(id).get().setTelefono(fono);
            return true;
        }
        return false;
    }
    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nombreContacto,
                                  String fonoContacto){
        if (findPasajero(id).isEmpty()) {
            pasajeros.add(new Pasajero(id, nom, nombreContacto, fono));
            findPasajero(id).get().setFonoContacto(fonoContacto);
            return true;
        }
        return false;
    }
    public boolean createBus(String patente, String marca, String modelo, int NroAsientos){
        if (findBus(patente).isEmpty()) {
            buses.add(new Bus(patente, NroAsientos));
            findBus(patente).get().setMarca(marca);
            findBus(patente).get().setModelo(modelo);
            return true;
        }
        return false;
    }
    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patenteBus){
        if (findBus(patenteBus).isPresent()) {
            if (findViaje(fecha.toString(), hora.toString(), patenteBus).isEmpty()) {
                viajes.add(new Viaje(fecha, hora, precio, findBus(patenteBus).get()));
                return true;
            }
        }
        return false;
    }
    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente) {
        if (findCliente(idCliente).isEmpty()) return false;
        if (findVenta(idDoc, tipo).isPresent()) return false;
        Optional<Cliente> clienteVenta = findCliente(idCliente);
        ventas.add(new Venta(idDoc, tipo, fechaVenta, clienteVenta.get()));
        return true;
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
        if (findViaje(fecha.toString(), hora.toString(), patenteBus).isPresent()) {
            int cantidadasientos = findViaje(fecha.toString(), hora.toString(), patenteBus).get().getBus().getNroAsientos();
            int contador = 0;
            cantidadasientos = (int) Math.ceil(cantidadasientos / 4.0);
            //saque eso de arriba de https://www.w3schools.com/java/ref_math_ceil.asp buscando como hacer lo de las filas
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

    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipoDocumento){
        if (findVenta(idDocumento, tipoDocumento).isEmpty()) return Optional.empty();
        return Optional.of(findVenta(idDocumento, tipoDocumento).get().getMonto());
    }

    public String getNombrePasajero(IdPersona idPasajero){
        if (findPasajero(idPasajero).isEmpty()) return null;
        return findPasajero(idPasajero).get().getNombreCompleto().toString();
    }

    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patenteBus, int asiento, IdPersona idPasajero, TipoDocumento tipo) {
        Optional<Viaje> viajeVenta = findViaje(fecha.toString(), hora.toString(), patenteBus);
        Optional<Venta> ventaViaje = findVenta(idDoc, tipo);
        Optional<Pasajero> pasajeroVenta = findPasajero(idPasajero);
        if (viajeVenta.isEmpty()) return false;
        if (findPasajero(idPasajero).isEmpty()) return false;
        if (!viajeVenta.get().existeDisponibilidad()) return false;
        ventaViaje.get().createPasaje(asiento, viajeVenta.get(), pasajeroVenta.get());
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
        for (Viaje listaViaje : viajes){
            listaViajes[contador][0] = LocalDate.parse(listaViaje.getFecha().toString()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            listaViajes[contador][1] = listaViaje.getHora().toString();
            listaViajes[contador][2] = String.valueOf(listaViaje.getPrecio());
            listaViajes[contador][3] = String.valueOf(listaViaje.getnroAsientosDisponibles());
            listaViajes[contador][4] = listaViaje.getBus().getPatente();
            contador++;
        }
        return listaViajes;
    }


    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patenteBus){
        Optional<Viaje> viajeListarPasajeros = findViaje(fecha.toString(), hora.toString(), patenteBus);
        if (viajeListarPasajeros.isPresent()) return viajeListarPasajeros.get().getListaPasajeros();
        else return new String[0][0];
    }


    private Optional<Cliente> findCliente(IdPersona id){
        for (Cliente c : clientes){
            if (c.getIdPersona().equals(id)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento){
        for (Venta v : ventas){
            if (v.getIdDocumento().equals(idDocumento) && v.getTipo().equals(tipoDocumento)){
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }
    private Optional<Bus> findBus(String patente){
        for (Bus b : buses){
            if (b.getPatente().equals(patente)){
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }
    private Optional<Viaje> findViaje(String fecha, String hora, String patenteBus){
        for (Viaje v : viajes){
            if (v.getFecha().toString().equals(fecha)
                    && v.getHora().toString().equals(hora)
                    && v.getBus().getPatente().equals(patenteBus)){
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }
    private Optional<Pasajero> findPasajero(IdPersona idPersona){
        for (Pasajero p : pasajeros){
            if (p.getIdPersona().equals(idPersona)){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
