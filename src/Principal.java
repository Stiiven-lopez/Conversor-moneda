import conversion.OperacionCambioDivisas;
import menu.ManejadorMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal que inicia el Convertidor de Monedas.
 */
public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("************************************************************");
        System.out.println("Bienvenidos al Convertidor de Monedas - Challenge Backend ONE.");
        try {
            OperacionCambioDivisas operacion = new OperacionCambioDivisas(); // Crear una instancia de OperacionCambioDivisas
            do {
                ManejadorMenu.mostrarMenu();
                int opcion = lectura.nextInt();
                ManejadorMenu.ejecutarOpcion(opcion, operacion, lectura); // Pasar OperacionCambioDivisas y Scanner
            } while (true);
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido.");
        } finally {
            lectura.close();
        }
    }
}