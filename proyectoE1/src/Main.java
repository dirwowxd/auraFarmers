import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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

            }

        } while (opcion != 9) ;
    }
    private void createCliente() {
        Nombre nombreCliente = new Nombre();
        idPersona idPersona=null;

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
            System.out.print("Nacionalidad : " );
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
        } else  {
            System.out.println("Cliente no se pudo guardar.");
        }




    }
    private void createBus() {
        System.out.println("Creación de un nuevo Bus");

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
    private void createViaje() {
        System.out.println("Creacion de un nuevo Viaje");

        System.out.println();

        System.out.print("Fecha[dd/mm/yyyy]: ");
        LocalDate fecha = LocalDate.parse(sc.next(), formatoFecha);

        System.out.print("Hora[hh:mm]: ");
        String textoHora = sc.nextLine();
        LocalTime hora = LocalTime.parse(textoHora, formatoHora);

        System.out.print("Precio: ");
        int precio = sc.nextInt();

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

    }
    private void listPasajerosViaje() {

    }
    private void listVentas() {

    }
    private void listViajes() {

    }
}

