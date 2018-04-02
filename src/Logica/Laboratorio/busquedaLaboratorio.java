/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;

import Logica.Extras.tablas;
import Interfaces.BusquedasTiquete;
import Interfaces.BusquedasTiqueteInicial;
import Interfaces.Bascula;
import Interfaces.LaboratorioTiqueteInicial;
import Logica.Laboratorio.laboratorioTiqueteInicial;
import Interfaces.Vehiculo;
import Logica.Extras.currencyFormat;
import Negocio.Conexion;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author uriel
 */
public class busquedaLaboratorio {

    public static Statement st4;

    public static Conexion Con;
    public static BusquedasTiquete BusTiquete;
    public static BusquedasTiqueteInicial BusT;
    public static ResultSet rs;
    public String columnas2[] = new String[]{"Muestreo", "Hora", "Humedad"};
    public String headerColumnas2[] = new String[]{"default", "default", "default"};
    public String camposColumnas2[] = new String[]{"center", "center", "center"};
    public static LaboratorioTiqueteInicial LabT;
    // public static bascula bas;
    public static laboratorioTiqueteInicial labt;
    public static String cedula, id;
    public static tablas tbl;
    public static DefaultTableModel mdelolab, modeloestufa;
    public static String columnasAgr[] = new String[]{"id", "Cedula", "Nombres", "Apellidos", "Direccion", "Municipio"};
    public static String columnasAgri[] = new String[]{"N° Tiq Lab", "N° Tiq Bas", "Agricultor", "Fecha", "Humedad", "Impureza", "Integral", "Cascarilla", "Blanco", "Partido", "Entero", "Yeso", "Dañado", "Ip"};
    public static String headerColumnasAgri[] = new String[]{"30", "30", "150", "40", "30", "30", "20", "30", "20", "20", "20", "20", "20", "20"};
    public static String camposColumnasAgri[] = new String[]{"center", "center", "left", "center", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right"};
    public static currencyFormat cu;

    public busquedaLaboratorio() {
        tbl = new tablas();
        cu = new currencyFormat();
        crearModeloAgricultor();
        //tabla_camposAgricultor();
    }

    public void crearModeloAgricultor() {
        //System.out.println("2");
        mdelolab = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno and laboratorio.estado='cerrado'");
        tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
        tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
        tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
        formatoTabla();
    }

    public void formatoTabla() {
        int row = BusT.tblLaboratorio.getRowCount();
        for (int i = 0; i < row; i++) {
            BusT.tblLaboratorio.setValueAt((cu.dateNotTime(BusT.tblLaboratorio.getValueAt(i, 3).toString())), i, 3);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 4).toString())), i, 4);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 5).toString())), i, 5);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 6).toString())), i, 6);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 7).toString())), i, 7);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 8).toString())), i, 8);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 9).toString())), i, 9);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 10).toString())), i, 10);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 11).toString())), i, 11);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 12).toString())), i, 12);
            BusT.tblLaboratorio.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.tblLaboratorio.getValueAt(i, 13).toString())), i, 13);
        }
    }

    public void crearModelo2() {
        modeloestufa = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        BusT.jTable3.setModel(modeloestufa);
    }

    public void tabla_consecutivo_campo() {
        int rec = BusT.tblLaboratorio.getSelectedRow();
        String id = BusT.tblLaboratorio.getValueAt(rec, 0).toString();
        try {
            Con = new Conexion();
            tbl.llenarTabla(BusT.jTable3, modeloestufa, columnas2.length, "SELECT muestreo,hora,humedad FROM muetraestufa where idLaboratorio ='" + id + "' ");
            tbl.alinearCamposTable(BusT.jTable3, camposColumnas2);
            formatoTablaEntradas();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //crearModelo2(id);
    }

    public void formatoTablaEntradas() {
        int row = BusT.jTable3.getRowCount();
        for (int i = 0; i < row; i++) {
            BusT.jTable3.setValueAt(cu.thousandsFormat(Double.parseDouble(BusT.jTable3.getValueAt(i, 2).toString())), i, 2);
        }
    }

    public static void desactivar_checkboxAgricultor() { // desactiva las checkbox
        BusT.chfecha.setSelected(false);
        BusT.chtiquete.setSelected(false);
        BusT.chcedula.setSelected(false);
    }

    public void buscarAgricultor() {
        String FechaIni, FechaFin;
        Date fecha, fecha2;

        SimpleDateFormat formatoI = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat formato2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        fecha = BusT.jDateinicial.getDate();
        fecha2 = BusT.jDatefinal.getDate();

        if (fecha == null && fecha2 == null) {
            FechaIni = "";
            FechaFin = "";
        } else {
            FechaIni = formatoI.format(fecha);
            FechaFin = formato2.format(fecha2);
        }

        cedula = BusT.txtcedula.getText();
        String numtiquete = BusT.txttiquete.getText();

        mdelolab = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (BusT.chfecha.isSelected() == true && BusT.chtiquete.isSelected() == true && BusT.chcedula.isSelected() == true) {
            if (!FechaIni.equals("") && !FechaFin.equals("") && !cedula.equals("") && !numtiquete.equals("")) {
                tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.estado='cerrado' and laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno AND personalexterno.cedula='" + cedula + "' and laboratorio.idTiquete='" + numtiquete + "' AND laboratorio.fecha > '" + FechaIni + "' AND laboratorio.fecha <'" + FechaFin + "'");
                tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
                tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
                tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chfecha.isSelected() == true && BusT.chtiquete.isSelected() == true) {
            if (!FechaIni.equals("") && !FechaFin.equals("") && !numtiquete.equals("")) {
                tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.estado='cerrado' and laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno AND laboratorio.idTiquete='" + numtiquete + "' AND laboratorio.fecha > '" + FechaIni + "' AND laboratorio.fecha <'" + FechaFin + "'");
                tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
                tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
                tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chfecha.isSelected() == true && BusT.chcedula.isSelected() == true) {
            if (!FechaIni.equals("") && !FechaFin.equals("") && !cedula.equals("")) {
                tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.estado='cerrado' and laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno AND personalexterno.cedula='" + cedula + "' AND laboratorio.fecha > '" + FechaIni + "' AND laboratorio.fecha <'" + FechaFin + "'");
                tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
                tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
                tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chtiquete.isSelected() == true && BusT.chcedula.isSelected() == true) {
            if (!numtiquete.equals("") && !cedula.equals("")) {
                tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.estado='cerrado' and laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno AND personalexterno.cedula='" + cedula + "' and laboratorio.idTiquete='" + numtiquete + "'");
                tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
                tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
                tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chfecha.isSelected() == true) {
            if (!FechaIni.equals("") && !FechaFin.equals("")) {
                tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.estado='cerrado' and laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno AND laboratorio.fecha > '" + FechaIni + "' AND laboratorio.fecha <'" + FechaFin + "'");
                tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
                tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
                tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chtiquete.isSelected() == true) {
            if (!numtiquete.equals("")) {
                tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.estado='cerrado' and laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno AND laboratorio.idTiquete='" + numtiquete + "'");
                tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
                tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
                tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chcedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(BusT.tblLaboratorio, mdelolab, columnasAgri.length, "SELECT idLaboratorio,tiquete.idTiquete,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),laboratorio.fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio,tiquete,personalexterno WHERE laboratorio.estado='cerrado' and laboratorio.idTiquete=tiquete.idTiquete and tiquete.idAgricultor=personalexterno.idPersonalExterno AND personalexterno.cedula='" + cedula + "'");
                tbl.alinearHeaderTable(BusT.tblLaboratorio, headerColumnasAgri);
                tbl.alinearCamposTable(BusT.tblLaboratorio, camposColumnasAgri);
                tbl.alinearHeaderTable(BusT.jTable3, headerColumnas2);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
    }

    public void reporteBasculaTiqPrincipal() {
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
                String path = "src\\reportes\\LaboratorioTiqLab.jasper";//aqui se encuentra el archivo del reporte
                //String path2 = "src\\reportes\\BasculaTiqueVarios_subreport1.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                //reporte2 = (JasperReport) JRLoader.loadObjectFromFile(path2);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                //JasperPrint jprint2 = JasperFillManager.fillReport(reporte2, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(busquedaLaboratorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }
}
