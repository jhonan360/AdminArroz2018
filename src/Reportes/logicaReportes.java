package Reportes;

import Negocio.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import static Reportes.logicaReportes.paths;
import Interfaces.BusquedasTiqueteInicial;
import Interfaces.LiquidacionesAprobadas;
import Interfaces.VerTiquetePrincipal;
import Interfaces.VerTiqueteVarios;
import Logica.Extras.currencyFormat;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
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
    static String paths = System.getProperty("user.dir");
    public static currencyFormat cu;
    static String pathExcel = System.getProperty("user.home");

    /**
     * BASCULA*
     */
    public logicaReportes() {
        cu = new currencyFormat();
    }

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
    public static void reporteCuotaFomento(int mes, int anio) {

        Map parametro = new HashMap();//mapeo de parametros
        parametro.put("mes", mes);//colocar parametros
        parametro.put("anio", anio);//colocar parametros

        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths + "//src//reportes//ContadorFedearroz.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
            System.out.println("s" + mes);
            System.out.println("s" + anio);
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

    public void reporteGerenteEstadisticas(int mesIni, int mesFin, int anio) {
        Map parametro = new HashMap();//mapeo de parametros
        parametro.put("mes1", mesIni);//colocar parametros
        parametro.put("mes2", mesFin);//colocar parametros
        parametro.put("anio1", anio);//colocar parametros
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths + "//src//reportes//GerenteEstadisticas.jasper";//aqui se encuentra el archivo del reporte
            System.out.println("su" + mesIni);
            System.out.println("su" + mesFin);
            System.out.println("su" + anio);
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void reporteGeneralMateriaPrima() {
        //Workbook book = new HSSFWorkbook();//indicamos que vamos a crear un archivo en excel version antigua
        Workbook book = new XSSFWorkbook();//indicamos que vamos a crear un archivo en excel version nueva
        Sheet sheet = book.createSheet("Silos");//crear una nueva pestaña

        try {
            InputStream is = new FileInputStream(paths + "//src//Img//logoOvalo.png"); //agregar logo
            byte[] bytes = IOUtils.toByteArray(is);//traer la imagen y convertirla
            int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);//traemos el index de la imagen
            is.close();//cerrar el input stream para no dejar archivos temporas abiertos
            CreationHelper help = book.getCreationHelper();//agregar imagen a ntro archivo
            Drawing draw = sheet.createDrawingPatriarch();//para poder crer la imagen

            //colocar imagen
            ClientAnchor anchor = help.createClientAnchor();//sacar el ancho de esta imagen
            anchor.setCol1(0);
            anchor.setRow1(0);
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(2, 5);

            //estilo titulo
            CellStyle tituloEstilo = book.createCellStyle();
            CellStyle tituloVarias = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);//Alineacion horizontal centrada
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);//Alineacion vertical centrada
            tituloVarias.setAlignment(HorizontalAlignment.CENTER);//Alineacion horizontal centrada
            tituloVarias.setVerticalAlignment(VerticalAlignment.CENTER);//Alineacion vertical centrada

            //tipo fuente
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");//tipo de fuente del titulo
            fuenteTitulo.setBold(true);//negrita
            fuenteTitulo.setFontHeightInPoints((short) 14);//indicamos el tamaño
            tituloEstilo.setFont(fuenteTitulo);//asignamos el formato al estilo

            //tipo fuente
            Font fuenteVarias = book.createFont();
            fuenteVarias.setFontName("Arial");//tipo de fuente del titulo
            fuenteVarias.setBold(true);//negrita
            fuenteVarias.setFontHeightInPoints((short) 12);//indicamos el tamaño
            tituloEstilo.setFont(fuenteVarias);//asignamos el formato al estilo
            
            //crear fila en donde va a estar el titulo
            Row filaTitulo = sheet.createRow(0);//fila donde esta el titulo
            Cell celdaTitulo = filaTitulo.createCell(2);//creamos celda
            celdaTitulo.setCellStyle(tituloEstilo);//agregamos a la celda el estilo
            celdaTitulo.setCellValue("MOLINO ESPINAL S.A");//agregamos contenido de la celda
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 13));//fi,fu,ci,cu

            //crear fila en donde va nit
            Row filaNit = sheet.createRow(2);//fila donde esta el titulo
            Cell celdafilaNit = filaNit.createCell(2);//creamos celda
            celdafilaNit.setCellStyle(tituloVarias);//agregamos a la celda el estilo
            celdafilaNit.setCellValue("NIT. 809.010.728-0");//agregamos contenido de la celda
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 13));//fi,fu,ci,cu
            
            //crear fila en donde va direccion
            Row filaDir = sheet.createRow(3);//fila donde esta el titulo
            Cell celdaDir = filaDir.createCell(2);//creamos celda
            celdaDir.setCellStyle(tituloVarias);//agregamos a la celda el estilo
            celdaDir.setCellValue("Km 1 via Espinal Ibagué Tels: 2485131 - 2483407 - Espinal - Tol.");//agregamos contenido de la celda
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 13));//fi,fu,ci,cu
            
            //crear fila en donde va correo
            Row filaEmail = sheet.createRow(4);//fila donde esta el titulo
            Cell celdEmail = filaEmail.createCell(2);//creamos celda
            celdEmail.setCellStyle(tituloVarias);//agregamos a la celda el estilo
            celdEmail.setCellValue("E-mail:molinoespinal_sa@hotmail.com");//agregamos contenido de la celda
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 13));//fi,fu,ci,cu
            
            //crear fila en donde va correo
            Row filaNomRepor = sheet.createRow(6);//fila donde esta el titulo
            Cell celdNomRepor = filaNomRepor.createCell(0);//creamos celda
            celdNomRepor.setCellStyle(tituloEstilo);//agregamos a la celda el estilo
            celdNomRepor.setCellValue("REPORTE GENERAL DE MATERIA PRIMA");//agregamos contenido de la celda
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 13));//fi,fu,ci,cu
            sheet.addMergedRegion(new CellRangeAddress(6, 6,0, 13));//fi,fu,ci,cu
            
            //columnas del reporte
            String[] cabecera = new String[]{"N°", "Cuenta", "Fecha", "Agricultor", "Tipo Arroz", "Lote", "Conductor", "Vehiculo", "Kl Brutos", "Destare", "Kl Netos", "Humedad", "Impureza", "Observación"};

            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());//color de relleno
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//relleno solido
            headerStyle.setBorderBottom(BorderStyle.THIN);//bordes, cuadricula
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            Font font = book.createFont();
            font.setFontName("Arial");//tipo de fuente 
            font.setBold(true);//negrita
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);//indicamos el tamaño
            headerStyle.setFont(font);//asignamos el formato al estilo

            Row filaEncabezados = sheet.createRow(7);//colocar una nueva fila para colocar encabezados
            //imprimimos el valor de las cabezar a las celdas
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEncabezado = filaEncabezados.createCell(i);//se crean por separado para adignar el estilo a las celdas
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(cabecera[i]);
            }
            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                int numFilaDatos = 8;

                CellStyle datosEstilo = book.createCellStyle();
                datosEstilo.setBorderBottom(BorderStyle.THIN);//bordes, cuadricula
                datosEstilo.setBorderLeft(BorderStyle.THIN);
                datosEstilo.setBorderRight(BorderStyle.THIN);
                datosEstilo.setBorderBottom(BorderStyle.THIN);

                rs = st.executeQuery("SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                int numCol = rs.getMetaData().getColumnCount();
                System.out.println(numCol);
                while (rs.next()) {
                    Row filaDatos = sheet.createRow(numFilaDatos);
                    for (int j = 0; j < numCol; j++) {
                        Cell CeldaDatos = filaDatos.createCell(j);//creamos por separao cada una de las celdas
                        CeldaDatos.setCellStyle(datosEstilo);//agregar formato de celdas
                        //Agregar valor a c/u de las celdas y definir los tipos de datos de cada campo
                        if (j == 8 || j == 9 || j == 10 || j == 11 || j == 12) {
                            CeldaDatos.setCellValue(rs.getDouble(j + 1));
                        } else {
                            CeldaDatos.setCellValue(rs.getString(j + 1));
                        }
                    }
                    numFilaDatos++;
                }
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);
                sheet.autoSizeColumn(9);
                sheet.autoSizeColumn(10);
                sheet.autoSizeColumn(11);
                sheet.autoSizeColumn(12);
                sheet.autoSizeColumn(13);

                sheet.setZoom(86);
                JOptionPane.showMessageDialog(null, "Su reporte ha sido guardado en Descargas");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //FileOutputStream fileout = new FileOutputStream("trilla.xls");//guardar archivo version antigua excel
            java.util.Date date = new Date();
            String dates = cu.DateTime(date);
            dates = dates.replace(" ", "_");
            dates = dates.replace(":", "-");

            String nameFile = "Report_" + dates;
            FileOutputStream fileout = new FileOutputStream(pathExcel + "/Downloads/" + nameFile + ".xlsx");//guardar archivo version nueva
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
