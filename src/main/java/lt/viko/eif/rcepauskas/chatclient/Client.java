package lt.viko.eif.rcepauskas.chatclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private boolean isConnected;

    public Client(Socket clientSocket, String username) {
        try {
            this.clientSocket = clientSocket;
            this.username = username;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void sendMessage(String message) {
        try {
            if (clientSocket.isConnected()) {
                out.println(message);
            }
        }
        catch (Exception e) {
            close();
        }
    }

    public void listenForMessage() {
        Thread thread = new Thread(() -> {
            String messageFromGroupChat;
            while (clientSocket.isConnected()) {
                try {
                    messageFromGroupChat = in.readLine();
                    System.out.println(messageFromGroupChat);
                }
                catch (IOException e) {
                    close();
                    break;
                }
            }
        });

        thread.start();
    }
}
