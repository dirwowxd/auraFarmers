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
    private void listPasajerosViaje(String [][] matrizPasajeros, String idViaje) {

        if (matrizPasajeros == null ||  matrizPasajeros.length == 0) {
            System.out.println("No hay pasajeros registrados para el viaje: "+ idViaje);
            return;
        }

        System.out.println("°*Listado de Pasajeros::*°");
        System.out.println("                (Viaje: " + idViaje + ")");
        System.out.println();

        System.out.println("*---------*-----------------*------------------------------------------*-----------------*");

        System.out.printf("| %-7s | %-15s | %-40s | %-15s |\n",
                "ASIENTO", "RUT/PASAPORTE", "NOMBRE CLIENTE", "TIPO PASAJERO");

        System.out.println("|---------+-----------------+------------------------------------------+-----------------|");

        for (int i = 0; i < matrizPasajeros.length; i++) {

            String asiento = matrizPasajeros[i][0];
            String documento = matrizPasajeros[i][1];
            String nombre = matrizPasajeros[i][2];
            String tipo = matrizPasajeros[i][3];

            System.out.printf("| %7s | %-15s | %-40s | %-15s |\n",
                    asiento, documento, nombre, tipo);
        }

        System.out.println("*---------*-----------------*------------------------------------------*-----------------*");
    }

    private void listVentas(String[][] matrizVentas) {
        if (matrizVentas == null || matrizVentas.length == 0) {
            System.out.println("No existen ventas registradas en el sistema.");
            return;
        }

        System.out.println("°*::Lista de ventas::*°");
        System.out.println();

        System.out.println("*-------------*-------------*--------------*-----------------*--------------------------------*----------------*---------------*");
        System.out.printf("| %-11s | %-11s | %-12s | %-15s | %-30s | %-14s | %-13s |\n",
                "ID DOCUMENTO", "TIPO DOCUMENTO", "FECHA", "RUT/PASAPORTE", "CLIENTE", "CANT. BOLETOS", "TOTAL VENTA");

        System.out.println("|-------------+-------------+--------------+-----------------+--------------------------------+----------------+---------------|");

        for (int i = 0; i < matrizVentas.length; i++) {

            String idDoc = matrizVentas[i][0];
            String tipoDoc = matrizVentas[i][1];
            String fecha = matrizVentas[i][2];
            String rut = matrizVentas[i][3];
            String cliente = matrizVentas[i][4];
            String cantBoletos = matrizVentas[i][5];
            String totalVenta = matrizVentas[i][6];


            System.out.printf("| %11s | %-11s | %-12s | %-15s | %-30s | %14s | %13s |\n",
                    idDoc, tipoDoc, fecha, rut, cliente, cantBoletos, totalVenta);
        }

        System.out.println("*-------------*-------------*--------------*-----------------*--------------------------------*----------------*---------------*");



    }

    private void listViajes(String[][] matrizViajes) {

        if (matrizViajes == null || matrizViajes.length == 0) {
            System.out.println("No hay viajes programados dentro del sistema.");
            return;
        }

        System.out.println("°*Listado de Viajes*°");
        System.out.println();

        System.out.println("*------------*--------------*----------*-----------------*-----------------*---------------*");

        System.out.printf("| %-10s | %-12s | %-8s | %-15s | %-15s | %-13s |\n",
                "ID VIAJE", "FECHA", "HORA", "ORIGEN", "DESTINO", "PATENTE BUS");

        System.out.println("|------------+--------------+----------+-----------------+-----------------+---------------|");

        for (int i = 0; i < matrizViajes.length; i++) {

            String idViaje = matrizViajes[i][0];
            String fecha = matrizViajes[i][1];
            String hora = matrizViajes[i][2];
            String origen = matrizViajes[i][3];
            String destino = matrizViajes[i][4];
            String patente = matrizViajes[i][5];

            System.out.printf("| %-10s | %-12s | %-8s | %-15s | %-15s | %-13s |\n",
                    idViaje, fecha, hora, origen, destino, patente);
        }

        System.out.println("*------------*--------------*----------*-----------------*-----------------*---------------*");

    }
}

