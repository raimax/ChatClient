package lt.viko.eif.rcepauskas.chatclient;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MainController {
    @FXML
    TextField ip, port, name, message;
    @FXML
    Label status;
    @FXML
    Button btnConnect, btnSend;
    @FXML
    ScrollPane messageList;
    @FXML
    public static VBox messageVBox;

    private Client client;

    @FXML
    protected void connectToServer() {

        if (isConfigurationProvided()) {
            try {
                client = new Client(new Socket(ip.getText(), Integer.parseInt(port.getText())), name.getText());
                onConnect();
            }
            catch (Exception e) {
                status.setText("Can't connect to server");
            }
        }

        /*if (!client.isConnected()) {
            if (isConfigurationProvided()) {
                try {
                    client = new Client(new Socket(ip.getText(), Integer.parseInt(port.getText())), name.getText());
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
        }*/
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
    }

    private void onDisconnect() {
        client.setConnected(false);
        status.setText("Disconnected");
        status.setTextFill(Color.color(1, 0, 0));
        btnConnect.setText("Connect");
        ip.setDisable(false);
        port.setDisable(false);
        btnSend.setDisable(true);
    }

    private boolean isConfigurationProvided() {
        return ip.getText().trim().length() > 0 && port.getText().trim().length() > 0 && name.getText().trim() != "";
    }

    @FXML
    protected void sendMessage() {
        client.sendMessage(message.getText());
    }
}