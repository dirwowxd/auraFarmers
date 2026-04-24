import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private SistemaVentaPasajes mish = new SistemaVentaPasajes();

    public static void main(String[] args) {
        Main loxd = new Main();
        loxd.menu();


    }

    private void menu() {
        int opcion;
        do {
            {

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
            }
        } while (opcion < 1 || opcion > 8);
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
    }
    private void createCliente() {
        System.out.println("....::: Crear nuevo cliente:::....");
        System.out.println(" ");
        System.out.print("Rut[1] o Pasaporte [2] : ");
        int rutPasaporte = sc.nextInt();
        System.out.print("R.U.T : ");
        int rutT = sc.nextInt();
        System.out.print("Sr.[1] o Sra. [2] : ");
        int sra = sc.nextInt();
        System.out.print("Nombres : ");
        String nombres = sc.next();
        System.out.print("Apellido Paterno : ");
        String apellidoPaterno = sc.next();
        System.out.print("Apellido Materno : ");
        String apellidoMaterno = sc.next();
        System.out.print("Telefono movil : ");
        String telefonoMovil = sc.next();
        System.out.println("Email : ");
        String email = sc.next();

        if (mish.createCliente(rutPasaporte, rutT, sra, apellidoPaterno, apellidoMaterno, nombres, telefonoMovil, email));
    }
    private void createBus() {
    }
    private void createViaje() {

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

