/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos1;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Luna
 */
public class UserModel {
    
    public SimpleStringProperty userId;
    public SimpleStringProperty userName;
    public SimpleStringProperty pass;
    public SimpleStringProperty permission;

    public UserModel(String userId, String userName, String pass, String permission) {
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.pass = new SimpleStringProperty(pass);
        this.permission = new SimpleStringProperty(permission);
    }

    public String getUserId() {
        return userId.get();
    }

    public String getUserName() {
        return userName.get();
    }

    public String getPass() {
        return pass.get();
    }

    public String getPermission() {
        return permission.get();
    }
}
