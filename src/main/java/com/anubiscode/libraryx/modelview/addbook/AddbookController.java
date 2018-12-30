package main.java.com.anubiscode.libraryx.modelview.addbook;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import main.java.com.anubiscode.libraryx.model.addbook.AddbookHandler;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddbookController implements Initializable {

    private AddbookHandler addbookHandler;

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
        addbookHandler = new AddbookHandler();
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
            boolean output = addbookHandler.addBook(
                    id.getText().toUpperCase(),
                    title.getText().toUpperCase(),
                    author.getText().toUpperCase(),
                    publisher.getText().toUpperCase()
            );
            if (output) AlertBox.show(Alert.AlertType.INFORMATION, "Saved", "The book was saved into database.");
            else AlertBox.show(Alert.AlertType.ERROR, "Error", "There was a problem attempting to save the book.");
        }
    }

    private boolean isInputValid() {
        boolean fields = title.getText().matches("(\\w+'?\\w+\\s?)+") &&
                author.getText().matches("(\\w+'?\\w+\\s?)+") &&
                publisher.getText().matches("(\\w+'?\\w+\\s?)+") &&
                id.getText().matches("[a-zA-Z]+\\d+");
        if (!fields) AlertBox.show(Alert.AlertType.WARNING, null, "Invalid input format or empty inputs");
        return fields;
    }
}
