import java.util.ArrayList;

public class SistemaVentaPasajes {
    ArrayList<Cliente> clientes = new ArrayList<>();
    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){
        for (Persona p : clientes){
            if (p.getIdPersona().equals(id)){
                return false;
            }
        }
        clientes.add(new Cliente(id, nom, email));
        return true;
    }
}
