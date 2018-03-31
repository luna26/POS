/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Luna
 */
public class FXMLDashbaordController implements Initializable {

    @FXML
    private Button btnInventory;

    private TableView<ProductModel> tableProduct = new TableView<ProductModel>();
    private final ObservableList<ProductModel> data
            = FXCollections.observableArrayList(
                    new ProductModel("Jacob", "Smith", "jacob.smith@example.com", "Jacob", "Jacob")
            );

    EventHandler<ActionEvent> buttonInventoryHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                System.out.println("ABRIENDO INVENTARIO!!!!!!!");

                Parent inventory = FXMLLoader.load(getClass().getResource("FXMLInventory.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Inventario");
                stage.setScene(new Scene(inventory));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDashbaordController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnInventory.setOnAction(buttonInventoryHandler);
    }

}
