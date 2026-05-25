package vista;

import Modelo.TipoDocumento;
import controlador.*;
import excepciones.SistemaVentaPasajesException;
import utilidades.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UISVP {

    Scanner sc = new Scanner(System.in);
    ControladorEmpresas controlador = ControladorEmpresas.getInstance();
    SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");



    public void menuUISVP() {
        int opcion = 0;

        do {
            System.out.println("Menu principal");
            System.out.println("1. Crear empresa");
            System.out.println("2. Contratar tripulante");
            System.out.println("3. Crear terminal");
            System.out.println("4. Crear cliente");
            System.out.println("5. Crear bus");
            System.out.println("6. Crear viaje");
            System.out.println("7. Vender pasajes");
            System.out.println("8. Listar ventas");
            System.out.println("9. Listar viajes");
            System.out.println("10. Listar pasajeros de viaje");
            System.out.println("11. Listar empresas");
            System.out.println("12. Listar llegadas/salidas de terminal");
            System.out.println("13. Listar ventas de empresa");
            System.out.println("14. Salir");
            System.out.println("------------------------------");
            System.out.print("Ingrese opcion: ");

            try {
                opcion = sc.nextInt();

                if (opcion < 1 || opcion > 14) {
                    System.out.println("Error: La opción debe ser un número entre el 1 y 14.");
                } else {
                    switch (opcion) {
                        case 1:
                            createEmpresa();
                            break;
                        case 2:
                            contrataTripulante();
                            break;
                        case 3:
                            createTerminal();
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
                            venderPasajes();
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
                        case 11:
                            listEmpresas();
                            break;
                        case 12:
                            listLlegadasSalidasTerminal();
                            break;
                        case 13:
                            listVentasEmpresa();
                            break;
                        case 14:
                            System.out.println("saliendo");
                            break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese solo numeros enteros");
                opcion = 0;
            }

        } while (opcion != 14);
    }

    private void listVentasEmpresa() {

    }

    private void listLlegadasSalidasTerminal() {

    }

    private void listEmpresas() {

    }


    public void listPasajerosViaje() {
        System.out.println("Listar pasajeros de viaje");

        System.out.print("Fecha [dd/mm/yyyy] : ");
        String fechaTexto = sc.next();

        System.out.print("Hora [hh:mm] : ");
        String horaTexto = sc.next();

        System.out.print("Patente Bus : ");
        String patente = sc.next();

        try {
            LocalDate fecha = LocalDate.parse(fechaTexto, formatoFecha);
            LocalTime hora = LocalTime.parse(horaTexto, formatoHora);

            String[][] Pasajeros = sistema.listPasajeros(fecha, hora, patente);

            if (Pasajeros.length == 0) {
                System.out.println("No hay pasajeros registrados para este viaje.");
            } else {
                System.out.println("LISTADO DE PASAJEROS REGISTRADOS EN EL VIAJE");

                for (int i = 0; i < Pasajeros.length; i++) {
                    String id = Pasajeros[i][0];
                    String nombre = Pasajeros[i][1];
                    String nomContacto = Pasajeros[i][2];
                    String fonoContacto = Pasajeros[i][3];

                    System.out.println("Pasajero N°" + (i + 1) + ":");
                    System.out.println("ID: " + id);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Contacto de Emergencia: " + nomContacto);
                    System.out.println("Teléfono Contacto: " + fonoContacto);
                    System.out.println("----");
                }
            }
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {//nose cual es la excepcion que saltaria al intentar convertir a localdate o localtime y que no cumpla con el formato, por eso uso exception en generico
            System.out.println("Error: Formato de fecha u hora incorrecto");
        }
    }

    private void listViajes() {

    }

    private void listVentas() {

    }

    private void venderPasajes() { // agregar origen comuna y destino comuna
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
        System.out.print("Origen (Comuna)");
        String origenComuna = sc.next();
        System.out.print("Destino (Comuna)");
        String destinoComuna = sc.next();
        System.out.println("::: Datos del cliente ");
        System.out.println("  ");
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

        try {
            SistemaVentaPasajes ventaIniciada = sistema.iniciaVenta(idDoc, tipoDocumento, fechaVenta, idCliente);
        } catch (SistemaVentaPasajesException e){
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n:::: Datos del cliente");

        System.out.println("Nombre Cliente : " + sistema.getNombrePasajero(idCliente));


        System.out.println("\n:::: Pasajes a vender");
        System.out.print("Cantidad de pasajes : ");
        int cantidadPasajes = sc.nextInt();
        System.out.print("Fecha de viaje [dd/mm/yyyy] : ");
        LocalDate fechaViaje = LocalDate.parse(sc.next(), formatoFecha);

        System.out.println("\n:::: Listado de horarios disponibles");
        String[][] horarios = sistema.getHorariosDisponibles(fechaViaje);
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
        String[][] asientos = sistema.listAsientosDeViaje(fechaViaje, horaViaje, patenteBus);

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
            System.out.print("utilidades.Rut[1] o utilidades.Pasaporte[2] : ");
            String tipoIdP = sc.next();
            IdPersona idPasajero = null;

            if (tipoIdP.equals("1")) {
                System.out.print("R.U.T : ");
                idPasajero = Rut.of(sc.next());
            } else {
                System.out.print("utilidades.Pasaporte : ");
                String numP = sc.next();
                System.out.print("Nacionalidad : ");
                idPasajero = Pasaporte.of(numP, sc.next());
            }

            if (sistema.getNombrePasajero(idPasajero).isEmpty()) {
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

                sistema.createPasajero(idPasajero, nomP, fonoP, nomC, fonoC);
            }

            sistema.vendePasaje(idDoc, tipoDocumento, fechaViaje, horaViaje, patenteBus, idPasajero, nroAsiento);
            System.out.println(":::: Modelo.Pasaje agregado exitosamente");
        }

        System.out.println("\n:::: Monto total de la venta: $" + sistema.getMontoVenta(idDoc, tipoDocumento));
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

    private void createViaje() {
        System.out.println("...:::: Creando un nuevo Viaje ::::...");

        System.out.print("Fecha [dd/mm/yyyy]: ");
        String fechaStr = sc.nextLine();
        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("Hora [hh:mm]: ");
        String horaStr = sc.nextLine();
        LocalTime hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));

        System.out.print("Precio: ");
        int precio = Integer.parseInt(sc.nextLine());

        System.out.print("Duración (minutos): ");
        int duracion = Integer.parseInt(sc.nextLine());

        System.out.print("Patente Bus: ");
        String patenteBus = sc.nextLine();

        System.out.print("Nro. de conductores: ");
        int nroConductores = Integer.parseInt(sc.nextLine());


        System.out.println(":: Id Auxiliar ::");
        System.out.print("Rut[1] p Pasaporte [2] : ");
        String rutPasaporte = sc.nextLine();
        if (rutPasaporte.equals("1")) {
            System.out.print("Rut : ");
            Rut rut = Rut.of(rutPasaporte);
        }
        if (rutPasaporte.equals("2")) {
            System.out.print("Numero Pasaporte: ");
            int numeroPasaporte = sc.nextInt();
            System.out.print("Nacionalidad Pasaporte: ");
            String Nacionalidad = sc.nextLine();
            Pasaporte pasaporte = Pasaporte.of(String.valueOf(numeroPasaporte), Nacionalidad);

        }

        System.out.print("Nombre comuna salida : ");
        String comunaSalida = sc.nextLine();

        System.out.print("Nombre comuna llegada : ");
        String comunaLlegada = sc.nextLine();
        try {
            sistema.createViaje(fecha,hora,precio,duracion,patenteBus);

        } catch (SistemaVentaPasajesException e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void createBus() {
    }

    private void createCliente() {
    }

    private void createTerminal() {
    }

    private void contrataTripulante() {
    }


    public void createEmpresa() {
        System.out.println("Creando una nueva Empresa");

        System.out.print("R.U.T : ");
        String rut = sc.next();

        sc.nextLine();

        System.out.print("Nombre : ");
        String nombre = sc.nextLine();

        System.out.print("url : ");
        String url = sc.next();

        try {
            Rut rutAhoraSi = Rut.of(rut);

            controlador.createEmpresa(rutAhoraSi, nombre, url);

            System.out.println("Empresa creada exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());

        }
    }

}
