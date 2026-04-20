import java.util.Scanner;

public class Main {
    //Vicente Salinas

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
            System.out.println("8) Consulta viajes disponible por fecha");
            System.out.println("9) Salir");
            System.out.println("--------------------------------------------------");
            System.out.println("..::Ingrese número de opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("...::::Crear un nuevo cliente:::...");
                    System.out.println("Rut[1] o Pasaporte[2]");
                    System.out.println("Sr. [1] o Sra. [2]");
                    System.out.println("Nombres: ");
                    System.out.println("Apellido paterno: ");
                    System.out.println("Apellido materno: ");
                    System.out.println("Telefono movil: ");
                    System.out.println("Email: ");

                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9: System.out.println("Saliendo...");
                default: System.out.println("Opcion invalida");
            }


        }while (opcion!=9);
    }
    private void createCliente(){

    }
    private void createBus(){

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