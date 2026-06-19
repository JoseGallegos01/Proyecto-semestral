package Controlador;

import Modelo.Conductor;
import Modelo.Empresa;
import Modelo.Viaje;
import Excepciones.SistemaVentaPasajesException;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;
import Modelo.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;


    public class ControladorEmpresas implements Serializable {

        private static ControladorEmpresas instance;

        private ArrayList<Empresa> empresas;
        private ArrayList<Modelo.Bus> bus;
        private ArrayList<Modelo.Terminal> terminales;


        public ControladorEmpresas() {
            this.empresas = new ArrayList<>();
            this.bus = new ArrayList<>();
            this.terminales = new ArrayList<>();
        }

        public static ControladorEmpresas getInstance() {
            if (instance == null) {
                instance = new ControladorEmpresas();
            }
            return instance;
        }

        public void createEmpresa(Rut rut, String nombre, String url) {
            if(findEmpresa(rut).isPresent()) {
                throw new SistemaVentaPasajesException("La empresa ya existe");
            }
            Empresa empresa = new Empresa(rut, nombre, url);
            Empresa.setUrl(url);
            this.empresas.add(empresa);
        }

        public void createBus(String patente, String marca, String modelo, int nroAsiento, Rut rutEmp) {
            Optional<Empresa> empresaOptional = findEmpresa(rutEmp);

            if(empresaOptional.isEmpty()){
                throw new SistemaVentaPasajesException("No existe la empresa con el rut ingresado");

            }

            if(findBus(patente).isPresent()){
                throw new SistemaVentaPasajesException("El bus con la patente indicada ya existe");
            }

            Bus bus = new Modelo.Bus(patente, nroAsiento, empresaOptional.get());
            bus.setMarca(marca);
            bus.setModelo(modelo);
            this.bus.add(bus);
            empresaOptional.get().addBus(bus);
        }

        public void createTerminal(String nombre, Direccion direccion) {
            if (findTerminal(nombre).isPresent()) {
                throw new SistemaVentaPasajesException("El terminal ya existe");
            }
            if(findTerminalPorComuna(direccion.getComuna()).isPresent()){
                throw new SistemaVentaPasajesException("El terminal de la comuna indicada ya existe");
            }

            Modelo.Terminal terminal = new Modelo.Terminal(nombre, direccion);
            this.terminales.add(terminal);
        }

        public void hireConductor(Rut rutEmp, IdPersona idPersona, Nombre nombre, Direccion direccion) {
            Optional<Empresa> empresaOptional = findEmpresa(rutEmp);
            if(empresaOptional.isEmpty()) {
                throw new SistemaVentaPasajesException("No existe la empresa con el rut ingresado");
            }

            boolean contratado = empresaOptional.get().addConductor(idPersona, nombre, direccion);
            if(!contratado){
                throw new SistemaVentaPasajesException("Ya esta contratado el Conductor/Auxiliar con el id otorgado");
            }

        }

        public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona idPersona, Nombre nombre, Direccion direccion) {
            Optional<Empresa> empresaOptional = findEmpresa(rutEmp);
            if(empresaOptional.isEmpty()) {
                throw new SistemaVentaPasajesException("No existe el empresa con el rut ingresado");
            }
            boolean contratado = empresaOptional.get().addAuxiliar(idPersona, nombre, direccion);
            if (!contratado){
                throw new SistemaVentaPasajesException("Ya esta contratado el Auxiliar/Conductor con el id otorgado ");
            }
        }

        public String[][] listEmpresas() {
            if (empresas.isEmpty()) {
                return new String[0][0];
            }
            return empresas.stream()
                    .map(empresa -> new String[]{
                            empresa.getRut().toString(),
                            empresa.getNombre(),
                            empresa.getUrl(),
                            String.valueOf(empresa.getTripulantes().length),
                            String.valueOf(empresa.getBuses().length),
                            String.valueOf(empresa.getVentas().length)

                    })
                    .toArray(String[][]::new);
        }

        public String[][] listLlegadaSalidaTerminal(String nombre, LocalDate fecha) {
            Optional<Terminal> terminalOptional = findTerminal(nombre);
            if (terminalOptional.isEmpty()) {
                throw new SistemaVentaPasajesException("No existe el terminal con el nombre ingresado");
            }
            Terminal terminal = terminalOptional.get();

            List<String[]> salidas = Arrays.stream(terminal.getSalidas())
                    .filter(salida -> salida.getFecha().equals(fecha))
                    .map(salida -> new String[]{
                            "Salida",
                            salida.getFechaHoraTermino().toLocalTime().toString(),
                            salida.getBus().getPatente(),
                            salida.getBus().getEmpresa().getNombre(),
                            String.valueOf(salida.getListaPasajeros().length)
                    })
                    .collect(Collectors.toList());

            List<String[]> llegadas = Arrays.stream(terminal.getLlegadas())
                    .filter(llegada -> llegada.getFechaHoraTermino().toLocalDate().equals(fecha))
                    .map(llegada -> new String[]{
                            "Llegada",
                            llegada.getFechaHoraTermino().toLocalTime().toString(),
                            llegada.getBus().getPatente(),
                            llegada.getBus().getEmpresa().getNombre(),
                            String.valueOf(llegada.getListaPasajeros().length)

                    })
                    .collect(Collectors.toList());
            List<String[]> resultado = new ArrayList<>();
            resultado.addAll(salidas);
            resultado.addAll(llegadas);

            return resultado.toArray(new String[0][0]);
        }
        public String[][] listVentasEmpresa(Rut rut) {
            Optional<Empresa> empresaOptional = findEmpresa(rut);
            if (empresaOptional.isEmpty()) {
                throw new SistemaVentaPasajesException("No existe la empresa con el rut ingresado");
            }

            Venta[] ventas = empresaOptional.get().getVentas();

            if (ventas.length == 0) {
                return new String[0][0];
            }
            return Arrays.stream(ventas)
                    .map(venta -> new String[]{
                            venta.getFecha().toString(),
                            venta.getTipo().toString(),
                            String.valueOf(venta.getMontoPagado()),
                            venta.getTipoPago()
                    })
                    .toArray(String[][]::new);
        }



        protected Optional<Empresa> findEmpresa(Rut rut ) {
            return empresas.stream()
                    .filter(emp ->emp.getRut().equals(rut))
                    .findFirst();
        }
        protected Optional<Terminal> findTerminal(String nombre) {
            return terminales.stream()
                    .filter(term->term.getNombre().equalsIgnoreCase(nombre))
                    .findFirst();
        }


        protected Optional<Terminal> findTerminalPorComuna(String comuna) {
            return terminales.stream()
                    .filter(term -> term.getDireccion().getComuna().equalsIgnoreCase(comuna))
                    .findFirst();
        }

        protected Optional<Modelo.Bus> findBus(String patente) {
            return bus.stream()
                    .filter(b -> b.getPatente().equalsIgnoreCase(patente))
                    .findFirst();
        }


        protected Optional<Conductor> findConductor(IdPersona id, Rut rutEmpresa) {
            Optional<Empresa> emp = findEmpresa(rutEmpresa);
            if (emp.isEmpty()) {
                return Optional.empty();

            }
            return Arrays.stream(emp.get().getTripulantes())
                    .filter(t -> t instanceof Conductor && t.getIdPersona().equals(id))
                    .map(t -> (Conductor) t)
                    .findFirst();
        }


        protected Optional<Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {

            Optional<Empresa> emp = findEmpresa(rutEmpresa);
            if (emp.isEmpty()) {
                return Optional.empty();

            }
            return Arrays.stream(emp.get().getTripulantes())
                    .filter(t -> t instanceof Auxiliar && t.getIdPersona().equals(id))
                    .map(t -> (Auxiliar) t)
                    .findFirst();
        }

        public static void loadControlador(ControladorEmpresas controlador){
            instance = controlador;
        }
        protected void setDatosIniciales(Object[] objetos){
            ArrayList<Rut> rutEmpresas = new ArrayList<>();
            ArrayList<Tripulante> tripulantes = new ArrayList<>();
            int contador = 0;
            for (Object o : objetos){
                if (o instanceof Empresa) empresas.add((Empresa) o);
                if (o instanceof Terminal) terminales.add((Terminal) o);
                if (o instanceof Bus) bus.add((Bus) o);
                if (o instanceof Rut) rutEmpresas.add((Rut) o);
                if (o instanceof Tripulante) tripulantes.add((Tripulante) o);
            }
            for (Tripulante t : tripulantes) {
                if (t instanceof Conductor){
                    hireConductor(rutEmpresas.get(contador), t.getIdPersona(), t.getNombreCompleto(), t.getDireccion());
                }
                if (t instanceof Auxiliar){
                    hireAuxiliarForEmpresa(rutEmpresas.get(contador), t.getIdPersona(), t.getNombreCompleto(), t.getDireccion());
                }
                contador++;
            }
            for (Bus b : bus) {
                b.getEmpresa().addBus(b);
            }
        }
    }

