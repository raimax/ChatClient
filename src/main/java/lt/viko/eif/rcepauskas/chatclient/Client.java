package lt.viko.eif.rcepauskas.chatclient;

import lt.viko.eif.rcepauskas.chatclient.SocketMessage.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    private ChatController chatController;

    public Client() {
    }

    public Client(Socket clientSocket, String username, ChatController chatController) {
        try {
            this.clientSocket = clientSocket;
            this.username = username;
            this.chatController = chatController;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException ex) {
            System.out.println("Error in Client: " + ex.getMessage());
        }
    }

    public void close() {
        try {
            out.close();
            if (clientSocket.isConnected()) {
                clientSocket.close();
            }
        }
        catch (Exception ex) {
            System.out.println("Error in close: " + ex.getMessage());
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(new SocketMessage(MessageType.MESSAGE, message));
        }
        catch (Exception ex) {
            System.out.println("Error in sendMessage: " + ex.getMessage());
            close();
        }
    }

    public void listenForMessage() {
        Thread thread = new Thread(() -> {
            SocketMessage messageFromGroupChat;

            try {
                while ((messageFromGroupChat = (SocketMessage)in.readObject()) != null) {
                    if (messageFromGroupChat.getMessageType() == MessageType.MESSAGE) {
                        chatController.addItemToList(messageFromGroupChat.getMessage());
                    }
                    if (messageFromGroupChat.getMessageType() == MessageType.ONLINE_USERS_LIST) {
                        chatController.addOnlineUsersToList(messageFromGroupChat.getOnlineUsers());
                    }
                }
            }
            catch (IOException ex) {
                close();
            }
            catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException error in listenForMessage: " + ex.getMessage());
                close();
            }

        });

        thread.start();
    }
}
