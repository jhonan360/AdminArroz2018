/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Inventario;

import Negocio.Conexion;
import Interfaces.VerProcedimientoInventario;
import Logica.Extras.currencyFormat;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.login;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lizeth
 */
public class verProcedimientoInventario {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static VerProcedimientoInventario VerProcedI;
    public static extras ext;
    public tablas tbl;
    public static String idProcedimiento, kilos, humedad;
    public static DefaultTableModel modelVerProcedI, modelEtapas;
    String columnProced[] = new String[]{"N° Procedimiento", "Bateria", "Secadora", "N Silo", "Kg", "Fecha Creación", "Hora Creación","Almacenamiento","Observacion"};
    String alineaHeaderProced[] = new String[]{"40", "30", "30", "30", "80", "50", "40","30","100"};
    String alineaCampoProced[] = new String[]{"center", "center", "center", "center", "right", "center", "center","left","left"};
    String columnEtapas[] = new String[]{"N° Etapa", "Etapa", "Fecha Muestra", "Hora Muestra", "Humedad"};
    String alineaHeaderEtapas[] = new String[]{"default", "default", "default", "default", "default"};
    String alineaCampoEtapas[] = new String[]{"center", "left", "center", "center", "right"};
    public static login login;
    currencyFormat cu;

    public verProcedimientoInventario() {
        tbl = new tablas();
        cu = new currencyFormat();
        ext = new extras();
        crearModeloProcedimiento();
    }

    public void crearModeloProcedimiento() {
        modelVerProcedI = new DefaultTableModel(null, columnProced) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,tiqueteensilos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora,tiqueteensilos WHERE procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSilos=tiqueteensilos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria GROUP by procedimiento.idProcedimiento ORDER BY procedimiento.idProcedimiento ASC");
        tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
        tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
        tbl.alinearHeaderTable(VerProcedI.tblEtapas, alineaHeaderEtapas);
        formatoTablaProced();
    }

    public void formatoTablaProced() {
        int row = VerProcedI.tblVerProcedI.getRowCount();
        for (int i = 0; i < row; i++) {
            kilos = VerProcedI.tblVerProcedI.getValueAt(i, 4).toString();
            kilos = cu.thousandsFormat(Double.parseDouble(kilos));
            VerProcedI.tblVerProcedI.setValueAt(kilos, i, 4);
        }
    }

    public void crearModeloEtapas() {
        modelEtapas = new DefaultTableModel(null, columnEtapas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        VerProcedI.tblEtapas.setModel(modelEtapas);
        tbl.alinearCamposTable(VerProcedI.tblEtapas, alineaCampoEtapas);
    }
    
    public void tablaCamposEtapa() {
        int rec = VerProcedI.tblVerProcedI.getSelectedRow();
        idProcedimiento = VerProcedI.tblVerProcedI.getValueAt(rec, 0).toString();
        try {
            Con = new Conexion();
            tbl = new tablas();
            tbl.llenarTabla(VerProcedI.tblEtapas, modelEtapas, columnEtapas.length, "SELECT idHistorialEtapa,etapa,fecha,hora,humedad from etapa where etapa.idProcedimiento='" + idProcedimiento + "'");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscar() {
        String fechaI, fechaF;
        Date FechaInicial, FechaFinal;

        SimpleDateFormat formatoI = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat formatoF = new SimpleDateFormat("yyy-MM-dd 23:59:59");
        FechaInicial = VerProcedI.dcFechaInicial.getDate();
        FechaFinal = VerProcedI.dcFechaFinal.getDate();

        if (FechaInicial == null && FechaFinal == null) {
            fechaI = "";
            fechaF = "";
        } else {
            fechaI = formatoI.format(FechaInicial);
            fechaF = formatoF.format(FechaFinal);
        }

        String procedimiento = VerProcedI.txtProcedimiento.getText();
        String bateria = VerProcedI.cmbBateria.getSelectedItem().toString();
        String silo = VerProcedI.cmbSilo.getSelectedItem().toString();

        modelVerProcedI = new DefaultTableModel(null, columnProced) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (VerProcedI.chProcedimiento.isSelected() == true && VerProcedI.chBateria.isSelected() == true && VerProcedI.chSilo.isSelected() == true && VerProcedI.chFecha.isSelected() == true) {
            if (!procedimiento.equals("") && !bateria.equals("") && !silo.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE idProcedimiento= '" + procedimiento + "' AND bateria.nombre= '" + bateria + "' AND silos.numero = '" + silo + "' AND fecha >= '" + fechaI + "' AND fecha<= '" + fechaF + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chProcedimiento.isSelected() == true && VerProcedI.chBateria.isSelected() == true && VerProcedI.chSilo.isSelected() == true) {
            if (!procedimiento.equals("") && !bateria.equals("") && !silo.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE idProcedimiento= '" + procedimiento + "' AND bateria.nombre= '" + bateria + "' AND silos.numero = '" + silo + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chProcedimiento.isSelected() == true && VerProcedI.chBateria.isSelected() == true && VerProcedI.chFecha.isSelected() == true) {
            if (!procedimiento.equals("") && !bateria.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE idProcedimiento= '" + procedimiento + "' AND bateria.nombre= '" + bateria + "' AND fecha >= '" + fechaI + "' AND fecha<= '" + fechaF + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chBateria.isSelected() == true && VerProcedI.chSilo.isSelected() == true && VerProcedI.chFecha.isSelected() == true) {
            if (!bateria.equals("") && !silo.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE bateria.nombre= '" + bateria + "' AND silos.numero = '" + silo + "' AND fecha >= '" + fechaI + "' AND fecha<= '" + fechaF + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chProcedimiento.isSelected() == true && VerProcedI.chBateria.isSelected() == true) {
            if (!procedimiento.equals("") && !bateria.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE idProcedimiento= '" + procedimiento + "' AND bateria.nombre= '" + bateria + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chProcedimiento.isSelected() == true && VerProcedI.chFecha.isSelected() == true) {
            if (!procedimiento.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE idProcedimiento= '" + procedimiento + "' AND fecha >= '" + fechaI + "' AND fecha<= '" + fechaF + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chBateria.isSelected() == true && VerProcedI.chSilo.isSelected() == true) {
            if (!bateria.equals("") && !silo.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE bateria.nombre= '" + bateria + "' AND silos.numero = '" + silo + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chBateria.isSelected() == true && VerProcedI.chFecha.isSelected() == true) {
            if (!bateria.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE bateria.nombre= '" + bateria + "' AND fecha >= '" + fechaI + "' AND fecha<= '" + fechaF + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chProcedimiento.isSelected() == true) {
            if (!procedimiento.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE idProcedimiento= '" + procedimiento + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chBateria.isSelected() == true) {
            if (!bateria.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE bateria.nombre= '" + bateria + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerProcedI.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerProcedI.tblVerProcedI, modelVerProcedI, columnProced.length, "SELECT idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,silos.kilos,fecha,hora,tipoAlmacenamiento,observacion FROM procedimiento,silos,bateria,secadora WHERE fecha >= '" + fechaI + "' AND fecha<= '" + fechaF + "' AND procedimiento.estado='finalizado' AND procedimiento.idSilos=silos.idSilos AND silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ORDER BY procedimiento.idProcedimiento ASC");
                tbl.alinearHeaderTable(VerProcedI.tblVerProcedI, alineaHeaderProced);
                tbl.alinearCamposTable(VerProcedI.tblVerProcedI, alineaCampoProced);
                formatoTablaProced();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
    }
}
