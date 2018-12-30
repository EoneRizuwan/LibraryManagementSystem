package main.java.com.anubiscode.libraryx.modelview.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void loadListBooks() {
        loadWindow("/fxml/book_list.fxml", "List of Books");
    }

    @FXML
    private void loadAddBook() {
        loadWindow("/fxml/book_add.fxml", "Add new book");
    }

    @FXML
    private void loadListMembers() {
        loadWindow("/fxml/member_list.fxml", "List of Members");
    }

    @FXML
    private void loadAddMember() {
        loadWindow("/fxml/member_add.fxml", "Add new member");
    }

    private void loadWindow(String loc, String header) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(header);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
