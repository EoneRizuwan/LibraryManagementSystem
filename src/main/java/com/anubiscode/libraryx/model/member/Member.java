package main.java.com.anubiscode.libraryx.model.member;

import javafx.beans.property.SimpleStringProperty;

public class Member {
    private SimpleStringProperty name;
    private SimpleStringProperty id;
    private SimpleStringProperty contactNo;
    private SimpleStringProperty email;

    public Member(String name, String id, String contactNo, String email) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.contactNo = new SimpleStringProperty(contactNo);
        this.email = new SimpleStringProperty(email);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getContactNo() {
        return contactNo.get();
    }

    public SimpleStringProperty contactNoProperty() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo.set(contactNo);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
