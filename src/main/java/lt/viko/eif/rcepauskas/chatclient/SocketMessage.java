package lt.viko.eif.rcepauskas.chatclient;

import java.io.Serializable;
import java.util.List;

public class SocketMessage implements Serializable {

    private MessageType messageType;
    private String message;
    private List<String> onlineUsers;

    public enum MessageType {
        MESSAGE,
        ONLINE_USERS_LIST
    }

    public SocketMessage(MessageType messageType, List<String> onlineUsers) {
        this.messageType = messageType;
        this.onlineUsers = onlineUsers;
    }

    public SocketMessage(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}
