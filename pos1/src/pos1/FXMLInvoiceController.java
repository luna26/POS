/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos1;

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luna
 */
public class FXMLInvoiceController implements Initializable {

    @FXML
    TableView<InvoiceModel> invoiceTbl;

    @FXML
    TableColumn codeCol, nameCol, uniPCol, cantCol, totalCant;

    @FXML
    Button btnAddProductInvoice, btnGenerateInvoice;

    @FXML
    TextField txtCodeToAdd, txtnameCust, txtidCus, txttelCus;

    @FXML
    Label totalWithoutIV, IVLabel, totaLabel;

    @FXML
    TextArea txtdirCus;

    ObservableList<InvoiceModel> dataInvoice = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        codeCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("code")
        );
        nameCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("name")
        );
        uniPCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("price")
        );
        cantCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("cant")
        );
        totalCant.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("total")
        );

        btnAddProductInvoice.setOnAction(btnInvoiceHandler);
        btnGenerateInvoice.setOnAction(btnInvoiceGenerateHandler);
    }

    EventHandler<ActionEvent> btnInvoiceHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                addProductToInvoice();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<ActionEvent> btnInvoiceGenerateHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                generateInvoice();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLInvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public void generateInvoice() throws SQLException, IOException {
        String customerName, customerAddress, customerTel, customerId, total, totalIV, IV;
        customerName = txtnameCust.getText();
        customerAddress = txtdirCus.getText();
        customerTel = txttelCus.getText();
        customerId = txtidCus.getText();
        total = totalWithoutIV.getText();
        totalIV = totaLabel.getText();
        IV = IVLabel.getText();
        String idInvoice;
        idInvoice = "";
        Connection connection = getConnectToDB();
        String query = "INSERT INTO pos_invoice (invoice_cust_name,invoice_cust_address,invoice_cust_id,invoice_cust_tel,invoice_iv, invoice_total, invoice_total_iv) VALUES(" + "\"" + customerName + "\"" + "," + "\"" + customerAddress + "\"" + "," + "\"" + customerId + "\"" + "," + customerTel + "," + total + "," + "\"" + totalIV + "\"" + ", " + "\"" + IV + "\"" + ");";

        try ( // create the java statement
                Statement st = connection.createStatement()) {
            st.executeUpdate(query);
        }

        String queryID = "SELECT invoice_id FROM pos_invoice ORDER BY invoice_id DESC LIMIT 1";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(queryID);
            while (rs.next()) {
                idInvoice = rs.getString("invoice_id");
                System.out.println("EL ID ES " + idInvoice);
                saveItemsInvoice(idInvoice);
            }
        }
        connection.close();
    }

    public void saveItemsInvoice(String idInvoice) throws SQLException, IOException {
        Connection connection = getConnectToDB();

        for (int i = 0; i < dataInvoice.size(); i++) {
            String query = "INSERT INTO pos_invoice_detail (invoice_id_product,detail_id_invoice,detail_cant,detail_total) VALUES(" + "\"" + dataInvoice.get(i).getCode() + "\"" + "," + "\"" + idInvoice + "\"" + "," + "\"" + dataInvoice.get(i).getCant() + "\"" + "," + "\"" + dataInvoice.get(i).getTotal() + "\"" + ");";
            try ( // create the java statement
                    Statement st = connection.createStatement()) {
                st.executeUpdate(query);
            }
        }
        openLastInvoice();
        connection.close();
    }

    public void openLastInvoice() throws IOException {
        Parent dashbaord = FXMLLoader.load(getClass().getResource("FXMLInvoiceDetail.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Detalle de factura");
        stage.setScene(new Scene(dashbaord));
        stage.show();
    }

    public void addProductToInvoice() throws SQLException {
        String code;
        Boolean flag;
        flag = false;
        code = txtCodeToAdd.getText();
        int cantProd, priceTotal;
        cantProd = 0;
        priceTotal = 0;

        Connection connection = getConnectToDB();
        String query = "SELECT * FROM pos_inventory WHERE inv_id = '" + code + "' ";

        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            int cant = dataInvoice.size();
            for (int i = 0; i < cant; i++) {
                if (dataInvoice.get(i).code.getValue().equals(code)) {
                    cantProd = Integer.parseInt(dataInvoice.get(i).cant.getValue());
                    cantProd = cantProd + 1;
                    priceTotal = cantProd * Integer.parseInt(dataInvoice.get(i).price.getValue());
                    dataInvoice.add(new InvoiceModel(dataInvoice.get(i).code.getValue(), dataInvoice.get(i).name.getValue(), dataInvoice.get(i).price.getValue(), Integer.toString(cantProd), Integer.toString(priceTotal)));
                    dataInvoice.remove(i);
                    flag = true;
                } else {
                    flag = false;
                }
            }
            if (flag == false) {
                while (rs.next()) {
                    dataInvoice.add(new InvoiceModel(rs.getString("inv_id"), rs.getString("inv_name"), rs.getString("inv_price"), "1", rs.getString("inv_price")));
                }
            }
            fillTotal();
            invoiceTbl.setItems(dataInvoice);
        }
    }

    public void fillTotal() {
        //TOTAL WITHOUT IV
        int total;
        double IV, totalIV;
        total = 0;
        IV = 0;
        for (int i = 0; i < dataInvoice.size(); i++) {
            total = total + Integer.parseInt(dataInvoice.get(i).total.getValue());
        }
        totalWithoutIV.setText(Integer.toString(total));

        //TOTAL IV
        IV = total * 0.13;
        IVLabel.setText(String.valueOf(IV));

        //TOTAL
        totalIV = IV + total;
        totaLabel.setText(String.valueOf(totalIV));

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
