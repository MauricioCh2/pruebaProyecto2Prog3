package labPresentation.Model;

import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class DOM {
    protected static Document document;
    protected static File file = new File ("laboratorio.xml");

    public DOM() throws ParserConfigurationException, IOException, TransformerException {
        if(!file.exists()){//De esta forma solo lo hara en caso de que no exista
            createDocument();
            generarXML(document);
        }
    }
    protected abstract void createTablePDF(PdfPTable pdf);


    private static void createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();

        //este sera nuestro nodo base
        Element rootdata = document.createElement("data"); //users
        document.appendChild(rootdata);

        //categorias element
        Element laboratorio = document.createElement("tipos_instrumentos");
        rootdata.appendChild(laboratorio); //hija de data
    }



    //Utilidades del xml----------------------------------------------------------------------------------------------
    public boolean checkCodigoExists(Document doc, String cod, String nodo, String atri) throws Exception {
        NodeList activoNodes;
        Document aux ;
        if(doc == null){
            document =parseXMLFile();
            activoNodes = document.getElementsByTagName(nodo);
        } else{
            activoNodes = doc.getElementsByTagName(nodo);
        }


        for(int i = 0; i < activoNodes.getLength(); i++) { //recorremos el xml en busqueda del codigo
            Node userNode = activoNodes.item(i);
            if(userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                //int id = Integer.parseInt(userElement.getAttribute("id"));
                if(cod.equals(userElement.getAttribute(atri))) // si el codigo ya existe pues no hacemos nada mas
                {
                    //throw new Exception("Este codigo ya existe");
                    return true;
                }
            }
        }

        return false;
    }
    public boolean checkelementNotExist(Document doc, String element, String nodo, String nom) throws Exception {
        NodeList activoNodes;
        Document aux ;
        if(doc == null){
            document =parseXMLFile();
            activoNodes = document.getElementsByTagName(nodo);
        } else{
            activoNodes = doc.getElementsByTagName(nodo);
        }


        for(int i = 0; i < activoNodes.getLength(); i++) { //recorremos el xml en busqueda del codigo
            Node userNode = activoNodes.item(i);
            if(userNode.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) userNode;
                //int id = Integer.parseInt(userElement.getAttribute("id"));
                if(element.equals(userElement.getElementsByTagName(nom).item(0).getTextContent())) // si el codigo ya existe pues no hacemos nada mas
                {
                    //throw new Exception("Este codigo ya existe");
                    return true;
                }
            }
        }

        return false;
    }
    protected Document parseXMLFile() throws ParserConfigurationException, IOException, SAXException {//parseamos el cocumento
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder(); //lee archivo XML y convertirlo a una estructura de datos en memoria para poder manipularlo en codigo
        return builder.parse(file);
    }

    protected void generarXML(Document doc) throws TransformerException, IOException {
        TransformerFactory factoria = TransformerFactory.newInstance();
        Transformer transformer = factoria.newTransformer();
        DOMSource source = new DOMSource(doc);

        try (FileWriter fw = new FileWriter(file); PrintWriter pw = new PrintWriter(fw)) {
            Result result = new StreamResult(pw);
            transformer.transform(source, result);
        }
        document = doc;
    }
}
