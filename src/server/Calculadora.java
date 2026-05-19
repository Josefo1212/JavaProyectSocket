package server;

public class Calculadora {
    @Operacion({"SUMA", "+"})
    public Double suma(double[] valores) {
        if (valores == null || valores.length == 0) {
            return null;
        }
        double total = 0.0;
        for (double valor : valores) {
            total += valor;
        }
        return total;
    }

    @Operacion({"RESTA", "-"})
    public Double resta(double[] valores) {
        if (valores == null || valores.length == 0) {
            return null;
        }
        double total = valores[0];
        for (int i = 1; i < valores.length; i++) {
            total -= valores[i];
        }
        return total;
    }

    @Operacion({"MULT", "*"})
    public Double mult(double[] valores) {
        if (valores == null || valores.length == 0) {
            return null;
        }
        double total = 1.0;
        for (double valor : valores) {
            total *= valor;
        }
        return total;
    }

    @Operacion({"DIV", "/"})
    public Double div(double[] valores) {
        if (valores == null || valores.length == 0) {
            return null;
        }
        double total = valores[0];
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] == 0.0) {
                return null;
            }
            total /= valores[i];
        }
        return total;
    }
}
