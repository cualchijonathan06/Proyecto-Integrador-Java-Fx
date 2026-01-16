package ec.edu.espoch.proyectointegradorjavafx.controller.interfaces;

import ec.edu.espoch.proyectointegradorjavafx.controller.usercase.ControladorOptimizacion;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Ambito;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Historial;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class OptimizacionViewController
{
    @FXML private ScrollPane spPrincipal;
    @FXML private Button btnLupa;

    @FXML private ComboBox<Ambito> cmbAmbito;
    @FXML private Label lblDescripcionTitulo;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lblResultado;
    @FXML private Button btnVerCalculo;

    @FXML private TableView<Historial> tblHistorial;
    @FXML private TableColumn<Historial, String> colAmbito;
    @FXML private TableColumn<Historial, String> colDescripcion;
    @FXML private TableColumn<Historial, Double> colA;
    @FXML private TableColumn<Historial, Double> colB;
    @FXML private TableColumn<Historial, String> colResultado;

    private ObservableList<Historial> listaHistorial = FXCollections.observableArrayList();
    private ControladorOptimizacion usecase = new ControladorOptimizacion();

    @FXML
    public void initialize()
    {
        configurarComboAmbito();
        configurarTabla();
        reiniciarVista();
    }

    private void configurarComboAmbito()
    {
        cmbAmbito.setItems(FXCollections.observableArrayList(Ambito.values()));

        cmbAmbito.setConverter(new StringConverter<Ambito>()
        {
            @Override
            public String toString(Ambito a)
            {
                if(a == null)
                {
                    return "";
                }

                if(a == Ambito.SOFTWARE)
                {
                    return "Software";
                }
                if(a == Ambito.EDUCACION)
                {
                    return "Educación";
                }
                if(a == Ambito.INDUSTRIAL)
                {
                    return "Industrial";
                }
                return "Salud";
            }

            @Override
            public Ambito fromString(String s)
            {
                return null;
            }
        });

        cmbAmbito.setOnAction(e ->
        {
            usecase.reset();
            reiniciarVista();
        });
    }

    private void configurarTabla()
    {
        tblHistorial.setItems(listaHistorial);

        colAmbito.setCellValueFactory(new PropertyValueFactory<>("ambito"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colA.setCellValueFactory(new PropertyValueFactory<>("a"));
        colB.setCellValueFactory(new PropertyValueFactory<>("b"));
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
    }

    private void reiniciarVista()
    {
        lblResultado.setText("");
        btnVerCalculo.setDisable(true);
        limpiarResaltados();
    }

    @FXML
    private void calcular()
    {
        Ambito ambito = cmbAmbito.getValue();

        if(ambito == null)
        {
            alerta("Seleccione un ámbito.");
            return;
        }

        String descripcion = txtDescripcion.getText();

        String analitico = usecase.calcular(ambito, descripcion);

        if(analitico == null || analitico.trim().isEmpty())
        {
            alerta("No se pudo calcular. Revise el ámbito o los datos.");
            return;
        }

        lblResultado.setText(analitico);
        btnVerCalculo.setDisable(false);
    }

    @FXML
    private void guardar()
    {
        if(usecase.getUltimoResultadoAnalitico() == null || usecase.getUltimoResultadoAnalitico().isEmpty())
        {
            alerta("Primero debe calcular el resultado antes de guardar.");
            return;
        }

        Ambito ambito = cmbAmbito.getValue();

        if(ambito == null)
        {
            alerta("Seleccione un ámbito.");
            return;
        }

        String descripcion = txtDescripcion.getText();

        Historial fila = usecase.armarFilaHistorial(ambito, descripcion);
        listaHistorial.add(fila);

        seleccionarUltimaFila();
        resaltarYEnfocar(tblHistorial);
    }

    private void seleccionarUltimaFila()
    {
        tblHistorial.refresh();
        tblHistorial.getSelectionModel().selectLast();
        tblHistorial.scrollTo(listaHistorial.size()-1);
    }

    @FXML
    private void verCalculoMatematico()
    {
        String detalle = usecase.getDetalleCalculo();

        if(detalle == null || detalle.trim().isEmpty())
        {
            alerta("Primero debe calcular.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cálculo matemático");
        alert.setHeaderText("Detalle de la optimización");
        alert.setContentText(detalle);
        alert.getDialogPane().setMinWidth(650);
        alert.getDialogPane().setMinHeight(420);
        alert.showAndWait();
    }

    @FXML
    private void mostrarMenuLupa()
    {
        ContextMenu menu = new ContextMenu();

        MenuItem irDescripcion = new MenuItem("Ir a: Descripción del problema");
        MenuItem irTabla = new MenuItem("Ir a: Tabla / Historial");
        MenuItem irCalculo = new MenuItem("Ver cálculo matemático");

        irDescripcion.setOnAction(e ->
        {
            limpiarResaltados();
            if(lblDescripcionTitulo != null)
            {
                lblDescripcionTitulo.getStyleClass().add("titulo-buscado");
                quitarTituloLuego(lblDescripcionTitulo);
            }
            resaltarYEnfocar(txtDescripcion);
            txtDescripcion.requestFocus();
        });

        irTabla.setOnAction(e ->
        {
            limpiarResaltados();
            resaltarYEnfocar(tblHistorial);
            tblHistorial.requestFocus();
        });

        irCalculo.setOnAction(e -> verCalculoMatematico());

        menu.getItems().addAll(irDescripcion, irTabla, irCalculo);
        menu.show(btnLupa, Side.BOTTOM, 0, 0);
    }

    private void resaltarYEnfocar(Control nodo)
    {
        nodo.getStyleClass().add("resaltado");
        irASeccion(nodo);
    }

    private void irASeccion(Control nodo)
    {
        if(spPrincipal == null || spPrincipal.getContent() == null)
        {
            return;
        }

        double alturaTotal = spPrincipal.getContent().getBoundsInLocal().getHeight();
        double y = nodo.getLayoutY();

        if(alturaTotal <= 0)
        {
            return;
        }

        spPrincipal.setVvalue(y / alturaTotal);
    }

    private void limpiarResaltados()
    {
        txtDescripcion.getStyleClass().remove("resaltado");
        tblHistorial.getStyleClass().remove("resaltado");

        if(lblDescripcionTitulo != null)
        {
            lblDescripcionTitulo.getStyleClass().remove("titulo-buscado");
        }
    }

    private void quitarTituloLuego(Label lbl)
    {
        PauseTransition pausa = new PauseTransition(Duration.seconds(1.2));
        pausa.setOnFinished(ev -> lbl.getStyleClass().remove("titulo-buscado"));
        pausa.play();
    }

    private void alerta(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
