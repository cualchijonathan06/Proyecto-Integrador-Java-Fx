package ec.edu.espoch.proyectointegradorjavafx.model.implementacion;

import ec.edu.espoch.proyectointegradorjavafx.model.interfaces.IProblemaOptimizacionDAO;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Problema;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Optimizacion;

public class ProblemaOptimizacionDAO implements IProblemaOptimizacionDAO {

    @Override
    public Optimizacion calcular(Problema problema) {
        return problema.getFuncion().encontrarExtremo();
    }

    @Override
    public String detalleCalculo(Problema problema, Optimizacion resultado) {
 
        double xOpt = resultado.getXOptimo();
        double valOpt = resultado.getValorOptimo();

        return "Detalle de la optimización\n\n"
                + "Modelo de ganancia:\n"
                + "G(x) = ax - bx^2\n\n"
                + "1) Derivada:\n"
                + "G'(x) = a - 2bx\n\n"
                + "2) Igualamos a cero:\n"
                + "a - 2bx = 0\n\n"
                + "3) Despejamos:\n"
                + "x* = a / (2b)\n\n"
                + "4) Punto óptimo:\n"
                + "x* = " + xOpt + "\n\n"
                + "5) Evaluamos G(x*):\n"
                + "G(x*) = (a^2) / (4b)\n"
                + "Valor óptimo = " + valOpt + "\n\n"
                + "Conclusión: Es un MÁXIMO porque el término -bx^2 hace la parábola hacia abajo.";
    }

}
