package ec.edu.espoch.proyectointegradorjavafx.controller.interfaces;

import ec.edu.espoch.proyectointegradorjavafx.controller.usercase.ControladorOptimizacion;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Ambito;
import ec.edu.espoch.proyectointegradorjavafx.model.objetos.Historial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OptimizacionViewController
{
    @FXML private ScrollPane spPrincipal;
    @FXML private TextField txtA;
    @FXML private TextField txtB;
    @FXML private Button btnLupa;
    @FXML private Button btnVerCalculo;
    @FXML private ComboBox<Ambito> cmbAmbito;
    @FXML private TextArea txtDescripcion;
    @FXML private Label lblResultado;
    @FXML private TableView<Historial> tblHistorial;
    @FXML private TableColumn<Historial, String> colAmbito;
    @FXML private TableColumn<Historial, String> colDescripcion;
    @FXML private TableColumn<Historial, Double> colA;
    @FXML private TableColumn<Historial, Double> colB;
    @FXML private TableColumn<Historial, String> colResultado;

    private final ObservableList<Historial> listaHistorial = FXCollections.observableArrayList();
    private final ControladorOptimizacion usecase = new ControladorOptimizacion();
    private ContextMenu menuLupa;

    @FXML
    public void initialize()
    {
        bloquearNumeros(txtA);
        bloquearNumeros(txtB);
        cmbAmbito.setItems(FXCollections.observableArrayList(Ambito.values()));
        cmbAmbito.setOnAction(e -> {
            usecase.reset();
            limpiarSalida();
        });
        tblHistorial.setItems(listaHistorial);
        colAmbito.setCellValueFactory(new PropertyValueFactory<>("ambito"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colA.setCellValueFactory(new PropertyValueFactory<>("a"));
        colB.setCellValueFactory(new PropertyValueFactory<>("b"));
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        configurarDobleClick(colDescripcion, "Descripción completa");
        configurarDobleClick(colResultado, "Resultado completo");
        crearMenuLupa();
        limpiarSalida();
    }

    private void limpiarSalida()
    {
        lblResultado.setText("");
        btnVerCalculo.setDisable(true);
    }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo)
    {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void bloquearNumeros(TextField campo)
    {
        campo.setOnKeyTyped(e -> {
            String c = e.getCharacter();
            if(!c.matches("[0-9\\.]"))
            {
                e.consume();
                mostrarAlerta("Advertencia", "Solo números (se aceptan decimales).", Alert.AlertType.WARNING);
            }
        });
    }

    @FXML
    private void calcular()
    {
        Ambito ambito = cmbAmbito.getValue();
        if(ambito == null)
        {
            mostrarAlerta("Aviso", "Seleccione un ámbito.", Alert.AlertType.WARNING);
            return;
        }
        String descripcion = txtDescripcion.getText();
        String analitico = usecase.calcular(ambito, descripcion);
        if(analitico == null || analitico.trim().isEmpty())
        {
            mostrarAlerta("Aviso", "No se pudo calcular.", Alert.AlertType.WARNING);
            return;
        }
        lblResultado.setText(analitico);
        btnVerCalculo.setDisable(false);
    }

    @FXML
    private void guardar()
    {
        String ultimo = usecase.getUltimoResultadoAnalitico();
        if(ultimo == null || ultimo.trim().isEmpty())
        {
            mostrarAlerta("Aviso", "Primero calcule antes de guardar.", Alert.AlertType.WARNING);
            return;
        }

        Ambito ambito = cmbAmbito.getValue();
        if(ambito == null)
        {
            mostrarAlerta("Aviso", "Seleccione un ámbito.", Alert.AlertType.WARNING);
            return;
        }

        Historial fila = usecase.armarFilaHistorial(ambito, txtDescripcion.getText());
        listaHistorial.add(fila);
        tblHistorial.refresh();
        tblHistorial.getSelectionModel().selectLast();
        tblHistorial.scrollTo(listaHistorial.size() - 1);
        irYResaltar(tblHistorial);
    }

    @FXML
    private void verCalculoMatematico()
    {
        String detalle = usecase.getDetalleCalculo();
        if(detalle == null || detalle.trim().isEmpty())
        {
            mostrarAlerta("Aviso", "Primero debe calcular.", Alert.AlertType.WARNING);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cálculo matemático");
        alert.setHeaderText(null);
        alert.setContentText(detalle);
        alert.getDialogPane().setMinWidth(650);
        alert.getDialogPane().setMinHeight(420);
        alert.showAndWait();
    }
    
    @FXML
    private void eliminarHistorial()
    {
        Historial seleccionado = tblHistorial.getSelectionModel().getSelectedItem();
        if(seleccionado == null)
        {
            mostrarAlerta("Advertencia", "Seleccione un registro del historial.", Alert.AlertType.WARNING);
            return;
        }

        if(pedirLoginParaEliminar())
        {
            listaHistorial.remove(seleccionado);
            mostrarAlerta("Listo", "Registro eliminado.", Alert.AlertType.INFORMATION);
        }
        else
        {
            mostrarAlerta("Aviso", "No autorizado.", Alert.AlertType.WARNING);
        }
    }

    private boolean pedirLoginParaEliminar()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ec/edu/espoch/proyectointegradorjavafx/login.fxml"));
            Parent root = loader.load();
            LoginViewController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Confirmar eliminación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            controller.setDialogStage(stage);
            stage.showAndWait();

            return controller.isAutenticado();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private void crearMenuLupa()
    {
        MenuItem verDescripcion = new MenuItem("Ver descripción");
        MenuItem verHistorial = new MenuItem("Ver historial");

        verDescripcion.setOnAction(e -> irYResaltar(txtDescripcion));
        verHistorial.setOnAction(e -> irYResaltar(tblHistorial));

        menuLupa = new ContextMenu(verDescripcion, verHistorial);
    }

    @FXML
    private void mostrarMenuLupa()
    {
        if(menuLupa != null)
        {
            menuLupa.show(btnLupa, Side.BOTTOM, 0, 0);
        }
    }

    private void irYResaltar(Control nodo)
    {
        if(nodo == null)
        {
            return;
        }

        txtDescripcion.getStyleClass().remove("resaltado");
        tblHistorial.getStyleClass().remove("resaltado");
        nodo.getStyleClass().add("resaltado");
        irA(nodo);
        nodo.requestFocus();
    }

    private void irA(Control nodo)
    {
        if(spPrincipal == null || spPrincipal.getContent() == null)
        {
            return;
        }
        double alturaTotal = spPrincipal.getContent().getBoundsInLocal().getHeight();
        double y = nodo.getBoundsInParent().getMinY();

        if(alturaTotal <= 0)
        {
            return;
        }
        spPrincipal.setVvalue(y / alturaTotal);
    }

    private void configurarDobleClick(TableColumn<Historial, String> columna, String titulo)
    {
        columna.setCellFactory(tc -> {
            TableCell<Historial, String> cell = new TableCell<Historial, String>() {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };

            cell.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2 && !cell.isEmpty())
                {
                    String texto = cell.getItem();
                    if(texto != null && !texto.trim().isEmpty())
                    {
                        mostrarDetalleCompleto(titulo, texto);
                    }
                }
            });
            return cell;
        });
    }

    private void mostrarDetalleCompleto(String titulo, String contenido)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        TextArea area = new TextArea(contenido);
        area.setWrapText(true);
        area.setEditable(false);
        alert.getDialogPane().setContent(area);
        alert.getDialogPane().setMinWidth(650);
        alert.getDialogPane().setMinHeight(400);
        alert.showAndWait();
    }
}
