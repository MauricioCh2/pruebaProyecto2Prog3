package labPresentation.Model;

import Protocol.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageModel {
    public static int CHAT = 1;
    public List<Message> messages;
    private JDOM xml_manager;

    public MessageModel() {
        xml_manager = new JDOM();
        messages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> mensajes) {
        this.messages = mensajes;
    }


    public void escribir_action(String msg){
        try {
            xml_manager.write_action(msg, System.out);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void init_xml_worker(int numeroWorker){
        try {
            xml_manager.init_file_worker(numeroWorker);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
