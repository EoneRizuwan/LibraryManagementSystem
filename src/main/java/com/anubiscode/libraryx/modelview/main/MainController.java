package main.java.com.anubiscode.libraryx.modelview.main;

import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.com.anubiscode.libraryx.model.book.Book;
import main.java.com.anubiscode.libraryx.model.book.BookHandler;
import main.java.com.anubiscode.libraryx.model.member.Member;
import main.java.com.anubiscode.libraryx.model.member.MemberHandler;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private BookHandler bookHandler;
    private MemberHandler memberHandler;

    @FXML
    private ObservableList<HBox> infoPanelList;
    @FXML
    private ObservableList<Text> bookInfoList;
    @FXML
    private ObservableList<Text> memberInfoList;
    @FXML
    private TextField bookID;
    @FXML
    private TextField memberID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookHandler = new BookHandler();
        memberHandler = new MemberHandler();

        for (HBox hBox : infoPanelList) JFXDepthManager.setDepth(hBox, 1);
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

    @FXML
    private void loadBookInfos() {
        if (!bookID.getText().isEmpty()) {
            Book book = bookHandler.getBookByID(bookID.getText().trim().toUpperCase());
            if (book != null) {
                bookInfoList.get(0).setText(book.getTitle());
                bookInfoList.get(1).setText(book.getAuthor());
                bookInfoList.get(2).setText(book.getPub());
                bookInfoList.get(3).setText(book.getAvail().equals("YES") ? "Available" : "Not Available");
            } else AlertBox.show(Alert.AlertType.WARNING, null, "Book is not found on database", false);
        } else AlertBox.show(Alert.AlertType.WARNING, null, "Book ID is needed to proceed", false);
    }

    @FXML
    private void loadMemberInfos() {
        if (!memberID.getText().isEmpty()) {
            Member member = memberHandler.getMemberByID(memberID.getText().trim().toUpperCase());
            if (member != null) {
                memberInfoList.get(0).setText(member.getName());
                memberInfoList.get(1).setText(member.getContactNo());
                memberInfoList.get(2).setText(member.getEmail());
            }else AlertBox.show(Alert.AlertType.WARNING, null, "Member is not found on database", false);
        } else AlertBox.show(Alert.AlertType.WARNING, null, "Member ID is needed to proceed", false);
    }
}
