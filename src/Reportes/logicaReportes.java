/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Interfaces.BusquedasTiqueteInicial;
import Interfaces.LiquidacionesAprobadas;
import Interfaces.VerTiquetePrincipal;
import Interfaces.VerTiqueteVarios;
import Negocio.Conexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Lizeth
 */
public class logicaReportes {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static VerTiquetePrincipal VerTiqPrincipal;
    public static VerTiqueteVarios VerTiqVarios;
    public static LiquidacionesAprobadas LiqAprobadas;
    public static BusquedasTiqueteInicial BusT;
    String paths = System.getProperty("user.dir");

    /**
     * BASCULA*
     */
    public void reporteBasculaTiqPrincipal() {
        int row = VerTiqPrincipal.tblVerTiqPrincipal.getSelectedRow();
        if (row != -1) {
            String idTiquete = VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(row, 0).toString();
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_tiquete", idTiquete);//colocar parametros

            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                String path = paths + "//src//reportes//BasculaTiquePrincipal.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    public void reporteBasculaTiqVarios() {
        int row = VerTiqVarios.tblVerTiqVarios.getSelectedRow();
        if (row != -1) {
            String idTiquete = VerTiqVarios.tblVerTiqVarios.getValueAt(row, 0).toString();
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_tiquete", idTiquete);//colocar parametros

            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                String path = paths + "//src//reportes//BasculaTiqueVarios.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    /**
     * LABORATORIO*
     */
    public void reportLaboratorioTiquete() {
        int row = BusT.tblLaboratorio.getSelectedRow();
        if (row != -1) {
            String idTiquete = BusT.tblLaboratorio.getValueAt(row, 0).toString();
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_tiquete", idTiquete);//colocar parametros

            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                JasperReport reporte2 = null;
                String path = paths + "//src//reportes//LaboratorioTiqLab.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    /**
     * CONTADURIA*
     */
    public void reporteCuotaFomento() {
        //JOptionPane.showMessageDialog(null, "EN PROCESOOO");

            String mes = "04";
            String anio = "2018";
            Map parametroMes = new HashMap();//mapeo de parametros
            parametroMes.put("mes", mes);//colocar parametros
            
            Map parametroAnio = new HashMap();//mapeo de parametros
            parametroAnio.put("anio", anio);
            
            
            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                String path = paths + "//src//reportes//ContadorFedearroz.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametroMes, c);//enviamos parametros
                JasperPrint jprint2 = JasperFillManager.fillReport(reporte, parametroAnio, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void reporteGerenteLiquidacion() {
        int row = LiqAprobadas.tblLiquidaciones.getSelectedRow();

        if (row != -1) {
            String idLiquidacion = LiqAprobadas.tblLiquidaciones.getValueAt(row, 0).toString();
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_liquidacion", idLiquidacion);//colocar parametros
            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();
                JasperReport reporte = null;
                String path = paths + "//src//reportes//GerenteLiquidacionesAprobadas.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
                Con.Desconectar();
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    /**
     * GERENTE*
     */
    public void reporteAgriculores() {
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths + "//src//reportes//GerenteAgricultores.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * INVENTARIO*
     */
    public void reporteMateriaPrimaTrillada() {
        JOptionPane.showMessageDialog(null, "en proceso");
        //Workbook book = new HSSFWorkbook();//indicamos que vamos a crear un archivo en excel version antigua
        Workbook book = new XSSFWorkbook();//indicamos que vamos a crear un archivo en excel version nueva
        Sheet sheet = book.createSheet("Silos");//crear una nueva pestaña

        try {
            InputStream is = new FileInputStream(paths + "//src//Img//logoOvalo.png"); //agregar logo
            byte[] bytes = IOUtils.toByteArray(is);//traer la imagen y convertirla
            int imgIndex = book.addPicture(bytes,Workbook.PICTURE_TYPE_PNG);//traemos el index de la imagen
            is.close();//cerrar el input stream para no dejar archivos temporas abiertos
            
            CreationHelper help = book.getCreationHelper();//agregar imagen a ntro archivo
            Drawing draw = sheet.createDrawingPatriarch();//para poder crer la imagen
            
            //colocar imagen
            ClientAnchor anchor = help.createClientAnchor();//sacar el ancho de esta imagen
            anchor.setCol1(0);
            anchor.setRow1(0);
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(1,3);
            
            //estilo titulo
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);//Alineacion horizontal centrada
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);//Alineacion vertical centrada
            
            //tipo fuente
            Font fuenteTitulo=book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short)14);
            tituloEstilo.setFont(fuenteTitulo);
            
            //crear fila en donde va a estar el titulo
            Row filaTitulo = sheet.createRow(2);
            Cell celdaTitulo = filaTitulo.createCell(2);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("MAteria prima trillada");
            sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 3));//fi,fu,ci,cu
            
            //columnas del reporte
            
            //FileOutputStream fileout = new FileOutputStream("trilla.xls");//guardar archivo version antigua excel
            FileOutputStream fileout = new FileOutputStream("trilla.xlsx");//guardar archivo version nueva
            book.write(fileout);
            fileout.close();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * ADMINISTRACION*
     */
    public void reporteUsuarios() {
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths + "//src//reportes//AdministracionUsuarios.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reporteEmpleados() {
        try {

            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths + "//src//reportes//AdministracionEmpleado.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object getServlet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
