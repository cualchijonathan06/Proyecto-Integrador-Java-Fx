/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espoch.proyectointegradorjavafx.model.objetos;

/**
 *
 * @author GIGABYTE
 */
public class Termino {
     private double coeficiente;
    private int exponente;

    public Termino(double coeficiente,int exponente)
    {
        this.coeficiente = coeficiente;
        this.exponente = exponente;
    }

    public double getCoeficiente()
    {
        return coeficiente;
    }

    public void setCoeficiente(double coeficiente)
    {
        this.coeficiente = coeficiente;
    }

    public int getExponente()
    {
        return exponente;
    }

    public void setExponente(int exponente)
    {
        this.exponente = exponente;
    }
}
