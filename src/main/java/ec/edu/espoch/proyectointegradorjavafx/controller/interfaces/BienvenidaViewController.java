package ec.edu.espoch.proyectointegradorjavafx.controller.interfaces;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BienvenidaViewController
{
    @FXML
    private Button btnSalir;

    @FXML
    private Button btnContinuar;

    @FXML
    private Label lblEstado;

    @FXML
    private void clicSalir()
    {
        System.exit(0);
    }

    @FXML
    private void clicContinuar() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/ec/edu/espoch/proyectointegradorjavafx/app.fxml"));
        btnContinuar.getScene().setRoot(root);
    }
}

