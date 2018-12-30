package main.java.com.anubiscode.libraryx.modelview.book;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import main.java.com.anubiscode.libraryx.model.book.BookHandler;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    private BookHandler bookHandler;

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookHandler = new BookHandler();
    }

    @FXML
    private void clearField() {
        id.setText("");
        title.setText("");
        author.setText("");
        publisher.setText("");
    }

    @FXML
    private void saveBook() {
        if (isInputValid()) {
            boolean output = bookHandler.addBook(
                    id.getText().trim().toUpperCase(),
                    title.getText().trim().toUpperCase(),
                    author.getText().trim().toUpperCase(),
                    publisher.getText().trim().toUpperCase()
            );
            if (output) {
                AlertBox.show(Alert.AlertType.INFORMATION, "Saved", "The book was saved into database.");
                clearField();
            }
            else AlertBox.show(Alert.AlertType.ERROR, "Error", "Book ID provided was already registered in database.");
        }
    }

    private boolean isInputValid() {
        boolean fields = title.getText().matches("\\w+('s)?(\\s?\\w+('s)?)*(\\s?[&,:-])?(\\s?\\w+('s)?)*") &&
                author.getText().matches("\\w+('s)?(\\s?\\w+('s)?)*(\\s?[&,:-])?(\\s?\\w+('s)?)*") &&
                publisher.getText().matches("\\w+('s)?(\\s?\\w+('s)?)*(\\s?[&,:-])?(\\s?\\w+('s)?)*") &&
                id.getText().matches("[a-zA-Z]+\\d+");
        if (!fields) AlertBox.show(Alert.AlertType.WARNING, null, "Invalid input format or empty inputs");
        return fields;
    }
}
