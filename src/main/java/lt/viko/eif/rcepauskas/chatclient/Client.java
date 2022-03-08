package lt.viko.eif.rcepauskas.chatclient;

import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isConnected;

    public void startConnection(String ip, int port) throws java.io.IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stopConnection() throws java.io.IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
