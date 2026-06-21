package Controlador;

import Modelo.Conductor;
import Modelo.Empresa;
import Modelo.Viaje;
import Excepciones.SVPException;
import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import utilidades.Rut;

import java.util.ArrayList;
import java.util.Optional;

public class ControladorEmpresas {
    private ArrayList<Empresa> empresas;
    private ArrayList<Modelo.Bus> bus;
    private ArrayList<Modelo.Terminal> terminales;


    public ControladorEmpresas() {
        this.empresas = new ArrayList<>();
        this.bus = new ArrayList<>();
        this.terminales = new ArrayList<>();
    }

    public static ControladorEmpresas getInstancia() {
        ControladorEmpresas instancia = null;
        if (instancia == null) {
            instancia = new ControladorEmpresas();
        }

        return instancia;
    }

    public void createEmpresa(Rut rut, String nombre, String url) {
        if(findEmpresa(rut).isPresent()){
            throw new SVPException("Ya existe empresa con el rut indicado");
        }

        Empresa empresa = new Empresa(rut, nombre, url);
        this.empresas.add(empresa);

    }

    public void createBus(String patente, String marca, String modelo, int nroAsiento, Rut rutEmp) {
        Optional<Empresa> empresaOptional = findEmpresa(rutEmp);

        if(empresaOptional.isEmpty()){
            throw new SVPException("No existe empresa con el rut indicado");

        }

        if(findBus(patente).isPresent()){
            throw new SVPException("Ya existe bus con la patente indicada");
        }

        Modelo.Bus bus = new Modelo.Bus(patente, marca, modelo, nroAsiento, empresaOptional.get());
        this.bus.add(bus);
    }

    public void createTerminal(String nombre, Direccion direccion) {
        if (findTerminal(nombre).isPresent()) {
            throw new SVPException("Ya existe terminal con el nombre indicado");
        }
        if(findTerminalPorComuna(direccion.getComuna()).isPresent()){
            throw new SVPException("Ya existe terminal en la comuna indicada");
        }

        Modelo.Terminal terminal = new Modelo.Terminal(nombre, direccion);
        this.terminales.add(terminal);
    }

    public void hireConductor(Rut rutEmp, IdPersona idPersona, Nombre nombre, Direccion direccion) {
        Optional<Empresa> empresaOptional = findEmpresa(rutEmp);
        if(empresaOptional.isEmpty()) {
         throw new SVPException("No existe el empresa con el rut ingresado");
        }

        boolean contratado = empresaOptional.get().addConductor(idPersona, nombre, direccion);
        if(contratado){
            throw new SVPException("Ya está contratado conductor con el id indicado en la empresa señalada");
        }

    }

    public void hireAuxiliarForEmpresa(Rut rutEmp, IdPersona idPersona, Nombre nombre, Direccion direccion) {
        Optional<Empresa> empresaOptional = findEmpresa(rutEmp);
        if(empresaOptional.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }
        boolean contratado = empresaOptional.get().addAuxiliar(idPersona, nombre, direccion);
        if (!contratado){
            throw new SVPException("Ya está contratado auxiliar con el id indicado en la empresa señalada");
        }
    }

    public String[][] listEmpresas() {
        if (empresas.size() == 0) {
            return new String[0][0];
        }
        String[][] lista = new String[empresas.size()][3];

        for (int i = 0; i < empresas.size(); i++) {
            Empresa empresa = empresas.get(i);

            lista[i][0] = empresa.getRut().toString();
            lista[i][1] = empresa.getNombre();
            lista[i][2] = empresa.getUrl();

        }

        return lista;
    }

    public String[][] listLlegadaSalidaTerminal() {
        Optional<Modelo.Terminal> terminalOptional = findTerminal(nombreTerminal);

        if (terminalOptional.isEmpty()) {
            throw new SVPException("No existe terminal con el nombre indicado");
        }
        Modelo.Terminal terminal = terminalOptional.get();
        ArrayList<String[]> lista = new ArrayList<>();

        Viaje[] salidas = terminal.getSalidas();
        for (Viaje salida : salidas) {
            if (salida.getFechaHoraTermino().toLocalDate().equals(fecha)) {
                String[] fila = new String[6];
                fila[0] = "Salida";
                fila[1] = salida.getFechaHoraTermino().toLocalTime().toString();
                fila[2] = salida.getBus().getPatente();
                fila[3] = salida.getTerminalLlegada().getDireccion().getComuna();
                fila[4] = salida.getBus().getEmpresa().getNombre();
                fila[5] = String.valueOf(salida.getListaPasajeros().length);

                lista.add(fila);
            }

        }

        Viaje[] llegadas = terminal.getLlegadas();
        for (Viaje llegada : llegadas) {
            if(llegada.getFechaHoraTermino().toLocalDate().equals(fecha)){
                String[] fila = new String[6];
                fila[0] = "Llegada";
                fila[1] = llegada.getFechaHoraTermino().toLocalTime().toString();
                fila[2] = llegada.getBus().getPatente();
                fila[3] = llegada.getTerminalSalida().getDireccion().getComuna();
                fila[4] = llegada.getBus().getEmpresa().getNombre();
                fila[5] = String.valueOf(llegada.getListaPasajeros().length);
                lista.add(fila);
            }
        }

        String[][] datos = new String[lista.size()][6];
        for (int i = 0; i < lista.size(); i++) {
            datos[i] = lista.get(i);
        }
        return datos;
    }

    public String[][] listVentasEmpresa(Rut rut) {
        Optional<Empresa> empresaOptional = findEmpresa(rut);
        if(empresaOptional.isEmpty()) {
            throw new SVPException("No existe empresa con el rut indicado");
        }

        return new String[0][0];
    }

    protected Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa emp : empresas) {
            if (emp.getRut().equals(rut)) {
                return Optional.of(emp);
            }
        }
        return Optional.empty();
    }

    protected Optional<Modelo.Terminal> findTerminal(String nombre) {
        for (Modelo.Terminal term : terminales) {
            if (term.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(term);
            }
        }
        return Optional.empty();
    }

    protected Optional<Modelo.Terminal> findTerminalPorComuna(String comuna) {
        for (Modelo.Terminal term : terminales) {
            if (term.getDireccion().getComuna().equalsIgnoreCase(comuna)) {
                return Optional.of(term);
            }
        }
        return Optional.empty();
    }

    protected Optional<Modelo.Bus> findBus(String patente) {
        for (Modelo.Bus b : bus) {
            if (b.getPatente().equalsIgnoreCase(patente)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    protected Optional<Conductor> findConductor(IdPersona id, Rut rutEmpresa) {
        Optional<Empresa> emp = findEmpresa(rutEmpresa);
        if (emp.isPresent()) {
            for (Modelo.Tripulante t : emp.get().getTripulantes()) {
                if (t instanceof Conductor && t.getIdPersona().equals(id)) {
                    return Optional.of((Conductor) t);
                }
            }
        }
        return Optional.empty();
    }

    protected Optional<Modelo.Auxiliar> findAuxiliar(IdPersona id, Rut rutEmpresa) {
        Optional<Empresa> emp = findEmpresa(rutEmpresa);
        if (emp.isPresent()) {
            for (Modelo.Tripulante t : emp.get().getTripulantes()) {
                if (t instanceof Modelo.Auxiliar && t.getIdPersona().equals(id)) {
                    return Optional.of((Modelo.Auxiliar) t);
                }
            }
        }
        return Optional.empty();
    }

}

