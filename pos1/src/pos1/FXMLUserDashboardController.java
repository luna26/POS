/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luna
 */
public class FXMLUserDashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Button btnInvoiceUser;

    EventHandler<ActionEvent> btnInvoiceHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Parent inventory = FXMLLoader.load(getClass().getResource("FXMLInvoice.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Facturar");
                stage.setScene(new Scene(inventory));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDashbaordController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btnInvoiceUser.setOnAction(btnInvoiceHandler);
    }

}
