package vista;

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
    private static UISVP instance;
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
    private UISVP() {
    }
    public static UISVP getInstance() {
        if (instance == null) {
            instance = new UISVP();

        }
        return instance;
    }



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

    private void venderPasajes() {

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
            sistema.createViaje(fecha,hora,precio,duracion,patenteBus, );
        } catch ()

    }

    private void createBus() {
    }

    private void createCliente() {
        Nombre nombreCliente = new Nombre();
        IdPersona idPersona = null;

        System.out.println("....::: Crear nuevo cliente:::....");
        System.out.println(" ");
        System.out.print("utilidades.Rut[1] o utilidades.Pasaporte [2] : ");
        String idDoc = sc.next();
        if (idDoc.equals("1")) {
            System.out.print("R.U.T : ");
            String rut = sc.next();
            idPersona = Rut.of(rut);
        } else if (idDoc.equals("2")) {
            System.out.print("utilidades.Pasaporte : ");
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


        try {
            sistema.createCliente(idPersona, nombreCliente, telefonoMovil, email);
            System.out.println("Cliente guardado correctamente.");
        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());
        }


    }
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

