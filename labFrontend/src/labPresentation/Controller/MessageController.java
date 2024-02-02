package labPresentation.Controller;

import Protocol.IDeliver;
import Protocol.Message;
import labLogic.ServiceProxy;
import labPresentation.Model.MessageModel;
import labPresentation.View.MensajesView;

public class MessageController implements IDeliver {
    private MensajesView view;
    private MessageModel Mesmodel;
    public MessageController(){
        this.Mesmodel = new MessageModel();
        ServiceProxy.instance().setIDeliver(this);
    }
    @Override
    public void deliver(Message messages) {
        Mesmodel.messages.add(messages);
        Mesmodel.commit(Mesmodel.CHAT);
        String textoActual = view.getTxP_textoMensajes().getText();
        view.getTxP_textoMensajes().setText( textoActual + "\n" +messages.getMessage());
    }


    public void initview(MensajesView messageView) {
        view = messageView;
    }
}
