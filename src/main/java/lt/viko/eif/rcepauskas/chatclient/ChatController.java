package lt.viko.eif.rcepauskas.chatclient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController {

    @FXML
    protected TextField ip, port, name, message;
    @FXML
    protected Label status;
    @FXML
    protected Button btnConnect, btnSend;
    @FXML
    protected ListView<String> messageList;

    private Client client = new Client();

    @FXML
    protected void connectToServer() {
        if (!client.isConnected()) {
            if (isConfigurationProvided()) {
                try {
                    client = new Client(new Socket(ip.getText(), Integer.parseInt(port.getText())), name.getText(), this);
                    onConnect();
                }
                catch (Exception e) {
                    status.setText("Can't connect to server");
                }
            }
        }
        else {
            try {
                client.close();
                onDisconnect();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onConnect() {
        client.setConnected(true);
        client.sendMessage(name.getText());
        client.listenForMessage();
        status.setText("Connected");
        status.setTextFill(Color.color(0, 1, 0));
        btnConnect.setText("Disconnect");
        ip.setDisable(true);
        port.setDisable(true);
        btnSend.setDisable(false);
        name.setDisable(true);
    }

    private void onDisconnect() {
        client.setConnected(false);
        status.setText("Disconnected");
        status.setTextFill(Color.color(1, 0, 0));
        btnConnect.setText("Connect");
        ip.setDisable(false);
        port.setDisable(false);
        btnSend.setDisable(true);
        name.setDisable(false);
    }

    private boolean isConfigurationProvided() {
        return ip.getText().trim().length() > 0 && port.getText().trim().length() > 0 && name.getText().trim() != "";
    }

    @FXML
    protected void sendMessage() {
        client.sendMessage(message.getText());
    }

    public void addItemToList(String message) {
        Platform.runLater(() -> {
            messageList.getItems().add(message);
            messageList.scrollTo(lastIndex(messageList));
        });
    }

    public static int lastIndex(ListView listView) {
        return listView.getItems().size() - 1;
    }
}