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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    TableColumn itemIdCol, itemNameCol, itemQtyCol, itemPriceCol, itemDescCol;
    @FXML
    Button btnAddProduct, btnDeleteProduct, btnUpdateProduct;

    //FIELDS FOR NEW PRODUCT
    @FXML
    TextField txtCod, txtName, txtCant, txtPrice;

    @FXML
    TextArea txtDesc;

    /*private final ObservableList<ProductModel> dataProducts = FXCollections.observableArrayList(
            new ProductModel("Jacob", "Smith", "jacob.smith@example.com", "Jacob", "Jacob")
    );*/
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

        btnAddProduct.setOnAction(addProductBtnHandler);
        btnDeleteProduct.setOnAction(deleteProductBtnHandler);
        btnUpdateProduct.setOnAction(UpdateProductBtnHandler);

        productTbl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (productTbl.getSelectionModel().getSelectedItem() != null) {
                    TableViewSelectionModel selectionModel = productTbl.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val = tablePosition.getTableColumn().getCellData(newValue);
                    try {
                        loadItem(val);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void loadItem(Object val) throws SQLException {
        String codeSelected = String.valueOf(val);
        Connection connection = getConnectToDB();
        String query = "SELECT * FROM pos_inventory WHERE inv_id='" + codeSelected + "'";

        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                txtName.setText(rs.getString("inv_name"));
                txtDesc.setText(rs.getString("inv_desc"));
                txtPrice.setText(rs.getString("inv_price"));
                txtCod.setText(rs.getString("inv_id"));
                txtCant.setText(rs.getString("inv_cant"));
            }
        }
    }

    public void deleteItem() throws SQLException {
        Connection connection = getConnectToDB();
        String query = "DELETE FROM pos_inventory WHERE inv_id = '" + txtCod.getText() + "' ";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            st.executeUpdate(query);
            productTbl.getItems().clear();
            productTbl.getItems().addAll(getProductsDB());
            txtName.setText("");
            txtDesc.setText("");
            txtPrice.setText("");
            txtCod.setText("");
            txtCant.setText("");
            connection.close();
        }

    }

    EventHandler<ActionEvent> addProductBtnHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                addNewProduct();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<ActionEvent> UpdateProductBtnHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                updateProductDB();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<ActionEvent> deleteProductBtnHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                deleteItem();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public void addNewProduct() throws SQLException {
        Connection connection = getConnectToDB();
        String query = "INSERT INTO pos_inventory (inv_code,inv_name,inv_desc,inv_cant,inv_price) VALUES(" + txtCod.getText() + "," + "\"" + txtName.getText() + "\"" + "," + "\"" + txtDesc.getText() + "\"" + "," + txtCant.getText() + "," + txtPrice.getText() + ");";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            st.executeUpdate(query);
            productTbl.getItems().clear();
            productTbl.getItems().addAll(getProductsDB());
            txtName.setText("");
            txtDesc.setText("");
            txtPrice.setText("");
            txtCod.setText("");
            txtCant.setText("");
            connection.close();
        }
    }

    public void updateProductDB() throws SQLException {
        String name, code, desc, cant, price, idProduct;
        name = txtName.getText();
        code = txtCod.getText();
        desc = txtDesc.getText();
        cant = txtCant.getText();
        price = txtPrice.getText();

        Connection connection = getConnectToDB();
        String query = "UPDATE pos_inventory set inv_code = '" + code + "', inv_name = '" + name + "',  inv_desc = '" + desc + "', inv_cant = '" + cant + "', inv_price = '" + price + "' where inv_id = '" + code + "'";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            st.executeUpdate(query);
        }
        txtName.setText("");
        txtCod.setText("");
        txtDesc.setText("");
        txtCant.setText("");
        txtPrice.setText("");
        productTbl.getItems().clear();
        productTbl.getItems().addAll(getProductsDB());
        connection.close();
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
        connection.close();
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
