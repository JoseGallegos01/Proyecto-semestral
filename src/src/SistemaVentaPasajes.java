import java.util.ArrayList;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Pasajero> pasajeros = new ArrayList<>();
    ArrayList<Bus> buses = new ArrayList<>();
    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){
        for (Cliente c : clientes){
            if (c.getIdPersona().equals(id)){
                return false;
            }
        }
        clientes.add(new Cliente(id, nom, email));
        for (Cliente c : clientes){
            if (c.getIdPersona().equals(id)){
                c.setTelefono(fono);
            }
        }
        return true;
    }
    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nombreContacto,
                                  String fonoContacto){
        for (Pasajero p : pasajeros){
            if (p.getIdPersona().equals(id)){
                return false;
            }
        }
        pasajeros.add(new Pasajero(id, nom, nombreContacto, fonoContacto));
        for (Pasajero p : pasajeros){
            if (p.getIdPersona().equals(id)){
                p.setFonoContacto(fonoContacto);
                p.setNomContacto(nombreContacto);
            }
        }
        return true;
    }
    public boolean createBus(String patente, String marca, String modelo, int NroAsientos){
        for (Bus b : buses){
            if (b.getPatente().equals(patente)){
                return false;
            }
        }
        buses.add(new Bus(patente, NroAsientos));
        for(Bus b : buses){
            if (b.getMarca().equals(marca)){
                b.setModelo(modelo);
                b.setMarca(marca);
            }
        }
        return true;
    }


    private Cliente findCliente(IdPersona id){
        for (Cliente c : clientes){
            if (c.getIdPersona().equals(id)){
                return c;
            }
        }
        return null;
    }
}
