/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Liquidacion;

import Logica.Gerencia.*;
import Interfaces.GerenteApruebaLiquidaciones;
import Interfaces.LiquidacionesAprobadas;
import Logica.Extras.currencyFormat;
import Negocio.Conexion;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
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
public class liquidacionesAprobadas {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static currencyFormat cu;
    public static LiquidacionesAprobadas LiqAprobadas;
    public static extras ext;
    public tablas tbl;
    public static String placa, color, modelo, marca, idVehiculo, idLiquidacion;
    public static DefaultTableModel modelLiquidaciones, modelDetalle;
    public static String columLiquidaciones[] = new String[]{"N°", "Fecha", "Cedula", "Agricultor", "Neto Pagar"};
    public static String alinearHeaderLiq[] = new String[]{"10", "30", "40", "90", "40"};
    public static String alinearCamposLiq[] = new String[]{"center", "center", "right", "left", "right"};
    public static String columDetalle[] = new String[]{"N° Tiq", "Fecha Tiq", "Kg Brutos", "Humedad O", "Impureza O", "% Castigo H", "% Castigo I", "Peso Compra", "Valor Kg", "Valor Total"};
    public static String alinearHeaderDetalle[] = new String[]{"40", "default", "default", "default", "default", "default", "default", "default", "80", "default"};
    public static String alinearCamposDetalle[] = new String[]{"center", "center", "right", "right", "right", "right", "right", "right", "right", "right"};
    public static login login;
    long kiloNetos, subTotal, valorImpuesto, valorFomArrocero, desAnticipo, netoPagar, humedadObtenida, impurezaObtenida, castigoHumedad, castigoImpureza, pesoCompra, valorTotal, netoAPagar, totalKilosCompra, vlrFomentoArrocero;
    public static String idLiquidaciones, fecha, humedadIdeal, impurezaIdeal, FomArroz, impuesto, tipoImpuesto;
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    DecimalFormat ft;

    public liquidacionesAprobadas() {
        cu = new currencyFormat();
        ext = new extras();
        tbl = new tablas();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        ft = new DecimalFormat("###,###.##", simbolo);
        crearModeloLiquidacion();
    }

    public void crearModeloLiquidacion() {
        modelLiquidaciones = new DefaultTableModel(null, columLiquidaciones) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
        tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
        tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
        tbl.alinearHeaderTable(LiqAprobadas.tblDetalleL, alinearHeaderDetalle);
        formatoTblLiquidacion();
    }

    public void formatoTblLiquidacion() {
        int row = LiqAprobadas.tblLiquidaciones.getRowCount();
        for (int i = 0; i < row; i++) {
            String fecha = cu.dateNotTime(LiqAprobadas.tblLiquidaciones.getValueAt(i, 1).toString());
            LiqAprobadas.tblLiquidaciones.setValueAt(fecha, i, 1);
            String netoPagar = LiqAprobadas.tblLiquidaciones.getValueAt(i, 4).toString();
            netoPagar = cu.moneyFormat(Double.parseDouble(netoPagar));
            LiqAprobadas.tblLiquidaciones.setValueAt(netoPagar, i, 4);
        }
    }

    public void tablaCamposLiquidacion() {
        int rec = LiqAprobadas.tblLiquidaciones.getSelectedRow();
        idLiquidacion = LiqAprobadas.tblLiquidaciones.getValueAt(rec, 0).toString();
        LiqAprobadas.lblNumLiquidacion.setText(idLiquidacion);
        LiqAprobadas.lblNomAgricultor.setText(LiqAprobadas.tblLiquidaciones.getValueAt(rec, 3).toString());

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT cedula, CONCAT( telefono, ' ', telefono2, ' ', telefono3 ), direccion, liquidaciones.fecha, liquidaciones.humedadIdeal, liquidaciones.impurezaIdeal, liquidaciones.fomArrocero, liquidaciones.impuesto, liquidaciones.porcenImpuesto, liquidaciones.kilosNeto, liquidaciones.kilosCompra, liquidaciones.subtotal, liquidaciones.valorFomArrocero, liquidaciones.valorImpuesto, liquidaciones.descuentoAnticipo, liquidaciones.netoPagar FROM personalexterno, tiquete, detalleliquidacion, liquidaciones WHERE liquidaciones.idLiquidaciones ='" + idLiquidacion + "' AND detalleliquidacion.idliquidaciones=liquidaciones.idLiquidaciones AND detalleliquidacion.idTiquete = tiquete.idTiquete AND tiquete.idAgricultor = personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");

            while (rs.next()) {
                LiqAprobadas.lblCedAgricultor.setText(rs.getString(1));
                LiqAprobadas.lblCelAgricultor.setText(rs.getString(2));
                LiqAprobadas.lblDirAgricultor.setText(rs.getString(3));
                LiqAprobadas.lblFechaLiquidacion.setText(cu.dateNotTime(rs.getString(4)));
                LiqAprobadas.lblHumedadIdeal.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(5))));
                LiqAprobadas.lblImpurezaIdeal.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(6))));
                LiqAprobadas.lblPorcentajeFomento.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(7))));
                LiqAprobadas.lblTipoImpuesto.setText(rs.getString(8));
                LiqAprobadas.lblPorcentajeImpuesto.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(9))));
                LiqAprobadas.lblKilosNetos.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(10))));
                Double klNetos = Double.parseDouble(rs.getString(10));
                LiqAprobadas.lblKilosCompra.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(11))));
                Double klCompra = Double.parseDouble(rs.getString(11));
                LiqAprobadas.lblSubtotal.setText(cu.moneyFormat(Double.parseDouble(rs.getString(12))));
                LiqAprobadas.lblVlrFomento.setText(cu.moneyFormat(Double.parseDouble(rs.getString(13))));
                LiqAprobadas.lblVlrImpuesto.setText(cu.moneyFormat(Double.parseDouble(rs.getString(14))));
                LiqAprobadas.lblDescAnticipo.setText(cu.moneyFormat(Double.parseDouble(rs.getString(15))));
                LiqAprobadas.lblNetoPagar.setText(cu.moneyFormat(Double.parseDouble(rs.getString(16))));

                Double descuento = klNetos - klCompra;
                LiqAprobadas.lblKilosDescuent.setText(String.valueOf(cu.thousandsFormat(descuento)));

            }
            tbl.llenarTabla(LiqAprobadas.tblDetalleL, modelDetalle, columDetalle.length, "SELECT detalleliquidacion.idTiquete,liquidaciones.fecha,tiquete.kilosNetos,tiquete.humedadUno,tiquete.impurezaUno,detalleliquidacion.castigoHumedad, detalleliquidacion.castigoImpureza,detalleliquidacion.pesoCompra,detalleliquidacion.valorKilo,detalleliquidacion.valorTotal FROM detalleliquidacion,liquidaciones,tiquete WHERE liquidaciones.idLiquidaciones='" + idLiquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete");
            tbl.alinearCamposTable(LiqAprobadas.tblDetalleL, alinearCamposDetalle);
            formatoTblDetalle();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearModeloDetalle() {
        modelDetalle = new DefaultTableModel(null, columDetalle) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        LiqAprobadas.tblDetalleL.setModel(modelDetalle);
        tbl.alinearHeaderTable(LiqAprobadas.tblDetalleL, alinearHeaderDetalle);
    }

    public void formatoTblDetalle() {
        int row = LiqAprobadas.tblDetalleL.getRowCount();
        for (int i = 0; i < row; i++) {
            String fecha = cu.dateNotTime(LiqAprobadas.tblDetalleL.getValueAt(i, 1).toString());
            LiqAprobadas.tblDetalleL.setValueAt(fecha, i, 1);
            String kgBrutos = cu.thousandsFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 2).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(kgBrutos, i, 2);
            String humedadO = cu.thousandsFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 3).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(humedadO, i, 3);
            String impurezaO = cu.thousandsFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 4).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(impurezaO, i, 4);
            String castigoH = cu.thousandsFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 5).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(castigoH, i, 5);
            String castigoI = cu.thousandsFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 6).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(castigoI, i, 6);
            String pesoCompra = cu.thousandsFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 7).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(pesoCompra, i, 7);
            String valorKg = cu.moneyFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 8).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(valorKg, i, 8);
            String valorTotal = cu.moneyFormat(Double.parseDouble(LiqAprobadas.tblDetalleL.getValueAt(i, 9).toString()));
            LiqAprobadas.tblDetalleL.setValueAt(valorTotal, i, 9);
        }
    }

    public void limpiarCampos() {
        LiqAprobadas.lblNomAgricultor.setText("");
        LiqAprobadas.lblNumLiquidacion.setText("");
        LiqAprobadas.lblCedAgricultor.setText("");
        LiqAprobadas.lblCelAgricultor.setText("");
        LiqAprobadas.lblDirAgricultor.setText("");
        LiqAprobadas.lblFechaLiquidacion.setText("");
        LiqAprobadas.lblHumedadIdeal.setText("");
        LiqAprobadas.lblImpurezaIdeal.setText("");
        LiqAprobadas.lblPorcentajeFomento.setText("");
        LiqAprobadas.lblTipoImpuesto.setText("");
        LiqAprobadas.lblPorcentajeImpuesto.setText("");
        LiqAprobadas.lblKilosNetos.setText("");
        LiqAprobadas.lblKilosCompra.setText("");
        LiqAprobadas.lblSubtotal.setText("");
        LiqAprobadas.lblVlrFomento.setText("");
        LiqAprobadas.lblVlrImpuesto.setText("");
        LiqAprobadas.lblDescAnticipo.setText("");
        LiqAprobadas.lblNetoPagar.setText("");
    }

    public void buscar() {
        String fechaI, fechaF;
        Date FechaInicial, FechaFinal;

        SimpleDateFormat formatoI = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat formatoF = new SimpleDateFormat("yyy-MM-dd 23:59:59");
        FechaInicial = LiqAprobadas.dcFechaInicial.getDate();
        FechaFinal = LiqAprobadas.dcFechaFinal.getDate();

        if (FechaInicial == null && FechaFinal == null) {
            fechaI = "";
            fechaF = "";
        } else {
            fechaI = formatoI.format(FechaInicial);
            fechaF = formatoF.format(FechaFinal);
        }

        String liquidacion = LiqAprobadas.txtLiquidacion.getText();
        String tiquete = LiqAprobadas.txtTiquete.getText();
        String agricultor = LiqAprobadas.txtAgricultor.getText();
        String idAgricultor = ext.getIdPersonalExterno(agricultor, "agricultor");

        modelLiquidaciones = new DefaultTableModel(null, columLiquidaciones) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chTiquete.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!liquidacion.equals("") && !tiquete.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chTiquete.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true) {
            if (!liquidacion.equals("") && !tiquete.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chTiquete.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!liquidacion.equals("") && !tiquete.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!liquidacion.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chTiquete.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!tiquete.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chTiquete.isSelected() == true) {
            if (!liquidacion.equals("") && !tiquete.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true) {
            if (!liquidacion.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!liquidacion.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chTiquete.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true) {
            if (!tiquete.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chTiquete.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!tiquete.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chAgricultor.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true) {
            if (!liquidacion.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chTiquete.isSelected() == true) {
            if (!tiquete.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and detalleliquidacion.idTiquete='" + tiquete + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chAgricultor.isSelected() == true) {
            if (!agricultor.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado'AND liquidaciones.fecha >='" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                tbl.alinearHeaderTable(LiqAprobadas.tblLiquidaciones, alinearHeaderLiq);
                tbl.alinearCamposTable(LiqAprobadas.tblLiquidaciones, alinearCamposLiq);
                formatoTblLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
    }

    public void reporteGerenteLiquidacion() {
        int row = LiqAprobadas.tblLiquidaciones.getSelectedRow();

        if (row != -1) {
            String idLiquidacion = LiqAprobadas.tblLiquidaciones.getValueAt(row, 0).toString();
            String nomContador = "";
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_liquidacion", idLiquidacion);//colocar parametros

            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                rs = st.executeQuery("SELECT CONCAT(empleado.nombres,(' '),empleado.apellidos) FROM liquidaciones,empleado,usuario WHERE liquidaciones.user=usuario.user AND usuario.user=empleado.user AND liquidaciones.idLiquidaciones='" + idLiquidacion + "'");

                while (rs.next()) {
                    nomContador = (rs.getString(1));
                    System.out.println(nomContador);
                }
                Map parametro2 = new HashMap();//mapeo de parametros
                parametro2.put("nom_contador", new String("liz"));//colocar parametros
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                String path = "src\\reportes\\GerenteLiquidacionesAprobadas.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperPrint jprint2 = JasperFillManager.fillReport(reporte, parametro2, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
                Con.Desconectar();
            } catch (JRException ex) {
                Logger.getLogger(liquidacionesAprobadas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(liquidacionesAprobadas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }
}
