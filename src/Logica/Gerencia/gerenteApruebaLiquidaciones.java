/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Gerencia;

import Interfaces.GerenteApruebaLiquidaciones;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lizeth
 */
public class gerenteApruebaLiquidaciones {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static GerenteApruebaLiquidaciones GApruebaL;
    public static extras ext;
    public tablas tbl;
    public static String placa, color, modelo, marca, idVehiculo, idLiquidacion;
    public static DefaultTableModel modelPendientes, modelTiquetes;
    public static String columPendientes[] = new String[]{"N° Liquidación", "Agricultor", "Valor", "Total"};
    public static String columTiquetes[] = new String[]{"N° Tiquete", "Fecha Tiquete", "Kg Brutos", "Humedad O", "Impureza O", "% Castigo H", "% Castigo I", "Peso Compra", "Valor Kg", "Valor Total"};
    public static login login;

    public gerenteApruebaLiquidaciones() {
        ext = new extras();
        //logs = new log();
        crearModeloLiquidacion();
    }

    public void crearModeloLiquidacion() {
        modelPendientes = new DefaultTableModel(null, columPendientes) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(GApruebaL.tblAprobarLiq, modelPendientes, columPendientes.length, "SELECT liquidaciones.idLiquidaciones, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),subTotal,netoPagar FROM liquidaciones,detalleliquidacion,tiquete,personalexterno WHERE liquidaciones.estado='en proceso' and liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
    }

    public void aprobarLiquidacion(String idLiquidacion) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE liquidaciones SET estado='aprobado' WHERE idLiquidaciones='" + idLiquidacion + "'");
            JOptionPane.showMessageDialog(null, "Liquidación Aprobada");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearModeloDetalle() {
        modelTiquetes = new DefaultTableModel(null, columTiquetes) {
            public boolean isCellEditable(int fila, int columna) {
                return true;
            }
        };
        GApruebaL.tblTiquetesLiq.setModel(modelTiquetes);
    }

    public void tablaCamposDetalle() {
        int rec = GApruebaL.tblAprobarLiq.getSelectedRow();
        idLiquidacion = GApruebaL.tblAprobarLiq.getValueAt(rec, 0).toString();
        GApruebaL.lblNumLiquidacion.setText(idLiquidacion);
        System.out.print(idLiquidacion);
        GApruebaL.lblNomAgricultor.setText(GApruebaL.tblAprobarLiq.getValueAt(rec, 1).toString());

        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT cedula,CONCAT(telefono,'   ',telefono2,'   ',telefono3),direccion,liquidaciones.fecha,liquidaciones.humedadIdeal,liquidaciones.impurezaIdeal,liquidaciones.fomArrocero,liquidaciones.impuesto,liquidaciones.porcenImpuesto,liquidaciones.kilosNeto,liquidaciones.kilosCompra,liquidaciones.subtotal,liquidaciones.valorFomArrocero,liquidaciones.valorImpuesto,liquidaciones.descuentoAnticipo,liquidaciones.netoPagar FROM personalexterno,tiquete,detalleliquidacion,liquidaciones WHERE liquidaciones.idLiquidaciones='" + idLiquidacion + "' AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");

            while (rs.next()) {
                GApruebaL.lblCedAgricultor.setText(rs.getString(1));
                GApruebaL.lblCelAgricultor.setText(rs.getString(2));
                GApruebaL.lblDirAgricultor.setText(rs.getString(3));
                Date Fecha = formato.parse(rs.getString(4));
                String fecha = formato.format(Fecha);
                GApruebaL.lblFechaLiquidacion.setText(fecha);
                GApruebaL.lblHumedadIdeal.setText(rs.getString(5));
                GApruebaL.lblImpurezaIdeal.setText(rs.getString(6));
                GApruebaL.lblPorcentajeFomento.setText(rs.getString(7));
                GApruebaL.lblTipoImpuesto.setText(rs.getString(8));
                GApruebaL.lblPorcentajeImpuesto.setText(rs.getString(9));
                GApruebaL.lblKilosNetos.setText(rs.getString(10));
                GApruebaL.lblKilosCompra.setText(rs.getString(11));
                GApruebaL.lblSubtotal.setText(rs.getString(12));
                GApruebaL.lblValorFomento.setText(rs.getString(13));
                GApruebaL.lblValorImpuesto.setText(rs.getString(14));
                GApruebaL.lblDesAnticipo.setText(rs.getString(15));
                GApruebaL.lblNetoPagar.setText(rs.getString(16));
            }

            tbl = new tablas();
            tbl.llenarTabla(GApruebaL.tblTiquetesLiq, modelTiquetes, columTiquetes.length, "SELECT detalleliquidacion.idTiquete,liquidaciones.fecha,tiquete.kilosBrutos,detalleliquidacion.humedad,detalleliquidacion.impureza,detalleliquidacion.castigoHumedad, detalleliquidacion.castigoImpureza,detalleliquidacion.pesoCompra,detalleliquidacion.valorKilo,detalleliquidacion.valorTotal FROM detalleliquidacion,liquidaciones,tiquete WHERE liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete");

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiarCampos() {
        GApruebaL.lblNomAgricultor.setText("");
        GApruebaL.lblNumLiquidacion.setText("");
        GApruebaL.lblCedAgricultor.setText("");
        GApruebaL.lblCelAgricultor.setText("");
        GApruebaL.lblDirAgricultor.setText("");
        GApruebaL.lblFechaLiquidacion.setText("");
        GApruebaL.lblHumedadIdeal.setText("");
        GApruebaL.lblImpurezaIdeal.setText("");
        GApruebaL.lblPorcentajeFomento.setText("");
        GApruebaL.lblTipoImpuesto.setText("");
        GApruebaL.lblPorcentajeImpuesto.setText("");
        GApruebaL.lblKilosNetos.setText("");
        GApruebaL.lblKilosCompra.setText("");
        GApruebaL.lblSubtotal.setText("");
        GApruebaL.lblValorFomento.setText("");
        GApruebaL.lblValorImpuesto.setText("");
        GApruebaL.lblDesAnticipo.setText("");
        GApruebaL.lblNetoPagar.setText("");
    }

}
