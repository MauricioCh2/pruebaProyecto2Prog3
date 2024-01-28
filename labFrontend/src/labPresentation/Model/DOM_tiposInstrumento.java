package labPresentation.Model;

import Protocol.TipoInstrumentoObj;
import com.itextpdf.text.pdf.PdfPTable;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class DOM_tiposInstrumento extends DOM{

    public DOM_tiposInstrumento() throws ParserConfigurationException, IOException, TransformerException {
    }
    public boolean addTipoInstrumento(TipoInstrumentoObj tipIns){
        boolean result = false;
        boolean codExist = false;
        boolean nomExist = false;
        try {
            if(!file.exists()){
                return result;
            }
            Document doc = parseXMLFile();
            codExist = checkCodigoExists(doc, tipIns.getCodigo(), "tipo_instrumento", "Codigo");
            nomExist = checkelementNotExist(doc, tipIns.getNombre(), "tipo_instrumento", "nombre");
            if(!codExist&!nomExist){
                Node root = doc.getElementsByTagName("tipos_instrumentos").item(0);

                Element newTipoInst = doc.createElement("tipo_instrumento"); //sera nuestra base

                Attr attr = doc.createAttribute("Codigo");
                attr.setValue(tipIns.getCodigo());
                newTipoInst.setAttributeNode(attr);

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(tipIns.getNombre()));
                newTipoInst.appendChild(nombre);

                Element unidad = doc.createElement("unidad");
                unidad.appendChild(doc.createTextNode(String.valueOf(tipIns.getUnidad())));
                newTipoInst.appendChild(unidad);


                //--------------------------------------------------------
                root.appendChild(newTipoInst);
                generarXML(doc);
                document = doc;
                result = true;
            }
            else {
                throw new Exception("Este código o nombre  ya existe :(");
            }
        } catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return result;
    }

    public boolean updateTipoInstrumento(TipoInstrumentoObj tipInst){
        boolean result = false;
        try {
            Document doc = parseXMLFile();
            if (!file.exists()) {
                return result;
            }
            XPathFactory factory = XPathFactory.newInstance(); //sirve para crear objetos XPath
            XPath xpath = factory.newXPath();//permite evaluar expreciones de un XML

            Element node = (Element) xpath.evaluate(
                    "//tipos_instrumentos/tipo_instrumento[@Codigo='" + tipInst.getCodigo()+ "']",
                    doc,
                    XPathConstants.NODE// se espera que el resultado sea del tipo NODE
            );
            if(node!=null){

                node.getElementsByTagName("nombre").item(0).setTextContent(tipInst.getNombre());
                node.getElementsByTagName("unidad").item(0).setTextContent(String.valueOf(tipInst.getUnidad()));

                generarXML(doc);
                result = true;
            }


            return result;
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException |
                 TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    public void cargaTiposATable(JTable tabla, JComboBox com){
        try {
            Document doc = parseXMLFile();

            // Obtener todos los elementos "vuelo"
            NodeList listTiposins = (NodeList) doc.getElementsByTagName("tipo_instrumento");

            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);//nos aseguramos que la tabla esta vacia para no sobrecargarla
            com.removeAllItems();
            // Iterar sobre los elementos "tipo_instrumento"
            for (int i = 0; i < listTiposins.getLength(); i++) {
                Element tipoInstr = (Element) listTiposins.item(i);// Obtener el elemento "tipo_instrumento" actual

                // Obtener los datos del tipo
                String codigo = tipoInstr.getAttribute("Codigo");
                String nombre = tipoInstr.getElementsByTagName("nombre").item(0).getTextContent();
                String unidad = tipoInstr.getElementsByTagName("unidad").item(0).getTextContent();

                Object[] newRow = {codigo,nombre,unidad};

                modelo.addRow(newRow);
                com.addItem(nombre);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

    }
//    public void cargaNombres(JComboBox com){//podriamos fucionar ambos realmente
//        try {
//            Document doc = parseXMLFile();
//
//            // Obtener todos los elementos "vuelo"
//            NodeList listTiposins = (NodeList) doc.getElementsByTagName("tipo_instrumento");
//
//
//            // Iterar sobre los elementos "tipo_instrumento"
//            for (int i = 0; i < listTiposins.getLength(); i++) {
//                Element tipoInstr = (Element) listTiposins.item(i);// Obtener el elemento "tipo_instrumento" actual
//
//                // Obtener los datos del tipo
//
//                String nombre = tipoInstr.getElementsByTagName("nombre").item(0).getTextContent();
//
//
//            }
//        } catch (ParserConfigurationException | IOException | SAXException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
    public boolean buscarTiposInsPorCod(String codigo, JTable tabla) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        boolean alguno = false;
        // Parsear el XML
        Document doc = parseXMLFile();

        // Obtener todos los elementos "tipo_instrumento"
        NodeList lisTipoIns = (NodeList) doc.getElementsByTagName("tipo_instrumento");

        String cod ;
        String nom;
        String un;


        // Iterar sobre los elementos "tipo_instrumento"
        for (int i = 0; i < lisTipoIns.getLength(); i++) {
            // Obtener el elemento "tipo_instrumento" actual
            Element tipoInst = (Element) lisTipoIns.item(i);

            // Comprobar si el codigo de tipo_instrumento coincide
            String codigoActual = tipoInst.getAttribute("Codigo");
            if (codigoActual.equals(codigo)) {

                cod = tipoInst.getAttribute("Codigo");
                nom = tipoInst.getElementsByTagName("nombre").item(0).getTextContent();
                un = tipoInst.getElementsByTagName("unidad").item(0).getTextContent();

                Object[] newRow = {cod, nom, un};
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                modelo.setRowCount(0);
                modelo.addRow(newRow);
                tabla.setRowSelectionInterval(0, 0);
                MouseEvent clickEvent = new MouseEvent(tabla, MouseEvent.MOUSE_CLICKED,
                        System.currentTimeMillis(),
                        0, 0, 0, 1, false);
                tabla.dispatchEvent(clickEvent); //activa el listener como si fuera un click
                alguno = true;
            }
        }

        return alguno;
    }
    public boolean buscarTiposInsPorNom(String tx, JTable tabla) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        boolean alguno = false;
        // Parsear el XML
        Document doc = parseXMLFile();

        // Obtener todos los elementos "tipo_instrumento"
        NodeList lisTipoIns = (NodeList) doc.getElementsByTagName("tipo_instrumento");

        String cod ;
        String nom;
        String un;


        // Iterar sobre los elementos "tipo_instrumento"
        for (int i = 0; i < lisTipoIns.getLength(); i++) {
            // Obtener el elemento "tipo_instrumento" actual
            Element tipoInst = (Element) lisTipoIns.item(i);

            // Comprobar si el codigo de tipo_instrumento coincide
            String nombreAct = tipoInst.getElementsByTagName("nombre").item(0).getTextContent();
            String uniActual = tipoInst.getElementsByTagName("unidad").item(0).getTextContent();
            String nombreActLower = nombreAct.toLowerCase();
            String uniActualLower = uniActual.toLowerCase();
            String txLower = tx.toLowerCase(); //pasamos todo a lower case para asegurarnos mejor de que coincidan
            if (nombreActLower.contains(txLower)|uniActualLower.contains(txLower)) {

                cod = tipoInst.getAttribute("Codigo");
                nom = tipoInst.getElementsByTagName("nombre").item(0).getTextContent();
                un = tipoInst.getElementsByTagName("unidad").item(0).getTextContent();

                Object[] newRow = {cod, nom, un};
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                if (!alguno) {
                    modelo.setRowCount(0);
                }
                modelo.addRow(newRow);
                tabla.setRowSelectionInterval(0, 0);
                MouseEvent clickEvent = new MouseEvent(tabla, MouseEvent.MOUSE_CLICKED,
                        System.currentTimeMillis(),
                        0, 0, 0, 1, false);
                tabla.dispatchEvent(clickEvent); //activa el listener como si fuera un click
                alguno = true;
            }
        }

        return alguno;
    }
    public boolean eliminarTipo(String cod) {
        boolean result = false;
        try {
            Document doc = parseXMLFile();
            if (!file.exists()) {
                return result;
            }
            XPathFactory factory = XPathFactory.newInstance(); //sirve para crear objetos XPath
            XPath xpath = factory.newXPath();//permite evaluar expreciones de un XML

            Element node = (Element) xpath.evaluate(
                    "//tipos_instrumentos/tipo_instrumento[@Codigo='" + cod+ "']",
                    doc,
                    XPathConstants.NODE// se espera que el resultado sea del tipo NODE
            );
            if(node!=null) {
                Node parent = node.getParentNode();
                parent.removeChild(node);
                generarXML(doc);
                result = true;
            }
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException |
                 TransformerException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void createTablePDF(PdfPTable table){
        try {
            Document doc = parseXMLFile();

            // Obtener todos los elementos "vuelo"
            NodeList listTiposins = (NodeList) doc.getElementsByTagName("tipo_instrumento");


            // Iterar sobre los elementos "tipo_instrumento"
            for (int i = 0; i < listTiposins.getLength(); i++) {
                Element tipoInstr = (Element) listTiposins.item(i);// Obtener el elemento "tipo_instrumento" actual

                // Obtener los datos del tipo
                String codigo = tipoInstr.getAttribute("Codigo");
                String nombre = tipoInstr.getElementsByTagName("nombre").item(0).getTextContent();
                String unidad = tipoInstr.getElementsByTagName("unidad").item(0).getTextContent();

                table.addCell(codigo);
                table.addCell(nombre);
                table.addCell(unidad);


            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

}
