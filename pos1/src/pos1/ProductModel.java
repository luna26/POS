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
public class ProductModel {

    public SimpleStringProperty itemId;
    public SimpleStringProperty itemName;
    public SimpleStringProperty price;
    public SimpleStringProperty desc;
    public SimpleStringProperty qty;

    public ProductModel(String itemId, String itemName, String price, String desc, String qty) {
        this.itemId = new SimpleStringProperty(itemId);
        this.itemName = new SimpleStringProperty(itemName);
        this.price = new SimpleStringProperty(price);
        this.desc = new SimpleStringProperty(desc);
        this.qty = new SimpleStringProperty(qty);
    }
    
    

    public String getItemId() {
        return itemId.get();
    }

    public String getItemName() {
        return itemName.get();
    }

    public String getPrice() {
        return price.get();
    }

    public String getDesc() {
        return desc.get();
    }

    public String getQty() {
        return qty.get();
    }
}
