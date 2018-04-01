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
public class InvoiceModel {

    public SimpleStringProperty code;
    public SimpleStringProperty name;
    public SimpleStringProperty price;
    public SimpleStringProperty cant;
    public SimpleStringProperty total;

    public InvoiceModel(String code, String name, String price, String cant, String total) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.cant = new SimpleStringProperty(cant);
        this.total = new SimpleStringProperty(total);
    }

    public String getCode() {
        return code.get();
    }

    public String getName() {
        return name.get();
    }

    public String getPrice() {
        return price.get();
    }

    public String getCant() {
        return cant.get();
    }

    public String getTotal() {
        return total.get();
    }
}
