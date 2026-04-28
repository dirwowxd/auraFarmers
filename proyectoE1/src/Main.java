import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import java.time.LocalTime;


public class Main {
    private Scanner sc = new Scanner(System.in);
    private SistemaVentaPasajes sistemas = new SistemaVentaPasajes();
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Main loxd = new Main();
        loxd.menu();


    }

    private void menu() {
        int opcion;
        do {



            System.out.println("==========================");
            System.out.println("...::: MENU PRINCIPAL:::...");
            System.out.println("                              ");
            System.out.println("1) Crear Cliente  ");
            System.out.println("2) Crear Bus  ");
            System.out.println("3) Crear Viaje  ");
            System.out.println("4) Vender Pasaje  ");
            System.out.println("5) Lista de Pasajeros ");
            System.out.println("6) Lista de Ventas ");
            System.out.println("7) Lista de Viajes ");
            System.out.println("8) Consulta Viajes disponibles por fecha ");
            System.out.println("9) salir ");
            System.out.print("Opcion:");
            opcion = sc.nextInt();


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
                    consultarViajesPorFecha();
                    break;

            }

        } while (opcion != 9);
    }



    private void createCliente() {
        Nombre nombreCliente = new Nombre();
        IdPersona idPersona = null;

        System.out.println("....::: Crear nuevo cliente:::....");
        System.out.println(" ");
        System.out.print("Rut[1] o Pasaporte [2] : ");
        String idDoc = sc.next();
        if (idDoc.equals("1")) {
            System.out.print("R.U.T : ");
            String rut = sc.next();
            idPersona = Rut.of(rut);
        } else if (idDoc.equals("2")) {
            System.out.print("Pasaporte : ");
            String pasaporte = sc.next();
            System.out.print("Nacionalidad : ");
            String nacionalidad = sc.next();
            idPersona = Pasaporte.of(pasaporte, nacionalidad);
        }
        System.out.print("Sr.[1] o Sra. [2] : ");
        int sra = sc.nextInt();
        if (sra == 1) {
            nombreCliente.setTratamiento(Tratamiento.SR);
        } else if (sra == 2) {
            nombreCliente.setTratamiento(Tratamiento.SRA);
        }
        sc.nextLine();
        System.out.print("Nombres : ");
        String nombres = sc.next();
        sc.nextLine();
        System.out.print("Apellido Paterno : ");
        String apellidoPaterno = sc.next();
        System.out.print("Apellido Materno : ");
        String apellidoMaterno = sc.next();
        System.out.print("Telefono movil : ");
        String telefonoMovil = sc.next();
        System.out.print("Email : ");
        String email = sc.next();
        nombreCliente.setNombres(nombres);
        nombreCliente.setApellidoPaterno(apellidoPaterno);
        nombreCliente.setApellidoMaterno(apellidoMaterno);


        boolean creadoExitosamente = sistemas.createCliente(idPersona, nombreCliente, telefonoMovil, email);
        if (creadoExitosamente) {
            System.out.println("Cliente guardado correctamente");
        } else {
            System.out.println("Cliente no se pudo guardar.");
        }


    }

    private void createBus() { //hecho por Nico
        System.out.println("....:::Creación de un nuevo Bus:::....");

        System.out.println();

        System.out.print("Patente: ");
        String patente = sc.next();

        System.out.print("Marca: ");
        String marca = sc.next();

        System.out.print("Modelo: ");
        String modelo = sc.next();

        System.out.print("Numero de asientos: ");
        int numeroAsientos = sc.nextInt();
        System.out.println();

        boolean si = sistemas.createBus(patente, marca, modelo, numeroAsientos);

        if(si){
            System.out.println("Bus creado exitosamente.");
        }else{
            System.out.println("Ya existe un Bus con esa patente.");
        }
    }
    private void createViaje() { //Hecho por Nico
        System.out.println("....:::Creacion de un nuevo Viaje:::....");

        System.out.println();

        System.out.print("Fecha[dd/mm/yyyy]: ");
        LocalDate fecha = LocalDate.parse(sc.next(), formatoFecha);

        System.out.print("Hora[hh:mm]: ");
        String textoHora = sc.next();
        LocalTime hora = LocalTime.parse(textoHora, formatoHora);

        System.out.print("Precio: ");
        int precio = sc.nextInt();
        sc.nextLine();

        System.out.print("Patente Bus: ");
        String patenteBus = sc.nextLine();

        boolean si = sistemas.createViaje(fecha, hora, precio, patenteBus);

        if(si){
            System.out.println("Viaje guardado exitosamente.");
        }else {
            System.out.println("No existe un Bus con esa patente o ya hay un viaje para esa fecha y hora.");
        }


    }

    private void vendePasajes() {
        TipoDocumento tipoDocumento = null;
        sc.nextLine();
        System.out.println("....::: Venta de pasajes ::::....");
        System.out.println(" ");
        System.out.println(":::: Datos de la Venta");
        System.out.print("ID Documento : ");
        String idDoc = sc.next();
        System.out.print("Tipo documento: [1] Boleta [2] Factura : ");
        int tipoDoc = sc.nextInt();
        if (tipoDoc == 1) {
            tipoDocumento = TipoDocumento.BOLETA;
        } else if (tipoDoc == 2) {
            tipoDocumento = TipoDocumento.FACTURA;
        }

        IdPersona idCliente = null;
        System.out.print("Fecha de venta [dd/mm/yyyy] : ");
        LocalDate fechaVenta = LocalDate.parse(sc.next(), formatoFecha);
        System.out.print("Rut [1] o Pasaporte [2] : ");
        String rutPasaporte = sc.next();
        if (rutPasaporte.equals("1")) {
            System.out.print("R.U.T : ");
            idCliente = Rut.of(sc.next());
        } else if (rutPasaporte.equals("2")) {
            System.out.print("Pasaporte : ");
            String p = sc.next();
            System.out.print("Nacionalidad : ");
            idCliente = Pasaporte.of(p, sc.next());
        }

        boolean ventaIniciada = sistemas.iniciaVenta(idDoc, tipoDocumento, fechaVenta, idCliente);
        if (!ventaIniciada) {
            System.out.println("La venta no se puede concretar");
            return;
        }

        System.out.println("\n:::: Datos del cliente");

            System.out.println("Nombre Cliente : " + sistemas.getNombrePasajero(idCliente));


        System.out.println("\n:::: Pasajes a vender");
        System.out.print("Cantidad de pasajes : ");
        int cantidadPasajes = sc.nextInt();
        System.out.print("Fecha de viaje [dd/mm/yyyy] : ");
        LocalDate fechaViaje = LocalDate.parse(sc.next(), formatoFecha);

        System.out.println("\n:::: Listado de horarios disponibles");
        String[][] horarios = sistemas.getHorariosDisponibles(fechaViaje);
        if (horarios.length == 0) {
            System.out.println("No existen viajes para la fecha indicada.");
            return;
        }

        System.out.println("*---*--------------*----------*----------*------------*");
        System.out.println("|   | BUS          | SALIDA   | VALOR    | ASIENTOS   |");
        System.out.println("|---+--------------+----------+----------+------------|");
        for (int i = 0; i < horarios.length; i++) {
            System.out.printf("| %d | %-12s | %-8s | $%-7s | %10s |\n", (i + 1), horarios[i][0], horarios[i][1], horarios[i][2], horarios[i][3]);
        }
        System.out.println("*---*--------------*----------*----------*------------*");

        System.out.print("Seleccione viaje en [1.." + horarios.length + "] : ");
        int seleccion = sc.nextInt();
        int indice = seleccion - 1;
        String patenteBus = horarios[indice][0];
        LocalTime horaViaje = LocalTime.parse(horarios[indice][1]);

        System.out.println("\n:::: Asientos disponibles para el viaje seleccionado");
        String[][] asientos = sistemas.listAsientosDeViaje(fechaViaje, horaViaje, patenteBus);

        System.out.println("*---*---*---*---*---*");
        for (int i = 0; i < asientos.length; i += 4) {
            String a1 = (i < asientos.length) ? (asientos[i][1].equalsIgnoreCase("Libre") ? asientos[i][0] : "*") : " ";
            String a2 = (i + 1 < asientos.length) ? (asientos[i+1][1].equalsIgnoreCase("Libre") ? asientos[i+1][0] : "*") : " ";
            String a3 = (i + 2 < asientos.length) ? (asientos[i+2][1].equalsIgnoreCase("Libre") ? asientos[i+2][0] : "*") : " ";
            String a4 = (i + 3 < asientos.length) ? (asientos[i+3][1].equalsIgnoreCase("Libre") ? asientos[i+3][0] : "*") : " ";

            System.out.printf("| %2s | %2s |   | %2s | %2s |\n", a1, a2, a4, a3);
            if (i + 4 < asientos.length) System.out.println("|---+---+---+---+---|");
        }
        System.out.println("*---*---*---*---*---*");

        System.out.print("Seleccione sus asientos [separe por ,] : ");
        String seleccionAsientos = sc.next();
        String[] asientosAComprar = seleccionAsientos.split(",");

        for (int i = 0; i < asientosAComprar.length; i++) {
            int nroAsiento = Integer.parseInt(asientosAComprar[i]);
            System.out.println("\n:::: Datos pasajeros " + (i + 1));
            System.out.print("Rut[1] o Pasaporte[2] : ");
            String tipoIdP = sc.next();
            IdPersona idPasajero = null;

            if (tipoIdP.equals("1")) {
                System.out.print("R.U.T : ");
                idPasajero = Rut.of(sc.next());
            } else {
                System.out.print("Pasaporte : ");
                String numP = sc.next();
                System.out.print("Nacionalidad : ");
                idPasajero = Pasaporte.of(numP, sc.next());
            }

            if (sistemas.getNombrePasajero(idPasajero) == null) {
                System.out.println(":::: Ingrese los datos completos del Pasajero y su Contacto");
                Nombre nomP = new Nombre();
                System.out.print("Sr.[1] o Sra.[2] : ");
                int opcionTratamiento = sc.nextInt();
                if (opcionTratamiento == 1) {
                    nomP.setTratamiento(Tratamiento.SR);
                } else {
                    nomP.setTratamiento(Tratamiento.SRA);
                }
                System.out.print("Nombres : ");
                nomP.setNombres(sc.next());
                sc.nextLine();


                System.out.print("Apellido Paterno : ");
                nomP.setApellidoPaterno(sc.next());
                System.out.print("Apellido Materno : ");
                nomP.setApellidoMaterno(sc.next());
                System.out.print("Telefono movil : ");

                String fonoP = sc.next();

                Nombre nomC = new Nombre();
                System.out.print("Tratamiento contacto [1] Sr [2] Sra: ");
                int opcionTratamientoContacto = sc.nextInt();
                if (opcionTratamientoContacto == 1) {
                    nomC.setTratamiento(Tratamiento.SR);
                } else {
                    nomC.setTratamiento(Tratamiento.SRA);
                }
                System.out.print("Nombres contacto: ");
                nomC.setNombres(sc.next());
                sc.nextLine();
                System.out.print("Apellido Paterno contacto: ");
                nomC.setApellidoPaterno(sc.next());
                System.out.print("Apellido Materno contacto: ");
                nomC.setApellidoMaterno(sc.next());
                System.out.print("Telefono contacto: ");
                String fonoC = sc.next();

                sistemas.createPasajero(idPasajero, nomP, fonoP, nomC, fonoC);
            }

            sistemas.vendePasaje(idDoc, tipoDocumento, fechaViaje, horaViaje, patenteBus, idPasajero, nroAsiento);
            System.out.println(":::: Pasaje agregado exitosamente");
        }

        System.out.println("\n:::: Monto total de la venta: $" + sistemas.getMontoVenta(idDoc, tipoDocumento));
        System.out.println("...:::: Venta generada exitosamente ::::....");

        System.out.println("\n:::: Imprimiendo los pasajes");
        for (String string : asientosAComprar) {
            System.out.println("\n----------------------- PASAJE -----------------------");
            System.out.println("FECHA DE VIAJE  : " + fechaViaje.format(formatoFecha));
            System.out.println("HORA DE VIAJE   : " + horaViaje.format(formatoHora));
            System.out.println("PATENTE BUS     : " + patenteBus);
            System.out.println("ASIENTO         : " + string);
            System.out.println("------------------------------------------------------");
        }
    }



    private void listPasajerosViaje() {
        System.out.println("....::: Listado de Pasajeros :::....");
        System.out.print("Fecha de viaje [dd/mm/yyyy] : ");
        LocalDate fecha = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.print("Hora de viaje [HH:mm] : ");
        LocalTime horaViaje = LocalTime.parse(sc.next(), DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println("Pantente del bus : ");
        String patenteBus=  sc.next();
        String [][] matrizPasajeros= sistemas.listPasajeros(fecha,horaViaje,patenteBus);

        if (matrizPasajeros == null ||  matrizPasajeros.length == 0) {
            System.out.println("No hay pasajeros registrados para el viaje: "+ patenteBus);
            return;
        }

        System.out.println("°*Listado de Pasajeros::*°");
        System.out.println("                (Viaje: " + patenteBus + ")");
        System.out.println();

        System.out.println("*---*------------*---------------------------*---------------------------*-------------------*");
        System.out.printf("| %-1s | %-10s | %-25s | %-25s | %-17s |\n", "A", "RUT/PASS", "PASAJERO", "CONTACTO", "TELEFONO CONTACTO");
        System.out.println("|---+------------+---------------------------+---------------------------+-------------------|");

        for (String[] matrizPasajero : matrizPasajeros) {
            System.out.printf("| %2s | %-10s | %-25s | %-25s | %-17s |\n",
                    matrizPasajero[0], // Asiento
                    matrizPasajero[1], // rut o pasaporte
                    matrizPasajero[2], // Pasajero
                    matrizPasajero[3], // Contacto
                    matrizPasajero[4]  // Teléfono
            );
        }
        System.out.println("*---*------------*---------------------------*---------------------------*-------------------*");
    }

    private void listVentas() {
        String[][] matrizVentas= sistemas.listVentas();
        if (matrizVentas == null || matrizVentas.length == 0) {
            System.out.println("No existen ventas registradas en el sistema.");
            return;
        }

        System.out.println("....:::Lista de ventas:::....");
        System.out.println();

        System.out.println("*-------------*-------------*--------------*-----------------*--------------------------------*----------------*---------------*");
        System.out.printf("| %-11s | %-11s | %-12s | %-15s | %-30s | %-14s | %-13s |\n",
                "ID DOCUMENTO", "TIPO DOCUMENTO", "FECHA", "RUT/PASAPORTE", "CLIENTE", "CANT. BOLETOS", "TOTAL VENTA");

        System.out.println("|-------------+-------------+--------------+-----------------+--------------------------------+----------------+---------------|");

        for (String[] matrizVenta : matrizVentas) {

            String idDoc = matrizVenta[0];
            String tipoDoc = matrizVenta[1];
            String fecha = matrizVenta[2];
            String rut = matrizVenta[3];
            String cliente = matrizVenta[4];
            String cantBoletos = matrizVenta[5];
            String totalVenta = matrizVenta[6];


            System.out.printf("| %11s | %-11s | %-12s | %-15s | %-30s | %14s | %13s |\n",
                    idDoc, tipoDoc, fecha, rut, cliente, cantBoletos, totalVenta);
        }

        System.out.println("*-------------*-------------*--------------*-----------------*--------------------------------*----------------*---------------*");



    }

    private void listViajes() {
        String[][] matrizViajes= sistemas.listViajes();

        if (matrizViajes == null || matrizViajes.length == 0) {
            System.out.println("No hay viajes programados dentro del sistema.");
            return;
        }

        System.out.println("....::: Listado de Viajes :::....");
        System.out.println();

        System.out.println("*------------*--------------*----------*-----------------*---------------*");

        System.out.printf("| %-10s | %-12s | %-8s | %-15s | %-15s |\n",
                "FECHA", "HORA", "PRECIO", "DISPONIBLES", "PATENTE BUS");

        System.out.println("|------------+--------------+----------+-----------------+---------------|");

        for (String[] matrizViaje : matrizViajes) {


            String fecha = matrizViaje[0];
            String hora = matrizViaje[1];
            String precio = matrizViaje[2];
            String disponibles = matrizViaje[3];
            String patente = matrizViaje[4];

            System.out.printf("| %-10s | %-12s | %-8s | %-15s | %-15s |\n",
                    fecha, hora, precio, disponibles, patente);
        }

        System.out.println("*------------*--------------*----------*-----------------*---------------*");

    }
    private void consultarViajesPorFecha() {
        System.out.println("....::: Consulta de Viajes Disponibles :::....");
        System.out.print("Ingrese la fecha a consultar [dd/mm/yyyy] : ");
        LocalDate fechaConsulta = LocalDate.parse(sc.next(), formatoFecha);

        String[][] horarios = sistemas.getHorariosDisponibles(fechaConsulta);

        if (horarios == null || horarios.length == 0) {
            System.out.println("\nNo existen viajes programados para la fecha indicada.");
            return;
        }
        System.out.println("\n°* Viajes disponibles para el " + fechaConsulta.format(formatoFecha) + " *°"); //una maquina yo me quedo hermoso
        System.out.println("*---------------*----------*----------*------------*");
        System.out.printf("| %-13s | %-8s | %-8s | %-10s |\n", "PATENTE BUS", "SALIDA", "VALOR", "LIBRES");
        System.out.println("|---------------+----------+----------+------------|");
        for (String[] horario : horarios) {
            String patente = horario[0];
            String hora = horario[1];
            String precio = horario[2];
            String libres = horario[3];

            System.out.printf("| %-13s | %-8s | $%-7s | %-10s |\n",
                    patente, hora, precio, libres);
        }

        System.out.println("*---------------*----------*----------*------------*");
    }
}


