/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Interfaces.BusquedasTiquete;
import Negocio.Conexion;
import Interfaces.TiqueteVarios;
import Interfaces.VerTiquetePrincipal;
import static Logica.Bascula.bascula.Vehiculo;
import Logica.Extras.currencyFormat;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.log;
import Logica.Extras.login;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lizeth
 */
public class verTiquetePrincipal {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static VerTiquetePrincipal VerTiqPrincipal;
    public static extras ext;
    public tablas tbl;
    public static String placa, color, modelo, marca, idVehiculo;
    public static DefaultTableModel modelVerTiqPrincipal;
    public static String columnas[] = new String[]{"N°", "Fecha", "Agricultor", "Tipo Arroz", "Lote", "Conductor", "Vehiculo", "Kl Brutos", "Destare", "Kl Netos", "H", "I", "Observación"};
    public static String headerColumnas[] = new String[]{"20", "45", "180", "default", "default", "180", "55", "55", "55", "55", "10", "10", "default"};
    public static String camposColumnas[] = new String[]{"center", "center", "left", "left", "left", "left", "left", "right", "right", "right", "right", "right", "left"};
    //public static log logs;
    public static login login;
    public static currencyFormat cu;

    public verTiquetePrincipal() {
        ext = new extras();
        cu = new currencyFormat();
        //logs = new log();
        crearModelo();
    }

    public void crearModelo() {
        modelVerTiqPrincipal = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
        tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
        tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
        formatoTabla();
    }

    public void formatoTabla() {
        int row = VerTiqPrincipal.tblVerTiqPrincipal.getRowCount();
        for (int i = 0; i < row; i++) {
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt((cu.dateNotTime(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 1).toString())), i, 1);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 7).toString())), i, 7);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 8).toString())), i, 8);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 9).toString())), i, 9);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 10).toString())), i, 10);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 11).toString())), i, 11);
        }
    }

    public void buscar() {
        //String select = "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo";
        String fechaI, fechaF;
        Date FechaInicial, FechaFinal;

        SimpleDateFormat formatoI = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat formatoF = new SimpleDateFormat("yyy-MM-dd 23:59:59");
        FechaInicial = VerTiqPrincipal.dcFechaInicial.getDate();
        FechaFinal = VerTiqPrincipal.dcFechaFinal.getDate();

        if (FechaInicial == null && FechaFinal == null) {
            fechaI = "";
            fechaF = "";
        } else {
            fechaI = formatoI.format(FechaInicial);
            fechaF = formatoF.format(FechaFinal);
        }

        String tiquete = VerTiqPrincipal.txtTiquete.getText();
        String agricultor = VerTiqPrincipal.txtAgricultor.getText();
        String idAgricultor = ext.getIdPersonalExterno(agricultor, "agricultor");

        modelVerTiqPrincipal = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true && VerTiqPrincipal.chFecha.isSelected()) {
            if (!tiquete.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                //tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.cedula like '%" + cedula + "%' and personalexterno.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE idtiquete='" + tiquete + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true) {
            if (!tiquete.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE idtiquete='" + tiquete + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chFecha.isSelected()) {
            if (!tiquete.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE idtiquete='" + tiquete + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chAgricultor.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE tiquete.idAgricultor='" + idAgricultor + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true) {
            if (!tiquete.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE idtiquete='" + tiquete + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chAgricultor.isSelected() == true) {
            if (!agricultor.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE tiquete.idAgricultor='" + idAgricultor + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo WHERE fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();*/
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
                String path = "src\\reportes\\BasculaTiquePrincipal.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(bascula.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

}
