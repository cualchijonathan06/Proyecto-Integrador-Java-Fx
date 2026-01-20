package ec.edu.espoch.proyectointegradorjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
@Override
public void start(Stage stage)
{
    try
    {
        Parent root = loadFXML("bienvenida");   // ANTES: "login"
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Proyecto Integrador - Optimización");
        stage.show();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}


    private Parent loadFXML(String fxml) throws Exception
    {
        String ruta = "/ec/edu/espoch/proyectointegradorjavafx/" + fxml + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        return loader.load();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
