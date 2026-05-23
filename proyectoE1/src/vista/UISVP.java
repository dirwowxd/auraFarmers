package vista;

import controlador.*;
import excepciones.SistemaVentaPasajesException;
import utilidades.Rut;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UISVP {

    Scanner sc = new Scanner(System.in);
    ControladorEmpresas controlador = ControladorEmpresas.getInstance();
    SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();

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

    private void listPasajerosViaje() {

    }

    private void listViajes() {

    }

    private void listVentas() {

    }

    private void venderPasajes() {

    }

    private void createViaje() {
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
        String rut = sc.nextLine();

        System.out.print("Nombre : ");
        String nombre = sc.nextLine();

        System.out.print("url : ");
        String url = sc.nextLine();

        try {
            Rut rutAhoraSi = Rut.of(rut);

            this.controlador.createEmpresa(rutAhoraSi, nombre, url);

            System.out.println("Empresa creada exitosamente");

        } catch (SistemaVentaPasajesException e) {
            System.out.println("Error: " + e.getMessage());

        }
    }

}
