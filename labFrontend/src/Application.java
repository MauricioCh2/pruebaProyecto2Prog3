import labLogic.ServiceProxy;
import labPresentation.Controller.MainController;

import javax.swing.*;
//comentario -----------------------------zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz
public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void createAndShowGUI() throws Exception { 


        try{
             new MainController();
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Error al conectar con el servidor, Por favor revice si esta en funcionamiento");
            System.out.println("Error en application wtf"+ex.getMessage());
            ServiceProxy.instance().stop();//en caso de que el servidopr no este iniciado se cerrara
            System.exit(0);
        }

    }

}