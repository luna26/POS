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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Luna
 */
public class FXMLInfoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField nameInfo, telInfo;

    @FXML
    Label numStore;

    @FXML
    TextArea dirInfo;

    @FXML
    Button btnUpdateInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            btnUpdateInfo.setOnAction(buttonUpdateHandler);
            getInfoStore();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    EventHandler<ActionEvent> buttonUpdateHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                updateInfoDB();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public void updateInfoDB() throws SQLException {
        String name, tel, dir, storeNum;
        name = nameInfo.getText();
        tel = telInfo.getText();
        dir = dirInfo.getText();
        storeNum = numStore.getText();

        Connection connection = getConnectToDB();
        //, info_num = '"+telInfo+"', info_address = '"+dirInfo+"'
        String query = "UPDATE pos_info set info_name = '"+name+"', info_num = '"+tel+"',  info_address = '"+dir+"' where info_id = '" + storeNum + "'";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
                st.executeUpdate(query);
        }
        connection.close();
    }

    public void getInfoStore() throws SQLException {
        String storeNum, storeName, storeDir, storeTel;
        storeNum = numStore.getText();
        storeName = "";
        storeDir = "";
        storeTel = "";

        Connection connection = getConnectToDB();
        String query = "SELECT * FROM pos_info WHERE info_id='" + storeNum + "'";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                storeName = rs.getString("info_name");
                storeDir = rs.getString("info_address");
                storeTel = rs.getString("info_num");
            }
            setInfoStore(storeName, storeDir, storeTel);
        }
        connection.close();
    }

    public void setInfoStore(String name, String dir, String tel) {
        nameInfo.setText(name);
        dirInfo.setText(dir);
        telInfo.setText(tel);
    }

    public Connection getConnectToDB() {
        Connection connection;
        connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "rootJava", "rootJava");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

}
