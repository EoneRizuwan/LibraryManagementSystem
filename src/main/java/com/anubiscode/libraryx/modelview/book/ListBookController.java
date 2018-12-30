package main.java.com.anubiscode.libraryx.modelview.book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.java.com.anubiscode.libraryx.model.book.Book;
import main.java.com.anubiscode.libraryx.model.book.BookHandler;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListBookController implements Initializable {
    private BookHandler bookHandler;
    private ObservableList<Book> list;

    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> pubCol;
    @FXML
    private TableColumn<Book, String> availCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookHandler = new BookHandler();
        list = FXCollections.observableArrayList();

        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        pubCol.setCellValueFactory(new PropertyValueFactory<>("pub"));
        availCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
    }

    private void loadData() {
        List<Book> bookList = bookHandler.getBooks();
        if (bookList.isEmpty()) {
            AlertBox.show(Alert.AlertType.WARNING, null, "Book List is empty.");
        }

        list.setAll(bookList);
        tableView.setItems(list);
    }
}
