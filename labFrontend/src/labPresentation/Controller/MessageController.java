package labPresentation.Controller;

import Protocol.IController;
import Protocol.Message;
import labLogic.ServiceProxy;
import labPresentation.Model.MessageModel;

public class MessageController implements IController {
    private MessageModel Mesmodel;
    public MessageController(){
        this.Mesmodel = new MessageModel();
        ServiceProxy.instance().setIDeliver(this);
    }
    @Override
    public void deliver(Message messages) {
        Mesmodel.messages.add(messages);
        Mesmodel.commit(Mesmodel.CHAT);
    }


}
