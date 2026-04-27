import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    //Vicente Salinas
    //tengo que ver donde se guarda el numero del asiento

    SistemaVentaPasajes sv = new SistemaVentaPasajes();
    private Scanner sc = new Scanner(System.in);
    int opcion;
    IdPersona id = null;
    Tratamiento tratamiento = null;
    TipoDocumento tipoDocumento = null;
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
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
            System.out.println("3) Crear viaje");
            System.out.println("4) Vender pasaje");
            System.out.println("5) Lista de pasajeros");
            System.out.println("6) Lista de ventas");
            System.out.println("7) Lista de viajes");
            System.out.println("8) Consulta viajes disponible por fecha");
            System.out.println("9) Salir");
            System.out.println("--------------------------------------------------");
            System.out.println("..::Ingrese número de opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    createCliente();
                    break;
                case 2:
                    createBus();
                    break;
                case 3:
                    createViaje();
                    break;
                case 4:
                    vendePasajes();
                    break;
                case 5:
                    listPasajerosViaje();
                    break;
                case 6:
                    listVentas();
                    break;
                case 7:
                    listPasajes();
                    break;
                case 8:
                case 9: System.out.println("Saliendo...");
                    break;
                default: System.out.println("Opcion invalida");
            }


        }while (opcion!=9);
    }
    private void createCliente(){
        System.out.println("...::::Crear un nuevo cliente:::...");
        System.out.println("Rut[1] o Pasaporte[2]");
        int opcionRutPasaporte = sc.nextInt();
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
        if (opcionSrSra==1) tratamiento = Tratamiento.SR;
        if (opcionSrSra==2) tratamiento = Tratamiento.SRA;
        System.out.println("Nombres: ");
        String nombres = sc.nextLine();
        sc.nextLine();
        System.out.println("Apellido paterno: ");
        String apellido_paterno = sc.nextLine();
        System.out.println("Apellido materno: ");
        String apellido_materno = sc.nextLine();
        sc.nextLine();
        System.out.println("Telefono movil: ");
        String telefono_movil = sc.nextLine();
        sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();
        sc.nextLine();
        Nombre nombre = new Nombre();
        nombre.setNombres(nombres);
        nombre.setApellidoPaterno(apellido_paterno);
        nombre.setApellidoMaterno(apellido_materno);
        nombre.setTratamiento(tratamiento);
        if (sv.createCliente(id, nombre, telefono_movil, email)){
            System.out.println("...::::Cliente guardado exitosamente::::...");
        }
        else {
            System.out.println("...::::Ya existe un cliente con el mismo id::::...");
        }
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
        else {
            System.out.println("...::::Ya hay un bus con la misma patente registrada::...");
        }
    }
    private void createViaje() {
        System.out.println("...::::Creacion de un nuevo viaje::::...");
        System.out.println("Fecha [dd/MM/yyyy] :");
        String fecha = sc.nextLine();
        System.out.println("Hora [hh:mm] :");
        String hora = sc.nextLine();
        System.out.println("Precio: ");
        int precio = sc.nextInt();
        sc.nextLine();
        System.out.println("Patente bus: ");
        String patenteBus = sc.nextLine();
        if (!sv.createViaje(LocalDate.parse(fecha, formatterDate), LocalTime.parse(hora, formatterTime), precio, patenteBus)){
            System.out.println("...::::No se ha podido crear el viaje, no existe el bus o ya hay un viaje registrado con el mismo bus::::...");
        }
        else System.out.println("...::::Viaje guardado exitosamente::::...");
    }
    private void vendePasajes(){
        System.out.println("...::::Venta de pasajes::::...");
        System.out.println("...::::Datos de la venta");
        System.out.println("Id documento: ");
        String idDocumento = sc.nextLine();
        System.out.println("Tipo de documento: [1] Boleta [2] Factura");
        int opcionTipoDocumento = sc.nextInt();
        if (opcionTipoDocumento==1) tipoDocumento = TipoDocumento.BOLETA;
        if (opcionTipoDocumento==2) tipoDocumento = TipoDocumento.FACTURA;
        System.out.println("Fecha de venta [dd/MM/yyyy] :");
        String fecha = sc.nextLine();
        System.out.println("...::::Datos del cliente");
        System.out.println("Rut [1] o Pasaporte [2]");
        int opcionRutPasaporte = sc.nextInt();
        if (opcionRutPasaporte == 1){
            System.out.println("Rut cliente");
            int rutCliente = sc.nextInt();
            System.out.println("DV");
            char dv = sc.next().charAt(0);
            id = new Rut(rutCliente, dv);
        }
        if (opcionRutPasaporte == 2){
            System.out.println("Numero pasaporte:");
            String numeroPasaporte = sc.nextLine();
            System.out.println("Nacionalidad:");
            String nacionalidad = sc.nextLine();
            id = new Pasaporte(numeroPasaporte, nacionalidad);
        }
        System.out.println("Nombre cliente: ");
        String nombreCliente = sc.nextLine();
        if (sv.iniciaVenta(idDocumento, tipoDocumento, (LocalDate.parse(fecha, formatterDate)), id)){
            System.out.println("Cantidad de pasajes a comprar:");
            int cantidadPasajes = sc.nextInt();
            sc.nextLine();
            System.out.println("Fecha del viaje:");
            String fechaViaje = sc.nextLine();
            sv.getHorariosDisponibles(LocalDate.parse(fechaViaje));
        }
        else {
            System.out.println("...::::Cliente no existe o la venta ya existe::::...");
        }

    }
    //tengo que ver como encontrar el asiento de cada pasajero
    private void listPasajerosViaje(){
        System.out.println("...::::Listado de pasajeros de un viaje:::");
        System.out.println("Fecha del viaje [dd/MM/yyy] :");
        String fecha = sc.nextLine();
        sc.nextLine();
        System.out.println("Hora [hh:mm] :");
        String hora = sc.nextLine();
        System.out.println("Patente del bus :");
        String patenteBus = sc.nextLine();
        if (sv.listPasajeros(LocalDate.parse(fecha, formatterDate), LocalTime.parse(hora, formatterTime),
                patenteBus).length == 0 ){
            System.out.println("...::::No se ha encontrado una lista de pasajeros para el viaje:::...");
        }
        else  {
            System.out.println("Asiento | RUT/PASS | Pasajero | Contacto | Telefono contacto");
            String[][] listadoPasajerosViaje = sv.listPasajeros(LocalDate.parse(fecha, formatterDate), LocalTime.parse(hora, formatterTime), patenteBus);
            for (String[] pasajero : listadoPasajerosViaje) {
                System.out.println(pasajero[0] + " - " + pasajero[1] + " - " + pasajero[2] + " " + pasajero[3]
                + " - " + pasajero[4]);
            }
        }
    }
    private void listVentas(){
        String[][] listaVentas = sv.listVentas();
        for (String[] venta : listaVentas) {
            System.out.println(venta[0] + " - " + venta[1] + " - " + venta[2] + " - " + venta[3] + " - " + venta[4]
            + " - " + venta[5] + " - " + venta[6]);
        }
    }
    private void listPasajes(){

    }
}