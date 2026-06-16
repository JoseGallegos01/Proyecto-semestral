package Persistencia;

import Controlador.ControladorEmpresas;
import Controlador.SistemaVentaPasajes;
import Excepciones.SistemaVentaPasajesException;
import Modelo.Cliente;
import Modelo.Pasajero;
import Modelo.Persona;
import Modelo.Terminal;
import utilidades.*;
import Modelo.*;

import java.io.*;
import java.util.*;

public class PersistenciaClase implements Serializable {
    ArrayList<Pasajero> listaPasajeros;
    ArrayList<Cliente> listaClientes;
    ArrayList<Empresa> listaEmpresas;

    static PersistenciaClase instance = null;

    public static PersistenciaClase getInstance() {
        if (instance==null){
            instance = new PersistenciaClase();
        }
        return instance;
    }
    //    public Object[] readDatosIniciales() throws FileNotFoundException {
//        Scanner scAr = new Scanner(new File("SVPDatosIniciales.txt"));
//        scAr.useDelimiter("\r\n|;");
//        String lineaLeida = scAr.next();
//        String seccionClientesPasajeros;
//        String seccionEmpresas;
//        String seccionTripulantes;
//        String seccionTerminales;
//        String seccionBuses;
//        String seccionViajes;
//        String rut;
//        String tratatamiento;
//        String tratamientoContacto;
//        String NombresPersona;
//        String ApellidoPaterno;
//        String ApellidoMaterno;
//        String NumeroDeTelefono;
//        String email;
//        String NombreContacto;
//        String ApellidoPaternoContacto;
//        String ApellidoMaternoContacto;
//        String fonoContacto;
//
//        String nombreEmpresa;
//        String linkEmpresa;
//        while (!scAr.hasNextLine()) {
//            //todo este while es para que solo lea clientes y pasajeros, tendre que explicar la logica en reunion
//            while (lineaLeida!="+"){
//                lineaLeida = scAr.next();
//                if(lineaLeida=="C"){
//                    rut = scAr.next();
//                    tratatamiento = scAr.next();
//                    NombresPersona = scAr.next();
//                    ApellidoPaterno = scAr.next();
//                    ApellidoMaterno = scAr.next();
//                    NumeroDeTelefono = scAr.next();
//                    email = scAr.next();
//                    SistemaVentaPasajes.getInstance().createCliente(registrarRut(rut), registrarNombre(NombresPersona),
//                            ApellidoPaterno, ApellidoMaterno);
//                    findCliente(registrarRut(rut)).get().getNombreCompleto().
//                            setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
//                    findCliente(registrarRut(rut)).get().setTelefono(NumeroDeTelefono);
//                    findCliente(registrarRut(rut)).get().setEmail(email);
//                }
//                if (lineaLeida=="P"){
//                    rut = scAr.next();
//                    tratatamiento = scAr.next();
//                    NombresPersona = scAr.next();
//                    ApellidoPaterno = scAr.next();
//                    ApellidoMaterno = scAr.next();
//                    NumeroDeTelefono = scAr.next();
//                    //tengo dudas respecto al tratamientoContacto, pues no hay contacto que tenga tratamiento
//                    //sin embargo eso esta dentro del archivo de txt que leer
//                    //tampoco hay contacto con apellidos
//                    tratamientoContacto = scAr.next();
//                    NombreContacto = scAr.next();
//                    ApellidoPaternoContacto = scAr.next();
//                    ApellidoMaternoContacto = scAr.next();
//                    fonoContacto = scAr.next();
//                    SistemaVentaPasajes.getInstance().createPasajero(registrarRut(rut), registrarNombre(NombresPersona), NumeroDeTelefono,
//                            registrarNombre(NombreContacto), fonoContacto);
//                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
//                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoMaterno(ApellidoMaterno);
//                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoPaterno(ApellidoPaterno);
//                }
//                if (lineaLeida=="CP") {
//                    rut = scAr.next();
//                    tratatamiento = scAr.next();
//                    NombresPersona = scAr.next();
//                    ApellidoPaterno = scAr.next();
//                    ApellidoMaterno = scAr.next();
//                    NumeroDeTelefono = scAr.next();
//                    email = scAr.next();
//                    tratamientoContacto = scAr.next();
//                    NombreContacto = scAr.next();
//                    ApellidoPaternoContacto = scAr.next();
//                    ApellidoMaternoContacto = scAr.next();
//                    fonoContacto = scAr.next();
//                    SistemaVentaPasajes.getInstance().createCliente(registrarRut(rut), registrarNombre(NombresPersona),
//                            ApellidoPaterno, ApellidoMaterno);
//                    SistemaVentaPasajes.getInstance().createPasajero(registrarRut(rut), registrarNombre(NombresPersona), NumeroDeTelefono,
//                            registrarNombre(NombreContacto), fonoContacto);
//                    findCliente(registrarRut(rut)).get().getNombreCompleto().
//                            setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
//                    findCliente(registrarRut(rut)).get().setTelefono(NumeroDeTelefono);
//                    findCliente(registrarRut(rut)).get().setEmail(email);
//                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setTratamiento(Tratamiento.valueOf(tratatamiento.toUpperCase()));
//                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoMaterno(ApellidoMaterno);
//                    findPasajero(registrarRut(rut)).get().getNombreCompleto().setApellidoPaterno(ApellidoPaterno);
//                }
//            }
//            //registro de empresas
//            while (lineaLeida!="+"){
//                lineaLeida = scAr.next();
//                rut = scAr.next();
//                nombreEmpresa = scAr.next();
//                linkEmpresa  = scAr.next();
//                ControladorEmpresas.getInstance().createEmpresa(registrarRut(rut), nombreEmpresa, linkEmpresa);
//            }
//            //para leer auxiliares y conductores
//            while (lineaLeida!="+"){
//                lineaLeida = scAr.next();
//                if (lineaLeida=="A"){
//
//                }
//            }
//        }
//    }

    public Object[] readDatosIniciales() throws SistemaVentaPasajesException {
        String nombreArchivo = "SVPDatosIniciales.txt"; //Asi se llama la vainaen el moodle
        List<Object> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int seccion = 0;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue;
                }

                if (linea.equals("+")) {
                    seccion++;
                    continue;
                }

                try  {
                    switch (seccion) {
                        case 0:
                            procesarClientePasajero(linea, lista);
                            break;
                        case 1:
                            procesarEmpresa(linea, lista);
                            break;
                        case 2:
                            procesarTripulante(linea, lista);
                            break;
                        case 3:
                            procesarTerminal(linea, lista);
                            break;
                        case 4:
                            procesarBus(linea, lista);
                            break;
                        case 5:
                            procesarViaje(linea, lista);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Error en la linea: " + linea + " / " + e.getMessage());
                }

            }
        }catch (IOException e){
            throw new SistemaVentaPasajesException(("Error en la lectura del archivo"));
        }
        return lista.toArray();
    }

    public Object[] readControladores() throws FileNotFoundException {
        SistemaVentaPasajes ControladorSistemaVentaPasajesInput = null;
        ControladorEmpresas ControladorEmpresasInput = null;
        try {
            ObjectInputStream inSV = new ObjectInputStream(new FileInputStream("SistemaVentasPasajes.obj"));
            ObjectInputStream inCE = new ObjectInputStream(new FileInputStream("ControladorEmpresas.obj"));
            ControladorSistemaVentaPasajesInput = ((SistemaVentaPasajes) inSV.readObject());
            ControladorEmpresasInput = ((ControladorEmpresas) inCE.readObject());
        } catch (FileNotFoundException e) {
            throw new SistemaVentaPasajesException("No se encontro el archivo de los controladores");
        } catch (IOException e) {
            throw new SistemaVentaPasajesException("No se pudo leer los controladores");
        } catch (ClassNotFoundException e) {
            throw new SistemaVentaPasajesException("No se encontraron el controlador");
        }
        return new Object[]{ControladorSistemaVentaPasajesInput, ControladorEmpresasInput};
    }

    public void saveControladores(Object[] controladores){
        try{
            ObjectOutputStream outSVP = new ObjectOutputStream(new FileOutputStream("SistemaVentasPasajes.obj"));
            ObjectOutputStream outCE = new ObjectOutputStream(new FileOutputStream("ControladorEmpresas.obj"));
            for (Object c : controladores) {
                if (c instanceof SistemaVentaPasajes) outSVP.writeObject(c);
                if (c instanceof ControladorEmpresas) outCE.writeObject(c);
            }
            outSVP.close();
            outCE.close();
        }catch (IOException e){
            throw new SistemaVentaPasajesException(e.getMessage());
        }
    }

    private void procesarClientePasajero(String linea, List<Object> lista){
        String[] datos = linea.split(";");
        String tipo = datos[0].trim();

        Rut rut = registrarRut(datos[1].trim());
        Nombre nombreCompleto = new Nombre();
        //Nombre nombreCompleto = new Nombre(Tratamiento.valueOf(datos[7].trim()), datos[3].trim(), datos[4].trim(), datos[5].trim());
        nombreCompleto.setNombres(datos[3].trim());
        nombreCompleto.setTratamiento(Tratamiento.valueOf(datos[7].trim()));
        nombreCompleto.setApellidoPaterno(datos[4].trim());
        nombreCompleto.setApellidoMaterno(datos[5].trim());
        String telefono = datos[6].trim();

        if (tipo.equals("C")) {
            String email = datos[7].trim();
            Cliente cliente = new Cliente(rut, nombreCompleto, email);
            cliente.setTelefono(telefono);
            lista.add(cliente);
        } else if (tipo.equals("P")) {
            Tratamiento conTrat = Tratamiento.valueOf(datos[7].trim());
            Nombre conNombre = new Nombre();
            conNombre.setNombres(datos[8].trim());
            conNombre.setTratamiento(conTrat);
            conNombre.setApellidoPaterno(datos[9].trim());
            conNombre.setApellidoMaterno(datos[10].trim());
            String conFono = datos[11].trim();
            Pasajero pasajero = new Pasajero(rut, nombreCompleto, conNombre, conFono);
            pasajero.setTelefono(telefono);
            lista.add(pasajero);
        } else if (tipo.equals("CP")) {
            String email = datos[7].trim();
            Cliente cliente = new Cliente(rut, nombreCompleto, email);
            cliente.setTelefono(telefono);
            lista.add(cliente);

            Tratamiento conTrat = Tratamiento.valueOf(datos[8].trim());
            Nombre conNombre = new Nombre();
            conNombre.setNombres(datos[8].trim());
            conNombre.setTratamiento(conTrat);
            conNombre.setApellidoPaterno(datos[9].trim());
            conNombre.setApellidoMaterno(datos[10].trim());
            String conFono = datos[11].trim();

            Pasajero pasajero = new Pasajero(rut, nombreCompleto, conNombre, conFono);
            pasajero.setTelefono(telefono);
            lista.add(pasajero);
        }

    }

    private void procesarEmpresa(String linea, List<Object> lista){
        String[] datos = linea.split(";");

        Rut rutEmpresa = registrarRut(datos[1].trim());
        String nombre = datos[1].trim();
        String url = datos[2].trim();

        Empresa empresa = new Empresa(rutEmpresa, nombre, url);
        lista.add(empresa);
    }

    private void procesarTripulante(String linea, List<Object> lista){
        String[] datos = linea.split(";");
        String tipo = datos[0].trim();

        Rut rutTripulante = registrarRut(datos[1].trim());
        Tratamiento tratamiento = Tratamiento.valueOf(datos[2].trim());
        Nombre nombreTripulante = new Nombre();
        nombreTripulante.setTratamiento(tratamiento);
        nombreTripulante.setNombres(datos[3].trim());
        nombreTripulante.setApellidoPaterno(datos[4].trim());
        nombreTripulante.setApellidoMaterno(datos[5].trim());

        String calle = datos[6].trim();
        int numero = Integer.parseInt(datos[7].trim());
        String comuna = datos[8].trim();
        Direccion direccion = new Direccion(calle, numero, comuna);

        if (tipo.equals("C")) {
            Conductor conductor = new Conductor(rutTripulante, nombreTripulante, direccion);
            lista.add(conductor);
        } else if (tipo.equals("A")) {
            Auxiliar auxiliar = new Auxiliar(rutTripulante, nombreTripulante, direccion);
            lista.add(auxiliar);
        }
    }

    private void procesarTerminal(String linea, List<Object> lista){
        String[] datos = linea.split(";");

        String nombre = datos[0].trim();
        String calle = datos[1].trim();
        int numero = Integer.parseInt(datos[2].trim());
        String comuna = datos[3].trim();

        Direccion direccion = new Direccion(calle, numero, comuna);
        Terminal terminal = new Terminal(nombre, direccion);
        lista.add(terminal);
    }

    private void procesarBus(String linea, List<Object> lista){
        String[] datos = linea.split(";");

        String patente = datos[0].trim();
        String marca = datos[1].trim();
        String modelo = datos[2].trim();
        Rut rutEmpresa = registrarRut(datos[4].trim());
        int nroAsientos = Integer.parseInt(datos[3].trim());
        Bus bus = new Bus(patente, nroAsientos, findEmpresa(rutEmpresa).get());
        lista.add(bus);
    }

    private void procesarViaje(String linea, List<Object> lista){
        String[] datos = linea.split(";");

        String fechaStr = datos[0].trim();
        String horaStr = datos[1].trim();
        int precio = Integer.parseInt(datos[2].trim());
        int duracion = Integer.parseInt(datos[3].trim());

        String patenteBus = datos[4].trim();
        String rutAuxiliar = datos[5].trim();
        String rutConductor = datos[6].trim();
        String terminalOrigen = datos[7].trim();
        String terminalDestino = datos[8].trim();
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
    protected Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa emp : listaEmpresas) {
            if (emp.getRut().equals(rut)) {
                return Optional.of(emp);
            }
        }
        return Optional.empty();
    }
}
