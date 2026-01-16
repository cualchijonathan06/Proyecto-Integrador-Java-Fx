package ec.edu.espoch.proyectointegradorjavafx.model.objetos;

public class Problema
{
    private Ambito ambito;
    private String descripcion;
    private String unidadX;
    private String unidadY;
    private Funcion funcion;
    private Optimizacion resultado;

    public Problema(Ambito ambito, String descripcion, String unidadX, String unidadY, Funcion funcion)
    {
        this.ambito = ambito;
        this.descripcion = descripcion;
        this.unidadX = unidadX;
        this.unidadY = unidadY;
        this.funcion = funcion;
    }

    public Ambito getAmbito()
    {
        return ambito;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public String getUnidadX()
    {
        return unidadX;
    }

    public String getUnidadY()
    {
        return unidadY;
    }

    public Funcion getFuncion()
    {
        return funcion;
    }

    public void setFuncion(Funcion funcion)
    {
        this.funcion = funcion;
    }

    public Optimizacion resolver()
    {
        resultado = funcion.encontrarExtremo();
        return resultado;
    }

    public Optimizacion getResultado()
    {
        return resultado;
    }
}
