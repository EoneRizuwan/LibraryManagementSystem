package main.java.com.anubiscode.libraryx.modelview.util;

import javafx.scene.control.Alert;

public class AlertBox {

    public static void show(Alert.AlertType alertType, String header, String content, boolean wait) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        if (wait) alert.showAndWait();
        else alert.show();
    }
}
