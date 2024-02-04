package labPresentation.Model;

import Protocol.Calibraciones;
import Protocol.Instrumento;
import Protocol.Mediciones;
import Protocol.TipoInstrumentoObj;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import labPresentation.Model.Calibraciones.DOM_calibraciones;
//import labPresentation.Model.Calibraciones.Mediciones;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PDF {
    private static final Font prueba = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC);
    private static final Font pru2 = FontFactory.getFont(FontFactory.HELVETICA, 22, Font.ITALIC);
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    //private String tipoReporte;

    Document document = new Document();

    List<TipoInstrumentoObj> listaTipos;
    List<Instrumento> listaInstrumentos;
    List<Calibraciones> listaCalibraciones;
    List<Mediciones> listaMediciones;

    public void setListaTipos(List<TipoInstrumentoObj> listaTipos) {
        this.listaTipos = listaTipos;
    }

    public void setListaInstrumentos(List<Instrumento> listaInstrumentos) {
        this.listaInstrumentos = listaInstrumentos;
    }

    public void setListaCalibraciones(List<Calibraciones> listaCalibraciones) {
        this.listaCalibraciones = listaCalibraciones;
    }

    public void setListaMediciones(List<Mediciones> listaMediciones) {
        this.listaMediciones = listaMediciones;
    }


    public PDF(){

    }

    public void createPDF(String tipoReporte) {
        try (FileOutputStream fos = new FileOutputStream(tipoReporte+".pdf")) {
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.setInitialLeading(20);

            document.open();

            // Metadata
            document.addAuthor("Mauricio Chaves");
            document.addSubject("Reporte ");
            document.addCreationDate();
            document.addTitle("SILAB:Sistema de Laboratorio Industrial");

            // Content
            document.add(new Paragraph("SILAB:Sistema de Laboratorio Industrial", pru2));
            document.add(new Paragraph("Reporte de  "+ tipoReporte +", creado a las: " + LocalDate.now().toString()));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            PdfPTable tabla = tablaReporte(tipoReporte);
            if(tabla != null){
                document.add(tabla);
            }


            document.close();
            JOptionPane.showMessageDialog(null, "Reporte creado.");
            try {
                File file = new File(tipoReporte + ".pdf");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                // Handle error if unable to open file
            }
        } catch (FileNotFoundException e) {
            // Handle file not found error
        } catch (DocumentException | IOException e) {
            // Handle PDF creation error
        }
    }
    public void createPDFreportes(String infoIns, String ser, String tipoReporte) {
        try (FileOutputStream fos = new FileOutputStream(tipoReporte+".pdf")) {
            document = new Document();

            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.setInitialLeading(20);

            document.open();

            // Metadata
            document.addAuthor("Mauricio Chaves");
            document.addSubject("Reporte ");
            document.addCreationDate();
            document.addTitle("SILAB:Sistema de Laboratorio Industrial");

            // Content
            document.add(new Paragraph("SILAB:Sistema de Laboratorio Industrial", pru2));
            document.add(new Paragraph("Reporte de  "+ tipoReporte +", creado a las: " + LocalDate.now().toString()));
            document.add(new Paragraph("\n"));
            //document.add(new Paragraph(toString, pru2));
            document.add(new Paragraph("\n"));


            document.add(new Paragraph("Calibrciones y mediciones de: "+infoIns));
            PdfPTable tabla = tablaCal();
            for(Calibraciones obj: listaCalibraciones){
                if(obj.getNo_SerieIns().equals(ser)){
                    tabla.addCell(obj.getTipo());
                    tabla.addCell(obj.getNo_SerieIns());
                    tabla.addCell(String.valueOf(obj.getNumeroCalibracion()));
                    tabla.addCell("-");
                    tabla.addCell("-");
                    tabla.addCell("-");
                    for(Mediciones med:listaMediciones){
                        tabla.addCell("-");//tipo
                        tabla.addCell("-");//serie
                        tabla.addCell("-");//numColum
                        //mediciones----------------------------
                        tabla.addCell(String.valueOf(med.getNumMedicion()));//numero
                        tabla.addCell(String.valueOf(med.getValorReferencia()));//referencia
                        tabla.addCell(String.valueOf(med.getValorMarcado()));///lectura

                    }
                }
            }

            if(tabla != null){
                document.add(tabla);
            }


            document.close();
            JOptionPane.showMessageDialog(null, "Reporte creado.");
            try {
                File file = new File(tipoReporte + ".pdf");
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                // Handle error if unable to open file
            }
        } catch (FileNotFoundException e) {
            // Handle file not found error
        } catch (DocumentException | IOException e) {
            // Handle PDF creation error
        }
    }

    public PdfPTable tablaReporte(String tipo){
        switch (tipo){
            case "tipos de instrumentos": return tablaTiposIns();
            case "instrumentos": return tablaIns();
            case "calibraciones": return tablaCal();

        }
        return null;
        
    }

    private PdfPTable tablaCal() {
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        PdfPCell tipo = new PdfPCell(new Phrase("Tipo"));
        tipo.setBackgroundColor(BaseColor.CYAN);
        PdfPCell numSerie = new PdfPCell(new Phrase("Numero de serie"));
        numSerie.setBackgroundColor(BaseColor.CYAN);
        PdfPCell numCal = new PdfPCell(new Phrase("Numero de calibración"));
        numCal.setBackgroundColor(BaseColor.CYAN);
        PdfPCell num = new PdfPCell(new Phrase("Numero"));
        num.setBackgroundColor(BaseColor.RED);
        PdfPCell ref = new PdfPCell(new Phrase("Referencia"));
        ref.setBackgroundColor(BaseColor.ORANGE);
        PdfPCell lec = new PdfPCell(new Phrase("Lectura"));
        lec.setBackgroundColor(BaseColor.ORANGE);

        tabla.addCell(tipo);
        tabla.addCell(numSerie);
        tabla.addCell(numCal);
        tabla.addCell(num);
        tabla.addCell(ref);
        tabla.addCell(lec);


        for(Calibraciones obj: listaCalibraciones){
           //if(obj.getNo_SerieIns().equals(ser)){
                tabla.addCell(obj.getTipo());
                tabla.addCell(obj.getNo_SerieIns());
                tabla.addCell(String.valueOf(obj.getNumeroCalibracion()));
                tabla.addCell("-");
                tabla.addCell("-");
                tabla.addCell("-");
                for(Mediciones med:listaMediciones){
                    tabla.addCell("-");//tipo
                    tabla.addCell("-");//serie
                    tabla.addCell("-");//numColum
                    //mediciones----------------------------
                    tabla.addCell(String.valueOf(med.getNumMedicion()));//numero
                    tabla.addCell(String.valueOf(med.getValorReferencia()));//referencia
                    tabla.addCell(String.valueOf(med.getValorMarcado()));///lectura

                }
           // }
        }


        return tabla;
    }

    public PdfPTable tablaTiposIns(){
        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        PdfPCell cod = new PdfPCell(new Phrase("Código"));
        cod.setBackgroundColor(BaseColor.CYAN);
        PdfPCell nom = new PdfPCell(new Phrase("Nombre"));
        nom.setBackgroundColor(BaseColor.ORANGE);
        PdfPCell un = new PdfPCell(new Phrase("Unidad"));
        un.setBackgroundColor(BaseColor.ORANGE);

        tabla.addCell(cod);
        tabla.addCell(nom);
        tabla.addCell(un);


        for (TipoInstrumentoObj obj: listaTipos){
            tabla.addCell(obj.getCodigo());
            tabla.addCell(obj.getNombre());
            tabla.addCell(obj.getUnidad());
        }


        return tabla;
    }
    public PdfPTable tablaIns(){
        PdfPTable tabla = new PdfPTable(6);



        tabla.setWidthPercentage(100);
        PdfPCell ser = new PdfPCell(new Phrase("No. serie"));
        ser.setBackgroundColor(BaseColor.CYAN);
        PdfPCell des = new PdfPCell(new Phrase("Descripción"));
        des.setBackgroundColor(BaseColor.ORANGE);
        PdfPCell min = new PdfPCell(new Phrase("Mínimo"));
        min.setBackgroundColor(BaseColor.ORANGE);
        PdfPCell max = new PdfPCell(new Phrase("Máximo"));
        max.setBackgroundColor(BaseColor.ORANGE);
        PdfPCell tol = new PdfPCell(new Phrase("Tolerancia"));
        tol.setBackgroundColor(BaseColor.ORANGE);
        PdfPCell tip = new PdfPCell(new Phrase("Tipo"));
        tip.setBackgroundColor(BaseColor.ORANGE);

        tabla.addCell(ser);
        tabla.addCell(des);
        tabla.addCell(min);
        tabla.addCell(max);
        tabla.addCell(tol);
        tabla.addCell(tip);


        for (Instrumento obj: listaInstrumentos){
            tabla.addCell(obj.getSerie());
            tabla.addCell(obj.getDescripcion());
            tabla.addCell(String.valueOf(obj.getMinimo()));
            tabla.addCell(String.valueOf(obj.getMaximo()));
            tabla.addCell(String.valueOf(obj.getTolerancia()));
            tabla.addCell(obj.getTipo());
        }


        return tabla;
    }

}
