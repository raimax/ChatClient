package lt.viko.eif.rcepauskas.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private ChatController chatController;

    public Client() {
    }

    public Client(Socket clientSocket, String username, ChatController chatController) {
        try {
            this.clientSocket = clientSocket;
            this.username = username;
            this.chatController = chatController;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            out.close();
            clientSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
                    while ((messageFromGroupChat = in.readLine()) != null) {
                        chatController.addItemToList(messageFromGroupChat);
                    }
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
