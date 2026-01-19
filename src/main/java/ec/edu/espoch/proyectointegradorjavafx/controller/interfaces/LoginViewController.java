package ec.edu.espoch.proyectointegradorjavafx.controller.interfaces;

import ec.edu.espoch.proyectointegradorjavafx.controller.usercase.LoginUseCase;
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

    private boolean autenticado = false;
    private Stage dialogStage;

    public void setDialogStage(Stage stage)
    {
        this.dialogStage = stage;
    }

    public boolean isAutenticado()
    {
        return autenticado;
    }

    @FXML
    private void ingresar()
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

        LoginUseCase useCase = new LoginUseCase();

        if(useCase.ingresar(usuario, contrasena))
        {
            autenticado = true;

            if(dialogStage != null)
            {
                dialogStage.close();
            }
        }
        else
        {
            lblMensaje.setText("Usuario o contraseña incorrectos.");
        }
    }
}
