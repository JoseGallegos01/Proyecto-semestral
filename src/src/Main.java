import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    //Vicente Salinas

    SistemaVentaPasajes sv = new SistemaVentaPasajes();
    private Scanner sc = new Scanner(System.in);
    int opcion;
    public static void main(String[] args) {
        Main m = new Main();
        m.menu();
    }
    private void menu(){
        do {
            System.out.println("==================================================");
            System.out.println("...:::Menú principal:::...");
            System.out.println("1) Crear Cliente");
            System.out.println("2) Crear Bus");
            System.out.println("3) Crear pasaje");
            System.out.println("4) Vender pasaje");
            System.out.println("5) Lista de pasajeros");
            System.out.println("6) Lista de ventas");
            System.out.println("7) Lista de viajes");
            System.out.println("8) Consulta viajes disponible por fecha");
            System.out.println("9) Salir");
            System.out.println("--------------------------------------------------");
            System.out.println("..::Ingrese número de opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    createCliente();
                case 2:
                    createBus();
                case 3:

                case 4:
                    vendePasajes();
                case 5:
                    listPasajerosViaje();
                case 6:
                    listVentas();
                case 7:
                    listPasajes();
                case 8:
                case 9: System.out.println("Saliendo...");
                default: System.out.println("Opcion invalida");
            }


        }while (opcion!=9);
    }
    private void createCliente(){
        System.out.println("...::::Crear un nuevo cliente:::...");
        System.out.println("Rut[1] o Pasaporte[2]");
        int opcionRutPasaporte = sc.nextInt();
        IdPersona id;
        if (opcionRutPasaporte==1){
            System.out.println("Ingrese el rut (sin el DV)");
            int rut = sc.nextInt();
            System.out.println("Ingrese el DV del rut");
            char dv = sc.next().charAt(0);
            id = new Rut(rut, dv);
        }
        else if (opcionRutPasaporte==2){
            System.out.println("Ingrese el numero del pasaporte");
            String numero = sc.nextLine();
            System.out.println("Ingrese la nacionalidad del pasaporte");
            String nacionalidad = sc.nextLine();
            id = new Pasaporte(numero, nacionalidad);
        }
        System.out.println("Sr. [1] o Sra. [2]");
        int opcionSrSra = sc.nextInt();
        Tratamiento tratamiento;
        if (opcionSrSra==1)tratamiento = Tratamiento.SR;
        if (opcionSrSra==2)tratamiento = Tratamiento.SRA;
        System.out.println("Nombres: ");
        String nombres = sc.next();
        System.out.println("Apellido paterno: ");
        String apellido_paterno = sc.next();
        System.out.println("Apellido materno: ");
        String apellido_materno = sc.next();
        System.out.println("Telefono movil: ");
        int telefono_movil = sc.nextInt();
        System.out.println("Email: ");
        String email = sc.next();
    }
    private void createBus(){
        System.out.println("...::::Creación de un nuevo bus:::...");
        System.out.println("Patente:");
        String patente = sc.next();
        System.out.println("Marca: ");
        String marca = sc.next();
        System.out.println("Modelo: ");
        String modelo = sc.next();
        System.out.println("Numero de asientos: ");
        int asientos = sc.nextInt();
        if (sv.createBus(patente, marca, modelo, asientos)) {
            System.out.println("...::::Bus guardado exitosamente:::...");
        }
        else  {
            System.out.println("...::::Ya hay un bus con la misma patente registrada::...");
        }
    }
    private void createViaje(){

    }
    private void vendePasajes(){

    }
    private void listPasajerosViaje(){

    }
    private void listVentas(){

    }
    private void listPasajes(){

    }
}