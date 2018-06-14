/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Auditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import Interfaces.Auditoria;
import Interfaces.Login;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import Logica.Extras.currencyFormat;
import Logica.Extras.tablas;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author uriel
 */
public class auditoria {

    public static Auditoria Auditoria;
    public static Login Login;
    public static currencyFormat cu;
    static final Logger LOGGER = Logger.getAnonymousLogger();
    String path = System.getProperty("user.dir");
    JFileChooser seleccionado1 = new JFileChooser();//Declaracion del objeto filechooser el cual abre la ventana encargada de recivir el archivo
    File Archivo1;//elemento tipo file almacena el arcivo
    File fichero = new File(path + "/logs/logs.log");
    File fichero2 = new File(path + "/descarga_logs/logs.log");
    String ficheroini = path + "/logs/logs.log";
    String ficherofin = path + "/descarga_logs/logs.log";
    File Dir = null;
    long Tamaño_Archivo1 = 0;//almacena el tamaño del archivo el kilobytes
    String NombreArchivo1 = "";//Almacena el nombre del archivo 1
    String Extension_Archivo1 = "";//almacena la extencion del archivo 1
    String evento = "";
    String usuario = "";
    FileReader filereader1 = null;//se encarga de leer el archivo 1
    BufferedReader bufferedreader1 = null;//se encarga de procesar el archivo 1
    String Lineas_Archivo1 = "";//lee cada una de las lineas del archivo 1 al momento de ser procesado
    int Cantidad_Lineas1 = 0;//almacena la cantidad del registros del archivo 1 
    String[][] matriz1 = new String[Cantidad_Lineas1][5];//matriz encargada de qalmacenar cada uno de los campos existentes en el archivo 1     
    String[][] matriz2 = new String[2][5];
    String[][] matriz3 = new String[2][5];
    String[][] matriz4 = new String[2][5];
    public tablas tbl;
    public DefaultTableModel Modelo1;//declara el midelo de la tabla 1 para el archivo 1
    public String columnas[] = new String[]{"N°", "Evento", "Cambios", "Usuario", "Fecha"};//declara el nombre de cada una de las columnas para las dos tablas
    static String pathDownload = System.getProperty("user.home");

    public auditoria() {
        cu = new currencyFormat();
        crearModelo();
        //LeerArchivos();

    }

    public static void salir() {
        if (Login != null) {
            Login.dispose();
            Login = new Login();
        } else {
            Login = new Login();
        }
        Login.setVisible(true);
    }

    public void crearModelo() {
        Modelo1 = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria ORDER BY fecha DESC ");
        //tbl.alinearHeaderTable(Cdt.jTable1, headerColumnas);
        //tbl.alinearCamposTable(Cdt.jTable1, camposColumnas);
        //tbl.rowNumberTabel(Cdt.jTable1);
    }

    public void buscar() {
        String fechaI, fechaF;
        Date FechaInicial, FechaFinal;

        SimpleDateFormat formatoI = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat formatoF = new SimpleDateFormat("yyy-MM-dd 23:59:59");
        FechaInicial = Auditoria.dcFechaInicial.getDate();
        FechaFinal = Auditoria.dcFechaFinal.getDate();

        if (FechaInicial == null && FechaFinal == null) {
            fechaI = "";
            fechaF = "";
        } else {
            fechaI = formatoI.format(FechaInicial);
            fechaF = formatoF.format(FechaFinal);
        }

        String evento = Auditoria.CmbEvento.getSelectedItem().toString();
        String usuario = Auditoria.TxtUsuario.getText();

        Modelo1 = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        
        if (Auditoria.ChEvento.isSelected() == true && Auditoria.ChUsuario.isSelected() == true && Auditoria.chFecha.isSelected()) {
            if (!evento.equals("") && !usuario.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria WHERE evento='" + evento + "' AND user LIKE '%" + usuario + "%' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "'  ORDER BY fecha DESC ");
                //tbl.alinearHeaderTable(Auditoria.tblAuditoria, headerColumnas);
                //tbl.alinearCamposTable(Auditoria.tblAuditoria, camposColumnas);
                //formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Auditoria.ChEvento.isSelected() == true && Auditoria.ChUsuario.isSelected() == true) {
            if (!evento.equals("") && !usuario.equals("")) {
                tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria WHERE evento='" + evento + "' AND user LIKE '%" + usuario + "%'  ORDER BY fecha DESC ");
                //tbl.alinearHeaderTable(Auditoria.tblAuditoria, headerColumnas);
                //tbl.alinearCamposTable(Auditoria.tblAuditoria, camposColumnas);
                //formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Auditoria.ChEvento.isSelected() == true && Auditoria.chFecha.isSelected()) {
            if (!evento.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria WHERE evento='" + evento + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "'  ORDER BY fecha DESC ");
                //tbl.alinearHeaderTable(Auditoria.tblAuditoria, headerColumnas);
                //tbl.alinearCamposTable(Auditoria.tblAuditoria, camposColumnas);
                //formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Auditoria.ChUsuario.isSelected() == true && Auditoria.chFecha.isSelected() == true) {
            if (!usuario.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria WHERE user LIKE '%" + usuario + "%' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "'  ORDER BY fecha DESC ");
                //tbl.alinearHeaderTable(Auditoria.tblAuditoria, headerColumnas);
                //tbl.alinearCamposTable(Auditoria.tblAuditoria, camposColumnas);
                //formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Auditoria.ChEvento.isSelected() == true) {
            if (!evento.equals("")) {
                tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria WHERE evento='" + evento + "'  ORDER BY fecha DESC ");
                //tbl.alinearHeaderTable(Auditoria.tblAuditoria, headerColumnas);
                //tbl.alinearCamposTable(Auditoria.tblAuditoria, camposColumnas);
                //formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Auditoria.ChUsuario.isSelected() == true) {
            if (!usuario.equals("")) {
                tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria WHERE user LIKE '%" + usuario + "%'  ORDER BY fecha DESC ");
                //tbl.alinearHeaderTable(Auditoria.tblAuditoria, headerColumnas);
                //tbl.alinearCamposTable(Auditoria.tblAuditoria, camposColumnas);
                //formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Auditoria.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(Auditoria.TablaArchivo1, Modelo1, columnas.length, "SELECT id,evento,log,user,fecha FROM logsauditoria WHERE fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "'  ORDER BY fecha DESC ");
                //tbl.alinearHeaderTable(Auditoria.tblAuditoria, headerColumnas);
                //tbl.alinearCamposTable(Auditoria.tblAuditoria, camposColumnas);
                //formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();*/
    }
    
    public void CopiarArchivo(){
        try {
            java.util.Date date = new Date();
            String dates = cu.DateTime(date);
            dates = dates.replace(" ", "_");
            dates = dates.replace(":", "-");
            String nameFile = "Logs_" + dates;
            String pathFile = pathDownload + "/Downloads/" + nameFile + ".txt";
            FileWriter flwriter = null;
            flwriter = new FileWriter(pathFile);
            BufferedWriter bfwriter = new BufferedWriter(flwriter);
            for (int i = 0 ; i < Auditoria.TablaArchivo1.getRowCount(); i++) //realiza un barrido por filas.
            {
                for(int j = 0 ; j < Auditoria.TablaArchivo1.getColumnCount();j++) //realiza un barrido por columnas.
                {
                    bfwriter.write((String)(Auditoria.TablaArchivo1.getValueAt(i,j)));
                    if (j < Auditoria.TablaArchivo1.getColumnCount() -1) { //agrega separador "," si no es el ultimo elemento de la fila.
                        bfwriter.write(",");
                    }
                }
                bfwriter.newLine(); //inserta nueva linea.
            }
            bfwriter.close();
            JOptionPane.showMessageDialog(null, "Exportación finalizada archivo alojado en descargas "+nameFile + ".txt");
        } catch (IOException e) {
            
        }
    }
}
