package Vista;

import Controlador.SistemaVentaPasajes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import modelo.*;
import utilidades.*;
public class UISVP {
    //Vicente Salinas

    SistemaVentaPasajes sv = new SistemaVentaPasajes();
    private Scanner sc = new Scanner(System.in);
    int opcion;
    IdPersona id = null;
    Tratamiento tratamiento = null;
    TipoDocumento tipoDocumento = null;
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

    private static UISVP instance = null;
    public static UISVP getInstance(){
        if(instance == null){
            instance = new UISVP();
        }
        return instance;
    }

    public void menu(){
        do {
            System.out.println("==================================================");
            System.out.println("...:::Menú principal:::...");
            System.out.println("1) Crear empresa");
            System.out.println("2) Contratar tripulante");
            System.out.println("3) Crear terminal");
            System.out.println("4) Crear Cliente");
            System.out.println("5) Crear Bus");
            System.out.println("6) Crear viaje");
            System.out.println("7) Vender pasaje");
            System.out.println("8) Lista de pasajeros");
            System.out.println("9) Lista de ventas");
            System.out.println("10) Lista de viajes");
            System.out.println("11) Listar empresas");
            System.out.println("12)Listar llegadas/salidas de terminal");
            System.out.println("13)Listar ventas de empresa");
            System.out.println("14) Cargar datos de prueba");
            System.out.println("15) Salir");
            System.out.println("--------------------------------------------------");
            System.out.println("..::Ingrese número de opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 4:
                    createCliente();
                    break;
                case 5:
                    createBus();
                    break;
                case 6:
                    createViaje();
                    break;
                case 7:
                    vendePasajes();
                    break;
                case 8:
                    listPasajerosViaje();
                    break;
                case 9:
                    listVentas();
                    break;
                case 10:
                    listViajes();
                    break;
                case 11:
                    listarEmpresas();
                    break;
                case 12:
                    listarLLegadasSalidasTerminal();
                    break;
                case 13:
                    listarVentasEmpresa();
                    break;

                case 14: createTestData();
                break;
                case 15: System.out.println("Saliendo...");
                    break;
                default: System.out.println("Opcion invalida");
            }


        }while (opcion!=10);
    }

    private void createEmpresa(){
        System.out.println("...::::Creando una nueva empresa::::...");
        System.out.println("R.U.T");
        System.out.println("Nombre:");
        System.out.println("url:");
    }

    private void contratarTripulante(){
        System.out.println("...::::Contratando un nuevo tripulante::::...");
        System.out.println("::::Datos de la empresa:");
        System.out.println("R.U.T");
        //debe ir dentro de un condicional lo siguiente supongo
        //if ()
    }

    private void createCliente(){
        System.out.println("...::::Crear un nuevo cliente:::...");
        System.out.println("utilidades.Rut[1] o utilidades.Pasaporte[2]");
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
            System.out.println("...::::modelo.Cliente guardado exitosamente::::...");
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
            System.out.println("...::::modelo.Bus guardado exitosamente:::...");
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
        else System.out.println("...::::modelo.Viaje guardado exitosamente::::...");
    }
    //metodo incompleto
    private void vendePasajes() {
        System.out.println("...::::Venta de pasajes::::...");


        System.out.println("Id documento: ");
        String idDocumento = sc.nextLine();
        System.out.println("Tipo de documento: [1] Boleta [2] Factura");
        int opcionTipoDocumento = sc.nextInt();
        sc.nextLine();
        TipoDocumento tipoDocumento = (opcionTipoDocumento == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

        System.out.println("Fecha de venta [dd/MM/yyyy]: ");
        String fechaVentaStr = sc.nextLine();
        LocalDate fechaVenta = LocalDate.parse(fechaVentaStr, formatterDate);


        System.out.println("Rut [1] o Pasaporte [2]");
        int opcionCliente = sc.nextInt();
        sc.nextLine();
        IdPersona idCliente = leerIdPersona(opcionCliente);

        if (!sv.iniciaVenta(idDocumento, tipoDocumento, fechaVenta, idCliente)) {
            System.out.println("...::::Cliente no existe o la venta ya existe::::...");
            return;
        }

        // NUEVOS DATOS: origen, destino, cantidad
        System.out.println("Fecha del viaje [dd/MM/yyyy]:");
        String fechaViajeStr = sc.nextLine();
        LocalDate fechaViaje = LocalDate.parse(fechaViajeStr, formatterDate);

        System.out.println("Comuna de origen:");
        String comunaOrigen = sc.nextLine();

        System.out.println("Comuna de destino:");
        String comunaDestino = sc.nextLine();

        System.out.println("Número de pasajes:");
        int cantidadPasajes = sc.nextInt();
        sc.nextLine();

        String[][] horarios = sv.getHorariosDisponibles(fechaViaje, comunaOrigen, comunaDestino, cantidadPasajes);
        if (horarios.length == 0) {
            System.out.println("...::::No hay viajes disponibles para esa fecha, origen y destino::::...");
            return;
        }


        System.out.println("...::::Listado de horarios disponibles: ");
        System.out.printf("%-3s %-10s %-8s %-8s %-10s%n", "", "BUS", "SALIDA", "VALOR", "ASIENTOS");
        for (int i = 0; i < horarios.length; i++) {
            System.out.println((i+1) + " | " + horarios[i][0] + " | " + horarios[i][1] + " | "
                    + horarios[i][2] + " | " + horarios[i][3]);
        }
        System.out.println("Seleccione el viaje [1..." + horarios.length + "]: ");
        int numViaje = sc.nextInt();
        sc.nextLine();
        String patenteBus = horarios[numViaje-1][0];
        String horaStr = horarios[numViaje-1][1];
        LocalTime hora = LocalTime.parse(horaStr, formatterTime);

        // Mostrar asientos
        String[] asientos = sv.listAsientosDelViaje(fechaViaje, hora, patenteBus);
        System.out.println("Asientos disponibles (número) u ocupados (*):");
        for (int i = 0; i < asientos.length; i++) {
            System.out.print(asientos[i] + " ");
            if ((i+1) % 10 == 0) System.out.println();
        }
        System.out.println();

        System.out.println("Seleccione " + cantidadPasajes + " asiento(s) (separados por coma):");
        String asientosSeleccionados = sc.nextLine();
        String[] listaAsientos = asientosSeleccionados.split(",");


        for (int i = 0; i < cantidadPasajes; i++) {
            System.out.println("...::::Datos pasajero " + (i+1) + "::::...");
            System.out.println("Rut[1] o Pasaporte[2]");
            int opcionPasajero = sc.nextInt();
            sc.nextLine();
            IdPersona idPasajero = leerIdPersona(opcionPasajero);

            System.out.println("Nombres: ");
            String nombres = sc.nextLine();
            System.out.println("Nombre contacto: ");
            String nombreContactoStr = sc.nextLine();
            System.out.println("Teléfono pasajero: ");
            String telefonoPasajero = sc.nextLine();
            System.out.println("Teléfono contacto: ");
            String telefonoContacto = sc.nextLine();

            Nombre nombrePasajero = new Nombre();
            nombrePasajero.setNombres(nombres);
            Nombre nombreContacto = new Nombre();
            nombreContacto.setNombres(nombreContactoStr);

            sv.createPasajero(idPasajero, nombrePasajero, telefonoPasajero, nombreContacto, telefonoContacto);

            int asiento = Integer.parseInt(listaAsientos[i].trim());
            sv.vendePasaje(idDocumento, fechaViaje, hora, patenteBus, asiento, idPasajero, tipoDocumento);
        }

        int monto = sv.getMontoVenta(idDocumento, tipoDocumento);
        System.out.println("Monto total: $" + monto);
        System.out.println("Pago: Efectivo[1] o Tarjeta[2]");
        int opcionPago = sc.nextInt();
        sc.nextLine();

        try {
            if (opcionPago == 1) {
                sv.pagaVenta(idDocumento, tipoDocumento);
            } else {
                System.out.println("Número de tarjeta:");
                String nroTarjeta = sc.nextLine();
                sv.pagaVenta(idDocumento, tipoDocumento, nroTarjeta);
            }
            System.out.println("...::::Venta realizada exitosamente::::...");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error al pagar: " + e.getMessage());
        }
    }

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
            System.out.printf("| %-6s | %-15s | %-30s | %-25s |%n",
                    "ASIENTO", "RUT/PASS", "PASAJERO", "TELEFONO CONTACTO");
            String[][] listadoPasajerosViaje = sv.listPasajeros(LocalDate.parse(fecha, formatterDate), LocalTime.parse(hora, formatterTime), patenteBus);
            for (String[] pasajero : listadoPasajerosViaje) {
                System.out.println(pasajero[0] + " | " + pasajero[1] + " | " + pasajero[2] + " | " + pasajero[3]);
            }
        }
    }
    private void listVentas(){
        String[][] listaVentas = sv.listVentas();
        System.out.printf("| %-10s | %-10s | %-12s | %-15s | %-30s | %-12s | %-12s |\n",
                "ID DOC", "TIPO DOC", "FECHA", "RUT", "CLIENTE", "CANT", "TOTAL");
        for (String[] venta : listaVentas) {
            System.out.printf("| %-10s | %-10s | %-12s | %-15s | %-30s | %-12s | %-12s |\n",
                    venta[0], venta[1], venta[2], venta[3], venta[4], venta[5], venta[6]);
        }
    }
    private void listViajes(){
        String[][] listaViajes = sv.listViajes();
        System.out.printf("| %s | %s | %s | %s | %s |\n",
                "FECHA", "HORA", "PRECIO", "DISPONIBILIDAD", "PATENTE");
        for (String[] viaje : listaViajes) {
            System.out.printf("| %s | %s | %s | %s | %s |\n",
                    viaje[0], viaje[1], viaje[2], viaje[3], viaje[4]);
        }
    }
    private void listarEmpresas(){
        String [][]empresas=sv.listEmpresas();
        if (empresas.length==0){
            System.out.println("No hay empresas registradas");
            return;
        }
        System.out.printf("%-15s %-30s %-40s%n", "RUT", "NOMBRE", "URL");
        for (String[] e : empresas) {
            System.out.printf("%-15s %-30s %-40s%n", e[0], e[1], e[2]);
        }
    }
    private void listarLLegadasSalidasTerminal(){
        System.out.print("Nombre del terminal:" );
        String nombre=sc.nextLine();
        System.out.print("Fecha [dd/MM/yyyy]:");
        String fechaStr=sc.nextLine();
        LocalDate fecha =LocalDate.parse(fechaStr,formatterDate);
        try {
            String [][]listado=sv.listLlegadasSalidasTerminal(nombre,fecha);
            if (listado.length==0){
                System.out.println("No hay llegadas/salidas para ese terminal en esas fecha");
                return ;
            }
            System.out.printf("%-10s %-8s %-15s %-20s %-12s%n", "TIPO", "HORA", "PATENTE", "EMPRESA", "PASAJEROS");
            for (String[] item : listado) {
                System.out.printf("%-10s %-8s %-15s %-20s %-12s%n", item[0], item[1], item[2], item[3], item[4]);
            }
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void listarVentasEmpresa(){
        System.out.print("RUT de la empresa:");
        String rut =sc.nextLine();
        try {
            String [][]ventass =sv.listVentasEmpresa(rut);
            if (ventas.length==0){
                System.out.println("La empresa no tiene ventas registradas");
                return;
            }
            System.out.printf("%-12s %-10s %-15s %-15s%n", "FECHA", "TIPO", "MONTO", "TIPO PAGO");
            for (String[] v : ventas) {
                System.out.printf("%-12s %-10s %-15s %-15s%n", v[0], v[1], v[2], v[3]);
            }
             } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }




    public void consultarViajesPorFecha() {
        System.out.println("==============================");
        System.out.println("Consulta viajes por fecha ");
        System.out.println("==============================");
        System.out.print("Ingrese fecha (dd/MM/yyyy):");

        String fecha = sc.nextLine();

        String[][] viajes = sv.getHorariosDisponibles(LocalDate.parse(fecha, formatterDate));

        if (viajes.length == 0) {
            System.out.println("No hay viajes disponibles para la fecha solicitada");
            return;

        }
        System.out.println("Se encontraron " + viajes.length + " viajes");

        System.out.println("PATENTE  HORA  PRECIO  DISPONIBLES");
        System.out.println("----------------------------------------");


        for (int i = 0; i < viajes.length; i++) {
            System.out.println(viajes[i][0] + " " +
                    viajes[i][0] + " " + viajes[i][1] + " " + viajes[i][2] + " " + viajes[i][3]);
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