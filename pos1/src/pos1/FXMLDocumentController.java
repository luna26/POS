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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Luna
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtUser, txtPass;

    @FXML
    private Label labelMinLength;

    @FXML
    private Button btnLogIn;

    EventHandler<ActionEvent> buttonLoginHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String user, pass;
            user = txtUser.getText();
            pass = txtPass.getText();

            if (checkText(user, pass)) {
                labelMinLength.setText("Verificando...");
                labelMinLength.setStyle("-fx-text-fill:'black'");
                try {
                    String[] arrayUserAlready = getCredentials(user);
                    if (checkCredentials(arrayUserAlready, user, pass)) {
                        if(Integer.parseInt(arrayUserAlready[2]) == 0){
                        }else if(Integer.parseInt(arrayUserAlready[2]) == 1){
                            Parent dashbaord = FXMLLoader.load(getClass().getResource("FXMLDashbaord.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Panel Administrativo");
                            stage.setScene(new Scene(dashbaord, 800, 800));
                            stage.show();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        }
                    } else {
                        labelMinLength.setStyle("-fx-text-fill:'red'");
                        labelMinLength.setText("Error de credenciales");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                labelMinLength.setText("Debe ingresar mas de 2 caracteres");
                labelMinLength.setStyle("-fx-text-fill:'red'");
            }
        }
    };

    private Boolean checkText(String user, String pass) {
        return user.length() >= 3 && pass.length() >= 3;
    }

    public boolean checkCredentials(String[] array, String user, String password) {
        return (array[0] == null ? user == null : array[0].equals(user)) && (array[1] == null ? password == null : array[1].equals(password));
    }

    public String[] getCredentials(String user) throws SQLException {
        Connection connection = getConnectToDB();

        String query = "SELECT * FROM pos_users WHERE user_name='"+user+"'";
        String user_name = "", user_password = "", permission="";
        String[] arrayUser;
        arrayUser = new String[3];
        try ( // create the java statement
                Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                user_name = rs.getString("user_name");
                user_password = rs.getString("user_password");
                permission = Integer.toString(rs.getInt("user_permission"));
            }
            arrayUser[0] = user_name;
            arrayUser[1] = user_password;
            arrayUser[2] = permission;
        }
        return arrayUser;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnLogIn.setOnAction(buttonLoginHandler);
    }
}
