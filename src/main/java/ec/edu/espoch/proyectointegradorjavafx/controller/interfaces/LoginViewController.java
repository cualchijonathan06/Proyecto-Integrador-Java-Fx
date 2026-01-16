package ec.edu.espoch.proyectointegradorjavafx.controller.interfaces;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController
{
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Button btnIngresar;
    @FXML private Label lblMensaje;

    @FXML
    private void ingresar() throws IOException
    {
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if(usuario == null || usuario.trim().isEmpty())
        {
            lblMensaje.setText("Ingrese el usuario.");
            return;
        }

        if(contrasena == null || contrasena.trim().isEmpty())
        {
            lblMensaje.setText("Ingrese la contraseña.");
            return;
        }

        if(usuario.equals("admin") && contrasena.equals("123"))
        {
            Parent root = FXMLLoader.load(getClass().getResource("/ec/edu/espoch/proyectointegradorjavafx/app.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Proyecto Integrador - Optimización");
            stage.show();
        }
        else
        {
            lblMensaje.setText("Usuario o contraseña incorrectos.");
        }
    }
}
