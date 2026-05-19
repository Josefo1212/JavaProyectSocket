package server;

public class Calculadora {
    public static Double calcular(String operation, double a, double b) {
        switch (operation.toUpperCase()) {
            case "SUMA": case "+": return a + b;
            case "RESTA": case "-": return a - b;
            case "MULT": case "*": return a * b;
            case "DIV": case "/":
                if (b == 0.0) return null; // Previene indeterminación
                return a / b;
            default: return null;
        }
    }
}

