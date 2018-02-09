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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    public static String columPendientes[] = new String[]{"N° Liquidación", "Agricultor", "Subtotal", "Neto Pagar"};
    public static String columTiquetes[] = new String[]{"N° Tiquete", "Fecha Tiquete", "Kg Brutos", "Humedad O", "Impureza O", "% Castigo H", "% Castigo I", "Peso Compra", "Valor Kg", "Valor Total"};
    public static login login;
    long kiloNetos, subTotal, valorImpuesto, valorFomArrocero, desAnticipo, netoPagar, humedadObtenida, impurezaObtenida, castigoHumedad, castigoImpureza, pesoCompra, valorTotal, netoAPagar, totalKilosCompra, vlrFomentoArrocero;
    public static String idLiquidaciones, fecha, humedadIdeal, impurezaIdeal, FomArroz, impuesto, tipoImpuesto;
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    DecimalFormat ft;

    public gerenteApruebaLiquidaciones() {
        ext = new extras();
        //logs = new log();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        ft = new DecimalFormat("###,###.##", simbolo);
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
        cambiarFormatoLiquidacion();
    }
    
    public void cambiarFormatoLiquidacion() {
        int row = GApruebaL.tblAprobarLiq.getRowCount();
        int j = 0;
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < row; i++) {
                //String idLiquidacion = GApruebaL.tblAprobarLiq.getValueAt(i, 0).toString();
                rs = st.executeQuery("SELECT liquidaciones.idLiquidaciones, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),subTotal,netoPagar FROM liquidaciones,detalleliquidacion,tiquete,personalexterno WHERE liquidaciones.estado='en proceso' and liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
                while (rs.next()) {
                    String Vector[] = new String[columPendientes.length];
                    Vector[0] = rs.getString(1);
                    Vector[1] = rs.getString(2);
                    Vector[2] = ft.format(Double.parseDouble(rs.getString(3)));
                    Vector[3] = ft.format(Double.parseDouble(rs.getString(4)));
                    for (j = 0; j < columPendientes.length; j++) {
                        GApruebaL.tblAprobarLiq.setValueAt(Vector[j], i, j);
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

        modelTiquetes = new DefaultTableModel(null, columTiquetes) {
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 3 || columna == 4) {
                    return true;
                }
                return false;
            }
        };
        GApruebaL.tblTiquetesLiq.setModel(modelTiquetes);
    }

    public void tablaCamposLiquidacion() {
        int rec = GApruebaL.tblAprobarLiq.getSelectedRow();
        idLiquidacion = GApruebaL.tblAprobarLiq.getValueAt(rec, 0).toString();
        GApruebaL.lblNumLiquidacion.setText(idLiquidacion);
        System.out.print(idLiquidacion);
        GApruebaL.lblNomAgricultor.setText(GApruebaL.tblAprobarLiq.getValueAt(rec, 1).toString());

        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT cedula, CONCAT( telefono, ' ', telefono2, ' ', telefono3 ), direccion, liquidaciones.fecha, liquidaciones.humedadIdeal, liquidaciones.impurezaIdeal, liquidaciones.fomArrocero, liquidaciones.impuesto, liquidaciones.porcenImpuesto, liquidaciones.kilosNeto, liquidaciones.kilosCompra, liquidaciones.subtotal, liquidaciones.valorFomArrocero, liquidaciones.valorImpuesto, liquidaciones.descuentoAnticipo, liquidaciones.netoPagar FROM personalexterno, tiquete, detalleliquidacion, liquidaciones WHERE liquidaciones.idLiquidaciones ='" + idLiquidacion + "' AND detalleliquidacion.idliquidaciones=liquidaciones.idLiquidaciones AND detalleliquidacion.idTiquete = tiquete.idTiquete AND tiquete.idAgricultor = personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");

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
                GApruebaL.lblKilosNetos.setText(ft.format(Double.parseDouble(rs.getString(10))));
                GApruebaL.lblKilosCompra.setText(ft.format(Double.parseDouble(rs.getString(11))));
                GApruebaL.lblSubtotal.setText(ft.format(Double.parseDouble(rs.getString(12))));
                GApruebaL.lblValorFomento.setText(ft.format(Double.parseDouble(rs.getString(13))));
                GApruebaL.lblValorImpuesto.setText(ft.format(Double.parseDouble(rs.getString(14))));
                GApruebaL.lblDesAnticipo.setText(ft.format(Double.parseDouble(rs.getString(15))));
                GApruebaL.lblNetoPagar.setText(ft.format(Double.parseDouble(rs.getString(16))));
            }

            tbl = new tablas();
            tbl.llenarTabla(GApruebaL.tblTiquetesLiq, modelTiquetes, columTiquetes.length, "SELECT detalleliquidacion.idTiquete,liquidaciones.fecha,tiquete.kilosNetos,detalleliquidacion.humedad,detalleliquidacion.impureza,detalleliquidacion.castigoHumedad, detalleliquidacion.castigoImpureza,detalleliquidacion.pesoCompra,detalleliquidacion.valorKilo,detalleliquidacion.valorTotal FROM detalleliquidacion,liquidaciones,tiquete WHERE liquidaciones.idLiquidaciones='" + idLiquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete");
            cambiarFormatoDetalle(idLiquidacion);
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarFormatoDetalle(String idLiquidacion) {
        int row = GApruebaL.tblTiquetesLiq.getRowCount();
        int j = 0;
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < row; i++) {
                //String idLiquidacion = GApruebaL.tblAprobarLiq.getValueAt(i, 0).toString();
                rs = st.executeQuery("SELECT detalleliquidacion.idTiquete,liquidaciones.fecha,tiquete.kilosNetos,detalleliquidacion.humedad,detalleliquidacion.impureza,detalleliquidacion.castigoHumedad, detalleliquidacion.castigoImpureza,detalleliquidacion.pesoCompra,detalleliquidacion.valorKilo,detalleliquidacion.valorTotal FROM detalleliquidacion,liquidaciones,tiquete WHERE liquidaciones.idLiquidaciones='" + idLiquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete");
                while (rs.next()) {
                    String Vector[] = new String[columTiquetes.length];
                    Vector[0] = rs.getString(1);
                    Vector[1] = rs.getString(2);
                    Vector[2] = ft.format(Double.parseDouble(rs.getString(3)));
                    Vector[3] = rs.getString(4);
                    Vector[4] = rs.getString(5);
                    Vector[5] = ft.format(Double.parseDouble(rs.getString(6)));
                    Vector[6] = ft.format(Double.parseDouble(rs.getString(7)));
                    Vector[7] = ft.format(Double.parseDouble(rs.getString(8)));
                    Vector[8] = ft.format(Double.parseDouble(rs.getString(9)));
                    Vector[9] = ft.format(Double.parseDouble(rs.getString(10)));
                    for (j = 0; j < columTiquetes.length; j++) {
                        GApruebaL.tblTiquetesLiq.setValueAt(Vector[j], i, j);
                    }
                    i++;
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void operacionesDetalle() {
        int row = GApruebaL.tblTiquetesLiq.getRowCount();

        if (GApruebaL.lblDesAnticipo.getText().equals("")) {
            this.desAnticipo = 0;
        } else if (!GApruebaL.lblDesAnticipo.getText().contains(",")) {
            this.desAnticipo = (long) Double.parseDouble(GApruebaL.lblDesAnticipo.getText());
            GApruebaL.lblDesAnticipo.setText(ft.format(desAnticipo));
        } else {
            desAnticipo = (long) Double.parseDouble(GApruebaL.lblDesAnticipo.getText().replace(",", ""));
            GApruebaL.lblDesAnticipo.setText(ft.format(desAnticipo));
        }

        if (row > 0) {

            humedadIdeal = GApruebaL.lblHumedadIdeal.getText();
            impurezaIdeal = GApruebaL.lblImpurezaIdeal.getText();
            FomArroz = GApruebaL.lblPorcentajeFomento.getText();
            impuesto = GApruebaL.lblPorcentajeImpuesto.getText();

            kiloNetos = 0;
            totalKilosCompra = 0;
            subTotal = 0;
            valorImpuesto = 0;
            valorFomArrocero = 0;
            netoPagar = 0;

            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                for (int i = 0; i < row; i++) {
                    String idTiquete = GApruebaL.tblTiquetesLiq.getValueAt(i, 0).toString();
                    rs = st.executeQuery("SELECT detalleliquidacion.idDetalleLiquidacion,detalleliquidacion.valorCarga,tiquete.kilosNetos FROM tiquete,detalleliquidacion WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND tiquete.idTiquete='" + idTiquete + "'");
                    if (rs.next()) {
                        String Vector[] = new String[columTiquetes.length];
                        String idDetalleLiquidacion = rs.getString(1);
                        String valorCarga = rs.getString(2);
                        String kilosNetos = rs.getString(3);

                        String fecha = GApruebaL.tblTiquetesLiq.getValueAt(i, 1).toString();
                        String kgBrutos = GApruebaL.tblTiquetesLiq.getValueAt(i, 2).toString();
                        String humedadO = GApruebaL.tblTiquetesLiq.getValueAt(i, 3).toString();
                        String impurezaO = GApruebaL.tblTiquetesLiq.getValueAt(i, 4).toString();

    
                        Vector[0] = idTiquete;
                        Vector[1] = fecha;
                        Vector[2] = kgBrutos;
                        Vector[3] = humedadO;
                        Vector[4] = impurezaO;

                        double castigoHumedad;
                        if (Double.parseDouble(humedadO) > Double.parseDouble(humedadIdeal)) {
                            castigoHumedad = (100 - Double.parseDouble(humedadO)) / (100 - Double.parseDouble(humedadIdeal));
                            double totalHumedad = Math.round(castigoHumedad * 100);
                            Vector[5] = String.valueOf(ft.format(totalHumedad / 100));//castigo humedad
                        } else {
                            Vector[5] = "1";
                            castigoHumedad = 1.00;
                        }
                        double castigoImpureza;
                        if (Double.parseDouble(impurezaO) > Double.parseDouble(impurezaIdeal)) {
                            castigoImpureza = (100 - Double.parseDouble(impurezaO)) / (100 - Double.parseDouble(impurezaIdeal));
                            double totalImpureza = Math.round(castigoImpureza * 100);
                            Vector[6] = String.valueOf(ft.format(totalImpureza / 100));//castigo impureza
                        } else {
                            Vector[6] = "1";
                            castigoImpureza = 1.00;
                        }

                        //Kilos Netos
                        double kiloNetos = Double.parseDouble(kilosNetos);

                        double pesoCompra = Math.round(castigoHumedad * castigoImpureza * kiloNetos);
                        Vector[7] = String.valueOf(ft.format(pesoCompra));

                        double valorKilo = Double.parseDouble(valorCarga) / 125;
                        Vector[8] = String.valueOf(ft.format(valorKilo));

                        long valorTotal = (long) (pesoCompra * valorKilo);
                        Vector[9] = String.valueOf(ft.format(valorTotal));

                        this.kiloNetos += kiloNetos;
                        this.totalKilosCompra += pesoCompra;
                        this.subTotal += valorTotal;

                        for (int j = 0; j < columTiquetes.length; j++) {
                            GApruebaL.tblTiquetesLiq.setValueAt(Vector[j], i, j);
                        }
                    }
                }
                double impuesto = (Double.parseDouble(this.impuesto) / 100);
                System.out.println(impuesto);

                double fomArroz = (Double.parseDouble(FomArroz) / 100);

                this.valorImpuesto = (long) ((long) this.subTotal * impuesto);
                this.valorFomArrocero = (long) (this.subTotal * fomArroz);

                this.netoPagar = (long) (subTotal - this.valorFomArrocero - this.valorImpuesto - this.desAnticipo);

                GApruebaL.lblKilosNetos.setText(String.valueOf(ft.format(this.kiloNetos)));
                GApruebaL.lblKilosCompra.setText(String.valueOf(ft.format(this.totalKilosCompra)));
                GApruebaL.lblSubtotal.setText(String.valueOf(ft.format(this.subTotal)));
                GApruebaL.lblValorFomento.setText(String.valueOf(ft.format(this.valorFomArrocero)));
                GApruebaL.lblValorImpuesto.setText(String.valueOf(ft.format(this.valorImpuesto)));
                GApruebaL.lblNetoPagar.setText(String.valueOf(ft.format(this.netoPagar)));
                Con.Desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void aprobarLiquidacion(String idLiquidacion) {
        String kilosNetos = GApruebaL.lblKilosNetos.getText().replace(",", "");
        String kilosCompra = GApruebaL.lblKilosCompra.getText().replace(",", "");
        String subtotal = GApruebaL.lblSubtotal.getText().replace(",", "");
        String vlrFomento = GApruebaL.lblValorFomento.getText().replace(",", "");
        String vlrImpuesto = GApruebaL.lblValorImpuesto.getText().replace(",", "");
        String descuento = GApruebaL.lblDesAnticipo.getText().replace(",", "");
        String netoPagar = GApruebaL.lblNetoPagar.getText().replace(",", "");

        if (!kilosNetos.equals("") && !kilosCompra.equals("") && !subtotal.equals("") && !vlrFomento.equals("") && !vlrImpuesto.equals("") && !netoPagar.equals("")) {
            actualizarLiquidacion(idLiquidacion, kilosNetos, kilosCompra, subtotal, vlrFomento, vlrImpuesto, descuento, netoPagar);
            actualizarDetalleLiquidacion();
            limpiarCampos();
            JOptionPane.showMessageDialog(null, "Liquidación Aprobada");

        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos obligatorios puede estar vacio");
        }
    }

    public void actualizarLiquidacion(String idLiquidacion, String kilosNetos, String kilosCompra, String subtotal, String vlrFomento, String vlrImpuesto, String descuento, String netoPagar) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE liquidaciones SET kilosNeto='" + kilosNetos + "',kilosCompra='" + kilosCompra + "',subTotal='" + subtotal + "',valorFomArrocero='" + vlrFomento + "',valorImpuesto='" + vlrImpuesto + "',descuentoAnticipo='" + descuento + "',netoPagar='" + netoPagar + "',estado='aprobado' WHERE idLiquidaciones='" + idLiquidacion + "'");
            System.out.println("liquidacion actualizada");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarDetalleLiquidacion() {
        int row = GApruebaL.tblTiquetesLiq.getRowCount();
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < row; i++) {
                String idTiquete = GApruebaL.tblTiquetesLiq.getValueAt(i, 0).toString();
                String fecha = GApruebaL.tblTiquetesLiq.getValueAt(i, 1).toString();
                String kilosNetos = GApruebaL.tblTiquetesLiq.getValueAt(i, 2).toString().replace(",", "");
                String humedadO = GApruebaL.tblTiquetesLiq.getValueAt(i, 3).toString();
                String impurezaO = GApruebaL.tblTiquetesLiq.getValueAt(i, 4).toString();
                String castigoH = GApruebaL.tblTiquetesLiq.getValueAt(i, 5).toString();
                String castigoI = GApruebaL.tblTiquetesLiq.getValueAt(i, 6).toString();
                String pesoCompra = GApruebaL.tblTiquetesLiq.getValueAt(i, 7).toString().replace(",", "");
                String valorKilo = GApruebaL.tblTiquetesLiq.getValueAt(i, 8).toString().replace(",", "");
                String valorTotal = GApruebaL.tblTiquetesLiq.getValueAt(i, 9).toString().replace(",", "");

                st.executeUpdate("UPDATE detalleliquidacion SET humedad='" + humedadO + "',impureza='" + impurezaO + "',castigoHumedad='" + castigoH + "',castigoImpureza='" + castigoI + "',pesoCompra='" + pesoCompra + "',valorTotal='" + valorTotal + "' WHERE idTiquete='" + idTiquete + "';");
                if (i == row - 1) {
                    System.out.println("detalle liquidacion actualizado");
                }
            }
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
