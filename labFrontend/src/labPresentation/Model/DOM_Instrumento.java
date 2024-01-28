package labPresentation.Model;

import Protocol.Instrumento;
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
import java.util.Objects;

public class DOM_Instrumento  extends DOM {

    public DOM_Instrumento() throws ParserConfigurationException, IOException, TransformerException {
    }

    public boolean addInstrumento(Instrumento tipIns) {
        boolean result = false;
        boolean codExist = false;
        try {
            if (!file.exists()) {
                return result;
            }
            Document doc = parseXMLFile();
            codExist = checkCodigoExists(doc, tipIns.getSerie(), "instrumento", "No_Serie");
            if (!codExist) {
                XPathFactory factory = XPathFactory.newInstance();
                XPath xpath = factory.newXPath();

                Element node = (Element) xpath.evaluate(
                        "//tipos_instrumentos/tipo_instrumento[nombre='" + tipIns.getTipo() + "']",
                        doc,
                        XPathConstants.NODE// se espera que el resultado sea del tipo NODE
                );
                if (node != null) {
                    // Node root = doc.getElementsByTagName("tipo_instrumento").item(0);

                    Element newInst = doc.createElement("instrumento"); //sera nuestra base
                    node.appendChild(newInst);

                    Attr attr = doc.createAttribute("No_Serie");
                    attr.setValue(tipIns.getSerie());
                    newInst.setAttributeNode(attr);

                    Element descripcion = doc.createElement("descripcion");
                    descripcion.appendChild(doc.createTextNode(tipIns.getDescripcion()));
                    newInst.appendChild(descripcion);

                    Element minimo = doc.createElement("minimo");
                    minimo.appendChild(doc.createTextNode(String.valueOf(tipIns.getMinimo())));
                    newInst.appendChild(minimo);

                    Element max = doc.createElement("maximo");
                    max.appendChild(doc.createTextNode(String.valueOf(tipIns.getMaximo())));
                    newInst.appendChild(max);

                    Element tolerancia = doc.createElement("tolerancia");
                    tolerancia.appendChild(doc.createTextNode(String.valueOf(tipIns.getMaximo())));
                    newInst.appendChild(tolerancia);


                    //--------------------------------------------------------

                    generarXML(doc);
                    document = doc;
                    result = true;
                }
            }else {
                throw new Exception("Este numero de serie ya existe :(");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean updateInstrumento(Instrumento ins) {
        boolean result = false;
        try {
            Document doc = parseXMLFile();
            if (!file.exists()) {
                return result;
            }
            XPathFactory factory = XPathFactory.newInstance(); //sirve para crear objetos XPath
            XPath xpath = factory.newXPath();//permite evaluar expreciones de un XML

            Element node = (Element) xpath.evaluate(
                    "//tipos_instrumentos/tipo_instrumento/instrumento[@No_Serie='" + ins.getSerie() + "']",
                    doc,
                    XPathConstants.NODE// se espera que el resultado sea del tipo NODE
            );
            if (node != null) {
                Element tipo = (Element) node.getParentNode();
                if(!Objects.equals(tipo.getElementsByTagName("nombre").item(0).getTextContent(), ins.getTipo())){
                    Node parent = node.getParentNode();
                    parent.removeChild(node);
                    generarXML(doc);
                    addInstrumento(ins);
                    document = doc;

                }else {
                    node.getElementsByTagName("descripcion").item(0).setTextContent(ins.getDescripcion());
                    node.getElementsByTagName("minimo").item(0).setTextContent(String.valueOf(ins.getMinimo()));
                    node.getElementsByTagName("maximo").item(0).setTextContent(String.valueOf(ins.getMaximo()));
                    node.getElementsByTagName("tolerancia").item(0).setTextContent(String.valueOf(ins.getTolerancia()));
                }
                generarXML(doc);
                result = true;
            }


            return result;
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException |
                 TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    public void cargaInstrumentosATable(JTable tabla) {
        try {
            Document doc = parseXMLFile();

            // Obtener todos los elementos "vuelo"
            NodeList lisInstrumento = (NodeList) doc.getElementsByTagName("instrumento");

            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);//nos aseguramos que la tabla esta vacia para no sobrecargarla
            // Iterar sobre los elementos "tipo_instrumento"
            for (int i = 0; i < lisInstrumento.getLength(); i++) {
                Element instrumento = (Element) lisInstrumento.item(i);// Obtener el elemento "tipo_instrumento" actual

                // Obtener los datos del tipo
                String serie = instrumento.getAttribute("No_Serie");
                String descripcion = instrumento.getElementsByTagName("descripcion").item(0).getTextContent();
                String minimo = instrumento.getElementsByTagName("minimo").item(0).getTextContent();
                String maximo = instrumento.getElementsByTagName("maximo").item(0).getTextContent();
                String tolerancia = instrumento.getElementsByTagName("tolerancia").item(0).getTextContent();

                Object[] newRow = {serie, descripcion, minimo, maximo, tolerancia};

                modelo.addRow(newRow);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean buscarInstrumentosPorSerie(String serie, JTable tabla) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        boolean alguno = false;
        // Parsear el XML
        Document doc = parseXMLFile();

        // Obtener todos los elementos "tipo_instrumento"
        NodeList lisInstrumento = (NodeList) doc.getElementsByTagName("instrumento");

        String ser;
        String desc;
        String min;
        String max;
        String tol;


        // Iterar sobre los elementos "tipo_instrumento"
        for (int i = 0; i < lisInstrumento.getLength(); i++) {
            // Obtener el elemento "tipo_instrumento" actual
            Element instru = (Element) lisInstrumento.item(i);

            // Comprobar si el codigo de tipo_instrumento coincide
            String codigoActual = instru.getAttribute("No_Serie");
            if (codigoActual.equals(serie)) {

                ser = instru.getAttribute("No_Serie");
                desc = instru.getElementsByTagName("descripcion").item(0).getTextContent();
                min = instru.getElementsByTagName("minimo").item(0).getTextContent();
                max = instru.getElementsByTagName("maximo").item(0).getTextContent();
                tol = instru.getElementsByTagName("tolerancia").item(0).getTextContent();

                Object[] newRow = {ser, desc, min, max, tol};
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
    public Instrumento buscarInstrumentosPorSerie(String serie) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        Instrumento insAux;
        boolean alguno = false;
        // Parsear el XML
        Document doc = parseXMLFile();

        // Obtener todos los elementos "tipo_instrumento"
        NodeList lisInstrumento = (NodeList) doc.getElementsByTagName("instrumento");

        String ser;
        String desc;
        String min;
        String max;
        String tol;
        String tip;
        String un;


        // Iterar sobre los elementos "tipo_instrumento"
        for (int i = 0; i < lisInstrumento.getLength(); i++) {
            // Obtener el elemento "tipo_instrumento" actual
            Element instru = (Element) lisInstrumento.item(i);

            // Comprobar si el codigo de tipo_instrumento coincide
            String codigoActual = instru.getAttribute("No_Serie");
            if (codigoActual.equals(serie)) {
                Element tipoInstrumento = (Element) instru.getParentNode();

                ser = instru.getAttribute("No_Serie");
                desc = instru.getElementsByTagName("descripcion").item(0).getTextContent();
                min = instru.getElementsByTagName("minimo").item(0).getTextContent();
                max = instru.getElementsByTagName("maximo").item(0).getTextContent();
                tol = instru.getElementsByTagName("tolerancia").item(0).getTextContent();
                tip = tipoInstrumento.getElementsByTagName("nombre").item(0).getTextContent();
                un = tipoInstrumento.getElementsByTagName("unidad").item(0).getTextContent();
                insAux = new Instrumento(ser,desc,tip,Integer.parseInt(max),Integer.parseInt(min),Double.parseDouble(tol));
                insAux.setUnidad(un);
                return  insAux;
            }
        }

        return null;
    }


    public boolean buscarInstrumentosPorDescr(String tx, JTable tabla) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        boolean alguno = false;
        // Parsear el XML
        Document doc = parseXMLFile();

        // Obtener todos los elementos "tipo_instrumento"
        NodeList lisInstrumento = (NodeList) doc.getElementsByTagName("instrumento");

        String ser;
        String desc;
        String min;
        String max;
        String tol;


        // Iterar sobre los elementos "tipo_instrumento"
        for (int i = 0; i < lisInstrumento.getLength(); i++) {
            // Obtener el elemento "tipo_instrumento" actual
            Element instru = (Element) lisInstrumento.item(i);

            // Comprobar si el codigo de tipo_instrumento coincide
            String textoActual = instru.getElementsByTagName("descripcion").item(0).getTextContent();
            String textoActualLower = textoActual.toLowerCase();
            String txLower = tx.toLowerCase(); //pasamos todo a lower case para asegurarnos mejor de que coincidan
            if (textoActualLower.contains(txLower)) {

                ser = instru.getAttribute("No_Serie");
                desc = instru.getElementsByTagName("descripcion").item(0).getTextContent();
                min = instru.getElementsByTagName("minimo").item(0).getTextContent();
                max = instru.getElementsByTagName("maximo").item(0).getTextContent();
                tol = instru.getElementsByTagName("tolerancia").item(0).getTextContent();

                Object[] newRow = {ser, desc, min, max, tol};
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

    public boolean eliminarInstrumeto(String ser) {
        boolean result = false;
        try {
            Document doc = parseXMLFile();
            if (!file.exists()) {
                return result;
            }
            XPathFactory factory = XPathFactory.newInstance(); //sirve para crear objetos XPath
            XPath xpath = factory.newXPath();//permite evaluar expreciones de un XML

            Element node = (Element) xpath.evaluate(
                    "//tipos_instrumentos/tipo_instrumento/instrumento[@No_Serie='" + ser + "']",
                    doc,
                    XPathConstants.NODE// se espera que el resultado sea del tipo NODE
            );
            if (node != null) {
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
            NodeList lisInstru = (NodeList) doc.getElementsByTagName("instrumento");


            // Iterar sobre los elementos "tipo_instrumento"
            for (int i = 0; i < lisInstru.getLength(); i++) {
                Element instr = (Element) lisInstru.item(i);// Obtener el elemento "tipo_instrumento" actual
                Element tipoInstr = (Element) instr.getParentNode();
                // Obtener los datos del tipo
                String serie = instr.getAttribute("No_Serie");
                String descripcion = instr.getElementsByTagName("descripcion").item(0).getTextContent();
                String minimo = instr.getElementsByTagName("minimo").item(0).getTextContent();
                String maximo = instr.getElementsByTagName("maximo").item(0).getTextContent();
                String tolerancia = instr.getElementsByTagName("tolerancia").item(0).getTextContent();
                String tipo = tipoInstr.getElementsByTagName("nombre").item(0).getTextContent();

                table.addCell(serie);
                table.addCell(descripcion);
                table.addCell(minimo);
                table.addCell(maximo);
                table.addCell(tolerancia);
                table.addCell(tipo);


            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
