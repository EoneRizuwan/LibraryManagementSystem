package main.java.com.anubiscode.libraryx.model.issue;

import java.sql.Timestamp;
import java.time.LocalDate;

public class IssueWrapper {
    private String bookID;
    private String memberID;
    private Timestamp issueTime;
    private int renewCount;

    public IssueWrapper(String bookID, String memberID, Timestamp issueTime, int renewCount) {
        this.bookID = bookID;
        this.memberID = memberID;
        this.issueTime = issueTime;
        this.renewCount = renewCount;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public Timestamp getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Timestamp issueTime) {
        this.issueTime = issueTime;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }
}
