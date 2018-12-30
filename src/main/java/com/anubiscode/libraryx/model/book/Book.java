package main.java.com.anubiscode.libraryx.model.book;

import javafx.beans.property.SimpleStringProperty;

public class Book {
    private SimpleStringProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty pub;
    private SimpleStringProperty avail;

    public Book(String id, String title, String author, String pub, boolean avail) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.pub = new SimpleStringProperty(pub);
        this.avail = new SimpleStringProperty(avail ? "YES" : "NO");
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

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getPub() {
        return pub.get();
    }

    public SimpleStringProperty pubProperty() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub.set(pub);
    }

    public String getAvail() {
        return avail.get();
    }

    public SimpleStringProperty availProperty() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail.set(avail);
    }
}
