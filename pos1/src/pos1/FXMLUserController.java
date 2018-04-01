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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Luna
 */
public class FXMLUserController implements Initializable {

    @FXML
    TableView<UserModel> userTbl;

    @FXML
    TableColumn userIdCol, userNameCol, userPassCol, userPermCol;

    @FXML
    TextField txtUser, txtPass, txtPerm;

    @FXML
    Button btnAddUser, btnDeleteUser;

    @FXML
    Label idUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userIdCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("userId")
        );
        userNameCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("userName")
        );
        userPassCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("pass")
        );
        userPermCol.setCellValueFactory(
                new PropertyValueFactory<ProductModel, String>("permission")
        );

        try {
            userTbl.setItems(getUsersDB());//Agregamos los datos en la tabla, aquí la tabla ya muestra la información.
        } catch (SQLException ex) {
            Logger.getLogger(FXMLInventoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnAddUser.setOnAction(addUserBtnHandler);
        btnDeleteUser.setOnAction(deleteUserBtnHandler);

        userTbl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (userTbl.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = userTbl.getSelectionModel();
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
        String query = "SELECT * FROM pos_users WHERE user_id='" + codeSelected + "'";

        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                txtUser.setText(rs.getString("user_name"));
                txtPass.setText(rs.getString("user_password"));
                txtPerm.setText(rs.getString("user_permission"));
                idUser.setText(rs.getString("user_id"));
            }
        }
    }

    public void deleteUser() throws SQLException {
        String userToDelete;
        userToDelete = idUser.getText();
        Connection connection = getConnectToDB();
        String query = "DELETE FROM pos_users WHERE user_id = '" + userToDelete + "' ";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            st.executeUpdate(query);
            txtPass.setText("");
            txtPerm.setText("");
            txtUser.setText("");
            idUser.setText("");
            userTbl.getItems().clear();
            userTbl.getItems().addAll(getUsersDB());
            connection.close();
        }
    }

    EventHandler<ActionEvent> addUserBtnHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                addUserDB();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<ActionEvent> deleteUserBtnHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                deleteUser();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public ObservableList getUsersDB() throws SQLException {
        Connection connection = getConnectToDB();
        String query = "SELECT * FROM pos_users";
        ObservableList<UserModel> dataUser = FXCollections.observableArrayList();

        try ( // create the java statement
                Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                dataUser.add(new UserModel(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_password"), rs.getString("user_permission")));
            }
        }
        connection.close();
        return dataUser;
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

    public void addUserDB() throws SQLException {
        String user, pass, perm;

        user = txtUser.getText();
        pass = txtPass.getText();
        perm = txtPerm.getText();

        Connection connection = getConnectToDB();
        String query = "INSERT INTO pos_users (user_name,user_password,user_permission) VALUES(" + "\"" + user + "\"" + "," + "\"" + pass + "\"" + "," + "\"" + perm + "\"" + ");";
        try ( // create the java statement
                Statement st = connection.createStatement()) {
            st.executeUpdate(query);
            userTbl.getItems().clear();
            userTbl.getItems().addAll(getUsersDB());
            txtPass.setText("");
            txtPerm.setText("");
            txtUser.setText("");
            connection.close();
        }
    }

}
