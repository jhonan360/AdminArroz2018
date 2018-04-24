/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

/**
 *
 * @author Lizeth
 */
import Logica.Extras.currencyFormat;
import Negocio.Conexion;
import static Reportes.logicaReportes.paths;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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

public class excel {

    public static Statement st;
    public static Conexion Con;
    public static ResultSet rs;
    public static currencyFormat cu;
    static String pathExcel = System.getProperty("user.home");

    public static void main(String[] args) {
        reporteMateriaPrimaTrillada();
    }

    public static void reporteMateriaPrimaTrillada() {
        JOptionPane.showMessageDialog(null, "en proceso");
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
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);//Alineacion horizontal centrada
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);//Alineacion vertical centrada

            //tipo fuente
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");//tipo de fuente del titulo
            fuenteTitulo.setBold(true);//negrita
            fuenteTitulo.setFontHeightInPoints((short) 14);//indicamos el tamaño
            tituloEstilo.setFont(fuenteTitulo);//asignamos el formato al estilo

            //crear fila en donde va a estar el titulo
            Row filaTitulo = sheet.createRow(1);//fila donde esta el titulo
            Cell celdaTitulo = filaTitulo.createCell(2);//creamos celda
            celdaTitulo.setCellStyle(tituloEstilo);//agregamos a la celda el estilo
            celdaTitulo.setCellValue("REPORTE GENERAL DE MATERIA PRIMA");//agregamos contenido de la celda
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 13));//fi,fu,ci,cu

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

            Row filaEncabezados = sheet.createRow(6);//colocar una nueva fila para colocar encabezados
            //imprimimos el valor de las cabezar a las celdas
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEncabezado = filaEncabezados.createCell(i);//se crean por separado para adignar el estilo a las celdas
                celdaEncabezado.setCellStyle(headerStyle);
                celdaEncabezado.setCellValue(cabecera[i]);
            }

            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                int numFilaDatos = 7;

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
                        //Agregar valor a c/u de las celdas
                        //definir los tipos de datos de cada campo
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            //FileOutputStream fileout = new FileOutputStream("trilla.xls");//guardar archivo version antigua excel
            java.util.Date date = new Date();
            cu=new currencyFormat();
            String dates = cu.DateTime(date);
            dates = dates.replace(" ", "_");
            dates = dates.replace(":", "-");

            String nameFile = "Report_" +dates;
            FileOutputStream fileout = new FileOutputStream(pathExcel + "/Downloads/" + nameFile + ".xlsx");//guardar archivo version nueva
            book.write(fileout);
            fileout.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
