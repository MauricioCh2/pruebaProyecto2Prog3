package labPresentation.Model;

import Protocol.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageModel {
    public static int CHAT = 1;
    public List<Message> messages;

    public MessageModel() {
        messages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> mensajes) {
        this.messages = mensajes;
    }

    public void addObserver(java.util.Observer o) {
//        super.addObserver(o);
//        this.commit(MessageModel.CHAT);
    }

    public void commit(int properties) {
//        this.setChanged();
//        this.notifyObservers(properties);
    }
}
