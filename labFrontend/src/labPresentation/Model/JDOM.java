
package labPresentation.Model;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

public class JDOM {
    private Document doc;
    SAXBuilder sax;
    File file;
    private static String FILENAME;

    public JDOM() {
        this.int_user_logs_folder();
    }
    private void generarRutaArchivo(int numeroWorker) {
        String idUsuario = String.valueOf(numeroWorker);
        String dir = "labFrontend" + File.separator + "user_logs" + File.separator;
        FILENAME = dir + "data_usuario_" + idUsuario + ".xml";
        System.out.println("ruta: " + FILENAME);
    }

    private void int_user_logs_folder() {
        String dir = "labFrontend" + File.separator + "user_logs" + File.separator;
        File logsFolder = new File(dir);
        if (!logsFolder.exists()) {
            if (logsFolder.mkdirs()) {
                System.out.println("Carpeta 'user_logs' creada");
            } else {
                System.out.println("Error al crear la carpeta 'user_logs'");
            }
        }
    }

    public void init_xml_file() throws IOException, JDOMException {
        this.sax = new SAXBuilder();
        file = new File(FILENAME);
        try {
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            if (file.exists() && file.length() != 0) {
                System.out.println("Archivo si existe\n\n\n\n");
                doc = sax.build(file);
                //this.clean_xml();
            } else {
                doc = new Document(new Element("data"));
                //doc.getRootElement().addContent(new Element("Creates"));
                //doc.getRootElement().addContent(new Element("Deletes"));
                //doc.getRootElement().addContent(new Element("Uptade"));
                //doc.getRootElement().addContent(new Element("Read"));
                doc.getRootElement().addContent(new Element("actions"));
                //this.int_xml(System.out);
            }
        }catch (Exception ex){
            System.out.println("Estoy en JDOM\n");
            System.out.println(ex.getMessage());
        }
        // doc.setRootElement(new Element("Activos"));
    }

    public void init_file_worker(int numeroWorker){
        try {
            generarRutaArchivo(numeroWorker);
            this.init_xml_file();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void write_action(String msg, OutputStream output) throws JDOMException, IOException{

        Element action = new Element("action");
        action.setText(msg);

        doc.getRootElement().getChild("actions").addContent(action);
        this.escribir_xml(output);

    }

    public void limpiar_xml(){
        Element root = doc.getRootElement().getChild("actions");
        root.removeChildren("action");
    }

    public void escribir_xml(OutputStream output) throws IOException {

        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(doc, output);

        try(FileWriter fileWriter = new FileWriter(file, false)){
            xmlOutputter.output(doc, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
