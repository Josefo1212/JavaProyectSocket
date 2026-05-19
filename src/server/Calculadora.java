package server;

public class Calculadora {
    @Operacion({"SUMA", "+"})
    public Double suma(double a, double b) {
        return a + b;
    }

    @Operacion({"RESTA", "-"})
    public Double resta(double a, double b) {
        return a - b;
    }

    @Operacion({"MULT", "*"})
    public Double mult(double a, double b) {
        return a * b;
    }

    @Operacion({"DIV", "/"})
    public Double div(double a, double b) {
        if (b == 0.0) {
            return null;
        }
        return a / b;
    }
}
