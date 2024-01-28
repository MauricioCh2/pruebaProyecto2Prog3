package labPresentation.Model.Calibraciones;


import Protocol.Calibraciones;
import Protocol.Instrumento;
import com.itextpdf.text.pdf.PdfPTable;
import labPresentation.Model.DOM;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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
import java.util.List;

public class DOM_calibraciones extends DOM {

    public DOM_calibraciones() throws ParserConfigurationException, IOException, TransformerException {
    }

    public boolean addCalibraciones(Calibraciones cal) {
        boolean result = false;
        boolean codExist = false;
        try {
            if (!file.exists()) {
                return result;
            }
            Document doc = parseXMLFile();
            //codExist = checkCodigoExists(doc, cal.getNo_SerieIns(), "instrumento", "No_Serie");
            //if (!codExist) {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            Element node = (Element) xpath.evaluate(
                    "//tipo_instrumento/instrumento[@No_Serie='" + cal.getNo_SerieIns() + "']",
                    doc,
                    XPathConstants.NODE// se espera que el resultado sea del tipo NODE
            );
            if (node != null) {
                // Node root = doc.getElementsByTagName("tipo_instrumento").item(0);

                Element newInst = doc.createElement("calibracion"); //sera nuestra base
                node.appendChild(newInst);

                Attr attr = doc.createAttribute("Numero_Calibracion");
                attr.setValue(String.valueOf(cal.getNumeroCalibracion()));
                newInst.setAttributeNode(attr);

                Element fecha = doc.createElement("fecha");
                fecha.appendChild(doc.createTextNode(String.valueOf(cal.getFecha())));
                newInst.appendChild(fecha);

                Element mediciones = doc.createElement("mediciones");
                mediciones.appendChild(doc.createTextNode(String.valueOf(cal.getNumeroMediciones())));
                newInst.appendChild(mediciones);


                //--------------------------------------------------------

                generarXML(doc);
                document = doc;
                result = true;
                // }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cargarCalibraciones(JTable tabla, String noSerie) {
        try {
            Document doc = parseXMLFile();

            // Obtener todos los elementos "vuelo"
            NodeList lisInstrumento = (NodeList) doc.getElementsByTagName("instrumento");

            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            // Iterar sobre los elementos "tipo_instrumento"
            for (int i = 0; i < lisInstrumento.getLength(); i++) {
                Element instrumento = (Element) lisInstrumento.item(i);// Obtener el elemento "tipo_instrumento" actual

                if (noSerie.equals(instrumento.getAttribute("No_Serie"))) {
                    NodeList lisCalibraciones = (NodeList) instrumento.getElementsByTagName("calibracion");//todas las calibraciones del elemento
                    for (int j = 0; j < lisInstrumento.getLength(); j++) {
                        // Obtener los datos del tipo
                        Element calibracion = (Element) lisCalibraciones.item(j);
                        if (calibracion != null) {
                            String numeroCal = calibracion.getAttribute("Numero_Calibracion");
                            String fecha = calibracion.getElementsByTagName("fecha").item(0).getTextContent();
                            String medicion = calibracion.getElementsByTagName("mediciones").item(0).getTextContent();

                            Object[] newRow = {numeroCal, fecha, medicion};

                            modelo.addRow(newRow);
                        }
                    }
                }


            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarCalibraciones(String ser, String numCa) {
        boolean result = false;
        try {
            Document doc = parseXMLFile();
            if (!file.exists()) {
                return result;
            }
            XPathFactory factory = XPathFactory.newInstance(); //sirve para crear objetos XPath
            XPath xpath = factory.newXPath();//permite evaluar expreciones de un XML

            NodeList nodes = (NodeList) xpath.evaluate(
                    "//tipos_instrumentos/tipo_instrumento/instrumento[@No_Serie='" + ser + "']/calibracion[@Numero_Calibracion='" + numCa + "']",
                    doc,
                    XPathConstants.NODESET// se espera que el resultado sea del tipo NODESET
            );
            if (nodes != null) {
                Element node = (Element) nodes.item(0);
                node.getParentNode().removeChild(node);
                generarXML(doc);
                result = true;
            }
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException |
                 TransformerException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean buscarCalibracionPorNum(String serie, JTable tabla) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        boolean alguno = false;
        // Parsear el XML
        Document doc = parseXMLFile();

        // Obtener todos los elementos "instrumento"
        NodeList lisInstrumento = doc.getElementsByTagName("instrumento");

        // Iterar sobre los elementos "instrumento"
        for (int i = 0; i < lisInstrumento.getLength(); i++) {
            // Obtener todos los elementos "calibracion" para el instrumento actual
            Element instru = (Element) lisInstrumento.item(i);
            NodeList calibraciones = instru.getElementsByTagName("calibracion");

            // Iterar sobre los elementos "calibracion"
            for (int j = 0; j < calibraciones.getLength(); j++) {
                // Obtener el elemento "calibracion" actual
                Element calibracion = (Element) calibraciones.item(j);

                // Comprobar si el número de calibración coincide
                String numCalibracionActual = calibracion.getAttribute("Numero_Calibracion");

                if (numCalibracionActual.equals(serie)) {
                    String num = calibracion.getAttribute("Numero_Calibracion");
                    String fecha = calibracion.getElementsByTagName("fecha").item(0).getTextContent();
                    String med = calibracion.getElementsByTagName("mediciones").item(0).getTextContent();
                    String ins = instru.getAttribute("No_Serie");

                    Object[] newRow = {num, ins, fecha, med};
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
        }
        return alguno;
    }
    public boolean buscarCalibracionPorNum(String serie,String numCal, JTable tabla){
        boolean alguno = false;
        try {
            Document doc = parseXMLFile();

            XPathFactory factory = XPathFactory.newInstance(); //sirve para crear objetos XPath
            XPath xpath = factory.newXPath();//permite evaluar expreciones de un XML

            Element node = (Element) xpath.evaluate(
                    "//tipos_instrumentos/tipo_instrumento/instrumento[@No_Serie='" + serie + "']",
                    doc,
                    XPathConstants.NODE// se espera que el resultado sea del tipo NODE
            );
            if(node!=null){
                NodeList lisCali = (NodeList) node.getElementsByTagName("calibracion");

                for(int i = 0; i < lisCali.getLength();i++) {
                    Element cali = (Element)lisCali.item(i);
                    String numCalibracionActual = cali.getAttribute("Numero_Calibracion");

                    if (numCalibracionActual.equals(numCal)) {//evalua ahora si con el numero de calibracion
                        String num = cali.getAttribute("Numero_Calibracion");
                        String fecha = cali.getElementsByTagName("fecha").item(0).getTextContent();
                        String med = cali.getElementsByTagName("mediciones").item(0).getTextContent();
                        String ins = cali.getAttribute("No_Serie");

                        Object[] newRow = {num, fecha, med};
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
            }

            return alguno;
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            throw new RuntimeException(e);
        }

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

                       String ser = instr.getAttribute("No_Serie");
                       String desc = instr.getElementsByTagName("descripcion").item(0).getTextContent();
                       String min = instr.getElementsByTagName("minimo").item(0).getTextContent();
                       String max = instr.getElementsByTagName("maximo").item(0).getTextContent();
                       String tol = instr.getElementsByTagName("tolerancia").item(0).getTextContent();
                       String tipo = tipoInstr.getElementsByTagName("nombre").item(0).getTextContent();

                   Instrumento aux = new Instrumento(ser,desc,tipo,min,max,tol);
                   if(instr.getElementsByTagName("calibracion").getLength() > 0){
                       NodeList lisCal = instr.getElementsByTagName("calibracion");
                       for (int j = 0; j < lisCal.getLength(); j++) {
                           Element cal = (Element) lisCal.item(j);// Obtener el elemento "calibracion" actual#################################
                           //if(cal.getAttribute("Numero_Calibracion").isEmpty()) {
                                table.addCell(tipo);
                                table.addCell(ser);
                                table.addCell(cal.getAttribute("Numero_Calibracion"));
                                table.addCell("-");
                                table.addCell("-");
                                table.addCell("-");


                               // Obtener los datos del tipo
                               String numCal = cal.getAttribute("Numero_Calibracion");
                               String fecha = cal.getElementsByTagName("fecha").item(0).getTextContent();
                               int numMediciones = Integer.parseInt(cal.getElementsByTagName("mediciones").item(0).getTextContent());
                               MedicionesModel medicionesModel = new MedicionesModel();
                               List<Mediciones> listMed = medicionesModel.obtenerLisMediciones(aux, Integer.parseInt(numCal));
                               int k = 1;
                               for(Mediciones med:listMed){
                              // for (int k = 0; k < numMediciones; k++) {
                                   table.addCell("-");//tipo
                                   table.addCell("-");//serie
                                   table.addCell("-");//numColum
                                   //mediciones----------------------------
                                   table.addCell(String.valueOf(k++));//numero
                                   table.addCell(String.valueOf(med.getValorReferencia()));//referencia
                                   //table.addCell(String.valueOf("lectura");
                                   // table.addCell(String.valueOf(l.getValorReferencia()));
                                   table.addCell(String.valueOf(med.getValorMarcado()));///lectura

                               }
                       }
                   }

               }
           } catch (ParserConfigurationException | IOException | SAXException e) {
               throw new RuntimeException(e);
           }
       }

    public void createTablePDF(PdfPTable table, String noSer){
        try {
            Document doc = parseXMLFile();

            // Obtener todos los elementos "vuelo"
            NodeList lisInstru = (NodeList) doc.getElementsByTagName("instrumento");


            XPathFactory factory = XPathFactory.newInstance(); //sirve para crear objetos XPath
            XPath xpath = factory.newXPath();//permite evaluar expreciones de un XML

            Element instr = (Element) xpath.evaluate(
                    "//tipos_instrumentos/tipo_instrumento/instrumento[@No_Serie='" + noSer + "']",
                    doc,
                    XPathConstants.NODE// se espera que el resultado sea del tipo NODE
            );

                Element tipoInstr = (Element) instr.getParentNode();

                String ser = instr.getAttribute("No_Serie");
                String desc = instr.getElementsByTagName("descripcion").item(0).getTextContent();
                String min = instr.getElementsByTagName("minimo").item(0).getTextContent();
                String max = instr.getElementsByTagName("maximo").item(0).getTextContent();
                String tol = instr.getElementsByTagName("tolerancia").item(0).getTextContent();
                String tipo = tipoInstr.getElementsByTagName("nombre").item(0).getTextContent();

                Instrumento aux = new Instrumento(ser,desc,tipo,min,max,tol);
                if(instr.getElementsByTagName("calibracion").getLength() > 0){
                    NodeList lisCal = instr.getElementsByTagName("calibracion");
                    for (int j = 0; j < lisCal.getLength(); j++) {
                        Element cal = (Element) lisCal.item(j);// Obtener el elemento "calibracion" actual#################################
                        //if(cal.getAttribute("Numero_Calibracion").isEmpty()) {
                        table.addCell(tipo);
                        table.addCell(ser);
                        table.addCell(cal.getAttribute("Numero_Calibracion"));
                        table.addCell("-");
                        table.addCell("-");
                        table.addCell("-");


                        // Obtener los datos del tipo
                        String numCal = cal.getAttribute("Numero_Calibracion");
                        String fecha = cal.getElementsByTagName("fecha").item(0).getTextContent();
                        int numMediciones = Integer.parseInt(cal.getElementsByTagName("mediciones").item(0).getTextContent());
                        MedicionesModel medicionesModel = new MedicionesModel();
                        List<Mediciones> listMed = medicionesModel.obtenerLisMediciones(aux, Integer.parseInt(numCal));
                        int k = 1;
                        for(Mediciones med:listMed){
                            // for (int k = 0; k < numMediciones; k++) {
                            table.addCell("-");//tipo
                            table.addCell("-");//serie
                            table.addCell("-");//numColum
                            //mediciones----------------------------
                            table.addCell(String.valueOf(k++));//numero
                            table.addCell(String.valueOf(med.getValorReferencia()));//referencia
                            //table.addCell(String.valueOf("lectura");
                            // table.addCell(String.valueOf(l.getValorReferencia()));
                            table.addCell(String.valueOf(med.getValorMarcado()));///lectura

                        }
                    }
                }


        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

}

