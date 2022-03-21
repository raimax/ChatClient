package lt.viko.eif.rcepauskas.chatclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    protected TextField message;
    @FXML
    protected Button btnSend;
    @FXML
    protected ListView<String> messageList, onlineUsersList;
    private Client client;
    private String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            onConnect();
        });
    }

    @FXML
    protected void sendMessage() {
        client.sendMessage(message.getText());
    }

    private void onConnect() {
        client.sendMessage(username);
        client.listenForMessage();
    }

    @FXML
    private void onDisconnect(ActionEvent event) {
        client.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("connectWindow.fxml"));
            Parent root = fxmlLoader.load();
            ConnectController connectController = fxmlLoader.getController();
            connectController.getStatus().setText("Disconnected");
            connectController.setName(username);
            WindowManager.changeStage(event, root);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItemToList(String message) {
        Platform.runLater(() -> {
            messageList.getItems().add(message);
            messageList.scrollTo(lastIndex(messageList));
        });
    }

    public void addOnlineUsersToList(List<String> onlineUsers) {
        Platform.runLater(() -> {
            onlineUsersList.getItems().clear();
            onlineUsersList.getItems().addAll(onlineUsers);
        });
    }

    public static int lastIndex(ListView listView) {
        return listView.getItems().size() - 1;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}