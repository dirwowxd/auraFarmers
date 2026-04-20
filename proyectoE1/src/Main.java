import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===========================");
        System.out.println("...::: MENU PRINCIPAL:::...");
        System.out.println("                             ");
        System.out.print("1) Crear Cliente ");
        System.out.print("2) Crear Bus ");
        System.out.print("3) Crear Viaje ");
        System.out.print("4) Vender Pasaje ");
        System.out.print("5) Lista de Pasajeros ");
        System.out.print("6) Lista de Ventas ");
        System.out.print("7) Lista de Viajes ");
        System.out.print("8) Consulta Viajes disponibles por fecha ");
        System.out.print("9) salir ");
        int opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("...::: Crear nuevo Cliente :::...");
                System.out.println("  ");
                System.out.print("Rut[1] o Pasaporte [2] : ");
                String rut1 = sc.next();
                System.out.print("R.U.T:  ");
                String rut2 = sc.next();
                System.out.print("SR.[1] O SRA.[2] : ");
                System.out.print("Nombres : ");
                String nombres = sc.next();
                System.out.println("Apellido Paterno : ");
                String apellido_paterno = sc.next();
                System.out.println("Apellido Materno : ");
                String apellido_materno = sc.next();
                System.out.println("Telefono Movil : ");
                int telefono_movil = sc.nextInt();
                System.out.println(" Email : ");
                String email = sc.next();

                //codigo de verificacion ( o metodo que se llame noma y verifique )

                System.out.println("...::: Cliente guardado exitosamente:::...");

            case 2:
                System.out.println("...::: Crear Bus :::...");
                System.out.println("  ");
                System.out.print("Patente : ");
                String patente = sc.next();
                System.out.print("Marca : ");
                String marca = sc.next();
                System.out.print("Modelo : ");
                String modelo = sc.next();
                System.out.print("Numero de asientos : ");
                int numero_asientos = sc.nextInt();
                System.out.println("     ");

                //codigo de verificacion ( o metodo que se llame noma y verifique )

                System.out.println("...::: Bus guardado exitosamente:::...");
                case 3:
                    System.out.println("...::: Creacion de un nuevo Viaje :::...");
                    System.out.println("  ");
                    System.out.print("Fecha[dd/mm/yyyy/] : ");
                    LocalDate fechaViaje = LocalDate.parse(sc.next());
                    System.out.print("Precio : ");
                    double precio = sc.nextDouble();
                    System.out.print("Patente Bus : ");
                    String patenteBus = sc.next();
                    System.out.println("     ");

                    //codigo de verificacion ( o metodo que se llame noma y verifique )

                    System.out.println("...:::Viaje guardado exitosamente:::...");


        }
    }
}
