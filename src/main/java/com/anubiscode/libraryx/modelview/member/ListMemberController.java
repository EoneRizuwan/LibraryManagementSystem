package main.java.com.anubiscode.libraryx.modelview.member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.anubiscode.libraryx.model.member.Member;
import main.java.com.anubiscode.libraryx.model.member.MemberHandler;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListMemberController implements Initializable {

    private MemberHandler memberHandler;
    private ObservableList<Member> memberList;

    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String>  nameCol;
    @FXML
    private TableColumn<Member, String>  contactCol;
    @FXML
    private TableColumn<Member, String>  emailCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        memberHandler = new MemberHandler();
        memberList = FXCollections.observableArrayList();

        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadData() {
        List<Member> list = memberHandler.getMembers();
        if (list.isEmpty()) {
            AlertBox.show(Alert.AlertType.WARNING, null, "Member List is empty.", false);
        }

        memberList.setAll(list);
        tableView.setItems(memberList);
    }
}
