package ec.edu.espoch.proyectointegradorjavafx.model.interfaces;

import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Problema;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Optimizacion;

public interface IProblemaOptimizacionDAO
{
    Optimizacion calcular(Problema problema);
    String detalleCalculo(Problema problema, Optimizacion resultado);
}
