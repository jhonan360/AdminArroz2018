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
    public static String columnas[] = new String[]{"N°", "Cuenta", "Fecha", "Agricultor", "Tipo Arroz", "Lote", "Conductor", "Vehiculo", "Kl Brutos", "Destare", "Kl Netos", "H", "I", "Observación"};
    public static String headerColumnas[] = new String[]{"20", "default", "65", "180", "default", "45", "180", "55", "55", "55", "55", "10", "10", "default"};
    public static String camposColumnas[] = new String[]{"center", "left", "center", "left", "left", "left", "left", "left", "right", "right", "right", "right", "right", "left"};
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
        tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
        tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
        tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
        formatoTabla();
    }

    public void formatoTabla() {
        int row = VerTiqPrincipal.tblVerTiqPrincipal.getRowCount();
        for (int i = 0; i < row; i++) {
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt((cu.dateNotTime(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 2).toString())), i, 2);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 8).toString())), i, 8);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 9).toString())), i, 9);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 10).toString())), i, 10);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 11).toString())), i, 11);
            VerTiqPrincipal.tblVerTiqPrincipal.setValueAt(cu.thousandsFormat(Double.parseDouble(VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(i, 12).toString())), i, 12);
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
        String idCuenta = VerTiqPrincipal.cmbCuenta.getSelectedItem().toString();

        modelVerTiqPrincipal = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chCuenta.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!tiquete.equals("") && !idCuenta.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND cuentas.nombre='" + idCuenta + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chCuenta.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true) {
            if (!tiquete.equals("") && !idCuenta.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND cuentas.nombre='" + idCuenta + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chCuenta.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!tiquete.equals("") && !idCuenta.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND cuentas.nombre='" + idCuenta + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!tiquete.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chCuenta.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!idCuenta.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE cuentas.nombre='" + idCuenta + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chCuenta.isSelected() == true) {
            if (!tiquete.equals("") && !idCuenta.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND cuentas.nombre='" + idCuenta + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true) {
            if (!tiquete.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!tiquete.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chCuenta.isSelected() == true && VerTiqPrincipal.chAgricultor.isSelected() == true) {
            if (!idCuenta.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE cuentas.nombre='" + idCuenta + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chCuenta.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!idCuenta.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE cuentas.nombre='" + idCuenta + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chAgricultor.isSelected() == true && VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE tiquete.idAgricultor='" + idAgricultor + "' AND fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chTiquete.isSelected() == true) {
            if (!tiquete.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE idTiquete='" + tiquete + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chCuenta.isSelected() == true) {
            if (!idCuenta.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE cuentas.nombre='" + idCuenta + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chAgricultor.isSelected() == true) {
            if (!agricultor.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE tiquete.idAgricultor='" + idAgricultor + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
                tbl.alinearHeaderTable(VerTiqPrincipal.tblVerTiqPrincipal, headerColumnas);
                tbl.alinearCamposTable(VerTiqPrincipal.tblVerTiqPrincipal, camposColumnas);
                formatoTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (VerTiqPrincipal.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(VerTiqPrincipal.tblVerTiqPrincipal, modelVerTiqPrincipal, columnas.length, "SELECT idTiquete,cuentas.nombre,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,tiquete,tipodearroz,variedad,lote,vehiculo,cuentas WHERE fecha >= '" + fechaI + "' AND fecha<='" + fechaF + "' AND tiquete.idAgricultor=ag.idPersonalExterno AND tiquete.idConductor=co.idPersonalExterno AND tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad AND tiquete.idLote=lote.idLote AND tiquete.idVehiculo=vehiculo.idVehiculo and tiquete.idCuenta=cuentas.idCuenta ORDER BY fecha DESC");
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
}
