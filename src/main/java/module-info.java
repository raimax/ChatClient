module lt.viko.eif.rcepauskas.chatclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens lt.viko.eif.rcepauskas.chatclient to javafx.fxml;
    exports lt.viko.eif.rcepauskas.chatclient;
}