package main.java.com.anubiscode.libraryx.modelview.member;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import main.java.com.anubiscode.libraryx.model.member.MemberHandler;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {

    private MemberHandler memberHandler;

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField contactNo;
    @FXML
    private JFXTextField email;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        memberHandler = new MemberHandler();
    }

    @FXML
    private void clearField() {
        name.setText("");
        id.setText("");
        contactNo.setText("");
        email.setText("");
    }

    @FXML
    private void saveMember() {
        if (isInputValid()) {
            boolean output = memberHandler.addMember(
                    id.getText(),
                    name.getText(),
                    contactNo.getText(),
                    email.getText()
            );
            if (output) {
                AlertBox.show(Alert.AlertType.INFORMATION, "Saved", "Member was saved into database.");
                clearField();
            } else
                AlertBox.show(Alert.AlertType.ERROR, "Error", "Member ID provided was already registered in database.");
        }
    }

    private boolean isInputValid() {
        boolean fields = name.getText().matches("\\w+(\\s\\w+(-\\w+)?)*") &&
                id.getText().matches("[a-zA-Z]+\\d+") &&
                contactNo.getText().matches("\\(\\d{3}\\)\\s\\d+[ -]?\\d+") &&
                email.getText().matches("\\w+@\\w+.\\w+");
        if (!fields) AlertBox.show(Alert.AlertType.WARNING, null, "Invalid input format or empty inputs");
        return fields;
    }
}
