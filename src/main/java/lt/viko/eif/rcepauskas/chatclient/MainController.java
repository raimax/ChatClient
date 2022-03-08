package lt.viko.eif.rcepauskas.chatclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.UnknownHostException;

public class MainController {
    @FXML
    TextField ip, port, name, message;
    @FXML
    Label status;
    @FXML
    Button btnConnect;

    private Client client = new Client();

    @FXML
    protected void connectToServer() {

        if (!client.isConnected()) {
            if (isIpAndPortProvided()) {
                try {
                    client.startConnection(ip.getText(), Integer.parseInt(port.getText()));
                    onConnect();
                }
                catch (Exception e) {
                    status.setText("Can't connect to server");
                }
            }
        }
        else {
            try {
                client.stopConnection();
                onDisconnect();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onConnect() {
        client.setConnected(true);
        status.setText("Connected");
        status.setTextFill(Color.color(0, 1, 0));
        btnConnect.setText("Disconnect");
        ip.setDisable(true);
        port.setDisable(true);
    }

    private void onDisconnect() {
        client.setConnected(false);
        status.setText("Disconnected");
        status.setTextFill(Color.color(1, 0, 0));
        btnConnect.setText("Connect");
        ip.setDisable(false);
        port.setDisable(false);
    }

    private boolean isIpAndPortProvided() {
        return ip.getText().trim().length() > 0 && port.getText().trim().length() > 0;
    }

    @FXML
    protected void sendMessage() {
        client.sendMessage(String.format("%s: %s", name.getText(), message.getText()));
    }
}