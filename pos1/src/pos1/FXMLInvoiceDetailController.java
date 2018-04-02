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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Luna
 */
public class FXMLInvoiceDetailController implements Initializable {

    @FXML
    Label dateLabel, invoiceNum, nameClient, addressId, idClient, telClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String idLastInvoice = "";
        idLastInvoice = "";
        getDate();
        try {
            idLastInvoice = getLastId();
            invoiceNum.setText(idLastInvoice);
            getInfoClient(idLastInvoice);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLInvoiceDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateLabel.setText(dateFormat.format(date));
    }

    public void getInfoClient(String idLastInvoice) throws SQLException {
        Connection connection = getConnectToDB();
        String queryID = "SELECT * FROM pos_invoice WHERE invoice_id = '"+idLastInvoice+"' ";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(queryID);
            while (rs.next()) {
                nameClient.setText(rs.getString("invoice_cust_name"));
                idClient.setText(rs.getString("invoice_cust_id"));
                addressId.setText(rs.getString("invoice_cust_address"));
                telClient.setText(rs.getString("invoice_cust_tel"));
            }
        }
        connection.close();
    }

    public void getInfoStore() {

    }

    public String getLastId() throws SQLException {
        String idInvoice = "";
        Connection connection = getConnectToDB();
        String queryID = "SELECT invoice_id FROM pos_invoice ORDER BY invoice_id DESC LIMIT 1";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(queryID);
            while (rs.next()) {
                idInvoice = rs.getString("invoice_id");
            }
        }
        connection.close();
        return idInvoice;
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
