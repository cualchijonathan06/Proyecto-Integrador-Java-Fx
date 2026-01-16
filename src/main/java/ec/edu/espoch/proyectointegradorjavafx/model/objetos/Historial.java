package ec.edu.espoch.proyectointegradorjavafx.model.objetos;

public class Historial
{
    private String ambito;
    private String descripcion;
    private double a;
    private double b;
    private String resultado;

    public Historial(String ambito, String descripcion, double a, double b, String resultado)
    {
        this.ambito = ambito;
        this.descripcion = descripcion;
        this.a = a;
        this.b = b;
        this.resultado = resultado;
    }

    public String getAmbito()
    {
        return ambito;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public double getA()
    {
        return a;
    }

    public double getB()
    {
        return b;
    }

    public String getResultado()
    {
        return resultado;
    }
}
