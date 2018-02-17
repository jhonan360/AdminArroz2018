/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Liquidacion;

import Logica.Gerencia.*;
import Interfaces.GerenteApruebaLiquidaciones;
import Interfaces.LiquidacionesAprobadas;
import Negocio.Conexion;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.login;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Lizeth
 */
public class liquidacionesAprobadas {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    //public static GerenteApruebaLiquidaciones GApruebaL;
    public static LiquidacionesAprobadas LiqAprobadas;
    public static extras ext;
    public tablas tbl;
    public static String placa, color, modelo, marca, idVehiculo, idLiquidacion;
    public static DefaultTableModel modelLiquidaciones, modelDetalle;
    public static String columLiquidaciones[] = new String[]{"N°", "Fecha", "Cedula", "Agricultor", "Neto Pagar"};
    public static String columDetalle[] = new String[]{"N° Tiquete", "Fecha Tiquete", "Kg Brutos", "Humedad O", "Impureza O", "% Castigo H", "% Castigo I", "Peso Compra", "Valor Kg", "Valor Total"};
    public static login login;
    long kiloNetos, subTotal, valorImpuesto, valorFomArrocero, desAnticipo, netoPagar, humedadObtenida, impurezaObtenida, castigoHumedad, castigoImpureza, pesoCompra, valorTotal, netoAPagar, totalKilosCompra, vlrFomentoArrocero;
    public static String idLiquidaciones, fecha, humedadIdeal, impurezaIdeal, FomArroz, impuesto, tipoImpuesto;
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    DecimalFormat ft;

    public liquidacionesAprobadas() {
        ext = new extras();
        //logs = new log();
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
        tbl = new tablas();
        tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
        cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
        
    }
    
    public void cambiarFormatoLiquidacion(String sentencia) {
        alinearCamposTablaLiquidaciones();
        alinearHeaderTablas();
        int row = LiqAprobadas.tblLiquidaciones.getRowCount();
        int j = 0;
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < row; i++) {
                //String idLiquidacion = GApruebaL.tblAprobarLiq.getValueAt(i, 0).toString();
                rs = st.executeQuery(sentencia);
                while (rs.next()) {
                    String Vector[] = new String[columLiquidaciones.length];
                    Vector[0] = rs.getString(1);
                    Date Fecha = formato.parse(rs.getString(2));
                    String fecha = formato.format(Fecha);
                    Vector[1] = fecha;
                    Vector[2] = rs.getString(3);
                    Vector[3] = rs.getString(4);
                    Vector[4] = ft.format(Double.parseDouble(rs.getString(5)));
                    for (j = 0; j < columLiquidaciones.length; j++) {
                        LiqAprobadas.tblLiquidaciones.setValueAt(Vector[j], i, j);
                    }
                    i++;
                }
            }
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
    }
    
    

    public void tablaCamposLiquidacion() {
        int rec = LiqAprobadas.tblLiquidaciones.getSelectedRow();
        idLiquidacion = LiqAprobadas.tblLiquidaciones.getValueAt(rec, 0).toString();
        LiqAprobadas.lblNumLiquidacion.setText(idLiquidacion);
        System.out.print(idLiquidacion);
        LiqAprobadas.lblNomAgricultor.setText(LiqAprobadas.tblLiquidaciones.getValueAt(rec, 3).toString());

        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT cedula, CONCAT( telefono, ' ', telefono2, ' ', telefono3 ), direccion, liquidaciones.fecha, liquidaciones.humedadIdeal, liquidaciones.impurezaIdeal, liquidaciones.fomArrocero, liquidaciones.impuesto, liquidaciones.porcenImpuesto, liquidaciones.kilosNeto, liquidaciones.kilosCompra, liquidaciones.subtotal, liquidaciones.valorFomArrocero, liquidaciones.valorImpuesto, liquidaciones.descuentoAnticipo, liquidaciones.netoPagar FROM personalexterno, tiquete, detalleliquidacion, liquidaciones WHERE liquidaciones.idLiquidaciones ='" + idLiquidacion + "' AND detalleliquidacion.idliquidaciones=liquidaciones.idLiquidaciones AND detalleliquidacion.idTiquete = tiquete.idTiquete AND tiquete.idAgricultor = personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");

            while (rs.next()) {
                LiqAprobadas.lblCedAgricultor.setText(rs.getString(1));
                LiqAprobadas.lblCelAgricultor.setText(rs.getString(2));
                LiqAprobadas.lblDirAgricultor.setText(rs.getString(3));
                Date Fecha = formato.parse(rs.getString(4));
                String fecha = formato.format(Fecha);
                LiqAprobadas.lblFechaLiquidacion.setText(fecha);
                LiqAprobadas.lblHumedadIdeal.setText(rs.getString(5));
                LiqAprobadas.lblImpurezaIdeal.setText(rs.getString(6));
                LiqAprobadas.lblPorcentajeFomento.setText(rs.getString(7));
                LiqAprobadas.lblTipoImpuesto.setText(rs.getString(8));
                LiqAprobadas.lblPorcentajeImpuesto.setText(rs.getString(9));
                LiqAprobadas.lblKilosNetos.setText(ft.format(Double.parseDouble(rs.getString(10))));
                LiqAprobadas.lblKilosCompra.setText(ft.format(Double.parseDouble(rs.getString(11))));
                LiqAprobadas.lblSubtotal.setText(ft.format(Double.parseDouble(rs.getString(12))));
                LiqAprobadas.lblVlrFomento.setText(ft.format(Double.parseDouble(rs.getString(13))));
                LiqAprobadas.lblVlrImpuesto.setText(ft.format(Double.parseDouble(rs.getString(14))));
                LiqAprobadas.lblDescAnticipo.setText(ft.format(Double.parseDouble(rs.getString(15))));
                LiqAprobadas.lblNetoPagar.setText(ft.format(Double.parseDouble(rs.getString(16))));
            }

            tbl = new tablas();
            tbl.llenarTabla(LiqAprobadas.tblDetalleL, modelDetalle, columDetalle.length, "SELECT detalleliquidacion.idTiquete,liquidaciones.fecha,tiquete.kilosNetos,detalleliquidacion.humedad,detalleliquidacion.impureza,detalleliquidacion.castigoHumedad, detalleliquidacion.castigoImpureza,detalleliquidacion.pesoCompra,detalleliquidacion.valorKilo,detalleliquidacion.valorTotal FROM detalleliquidacion,liquidaciones,tiquete WHERE liquidaciones.idLiquidaciones='" + idLiquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete");
            cambiarFormatoDetalle(idLiquidacion);
            alinearCamposTablaDetalle();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarFormatoDetalle(String idLiquidacion) {
        int row = LiqAprobadas.tblDetalleL.getRowCount();
        int j = 0;
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < row; i++) {
                //String idLiquidacion = GApruebaL.tblAprobarLiq.getValueAt(i, 0).toString();
                rs = st.executeQuery("SELECT detalleliquidacion.idTiquete,liquidaciones.fecha,tiquete.kilosNetos,detalleliquidacion.humedad,detalleliquidacion.impureza,detalleliquidacion.castigoHumedad, detalleliquidacion.castigoImpureza,detalleliquidacion.pesoCompra,detalleliquidacion.valorKilo,detalleliquidacion.valorTotal FROM detalleliquidacion,liquidaciones,tiquete WHERE liquidaciones.idLiquidaciones='" + idLiquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete");
                while (rs.next()) {
                    String Vector[] = new String[columDetalle.length];
                    Vector[0] = rs.getString(1);
                    Date Fecha = formato.parse(rs.getString(2));
                    String fecha = formato.format(Fecha);
                    Vector[1] = fecha;
                    Vector[2] = ft.format(Double.parseDouble(rs.getString(3)));
                    Vector[3] = rs.getString(4);
                    Vector[4] = rs.getString(5);
                    Vector[5] = ft.format(Double.parseDouble(rs.getString(6)));
                    Vector[6] = ft.format(Double.parseDouble(rs.getString(7)));
                    Vector[7] = ft.format(Double.parseDouble(rs.getString(8)));
                    Vector[8] = ft.format(Double.parseDouble(rs.getString(9)));
                    Vector[9] = ft.format(Double.parseDouble(rs.getString(10)));
                    for (j = 0; j < columDetalle.length; j++) {
                        LiqAprobadas.tblDetalleL.setValueAt(Vector[j], i, j);
                    }
                    i++;
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alinearHeaderTablas() {

        TableColumnModel columnModel = LiqAprobadas.tblLiquidaciones.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);

        TableCellRenderer rendererFromHeaderLiqui = LiqAprobadas.tblLiquidaciones.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeaderLiqui;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        TableCellRenderer rendererFromHeaderDetalle = LiqAprobadas.tblDetalleL.getTableHeader().getDefaultRenderer();
        JLabel headerLabel2 = (JLabel) rendererFromHeaderDetalle;
        headerLabel2.setHorizontalAlignment(JLabel.CENTER);
    }

    public void alinearCamposTablaLiquidaciones() {
        DefaultTableCellRenderer Right = new DefaultTableCellRenderer();
        Right.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer Center = new DefaultTableCellRenderer();
        Center.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer Left = new DefaultTableCellRenderer();
        Left.setHorizontalAlignment(SwingConstants.LEFT);
        LiqAprobadas.tblLiquidaciones.getColumnModel().getColumn(0).setCellRenderer(Center);
        LiqAprobadas.tblLiquidaciones.getColumnModel().getColumn(1).setCellRenderer(Center);
        LiqAprobadas.tblLiquidaciones.getColumnModel().getColumn(2).setCellRenderer(Right);
        LiqAprobadas.tblLiquidaciones.getColumnModel().getColumn(3).setCellRenderer(Left);
        LiqAprobadas.tblLiquidaciones.getColumnModel().getColumn(4).setCellRenderer(Right);
    }

    public void alinearCamposTablaDetalle() {
        DefaultTableCellRenderer Right = new DefaultTableCellRenderer();
        Right.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer Center = new DefaultTableCellRenderer();
        Center.setHorizontalAlignment(SwingConstants.CENTER);

        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(0).setCellRenderer(Center);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(1).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(2).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(3).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(4).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(5).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(6).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(7).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(8).setCellRenderer(Right);
        LiqAprobadas.tblDetalleL.getColumnModel().getColumn(9).setCellRenderer(Right);

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
        //String select = "SELECT idTiquete,fecha,CONCAT(ag.nombres,' ',ag.apellidos) AS agricultor,CONCAT(tipodearroz.nombre,'-',variedad.nombre),lote.nombre,CONCAT(co.nombres,' ',co.apellidos) AS conductor,vehiculo.placa,kilosBrutos,destare,kilosNetos,humedadUno,impurezaUno,observacion FROM personalexterno ag, personalexterno co,liquidacion,tipodearroz,variedad,lote,vehiculo";
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
        String agricultor = LiqAprobadas.txtAgricultor.getText();
        String idAgricultor = ext.getIdPersonalExterno(agricultor, "agricultor");

        modelLiquidaciones = new DefaultTableModel(null, columLiquidaciones) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true && LiqAprobadas.chFecha.isSelected()) {
            if (!liquidacion.equals("") && !agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                //tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM personalexterno,municipios WHERE personalexterno.cedula like '%" + cedula + "%' and personalexterno.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio and personalexterno.tipo='conductor' ");
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chAgricultor.isSelected() == true) {
            if (!liquidacion.equals("") && !agricultor.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true && LiqAprobadas.chFecha.isSelected()) {
            if (!liquidacion.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chAgricultor.isSelected() == true && LiqAprobadas.chFecha.isSelected() == true) {
            if (!agricultor.equals("") && !fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' AND tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.fecha >= '" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chLiquidacion.isSelected() == true) {
            if (!liquidacion.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and liquidaciones.idLiquidaciones='" + liquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chAgricultor.isSelected() == true) {
            if (!agricultor.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado' and tiquete.idAgricultor='" + idAgricultor + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (LiqAprobadas.chFecha.isSelected() == true) {
            if (!fechaI.equals("") && !fechaF.equals("")) {
                tbl.llenarTabla(LiqAprobadas.tblLiquidaciones, modelLiquidaciones, columLiquidaciones.length, "SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado'AND liquidaciones.fecha >='" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                cambiarFormatoLiquidacion("SELECT liquidaciones.idLiquidaciones,liquidaciones.fecha,personalexterno.cedula,CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),netoPagar FROM liquidaciones,personalexterno,detalleliquidacion,tiquete WHERE liquidaciones.estado='aprobado'AND liquidaciones.fecha >='" + fechaI + "' AND liquidaciones.fecha<='" + fechaF + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();*/
    }

}
