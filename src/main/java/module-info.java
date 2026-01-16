module ec.edu.espoch.proyectointegradorjavafx
{
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.espoch.proyectointegradorjavafx.controller.interfaces to javafx.fxml;

    opens ec.edu.espoch.proyectointegradorjavafx.model.objetos to javafx.base;

    exports ec.edu.espoch.proyectointegradorjavafx;
}
