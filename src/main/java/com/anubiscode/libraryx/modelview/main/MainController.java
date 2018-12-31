package main.java.com.anubiscode.libraryx.modelview.main;

import com.jfoenix.effects.JFXDepthManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.com.anubiscode.libraryx.model.book.Book;
import main.java.com.anubiscode.libraryx.model.book.BookHandler;
import main.java.com.anubiscode.libraryx.model.book.IssueType;
import main.java.com.anubiscode.libraryx.model.issue.IssueHandler;
import main.java.com.anubiscode.libraryx.model.issue.IssueWrapper;
import main.java.com.anubiscode.libraryx.model.member.Member;
import main.java.com.anubiscode.libraryx.model.member.MemberHandler;
import main.java.com.anubiscode.libraryx.modelview.util.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private BookHandler bookHandler;
    private MemberHandler memberHandler;
    private IssueHandler issueHandler;
    private Book book;
    private Member member;
    private IssueWrapper issueWrapper;

    private ObservableList<String> issuedList;

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
    @FXML
    private TextField issuedBookID;
    @FXML
    private ListView<String> issuedListView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookHandler = new BookHandler();
        memberHandler = new MemberHandler();
        issueHandler = new IssueHandler();
        book = null;
        member = null;

        issuedList = FXCollections.observableArrayList();

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
            book = bookHandler.getBookByID(bookID.getText().trim().toUpperCase());
            if (book != null) {
                bookInfoList.get(0).setText(book.getTitle());
                bookInfoList.get(1).setText(book.getAuthor());
                bookInfoList.get(2).setText(book.getPub());
                bookInfoList.get(3).setText(book.getAvail().equals("YES") ? "Available" : "Not Available");
            } else AlertBox.show(Alert.AlertType.WARNING, null,
                    "Book is not found on database", false);
        } else AlertBox.show(Alert.AlertType.WARNING, null,
                "Book ID is needed to proceed", false);
    }

    @FXML
    private void loadMemberInfos() {
        if (!memberID.getText().isEmpty()) {
            member = memberHandler.getMemberByID(memberID.getText().trim().toUpperCase());
            if (member != null) {
                memberInfoList.get(0).setText(member.getName());
                memberInfoList.get(1).setText(member.getContactNo());
                memberInfoList.get(2).setText(member.getEmail());
            } else AlertBox.show(Alert.AlertType.WARNING, null,
                    "Member is not found on database", false);
        } else AlertBox.show(Alert.AlertType.WARNING, null,
                "Member ID is needed to proceed", false);
    }

    @FXML
    private void loadIssueOp() {
        if (book != null && member != null) {
            String content = "Book Title : " + book.getTitle() + "\n" +
                    "Member : " + member.getName();

            Optional<ButtonType> response = AlertBox.showAndWait(Alert.AlertType.CONFIRMATION,
                    "Proceed Issue Operation?", content).showAndWait();
            if (response.isPresent() && response.get() == ButtonType.OK) {

                // Check if the book was available or not.
                if (!book.getAvail().equals("YES")) {
                    AlertBox.show(Alert.AlertType.WARNING, null,
                            "The book was unavailable to be issued.", false);
                    return;
                }

                boolean update = issueHandler.addIssue(book.getId(), member.getId());
                if (update) {
                    bookInfoList.get(3).setText("Not Available");
                    if (bookHandler.updateIssuedBook(book.getId(), IssueType.ISSUING))
                        System.out.println("Database update successful");
                    else
                        System.err.println("Database update was unsuccessful");
                    AlertBox.show(Alert.AlertType.INFORMATION, null,
                            "Issuing was successful.", false);
                    book.setAvail("NO");
                } else {
                    AlertBox.show(Alert.AlertType.ERROR, null,
                            "There was an error while trying to issued the book.\n" +
                            "Please Try again later.", false);
                }
            }
        } else {
            AlertBox.show(Alert.AlertType.ERROR, null,
                    "Please try to search the book and member again.", false);
        }
    }

    @FXML
    private void loadIssuedInfos() {
        issueWrapper = issueHandler.getIssued(issuedBookID.getText().trim().toUpperCase());
        if (issueWrapper == null) {
            AlertBox.show(Alert.AlertType.WARNING, null, "Book ID was not issued yet or\n" +
                    "doesn't found on database", false);
            return;
        }
        fillIssuedListView(issueWrapper);
        issuedListView.setItems(issuedList);

    }

    private void fillIssuedListView(IssueWrapper issueWrapper) {
        issuedList.clear();
        issuedList.add("ISSUE INFO");
        Date date = issueWrapper.getIssueTime();
        issuedList.add("\tTime: " + date);
        issuedList.add("\tRenew Count: " + issueWrapper.getRenewCount());
        issuedList.add(" ");

        Book issuedBook = bookHandler.getBookByID(issueWrapper.getBookID());
        issuedList.add("BOOK INFO");
        issuedList.add("\tTitle: " + issuedBook.getTitle());
        issuedList.add("\tAuthor: " + issuedBook.getAuthor());
        issuedList.add("\tPublisher: " + issuedBook.getPub());
        issuedList.add(" ");

        Member issuedMember = memberHandler.getMemberByID(issueWrapper.getMemberID());
        issuedList.add("MEMBER INFO");
        issuedList.add("\tName: " + issuedMember.getName());
        issuedList.add("\tPhone No: " + issuedMember.getContactNo());
        issuedList.add("\tEmail: " + issuedMember.getEmail());
    }

    @FXML
    private void renewIssuedCount() {
        if (issueWrapper != null) {
            Optional<ButtonType> choice = AlertBox.showAndWait(Alert.AlertType.CONFIRMATION, null,
                    "Proceed to renewing the current issued?").showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                if (issueHandler.incrementRenewCount(issueWrapper.getBookID())) {
                    issueWrapper.setRenewCount(issueWrapper.getRenewCount() + 1);
                    fillIssuedListView(issueWrapper);

                    AlertBox.show(Alert.AlertType.INFORMATION, null,
                            "Issued was renewed.", false);
                }
            }
        } else {
            AlertBox.show(Alert.AlertType.ERROR, null,
                    "No issued record was found.\n" +
                            "Please try searching another Book ID again.", false);
        }
    }

    @FXML
    private void returnIssued() {
        if (issueWrapper != null) {
            Optional<ButtonType> choice = AlertBox.showAndWait(Alert.AlertType.CONFIRMATION, null,
                    "Proceed to returning the current issued book?").showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                if (issueHandler.deleteIssued(issueWrapper.getBookID())) {

                    // Updating book availability to the database
                    if (bookHandler.updateIssuedBook(issueWrapper.getBookID(), IssueType.RETURNING)) {
                        System.out.println("Updating book availability was successful.");
                    } else {
                        System.err.println("There was an error updating book availability after returning.");
                    }

                    // Updating book available to the issued info panel.
                    if (book != null && book.getId().equals(issueWrapper.getBookID()) &&
                            bookInfoList.get(3).getText().equals("Not Available")) {
                        book.setAvail("YES");
                        bookInfoList.get(3).setText("Available");
                    }

                    AlertBox.show(Alert.AlertType.INFORMATION, null,
                            "The issued book was successfully returned.", false);
                    issueWrapper = null;
                    issuedList.clear();
                } else {
                    AlertBox.show(Alert.AlertType.ERROR, null,
                            "There was an error when trying to returning the book.\n" +
                                    "Please try again later.", false);
                }
            }
        } else {
            AlertBox.show(Alert.AlertType.ERROR, null,
                    "No issued record was found.\n" +
                            "Please try searching another Book ID again.", false);
        }
    }
}
