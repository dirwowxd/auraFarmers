package vista;


import Modelo.TipoDocumento;

import controlador.*;
import excepciones.SVPException;
import utilidades.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Optional;
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
            System.out.println("14. Generar pasajes de venta");
            System.out.println("15. Leer datos iniciales");
            System.out.println("16. Guardar datos del sistema");
            System.out.println("17. Leer datos del sistema");
            System.out.println("18. Salir");
            System.out.println("------------------------------");
            System.out.print("Ingrese opcion: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                if (opcion < 1 || opcion > 18) {
                    System.out.println("Error: La opción debe ser un número entre el 1 y 18.");
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
                            generatePasajesVenta();
                            break;
                        case 15:
                            readDatosIniciales();
                            break;
                        case 16:
                            saveDatosSistema();
                            break;
                        case 17:
                            readDatosSistema();
                            break;
                        case 18:
                            System.out.println("Saliendo...");
                            return;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese solo numeros enteros");
                sc.nextLine();
                opcion = 0;
            }

        } while (opcion != 18);
    }

    private void listVentasEmpresa() {
        System.out.println("\n...:::: Ventas por Empresa ::::....");
        System.out.print("Ingrese el RUT de la empresa: ");
        String rutInput = sc.nextLine();
        Rut rut = Rut.of(rutInput);

        try {
            ControladorEmpresas controlador = ControladorEmpresas.getInstance();
            String[][] datos = controlador.listVentasEmpresa(rut);
            if (datos.length == 0) {
                System.out.println("No hay ventas registradas para esta empresa.");
                return;
            }
            System.out.printf("%-15s | %-15s | %-10s | %-15s%n", "Fecha", "Tipo", "Monto", "Pago");
            System.out.println("------------------------------------------------------------------");

            for (String[] fila : datos) {
                System.out.printf("%-15s | %-15s | %-10s | %-15s%n",
                        fila[0], fila[1], fila[2], fila[3]);
            }
        } catch (SVPException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void listLlegadasSalidasTerminal() {
        System.out.println("LISTADO LLEGADAS Y SALIDAS");
        System.out.print("Nombre terminal : ");
        String terminal = sc.nextLine();
        System.out.print("Fecha [dd/mm/yyyy] : ");
        String fechaTexto = sc.nextLine();
        try {
            LocalDate fecha = LocalDate.parse(fechaTexto,
                    formatoFecha);
            String[][] viajes =
                    controlador.listLlegadasSalidasTerminal(terminal,
                            fecha);
            if (viajes.length == 0) {
                System.out.println("No existen viajes");
                return;
            }
            for (String[] viaje : viajes) {
                System.out.println("------------------------");
                System.out.println("Tipo : " + viaje[0]);
                System.out.println("Hora : " + viaje[1]);
                System.out.println("Terminal : " + viaje[2]);
                System.out.println("Patente : " + viaje[3]);
                System.out.println("Precio : " + viaje[4]);
            }
        } catch (SVPException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
        }

    private void listEmpresas() {
        System.out.println("\n...::::: Listado de empresas ::::....\n");
        ControladorEmpresas controlador = ControladorEmpresas.getInstance();
        String[][] datos = controlador.listEmpresas();
        if (datos.length == 0) {
            System.out.println(" No existen empresas registradas en el sistema ");
            return;
        }

        String separador = "----------------------------------------------------------------------------------------------------------------------*";
        String formato   = "| %-13s | %-28s | %-28s | %-17s | %-10s | %-12s |%n";

        System.out.println(separador);
        System.out.printf(formato, "RUT EMPRESA", "NOMBRE", "URL", "NRO. TRIPULANTES", "NRO. BUSES", "NRO. VENTAS");
        System.out.println(separador);

        for (String[] fila : datos) {
            System.out.printf(formato, fila[0], fila[1], fila[2], fila[3], fila[4], fila[5]);
            System.out.println(separador);
        }
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
        } catch (SVPException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Error: Formato de fecha u hora incorrecto");
        }
    }

    private void listViajes() {
        System.out.println("\n...::::: Listado de viajes ::::....\n");

        SistemaVentaPasajes svp = SistemaVentaPasajes.getInstance();
        String[][] datos = svp.listViajes();

        if (datos.length == 0) {
            System.out.println(" No existen viajes registrados en el sistema ");
            return;
        }

        String separador = "----------------------------------------------------------------------------------------------------------*";
        String formato   = "| %-12s | %-9s | %-10s | %-7s | %-14s | %-8s | %-15s | %-15s |%n";

        System.out.println(separador);
        System.out.printf(formato, "FECHA", "HORA SALE", "HORA LLEGA", "PRECIO", "ASIENTOS DISP.", "PATENTE", "ORIGEN", "DESTINO");
        System.out.println(separador);

        for (String[] fila : datos) {
            System.out.printf(formato, fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6], fila[7]);
            System.out.println(separador);
        }
    }

    private void listVentas() {
        System.out.println("\n...:::: Listado General de Ventas ::::....\n");

        String[][] datos = sistema.listVentas();

        if (datos == null || datos.length == 0) {
            System.out.println("No hay ventas registradas en el sistema.");
            return;
        }
        String separador = "-----------------------------------------------------------------------------------------------------------";
        String formato = "%-10s | %-8s | %-12s | %-12s | %-20s | %-8s | %-12s%n";
        System.out.println(separador);
        System.out.printf(formato, "ID Doc", "Tipo", "Fecha", "ID Cliente", "Nombre", "Pasajes", "Monto");
        System.out.println(separador);
        for (String[] fila : datos) {
            System.out.printf(formato, fila[0], fila[1], fila[2], fila[3], fila[4], fila[5], fila[6]);
        }
        System.out.println(separador);
    }





    private void venderPasajes() {
        System.out.println("\n....::: Venta de pasajes ::::....");
        System.out.println(" ");
        System.out.println(":::: Datos de la Venta");

        System.out.print("ID Documento : ");
        String idDoc = sc.nextLine();

        System.out.print("Tipo documento: [1] Boleta [2] Factura : ");
        int tipoDoc = Integer.parseInt(sc.nextLine());
        TipoDocumento tipoDocumento = (tipoDoc == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

        System.out.print("Fecha de viaje [dd/mm/yyyy] : ");
        String fechaViajeStr = sc.nextLine();
        LocalDate fechaViaje = LocalDate.parse(fechaViajeStr, formatoFecha);

        System.out.print("Origen (Comuna): ");
        String origenComuna = sc.nextLine();

        System.out.print("Destino (Comuna): ");
        String destinoComuna = sc.nextLine();

        System.out.println("::: Datos del cliente ");
        System.out.print("Rut [1] o Pasaporte [2] : ");
        int tipoIdCliente = Integer.parseInt(sc.nextLine());
        IdPersona idCliente = null;

        if (tipoIdCliente == 1) {
            System.out.print("R.U.T : ");
            idCliente = Rut.of(sc.nextLine());
        } else {
            System.out.print("Pasaporte : ");
            String p = sc.nextLine();
            System.out.print("Nacionalidad : ");
            idCliente = Pasaporte.of(p, sc.nextLine());
        }

        System.out.print("Cantidad de pasajes : ");
        int cantidadPasajes = Integer.parseInt(sc.nextLine());
        try {
            sistema.iniciaVenta(idDoc, tipoDocumento, LocalDate.now(), idCliente);
        } catch (SVPException e) {
            System.out.println("*** Error: " + e.getMessage() + " ***");
            return;
        }
        Optional<String> nomClienteOpt = sistema.getNombrePasajero(idCliente);
        String nombreClienteStr = nomClienteOpt.isPresent() ? nomClienteOpt.get() : "No registrado";
        System.out.println("Nombre Cliente : " + nombreClienteStr);
        System.out.println("\n:::: Listado de horarios disponibles");
        String[][] horarios = sistema.getHorariosDisponibles(fechaViaje);

        if (horarios.length == 0) {
            System.out.println("No existen viajes disponibles para los criterios indicados.");
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
        int seleccion = Integer.parseInt(sc.nextLine());
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
        String seleccionAsientos = sc.nextLine();
        String[] asientosAComprar = seleccionAsientos.split(",");

        for (int i = 0; i < asientosAComprar.length; i++) {
            int nroAsiento = Integer.parseInt(asientosAComprar[i]);
            System.out.println("\n:::: Datos pasajeros " + (i + 1));
            System.out.print("Rut[1] o Pasaporte[2] : ");
            int tipoIdP = Integer.parseInt(sc.nextLine());
            IdPersona idPasajero;

            if (tipoIdP == 1) {
                System.out.print("R.U.T : ");
                idPasajero = Rut.of(sc.nextLine());
            } else {
                System.out.print("Pasaporte : ");
                String numP = sc.nextLine();
                System.out.print("Nacionalidad : ");
                idPasajero = Pasaporte.of(numP, sc.nextLine());
            }

            Optional<String> nomPasajeroOpt = sistema.getNombrePasajero(idPasajero);

            if (nomPasajeroOpt.isEmpty()) {
                System.out.println(":::: Ingrese los datos completos del Pasajero y su Contacto");
                Nombre nomP = new Nombre();
                System.out.print("Sr.[1] o Sra.[2] : ");
                int opcionTratamiento = Integer.parseInt(sc.nextLine());
                nomP.setTratamiento((opcionTratamiento == 1) ? Tratamiento.SR : Tratamiento.SRA);

                System.out.print("Nombres : ");
                nomP.setNombres(sc.nextLine());
                System.out.print("Apellido Paterno : ");
                nomP.setApellidoPaterno(sc.nextLine());
                System.out.print("Apellido Materno : ");
                nomP.setApellidoMaterno(sc.nextLine());

                System.out.print("Telefono movil : ");
                String fonoP = sc.nextLine();

                Nombre nomC = new Nombre();
                System.out.print("Tratamiento contacto [1] Sr [2] Sra: ");
                int opcionTratamientoContacto = Integer.parseInt(sc.nextLine());
                nomC.setTratamiento((opcionTratamientoContacto == 1) ? Tratamiento.SR : Tratamiento.SRA);

                System.out.print("Nombres contacto: ");
                nomC.setNombres(sc.nextLine());
                System.out.print("Apellido Paterno contacto: ");
                nomC.setApellidoPaterno(sc.nextLine());
                System.out.print("Apellido Materno contacto: ");
                nomC.setApellidoMaterno(sc.nextLine());

                System.out.print("Telefono contacto: ");
                String fonoC = sc.nextLine();

                try {
                    sistema.createPasajero(idPasajero, nomP, fonoP, nomC, fonoC);
                } catch (SVPException e) {
                    System.out.println(" Error : " + e.getMessage());
                    return;
                }
            }

            try {
                sistema.vendePasaje(idDoc, tipoDocumento, fechaViaje, horaViaje, patenteBus, idPasajero, nroAsiento);
                System.out.println(":::: Pasaje asignado con éxito.");
            } catch (SVPException e) {
                System.out.println(" Error : " + e.getMessage());
                return;
            }
        }

        Optional<Integer> montoOpt = Optional.of(sistema.getMontoVenta(idDoc, tipoDocumento));
        int montoTotal = montoOpt.orElse(0);
        System.out.println("\n:::: Monto total de la venta: $" + montoTotal);
        System.out.println(":::: Pago de la venta");
        System.out.print("Efectivo[1] o Tarjeta[2] : ");
        int tipoPago = Integer.parseInt(sc.nextLine());

        try {
            if (tipoPago == 1) {
                sistema.pagaVenta(idDoc, tipoDocumento);
            } else {
                System.out.print("Ingrese número de tarjeta: ");
                long nroTarjeta = Long.parseLong(sc.nextLine());
                sistema.pagaVenta(idDoc, tipoDocumento, nroTarjeta);
            }
            System.out.println("...:::: Venta realizada exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println("*** Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(" Error : "+e.getMessage());
        }

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

    public void createViaje() {
        try {
            System.out.println("\n..:::: Creando un nuevo Viaje ::::....");

            System.out.print("Fecha del viaje (dd/MM/yyyy): ");
            String fechaStr = sc.nextLine();
            LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Hora del viaje (HH:mm): ");
            String horaStr = sc.nextLine();
            LocalTime hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));

            System.out.print("Precio del pasaje: ");
            int precio = Integer.parseInt(sc.nextLine());

            System.out.print("Duracion del viaje (minutos): ");
            int duracion = Integer.parseInt(sc.nextLine());
            System.out.print("Patente del bus: ");
            String patenteBus = sc.nextLine();
            System.out.print("Cantidad de conductores (1 o 2): ");
            int cantidadConductores = Integer.parseInt(sc.nextLine());
            while (cantidadConductores < 1 || cantidadConductores > 2) {
                System.out.println("ERROR. Debe ingresar 1 o 2 conductores.");
                System.out.print("Cantidad de conductores (1 o 2): ");
                cantidadConductores = Integer.parseInt(sc.nextLine());
            }
            IdPersona[] idTripulantes = new IdPersona[cantidadConductores + 1];
            System.out.println("\n:: Id Auxiliar ::");
            System.out.print("Rut[1] o Pasaporte[2] : ");
            int tipoAux = Integer.parseInt(sc.nextLine());

            if (tipoAux == 1) {
                System.out.print("R.U.T : ");
                idTripulantes[0] = Rut.of(sc.nextLine());
            } else {
                System.out.print("Numero de Pasaporte : ");
                String numP = sc.nextLine();
                System.out.print("Nacionalidad : ");
                idTripulantes[0] = Pasaporte.of(numP, sc.nextLine());
            }

            for (int i = 1; i <= cantidadConductores; i++) {
                System.out.println("\n:: Id Conductor " + i + " ::");
                System.out.print("Rut[1] o Pasaporte[2] : ");
                int tipoCond = Integer.parseInt(sc.nextLine());

                if (tipoCond == 1) {
                    System.out.print("R.U.T : ");
                    idTripulantes[i] = Rut.of(sc.nextLine());
                } else {
                    System.out.print("Numero de Pasaporte : ");
                    String numP = sc.nextLine();
                    System.out.print("Nacionalidad : ");
                    idTripulantes[i] = Pasaporte.of(numP, sc.nextLine());
                }
            }

            String[] nomComunas = new String[2];
            System.out.print("Nombre comuna salida: ");
            nomComunas[0] = sc.nextLine();
            System.out.print("Nombre comuna llegada: ");
            nomComunas[1] = sc.nextLine();

            sistema.createViaje(fecha, hora, precio, duracion, patenteBus, idTripulantes, nomComunas);
            System.out.println("...:::: Viaje guardado exitosamente ::::....");

        } catch (SVPException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() );
        }
    }


    private void createBus() {
        System.out.println("\n...::::: Creando un nuevo Bus ::::....\n");

        System.out.print("Patente : ");
        String patente = sc.nextLine();

        System.out.print("            Marca : ");
        String marca = sc.nextLine();

        System.out.print("Modelo : ");
        String modelo = sc.nextLine();

        System.out.print("Número de asientos : ");
        int nroAsientos = sc.nextInt();
        sc.nextLine();

        System.out.println("\n:::: Dato de la empresa");
        System.out.print("R.U.T : ");
        String rutStr = sc.nextLine();

        try {
            Rut rutEmpresa =  Rut.of(rutStr);
            ControladorEmpresas controlador = ControladorEmpresas.getInstance();
            controlador.createBus(patente, marca, modelo, nroAsientos, rutEmpresa);
            System.out.println("\n...::::: Bus guardado exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println("\n Error: " + e.getMessage() + " ");
        }
    }

    private void createTerminal() {
        System.out.println("\n...::::: Creando un nuevo Terminal ::::....\n");

        System.out.print("Nombre : ");
        String nombre = sc.nextLine();

        System.out.print("Calle : ");
        String calle = sc.nextLine();

        System.out.print("Numero : ");
        int numero = sc.nextInt();
        sc.nextLine();

        System.out.print("Comuna : ");
        String comuna = sc.nextLine();

        try {
            Direccion dir = new Direccion(calle, numero, comuna);
            ControladorEmpresas controlador = ControladorEmpresas.getInstance();
            controlador.createTerminal(nombre, dir);
            System.out.println("\n...::::: Terminal guardado exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println("\n Error: " + e.getMessage() + " ");
        }
    }

    private void contrataTripulante() {
        System.out.println("\n...:::: Contactando un nuevo Tripulante ::::....\n");
        System.out.print("R.U.T de la empresa: ");
        String rutEmpresa = sc.nextLine();
        System.out.print("Auxiliar [1] o Conductor [2] : ");
        int tipoTripulante = Integer.parseInt(sc.nextLine());
        System.out.print("Rut [1] o Pasaporte [2] : ");
        int tipoId = Integer.parseInt(sc.nextLine());
        String valorId;
        String nacionalidad = "";
        if (tipoId == 1) {
            System.out.print("R.U.T : ");
            valorId = sc.nextLine();
        } else {
            System.out.print("Número Pasaporte : ");
            valorId = sc.nextLine();
            System.out.print("Nacionalidad : ");
            nacionalidad = sc.nextLine();
        }
        System.out.print("Sr.[1] o Sra.[2] : ");
        int tratamiento = Integer.parseInt(sc.nextLine());
        Tratamiento trat = (tratamiento == 1) ? Tratamiento.SR : Tratamiento.SRA;
        System.out.print("Nombres : ");
        String nombres = sc.nextLine();
        System.out.print("Apellido Paterno : ");
        String apellidoPaterno = sc.nextLine();
        System.out.print("Apellido Materno : ");
        String apellidoMaterno = sc.nextLine();

        System.out.print("Calle : ");
        String calle = sc.nextLine();
        System.out.print("Numero : ");
        int numero = Integer.parseInt(sc.nextLine());
        System.out.print("Comuna : ");
        String comuna = sc.nextLine();
        try {
            Rut rutEmp = Rut.of(rutEmpresa);

            IdPersona id;
            if (tipoId == 1) {
                id = Rut.of(valorId);
            } else {
                id = Pasaporte.of(valorId, nacionalidad);
            }
            Nombre nom = new Nombre(trat, nombres, apellidoPaterno, apellidoMaterno);
            Direccion dir = new Direccion(calle, numero, comuna);
            ControladorEmpresas controlador = ControladorEmpresas.getInstance();
            if (tipoTripulante == 1) {
                controlador.hireAuxiliarForEmpresa(rutEmp, id, nom, dir);
                System.out.println("\n...:::: Auxiliar contratado exitosamente ::::....");
            } else {
                controlador.hireConductorForEmpresa(rutEmp, id, nom, dir);
                System.out.println("\n...:::: Conductor contratado exitosamente ::::....");
            }
        } catch (SVPException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Error en el formato de los datos: " + e.getMessage());
        }
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

        } catch (SVPException e) {
            System.out.println("Error: " + e.getMessage());

        }
    }
    private void createCliente() {
        System.out.println("\n...:::: Creando nuevo Cliente ::::....");

        System.out.print("Rut [1] o Pasaporte [2] : ");
        int tipoId = Integer.parseInt(sc.nextLine());

        IdPersona id;
        if (tipoId == 1) {
            System.out.print("RUT : ");
            String rut = sc.nextLine();
            id = Rut.of(rut);
        } else {
            System.out.print("Número Pasaporte : ");
            String numP = sc.nextLine();
            System.out.print("Nacionalidad : ");
            String nac = sc.nextLine();
            id = Pasaporte.of(numP, nac);
        }
        Nombre nom = new Nombre();
        System.out.print("Sr.[1] o Sra.[2] : ");
        int trat = Integer.parseInt(sc.nextLine());
        nom.setTratamiento((trat == 1) ? Tratamiento.SR : Tratamiento.SRA);

        System.out.print("Nombres : ");
        nom.setNombres(sc.nextLine());
        System.out.print("Apellido Paterno : ");
        nom.setApellidoPaterno(sc.nextLine());
        System.out.print("Apellido Materno : ");
        nom.setApellidoMaterno(sc.nextLine());

        System.out.print("Telefono : ");
        String fono = sc.nextLine();
        System.out.print("Email : ");
        String email = sc.nextLine();

        try {
            ControladorEmpresas controlador = ControladorEmpresas.getInstance();
            sistema.createCliente(id, nom, fono, email);
            System.out.println("...:::: Cliente creado exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void readDatosIniciales() {
        try {
            System.out.println("\n...:::: Cargando datos iniciales desde archivo ::::....");
            sistema.readDatosIniciales();
            System.out.println("...:::: Datos cargados exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println("Error al cargar datos iniciales: " + e.getMessage());
        }
    }
    private void readDatosSistema() {
        System.out.println(" Leyendo datos del sistema ");
        try {
           sistema.readDatosSistemas();
            System.out.println(" Datos del sistema cargados exitosamente ");
        } catch (SVPException e) {
            System.out.println("Error al leer los datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    private void generatePasajesVenta() throws SVPException {
        System.out.println(" Generar Pasajes de Venta");
        System.out.print("ID Documento: ");
        String idDoc = sc.nextLine();
        System.out.print("Tipo documento [1] Boleta [2] Factura: ");
        int tipoDocOpcion = 0;
        try {
            tipoDocOpcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            return;
        }
        TipoDocumento tipo = (tipoDocOpcion == 1) ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;
        try {
            sistema.generatePasajesVenta(idDoc, tipo);
            System.out.println(" Archivo de pasajes generado exitosamente ");
        } catch (SVPException e) {

            System.out.println("Error al generar pasajes: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    public void saveDatosSistema() {
        try {
            sistema.saveDatosSistema();
            System.out.println(":::: Datos del sistema guardados exitosamente ::::.");
        } catch (SVPException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }


    public void PagaVentaPasajes(String idDoc, TipoDocumento tipo) {
         try {

             SistemaVentaPasajes.getInstance().pagaVenta(idDoc, tipo);
             System.out.println("\n El pago del documento" + tipo + " con ID" + idDoc + "se registro y realizo con exito!");

         } catch (SVPException e) {
             System.out.println("No se pudo procesar el pago: "+e.getMessage());
         } catch (Exception e) {
             System.out.println("Ocurrio un error al momento de realizar el pago: "+e.getMessage());
         }
    }

}
