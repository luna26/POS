/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos1;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Luna
 */
public class FXMLInventoryController implements Initializable {

    /*final ObservableList<ProductModel> dataProduct = FXCollections.observableArrayList(
            new ProductModel("Jose 1", "Manuel", "kaltwulx@blabla", "kaltwulx@blabla", "kaltwulx@blabla")
    );*/

    @FXML
    TableView<ProductModel> productTbl;
    @FXML
    TableColumn itemIdCol;
    @FXML
    TableColumn itemNameCol;
    @FXML
    TableColumn itemQtyCol;
    @FXML
    TableColumn itemPriceCol;
    @FXML
    TableColumn itemDescCol;

    private final ObservableList<ProductModel> dataProducts = FXCollections.observableArrayList(
            new ProductModel("Jacob", "Smith", "jacob.smith@example.com", "Jacob", "Jacob")
    );

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        itemIdCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("itemId")
        );
        itemNameCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("itemName")
        );
        itemPriceCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("price")
        );
        itemDescCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("desc")
        );
        itemQtyCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("qty")
        );

        try {
            productTbl.setItems(getProductsDB());//Agregamos los datos en la tabla, aquí la tabla ya muestra la información.
        } catch (SQLException ex) {
            Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList getProductsDB() throws SQLException {
        Connection connection = getConnectToDB();
        String query = "SELECT * FROM pos_inventory";
        ObservableList<ProductModel> dataPro = FXCollections.observableArrayList();
        
        try ( // create the java statement
                Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                 dataPro.add(new ProductModel(rs.getString("inv_id"), rs.getString("inv_name"), rs.getString("inv_price"), rs.getString("inv_desc"), rs.getString("inv_cant")));
            }
        }
        return dataPro;
    }

    public Connection getConnectToDB() {
        Connection connection;
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "rootJava", "rootJava");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}
