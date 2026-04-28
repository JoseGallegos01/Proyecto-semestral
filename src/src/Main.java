import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    //Vicente Salinas
    //tengo que ver donde se guarda el numero del asiento
    //Debo ver cada detalle del rut

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
            System.out.println("9) Cargar datos de prueba");
            System.out.println("10) Salir");
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
                    listViajes();
                    break;
                case 8:
                    System.out.println("Metodo incompleto");
                    break;
                case 9: createTestData();
                break;
                case 10: System.out.println("Saliendo...");
                    break;
                default: System.out.println("Opcion invalida");
            }


        }while (opcion!=10);
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
        sc.nextLine();
        if (opcionSrSra==1) tratamiento = Tratamiento.SR;
        if (opcionSrSra==2) tratamiento = Tratamiento.SRA;
        System.out.println("Nombres: ");
        String nombres = sc.nextLine();
        System.out.println("Apellido paterno: ");
        String apellido_paterno = sc.nextLine();
        System.out.println("Apellido materno: ");
        String apellido_materno = sc.nextLine();
        System.out.println("Telefono movil: ");
        String telefono_movil = sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();
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
    //metodo incompleto
    private void vendePasajes(){
        System.out.println("...::::Venta de pasajes::::...");
        System.out.println("...::::Datos de la venta");
        System.out.println("Id documento: ");
        String idDocumento = sc.nextLine();
        System.out.println("Tipo de documento: [1] Boleta [2] Factura");
        int opcionTipoDocumento = sc.nextInt();
        sc.nextLine();
        if (opcionTipoDocumento==1) tipoDocumento = TipoDocumento.BOLETA;
        if (opcionTipoDocumento==2) tipoDocumento = TipoDocumento.FACTURA;
        System.out.println("Fecha de venta [dd/MM/yyyy] :");
        String fecha = sc.nextLine();
        System.out.println("...::::Datos del cliente");
        System.out.println("Rut [1] o Pasaporte [2]");
        int opcionRutPasaporte = sc.nextInt();
        sc.nextLine();
        if (opcionRutPasaporte == 1){
            System.out.println("Rut cliente");
            int rutCliente = sc.nextInt();
            sc.nextLine();
            System.out.println("DV");
            char dv = sc.next().charAt(0);
            id = new Rut(rutCliente, dv);
            sc.nextLine();
        }
        if (opcionRutPasaporte == 2){
            System.out.println("Numero pasaporte:");
            String numeroPasaporte = sc.nextLine();
            System.out.println("Nacionalidad:");
            String nacionalidad = sc.nextLine();
            id = new Pasaporte(numeroPasaporte, nacionalidad);
        }
        if (sv.iniciaVenta(idDocumento, tipoDocumento, (LocalDate.parse(fecha, formatterDate)), id)){
            System.out.println("Cantidad de pasajes a comprar:");
            int cantidadPasajes = sc.nextInt();
            sc.nextLine();
            System.out.println("Fecha del viaje:");
            String fechaViaje = sc.nextLine();
            if (sv.getHorariosDisponibles(LocalDate.parse(fechaViaje, formatterDate)).length!=0){
                System.out.println("...::::Listado de horarios disponibles: ");
                String horarios[][] = sv.getHorariosDisponibles(LocalDate.parse(fechaViaje, formatterDate));
                for (int i = 0; i < horarios.length; i++) {
                    System.out.println((i+1) + " - " + horarios[i][0] + " - " + horarios[i][1] + " - "
                            + horarios[i][2] + " - " + horarios[i][3]);
                }
                System.out.println("Seleccione el viaje en [1 ..." + horarios.length + "] : ");
                int numViaje = sc.nextInt();
                sc.nextLine();
                String patenteBus = horarios[numViaje-1][0];
                String hora = horarios[numViaje-1][1];
                String valor = horarios[numViaje-1][2];
                String asientos = horarios[numViaje-1][3];
                String listaAsientos[][] = sv.listAsientosDelViaje(LocalDate.parse(fechaViaje, formatterDate), LocalTime.parse(hora, formatterTime), patenteBus);
                for (String[] listaAsiento: listaAsientos) {
                    System.out.println(listaAsiento[0] + " - " + listaAsiento[1] +  " - " + listaAsiento[3] + " - " + listaAsiento[2]);
                }
                if (cantidadPasajes>1) System.out.println("Ingrese sus asientos [separe por ,]");
                if (cantidadPasajes==1) System.out.println("Seleccione su asiento");
                String asientosComprados = sc.nextLine();
                String[] listaAsientosComprados = asientosComprados.split(",");
                int asientosCompradosLista[] = new int[listaAsientosComprados.length];
                for (int i = 0; i < asientosCompradosLista.length; i++) {
                    asientosCompradosLista[i] = Integer.parseInt(listaAsientosComprados[i]);
                }
                for (int i = 0 ; i<cantidadPasajes ; i++){
                    if (cantidadPasajes>1) System.out.println("...::::Datos pasajeros " + (i+1));
                    else System.out.println("...::::Datos pasajero");
                    System.out.println("Rut[1] o Pasaporte[2]");
                    int opcionRutPasaportePasajes = sc.nextInt();
                    sc.nextLine();
                    if (opcionRutPasaportePasajes==1){
                        System.out.println("Ingrese el rut (sin el DV)");
                        int rut = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Ingrese el DV del rut");
                        char dv = sc.next().charAt(0);
                        id = new Rut(rut, dv);
                        sc.nextLine();
                    }
                    else if (opcionRutPasaportePasajes==2){
                        System.out.println("Ingrese el numero del pasaporte");
                        String numero = sc.nextLine();
                        System.out.println("Ingrese la nacionalidad del pasaporte");
                        String nacionalidad = sc.nextLine();
                        id = new Pasaporte(numero, nacionalidad);
                    }
                    Nombre nombrePasajero = new Nombre();
                    Nombre contactoNombrePasajero = new Nombre();
                    System.out.println("Ingrese nombres: ");
                    nombrePasajero.setNombres(sc.nextLine());
                    System.out.println("Nombre contacto del pasajero: ");
                    contactoNombrePasajero.setNombres(sc.nextLine());
                    System.out.println("Telefono del pasejero: ");
                    String telefonoPasajero = sc.nextLine();
                    System.out.println("Telefono contacto del pasejero: ");
                    String telefonoContacto = sc.nextLine();
                    sv.createPasajero(id, nombrePasajero, telefonoPasajero, contactoNombrePasajero, telefonoContacto);
                    sv.vendePasaje(idDocumento, LocalDate.parse(fechaViaje, formatterDate), LocalTime.parse(hora, formatterTime),
                            patenteBus, asientosCompradosLista[i], id, tipoDocumento);
                }
            } else {
                System.out.println("...::::No hay horarios para esa fecha::::...");
            }
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
    private void listViajes(){
        String[][] listaViajes = sv.listViajes();
        for (String[] viaje : listaViajes) {
            System.out.println(viaje[0] + " - " + viaje[1] + " -  " + viaje[2] + " - " + viaje[3] +  " - " + viaje[4]);
        }
    }
    private void createTestData(){
        char dvTest = 2;
        IdPersona testId1 = new Rut(22222222, dvTest);
        IdPersona testId2 = new Rut(33333333, dvTest);
        Nombre test1 = new Nombre();
        Nombre test2 = new Nombre();
        test1.setNombres("John");
        test2.setNombres("Jane");
        test1.setTratamiento(Tratamiento.SR);
        test2.setTratamiento(Tratamiento.SRA);
        test1.setApellidoPaterno("Doe");
        test2.setApellidoMaterno("Doe");
        sv.createBus("1111Test", "Test", "Test", 20);
        sv.createViaje(LocalDate.parse("01/01/2026", formatterDate),
                LocalTime.parse("10:30", formatterTime), 300, "1111Test");
        sv.createCliente(testId1, test1, "+56 9 11111111", "JohnDoe@gmail.com");
        sv.createCliente(testId2, test2, "+56 9 11111111", "JaneDoe@gmail.com");
        sv.createPasajero(testId1, test1, "+56 9 11111111",test1, "+56 9 11111111");
        sv.createPasajero(testId2, test2, "+56 9 11111111",test2, "+56 9 11111111");
        sv.iniciaVenta("67", TipoDocumento.FACTURA, LocalDate.parse("01/01/2026", formatterDate), testId1);
        sv.iniciaVenta("68", TipoDocumento.BOLETA, LocalDate.parse("01/01/2026", formatterDate), testId2);
        sv.vendePasaje("67", LocalDate.parse("01/01/2026", formatterDate), LocalTime.parse("10:30"), "1111Test", 1, testId1, TipoDocumento.FACTURA);
        sv.vendePasaje("68", LocalDate.parse("01/01/2026", formatterDate), LocalTime.parse("10:30"), "1111Test", 2, testId1, TipoDocumento.BOLETA);
        System.out.println("...::::Datos de prueba creados:::...");
    }
}