package ec.edu.espoch.proyectointegradorjavafx.model.objetos;

public class Optimizacion
{
    private double xOptimo;
    private double valorOptimo;
    private TipoExtremo tipoExtremo;

    public Optimizacion()
    {
    }

    public Optimizacion(double xOptimo,double valorOptimo,TipoExtremo tipoExtremo)
    {
        this.xOptimo = xOptimo;
        this.valorOptimo = valorOptimo;
        this.tipoExtremo = tipoExtremo;
    }

    public double getXOptimo()
    {
        return xOptimo;
    }

    public double getValorOptimo()
    {
        return valorOptimo;
    }

    public TipoExtremo getTipoExtremo()
    {
        return tipoExtremo;
    }
}
