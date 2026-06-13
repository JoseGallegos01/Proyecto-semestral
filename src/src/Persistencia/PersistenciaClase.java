package Persistencia;

import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Excepciones.SistemaVentaPasajesException;
import Modelo.Cliente;
import Modelo.Pasajero;
import Modelo.Persona;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;
import utilidades.Tratamiento;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class PersistenciaClase {
    ArrayList<Pasajero> listaPasajeros;
    ArrayList<Cliente> listaClientes;
    public Object[] readDatosIniciales() throws FileNotFoundException {
        Scanner scAr = new Scanner(new File("SVPDatosIniciales.txt"));
        scAr.useDelimiter("\r\n|;");
        String lineaLeida = scAr.next();
        String seccionClientesPasajeros;
        String seccionEmpresas;
        String seccionTripulantes;
        String seccionTerminales;
        String seccionBuses;
        String seccionViajes;
        String rut;
        String tratatamiento;
        String tratamientoContacto;
        String NombresPersona;
        String ApellidoPaterno;
        String ApellidoMaterno;
        String NumeroDeTelefono;
        String email;
        String NombreContacto;
        String ApellidoPaternoContacto;
        String ApellidoMaternoContacto;
        String fonoContacto;

        String nombreEmpresa;
        String linkEmpresa;
        while (!scAr.hasNextLine()) {
            //todo este while es para que solo lea clientes y pasajeros, tendre que explicar la logica en reunion
            while (lineaLeida!="+"){
                lineaLeida = scAr.next();
                if(lineaLeida=="C"){
                    rut = scAr.next();
                    tratatamiento = scAr.next();
                    NombresPersona = scAr.next();
                    ApellidoPaterno = scAr.next();
                    ApellidoMaterno = scAr.next();
                    NumeroDeTelefono = scAr.next();
                    email = scAr.next();
                    SistemaVentaPasajes.getInstance().createCliente(registrarRut(rut), registrarNombre(NombresPersona),
                            ApellidoPaterno, ApellidoMaterno);
                    findCliente(registrarRut(rut)).get().getNombreCompleto().
                            setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
                    findCliente(registrarRut(rut)).get().setTelefono(NumeroDeTelefono);
                    findCliente(registrarRut(rut)).get().setEmail(email);
                }
                if (lineaLeida=="P"){
                    rut = scAr.next();
                    tratatamiento = scAr.next();
                    NombresPersona = scAr.next();
                    ApellidoPaterno = scAr.next();
                    ApellidoMaterno = scAr.next();
                    NumeroDeTelefono = scAr.next();
                    //tengo dudas respecto al tratamientoContacto, pues no hay contacto que tenga tratamiento
                    //sin embargo eso esta dentro del archivo de txt que leer
                    //tampoco hay contacto con apellidos
                    tratamientoContacto = scAr.next();
                    NombreContacto = scAr.next();
                    ApellidoPaternoContacto = scAr.next();
                    ApellidoMaternoContacto = scAr.next();
                    fonoContacto = scAr.next();
                    SistemaVentaPasajes.getInstance().createPasajero(registrarRut(rut), registrarNombre(NombresPersona), NumeroDeTelefono,
                            registrarNombre(NombreContacto), fonoContacto);
                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoMaterno(ApellidoMaterno);
                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoPaterno(ApellidoPaterno);
                }
                if (lineaLeida=="CP") {
                    rut = scAr.next();
                    tratatamiento = scAr.next();
                    NombresPersona = scAr.next();
                    ApellidoPaterno = scAr.next();
                    ApellidoMaterno = scAr.next();
                    NumeroDeTelefono = scAr.next();
                    email = scAr.next();
                    tratamientoContacto = scAr.next();
                    NombreContacto = scAr.next();
                    ApellidoPaternoContacto = scAr.next();
                    ApellidoMaternoContacto = scAr.next();
                    fonoContacto = scAr.next();
                    SistemaVentaPasajes.getInstance().createCliente(registrarRut(rut), registrarNombre(NombresPersona),
                            ApellidoPaterno, ApellidoMaterno);
                    SistemaVentaPasajes.getInstance().createPasajero(registrarRut(rut), registrarNombre(NombresPersona), NumeroDeTelefono,
                            registrarNombre(NombreContacto), fonoContacto);
                    findCliente(registrarRut(rut)).get().getNombreCompleto().
                            setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
                    findCliente(registrarRut(rut)).get().setTelefono(NumeroDeTelefono);
                    findCliente(registrarRut(rut)).get().setEmail(email);
                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoMaterno(ApellidoMaterno);
                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoPaterno(ApellidoPaterno);
                }
            }
            //registro de empresas
            while (lineaLeida!="+"){
                lineaLeida = scAr.next();
                rut = scAr.next();
                nombreEmpresa = scAr.next();
                linkEmpresa  = scAr.next();
                ControladorEmpresas.getInstance().createEmpresa(registrarRut(rut), nombreEmpresa, linkEmpresa);
            }
        }
    }

    private Optional<Cliente> findCliente(IdPersona id) {
        for (Cliente c : listaClientes) {
            if (c.getIdPersona().equals(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        for (Pasajero p : listaPasajeros) {
            if (p.getIdPersona().equals(idPersona)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private Rut registrarRut(String rut){
        try {
            Rut rutRegistrado;
            String rutCompleto = rut;
            rutCompleto = rutCompleto.replace(".", "");
            String[] partes = rutCompleto.split("-");
            int numero = Integer.parseInt(partes[0]);
            char dv = partes[1].charAt(0);
            return rutRegistrado = new Rut(numero, dv);
        }catch (Exception e){
            throw new SistemaVentaPasajesException(("formato de rut incorrecto, debe ser xx.xxx.xxx-x"));
        }
    }

    private Nombre registrarNombre(String nombre){
        Nombre nombreRegistrado = new Nombre();
        nombreRegistrado.setNombres(nombre);
        return nombreRegistrado;
    }

}
