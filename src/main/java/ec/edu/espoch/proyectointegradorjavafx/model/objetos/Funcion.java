package ec.edu.espoch.proyectointegradorjavafx.model.objetos;

public class Funcion {

    private Termino[] terminos = new Termino[2];

    public Funcion(Termino[] terminos) {
        this.terminos[0] = terminos[0];
        this.terminos[1] = terminos[1];
    }

    public double evaluar(double x) {
        double suma = 0;

        for (int i = 0; i <= 1; i++) {
            double c = terminos[i].getCoeficiente();
            int e = terminos[i].getExponente();

            suma = suma + (c * Math.pow(x, e));
        }

        return suma;
    }

    public Funcion derivadaPrimera() {
        Termino[] deriv = new Termino[2];

        for (int i = 0; i <= 1; i++) {
            double c = terminos[i].getCoeficiente();
            int e = terminos[i].getExponente();

            if (e != 0) {
                double nuevoCoef = c * e;
                int nuevoExp = e - 1;

                deriv[i] = new Termino(nuevoCoef, nuevoExp);
            } else {
                deriv[i] = new Termino(0, 0);
            }
        }

        return new Funcion(deriv);
    }

    public Funcion derivadaSegunda() {
        Funcion primera = derivadaPrimera();
        return primera.derivadaPrimera();
    }

    public Optimizacion encontrarExtremo() {
        Funcion d1 = derivadaPrimera();
        Funcion d2 = derivadaSegunda();

        double m = 0;
        double n = 0;

        for (int i = 0; i <= 1; i++) {
            int exp = d1.terminos[i].getExponente();
            double coef = d1.terminos[i].getCoeficiente();

            if (exp == 1) {
                m = coef;
            } else {
                if (exp == 0) {
                    n = coef;
                }
            }
        }

        double xOptimo = 0;

        if (m != 0) {
            xOptimo = (-n) / m;
        }

        double valorOptimo = evaluar(xOptimo);

        double d2valor = d2.evaluar(xOptimo);

        TipoExtremo tipo = TipoExtremo.MINIMO;

        if (d2valor < 0) {
            tipo = TipoExtremo.MAXIMO;
        } else {
            tipo = TipoExtremo.MINIMO;
        }

        return new Optimizacion(xOptimo, valorOptimo, tipo);
    }
}
