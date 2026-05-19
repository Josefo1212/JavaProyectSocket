package client;

import java.util.Scanner;

public class InterfazUsuario {
    private final Scanner scanner;

    public InterfazUsuario() {
        this.scanner = new Scanner(System.in);
    }

    public String pedirOperacion() {
        System.out.print("Operacion (SUMA, RESTA, MULT, DIV) o SALIR: ");
        return scanner.nextLine().trim();
    }

    public String pedirCantidadParametros() {
        System.out.print("Cantidad de parametros (minimo 2): ");
        return scanner.nextLine().trim();
    }

    public String pedirNumero(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public void cerrar() {
        scanner.close();
    }
}
