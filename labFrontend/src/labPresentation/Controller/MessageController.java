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
        ServiceProxy.instance().solicitar_numero_worker();
    }
    @Override
    public void deliver(Message messages) {
        Mesmodel.messages.add(messages);

        String textoActual = view.getTxP_textoMensajes().getText();
        view.getTxP_textoMensajes().setText( textoActual + "\n" +messages.getMessage());
        Mesmodel.escribir_action(messages.getMessage());
    }

    @Override
    public void set_numero_worker(int numeroWorker) {
        Mesmodel.init_xml_worker(numeroWorker);
    }

    public void initview(MensajesView messageView) {
        view = messageView;
    }
}
