package lt.viko.eif.rcepauskas.chatclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.Socket;

public class ConnectController {
    @FXML
    protected TextField ip;
    @FXML
    protected TextField port;
    @FXML
    protected TextField name;
    @FXML
    protected Label status;
    @FXML
    protected Button btnConnect;

    @FXML
    protected void connectToServer(ActionEvent event) {
        if (isConfigurationProvided(new String[] {ip.getText(), port.getText(), name.getText()})) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
                Parent root = fxmlLoader.load();
                ChatController chatController = fxmlLoader.getController();
                Client client = new Client(new Socket(ip.getText(), Integer.parseInt(port.getText())), name.getText(), chatController);
                chatController.setUsername(name.getText());
                chatController.setClient(client);
                WindowManager.changeStage(event, root);
            }
            catch (Exception e) {
                e.printStackTrace();
                status.setText("Can't connect to server");
            }
        }
        else {
            status.setText("Configuration not provided");
        }
    }

    private boolean isConfigurationProvided(String[] args) {
        for (String arg : args) {
            if (arg == null) {
                return false;
            }
            if (arg.trim().length() < 1) {
                return false;
            }
        }

        return true;
    }

    public Label getStatus() {
        return status;
    }
}
