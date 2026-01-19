package ec.edu.espoch.proyectointegradorjavafx.controller.usercase;

import ec.edu.espoch.proyectointegradorjavafx.model.implementacion.ProblemaOptimizacionDAO;
import ec.edu.espoch.proyectointegradorjavafx.model.interfaces.IProblemaOptimizacionDAO;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Ambito;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Funcion;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Historial;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Optimizacion;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Problema;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Termino;

public class ControladorOptimizacion {

    private double aInterno;
    private double bInterno;

    private String detalleCalculo;
    private String ultimoResultadoAnalitico;

    private IProblemaOptimizacionDAO dao;

    public ControladorOptimizacion() {
        dao = new ProblemaOptimizacionDAO();
        aInterno = 0;
        bInterno = 0;
        detalleCalculo = "";
        ultimoResultadoAnalitico = "";
    }

    public void reset() {
        detalleCalculo = "";
        ultimoResultadoAnalitico = "";
        aInterno = 0;
        bInterno = 0;
    }

    public String calcular(Ambito ambito, String descripcion) {
        if (ambito == null) {
            return "";
        }

        cargarCoeficientesInternos(ambito);

        if (bInterno == 0) {
            return "";
        }

        Termino[] terminos = new Termino[2];
        terminos[0] = new Termino(aInterno, 1);
        terminos[1] = new Termino(-bInterno, 2);

        Funcion funcion = new Funcion(terminos);

        Problema problema = new Problema(
                ambito,
                descripcion,
                "x",
                "USD",
                funcion
        );

        Optimizacion r = dao.calcular(problema);

        double xOptimo = redondear(r.getXOptimo(), 2);
        double gananciaMax = redondear(r.getValorOptimo(), 2);

        String analitico = construirRespuestaAnalitica(ambito, xOptimo, gananciaMax);

        ultimoResultadoAnalitico = analitico;
        detalleCalculo = dao.detalleCalculo(problema, r);

        return analitico;
    }

    public String getDetalleCalculo() {
        return detalleCalculo;
    }

    public String getUltimoResultadoAnalitico() {
        return ultimoResultadoAnalitico;
    }

    public Historial armarFilaHistorial(Ambito ambito, String descripcion) {
        return new Historial(
                ambito.toString(),
                descripcion,
                aInterno,
                bInterno,
                ultimoResultadoAnalitico
        );
    }

    private void cargarCoeficientesInternos(Ambito ambito) {
        if (ambito == null) {
            aInterno = 0;
            bInterno = 0;
            return;
        }

        if (ambito == Ambito.SOFTWARE) {
            aInterno = 20;
            bInterno = 2;
        } else {
            if (ambito == Ambito.EDUCACION) {
                aInterno = 24;
                bInterno = 3;
            } else {
                if (ambito == Ambito.INDUSTRIAL) {
                    aInterno = 30;
                    bInterno = 5;
                } else {
                    aInterno = 16;
                    bInterno = 2;
                }
            }
        }
    }

    private String construirRespuestaAnalitica(Ambito ambito, double xOptimo, double gananciaMax) {
        String xBonito = formatearEnteroSiSePuede(xOptimo);

        if (ambito == Ambito.SOFTWARE) {
            return " Se deben vender " + xBonito + " licencias para obtener una ganancia máxima de " + gananciaMax + " USD.";
        } else {
            if (ambito == Ambito.EDUCACION) {
                return " La ganancia máxima se obtiene con " + xBonito + " estudiantes y es de " + gananciaMax + " USD.";
            } else {
                if (ambito == Ambito.INDUSTRIAL) {
                    return " La producción óptima es de " + xBonito + " unidades, con una ganancia máxima de " + gananciaMax + " USD.";
                } else {
                    return " La clínica maximiza su ganancia atendiendo a " + xBonito + " pacientes, con una ganancia de " + gananciaMax + " USD.";
                }
            }
        }
    }

    private double redondear(double valor, int decimales) {
        double factor = Math.pow(10, decimales);
        return Math.round(valor * factor) / factor;
    }

    private String formatearEnteroSiSePuede(double x) {
        if (x == Math.floor(x)) {
            return String.valueOf((int) x);
        }
        return String.valueOf(x);
    }
    
}
