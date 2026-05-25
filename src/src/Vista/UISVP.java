package Vista;

import Controlador.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import Excepciones.SistemaVentaPasajesException;
import Modelo.*;
import utilidades.*;
import Controlador.*;


public class UISVP {
    //Vicente Salinas

    private Scanner sc = new Scanner(System.in);
    int opcion;
    IdPersona id = null;
    Tratamiento tratamiento = null;
    TipoDocumento tipoDocumento = null;
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static UISVP instance = null;
    public static UISVP getInstance(){
        if(instance == null){
            instance = new UISVP();
        }
        return instance;
    }
    SistemaVentaPasajes sv = SistemaVentaPasajes.getInstance();
    ControladorEmpresas ce = ControladorEmpresas.getInstance();
    public void menu() {
        try {
            do {
                System.out.println("==================================================");
                System.out.println("...:::Menú principal:::...");
                System.out.println("1) Crear empresa");
                System.out.println("2) Contratar tripulante");
                System.out.println("3) Crear terminal");
                System.out.println("4) Crear Cliente");
                System.out.println("5) Crear Bus");
                System.out.println("6) Crear viaje");
                System.out.println("7) Vender pasajes");
                System.out.println("8) Listar ventas");
                System.out.println("9) Listar viajes");
                System.out.println("10) Listar pasajeros de viaje");
                System.out.println("11) Listar empresas");
                System.out.println("12) Listar llegadas/salidas del terminal");
                System.out.println("13) Listar ventas de empresa");
                System.out.println("14) Cargar datos de prueba");
                System.out.println("15) salir");
                //creo que la opcion de viajes sera borrada pues no esta en la pauta del avance dos
                //System.out.println("X) Consulta viajes disponible por fecha");
                System.out.println("--------------------------------------------------");
                System.out.println("..::Ingrese número de opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        createEmpresa();
                        break;
                    case 2:
                        contratarTripulante();
                        break;
                    case 3:
                        crearTerminal();
                        break;
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
                        listVentas();
                        break;
                    case 9:
                        listViajes();
                        break;
                    case 10:
                        listPasajerosViaje();
                        break;
                    //case 8:
                    // consultarViajesPorFecha();
                    // break;
                    case 11:
                        listEmpresas();
                        break;
                    case 12:
                        listLlegadasSalidasTerminal();
                        break;
                    case 13:
                        listVentasEmpresas();
                        break;
                    case 14:
                        createTestData();
                        break;
                    case 15:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }


            } while (opcion != 15);
        }catch (SistemaVentaPasajesException e){
            throw new SistemaVentaPasajesException(e.getMessage());
        }
    }

    private void createEmpresa(){
        System.out.println("...::::Creando una nueva empresa::::...");
        System.out.println("R.U.T");
        System.out.println("(XX.XXX.XXX-X)");
        String rutEmpresa =  sc.nextLine();
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("url:");
        String url =  sc.nextLine();
        try {
            ce.createEmpresa(registrarRut(rutEmpresa), nombre, url);
        }catch (SistemaVentaPasajesException e){
            System.out.println(e.getMessage());
        }
    }

    private void contratarTripulante(){
        System.out.println("...::::Contratando un nuevo tripulante::::...");
        System.out.println("::::Datos de la empresa:");
        System.out.println("R.U.T");
        System.out.println("(XX.XXX.XXX-X)");
        String rutEmpresa =  sc.nextLine();
        try {
            System.out.println("::::Datos tripulante:");
            System.out.println("Auxiliar[1] o conductor[2]");
            int opcionAuxOCond = sc.nextInt();
            sc.nextLine();
            System.out.println("Rut[1] o Pasaporte[2]");
            int opcionRutPasaporte = sc.nextInt();
            sc.nextLine();
            if(opcionRutPasaporte == 1){
                System.out.println("R.U.T:");
                String rutTripulante = sc.nextLine();
                id = registrarRut(rutTripulante);
            }
            if(opcionRutPasaporte == 2){
                System.out.println("Ingrese el numero del pasaporte");
                String numero = sc.nextLine();
                System.out.println("Ingrese la nacionalidad del pasaporte");
                String nacionalidad = sc.nextLine();
                id = new Pasaporte(numero, nacionalidad);
            }
            System.out.println("Sr[1] o Sra[2]");
            int tipoTratamiento = sc.nextInt();
            sc.nextLine();
            if (tipoTratamiento == 1) tratamiento = Tratamiento.SR;
            if (tipoTratamiento == 2) tratamiento = Tratamiento.SRA;
            System.out.println("Nombres:");
            String nombres = sc.nextLine();
            System.out.println("Apellido Paterno:");
            String apellidoPaterno = sc.nextLine();
            System.out.println("Apellido Materno:");
            String apellidoMaterno = sc.nextLine();
            System.out.println("Calle:");
            String calle = sc.nextLine();
            System.out.println("Numero:");
            int numero = sc.nextInt();
            sc.nextLine();
            System.out.println("Comuna:");
            String comuna = sc.nextLine();
            Nombre nombre = new Nombre();
            nombre.setNombres(nombres);
            nombre.setApellidoPaterno(apellidoPaterno);
            nombre.setApellidoMaterno(apellidoMaterno);
            nombre.setTratamiento(tratamiento);
            Direccion direccion = new Direccion(calle, numero, comuna);
            try {
                if (opcionAuxOCond == 1) ce.hireAuxiliarForEmpresa(registrarRut(rutEmpresa), id, nombre, direccion);
                if (opcionAuxOCond == 2) ce.hireConductor(registrarRut(rutEmpresa), id, nombre, direccion);
            }catch (SistemaVentaPasajesException e){
                throw new SistemaVentaPasajesException(e.getMessage());
            }
        }catch (SistemaVentaPasajesException e){
            System.out.println(e.getMessage());
        }
    }

    private void crearTerminal(){
        System.out.println("...::::Creando un nuevo terminal::::...");
        System.out.println("Nombre:");
        String nombre = sc.nextLine();
        System.out.println("Calle");
        String calle = sc.nextLine();
        System.out.println("Numero:");
        int numero = sc.nextInt();
        sc.nextLine();
        System.out.println("Comuna");
        String comuna = sc.nextLine();
        Direccion direccion = new Direccion(calle, numero, comuna);
        try {
            ce.createTerminal(nombre, direccion);
        }catch (SistemaVentaPasajesException e){
            throw new SistemaVentaPasajesException(e.getMessage());
        }
    }

    private void createCliente(){
        System.out.println("...::::Crear un nuevo cliente:::...");
        System.out.println("Rut[1] o Pasaporte[2]");
        int opcionRutPasaporte = sc.nextInt();
        sc.nextLine();
        if (opcionRutPasaporte==1){
            System.out.println("Ingrese el rut");
            String rut = sc.nextLine();
            id = registrarRut(rut);
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
        try{
            sv.createCliente(id, nombre, telefono_movil, email);
            System.out.println("...::::Cliente guardado exitosamente::::...");
        }catch (SistemaVentaPasajesException e){
            throw new SistemaVentaPasajesException("...::::Ya existe un cliente con el mismo id::::...");
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
        System.out.println("Datos de la empresa:");
        System.out.println("R.U.T: ");
        String rutEmpresa = sc.next();
        try{
            ce.createBus(patente, marca, modelo, asientos, registrarRut(rutEmpresa));
            System.out.println("...::::modelo.Bus guardado exitosamente:::...");
        } catch (SistemaVentaPasajesException e){
            throw new SistemaVentaPasajesException("...::::Ya hay un bus con la misma patente registrada::...");
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
        System.out.println("Duracion: (Minutos)");
        int duracion = sc.nextInt();
        sc.nextLine();
        System.out.println("Patente bus: ");
        String patenteBus = sc.nextLine();
        System.out.println("Nro conductores: [1 o 2] ");
        int nroConductores = sc.nextInt();
        sc.nextLine();
        IdPersona[] tripulantes = new IdPersona[nroConductores + 1];
        System.out.println("...::::Id auxiliar: ");
        System.out.println("R.U.T[1] o Pasaporte[2]");
        int idAuxiliar = sc.nextInt();
        if (idAuxiliar==1){
            System.out.println("R.U.T");
            String rutAuxiliar =  sc.nextLine();
            tripulantes[0] = registrarRut(rutAuxiliar);
        }
        if (idAuxiliar==2){
            System.out.println("Numero del pasaporte: ");
            int numeroPasaporte = sc.nextInt();
            sc.nextLine();
            System.out.println("Nacionalidad del pasaporte: ");
            String nacionalidadPasaporte = sc.nextLine();
            tripulantes[0] = registrarPasaporte(String.valueOf(numeroPasaporte), nacionalidadPasaporte);
        }
        System.out.println("::Id conductor::");
        for (int i = 0; i < nroConductores; i++){
            System.out.println("R.U.T[1] o Pasaporte[2]");
            int idConductor = sc.nextInt();
            if (idConductor==1){
                System.out.println("R.U.T");
                String rutConductor =  sc.nextLine();
                tripulantes[i+1] = registrarRut(rutConductor);
            }
            if (idConductor==2){
                System.out.println("Numero del pasaporte: ");
                int numeroPasaporteConductor = sc.nextInt();
                sc.nextLine();
                System.out.println("Nacionalidad del pasaporte: ");
                String nacionalidadPasaporteConductor = sc.nextLine();
                tripulantes[i+1] = registrarPasaporte(String.valueOf(numeroPasaporteConductor), nacionalidadPasaporteConductor);
            }
        }
        System.out.println("Comuna salida:");
        String comunaSalida = sc.nextLine();
        System.out.println("Comuna llegada:");
        String comunaLlegada = sc.nextLine();
        String[] comunas = {comunaSalida, comunaLlegada};
        try {
            sv.createViaje(LocalDate.parse(fecha, formatterDate), LocalTime.parse(hora, formatterTime), precio, duracion, patenteBus, tripulantes, comunas);
            System.out.println("...::::Viaje guardado exitosamente::::...");
        }catch (SistemaVentaPasajesException e){
            throw new SistemaVentaPasajesException("...::::No se ha podido crear el viaje, no existe el bus o ya hay un viaje registrado con el mismo bus::::...");
        }
    }

    private void vendePasajes(){
        System.out.println("...::::modelo.Venta de pasajes::::...");
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
        System.out.println("Origen (comuna)");
        String origen = sc.nextLine();
        System.out.println("Destino (comuna)");
        String destino = sc.nextLine();
        System.out.println("...::::Datos del cliente");
        System.out.println("utilidades.Rut [1] o utilidades.Pasaporte [2]");
        int opcionRutPasaporte = sc.nextInt();
        sc.nextLine();
        if (opcionRutPasaporte == 1){
            System.out.println("Rut cliente");
            String rutCliente = sc.nextLine();
            id = registrarRut(rutCliente);
        }
        if (opcionRutPasaporte == 2){
            System.out.println("Numero pasaporte:");
            String numeroPasaporte = sc.nextLine();
            System.out.println("Nacionalidad:");
            String nacionalidad = sc.nextLine();
            id = new Pasaporte(numeroPasaporte, nacionalidad);
        }
        try{
            sv.iniciaVenta(idDocumento, tipoDocumento, (LocalDate.parse(fecha, formatterDate)), id);
            System.out.println("Cantidad de pasajes a comprar:");
            int cantidadPasajes = sc.nextInt();
            sc.nextLine();
            System.out.println("Fecha del viaje:");
            String fechaViaje = sc.nextLine();
            if (sv.getHorariosDisponibles(LocalDate.parse(fechaViaje, formatterDate)).length!=0){
                System.out.println("...::::Listado de horarios disponibles: ");
                String horarios[][] = sv.getHorariosDisponibles(LocalDate.parse(fechaViaje, formatterDate));
                System.out.printf("%-3s %-10s %-8s %-8s %-10s%n",
                        "", "BUS", "SALIDA", "VALOR", "ASIENTOS");
                for (int i = 0; i < horarios.length; i++) {
                    System.out.println((i+1) + " | " + horarios[i][0] + " | " + horarios[i][1] + " | "
                            + horarios[i][2] + " | " + horarios[i][3]);
                }
                System.out.println("Seleccione el viaje en [1..." + horarios.length + "] : ");
                int numViaje = sc.nextInt();
                sc.nextLine();
                String patenteBus = horarios[numViaje-1][0];
                String hora = horarios[numViaje-1][1];
                String valor = horarios[numViaje-1][2];
                String asientos = horarios[numViaje-1][3];
                String listaAsientos[][] = sv.listAsientosDelViaje(LocalDate.parse(fechaViaje, formatterDate), LocalTime.parse(hora, formatterTime), patenteBus);
                for (String[] listaAsiento: listaAsientos) {
                    System.out.println(listaAsiento[0] + " | " + listaAsiento[1] +  " | " + listaAsiento[3] + " | " + listaAsiento[2]);
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
                        System.out.println("Ingrese el rut (XX.XXX.XXX-X)");
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
                    System.out.println("utilidades.Nombre contacto del pasajero: ");
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
        catch (SistemaVentaPasajesException e){
            throw new SistemaVentaPasajesException("...::::Cliente no existe o la venta ya existe::::...");
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

    private void listEmpresas(){
        String[][] listaEmpresas = ce.listEmpresas();
        System.out.printf("| %-12s | %-20s | %-30s | %-15s | %-12s | %-12s |%n",
                "RUT EMPRESA",
                "NOMBRE",
                "URL",
                "NRO. TRIPULANTES",
                "NRO. BUSES",
                "NRO. VENTAS");
        for (String[] e : listaEmpresas) {
            System.out.printf("| %-12s | %-20s | %-30s | %-15s | %-12s | %-12s |%n",
                    e[0],
                    e[1],
                    e[2],
                    e[3],
                    e[4],
                    e[5]);
        }

    }
    private void listLlegadasSalidasTerminal(){
        System.out.println("...:::: Listado de llegadas y salidas de un terminal ::::...");
        System.out.println("Nombre terminal:");
        String nombre = sc.nextLine();
        System.out.println("Fecha: [dd/mm/yyyy]");
        String fecha = sc.nextLine();
        LocalDate fechaTerminal = LocalDate.parse(fecha, formatterDate);
        Date fechaLlegadasSalidas = java.sql.Date.valueOf(fechaTerminal);
        String[][] llegadasSalidasTerminal = ce.listLlegadaSalidaTerminal(nombre, fechaLlegadasSalidas);
        System.out.printf(
                "| %-10s | %-8s | %-10s | %-20s | %-10s |%n",
                "TIPO",
                "HORA",
                "PATENTE",
                "EMPRESA",
                "PASAJEROS");
        for (String[] l : llegadasSalidasTerminal) {
            System.out.printf(
                    "| %-10s | %-8s | %-10s | %-20s | %-10s |%n",
                    l[0],
                    l[1],
                    l[2],
                    l[3],
                    l[4]
            );
        }
    }
    private void listVentasEmpresas(){
        System.out.println("...:::: Listado de ventas de una empresa ::::...");
        System.out.println("R.U.T");
        String rut = sc.nextLine();
        String[][] listVentasEmpresas = ce.listVentasEmpresa(registrarRut(rut));
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
      //  ce.createBus("1111Test", "Test", "Test", 20);
      // sv.createViaje(LocalDate.parse("01/01/2026", formatterDate),
            //   LocalTime.parse("10:30", formatterTime), 300, 10, "1111Test", 1);
       // sv.createCliente(testId1, test1, "+56 9 11111111", "JohnDoe@gmail.com");
        //sv.createCliente(testId2, test2, "+56 9 11111111", "JaneDoe@gmail.com");
        //sv.createPasajero(testId1, test1, "+56 9 11111111",test1, "+56 9 11111111");
        sv.createPasajero(testId2, test2, "+56 9 11111111",test2, "+56 9 11111111");
        sv.iniciaVenta("67", TipoDocumento.FACTURA, LocalDate.parse("01/01/2026", formatterDate), testId1);
        sv.iniciaVenta("68", TipoDocumento.BOLETA, LocalDate.parse("01/01/2026", formatterDate), testId2);
        sv.vendePasaje("67", LocalDate.parse("01/01/2026", formatterDate), LocalTime.parse("10:30"), "1111Test", 1, testId1, TipoDocumento.FACTURA);
        sv.vendePasaje("68", LocalDate.parse("01/01/2026", formatterDate), LocalTime.parse("10:30"), "1111Test", 2, testId1, TipoDocumento.BOLETA);
        System.out.println("...::::Datos de prueba creados:::...");
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
        }catch (ArrayIndexOutOfBoundsException e){
            throw new ArrayIndexOutOfBoundsException("RUT con formato erroneo");
        }
    }

    private Pasaporte registrarPasaporte(String numero, String nacionalidad){
        Pasaporte pasaporteRegistrado;
        return pasaporteRegistrado = new Pasaporte(numero, nacionalidad);
    }
}