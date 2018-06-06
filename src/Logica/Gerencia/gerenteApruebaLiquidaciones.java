/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Gerencia;

import Interfaces.GerenteApruebaLiquidaciones;
import Logica.Extras.currencyFormat;
import Negocio.Conexion;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.login;
import Reportes.logicaReportes;
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
public class gerenteApruebaLiquidaciones {

    public static currencyFormat cu;
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static GerenteApruebaLiquidaciones GApruebaL;
    public static extras ext;
    public tablas tbl;
    public static logicaReportes reportes;
    public static String placa, color, modelo, marca, idVehiculo, idLiquidacion,user,gerente;
    public static DefaultTableModel modelPendientes, modelTiquetes;
    public static String columPendientes[] = new String[]{"N°", "Agricultor", "Subtotal", "Neto Pagar"};
    public static String alinearHeaderPendientes[] = new String[]{"10", "default", "default", "default"};
    public static String alinearCamposPendientes[] = new String[]{"center", "left", "right", "right"};
    public static String columTiquetes[] = new String[]{"N° Tiquete", "Fecha Tiquete", "Kg Brutos", "Humedad O", "Impureza O", "% Castigo H", "% Castigo I", "Peso Compra", "Vlr Carga", "Vlr Kg", "Vlr Total"};
    public static String alinearHeaderTiquetes[] = new String[]{"default", "default", "default", "default", "default", "default", "default", "default", "default", "default", "default"};
    public static String alinearCamposTiquetes[] = new String[]{"center", "center", "right", "right", "right", "right", "right", "right", "right", "right", "right"};
    public static login login;
    long kiloNetos, subTotal, valorImpuesto, valorFomArrocero, desAnticipo, netoPagar, humedadObtenida, impurezaObtenida, castigoHumedad, castigoImpureza, pesoCompra, valorTotal, netoAPagar, totalKilosCompra, vlrFomentoArrocero;
    public static String idLiquidaciones, fecha, humedadIdeal, impurezaIdeal, FomArroz, impuesto, tipoImpuesto;

    public gerenteApruebaLiquidaciones() {
        cu = new currencyFormat();
        ext = new extras();
        tbl = new tablas();
        crearModeloLiquidacion();
    }

    public void crearModeloLiquidacion() {
        modelPendientes = new DefaultTableModel(null, columPendientes) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(GApruebaL.tblLiquidaciones, modelPendientes, columPendientes.length, "SELECT liquidaciones.idLiquidaciones, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),subTotal,netoPagar FROM liquidaciones,detalleliquidacion,tiquete,personalexterno WHERE liquidaciones.estado='en proceso' and liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete AND tiquete.idAgricultor=personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");
        tbl.alinearHeaderTable(GApruebaL.tblLiquidaciones, alinearHeaderPendientes);
        tbl.alinearCamposTable(GApruebaL.tblLiquidaciones, alinearCamposPendientes);
        tbl.alinearHeaderTable(GApruebaL.tblDetalleL, alinearHeaderTiquetes);
        formatoTblLiquidacion();
    }

    public void formatoTblLiquidacion() {
        int row = GApruebaL.tblLiquidaciones.getRowCount();
        for (int i = 0; i < row; i++) {
            String subtotal = cu.moneyFormat(Double.parseDouble(GApruebaL.tblLiquidaciones.getValueAt(i, 2).toString()));
            GApruebaL.tblLiquidaciones.setValueAt(subtotal, i, 2);
            String netoPagar = cu.moneyFormat(Double.parseDouble(GApruebaL.tblLiquidaciones.getValueAt(i, 3).toString()));
            GApruebaL.tblLiquidaciones.setValueAt(netoPagar, i, 3);
        }
    }

    public void tablaCamposLiquidacion() {
        tbl.alinearCamposTable(GApruebaL.tblDetalleL, alinearCamposTiquetes);
        int rec = GApruebaL.tblLiquidaciones.getSelectedRow();
        idLiquidacion = GApruebaL.tblLiquidaciones.getValueAt(rec, 0).toString();
        GApruebaL.lblNumLiquidacion.setText(idLiquidacion);
        GApruebaL.lblNomAgricultor.setText(GApruebaL.tblLiquidaciones.getValueAt(rec, 1).toString());

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT cedula, CONCAT( telefono, ' ', telefono2, ' ', telefono3 ), direccion, liquidaciones.fecha, liquidaciones.humedadIdeal, liquidaciones.impurezaIdeal, liquidaciones.fomArrocero, liquidaciones.impuesto, liquidaciones.porcenImpuesto, liquidaciones.kilosNeto, liquidaciones.kilosCompra, liquidaciones.subtotal, liquidaciones.valorFomArrocero, liquidaciones.valorImpuesto, liquidaciones.descuentoAnticipo, liquidaciones.netoPagar FROM personalexterno, tiquete, detalleliquidacion, liquidaciones WHERE liquidaciones.idLiquidaciones ='" + idLiquidacion + "' AND detalleliquidacion.idliquidaciones=liquidaciones.idLiquidaciones AND detalleliquidacion.idTiquete = tiquete.idTiquete AND tiquete.idAgricultor = personalexterno.idPersonalExterno GROUP BY liquidaciones.idLiquidaciones");

            while (rs.next()) {
                GApruebaL.lblCedAgricultor.setText(rs.getString(1));
                GApruebaL.lblCelAgricultor.setText(rs.getString(2));
                GApruebaL.lblDirAgricultor.setText(rs.getString(3));
                GApruebaL.lblFechaLiquidacion.setText(cu.dateNotTime(rs.getString(4)));
                GApruebaL.lblHumedadIdeal.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(5))));
                GApruebaL.lblImpurezaIdeal.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(6))));
                GApruebaL.lblPorcentajeFomento.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(7))));
                GApruebaL.lblTipoImpuesto.setText(rs.getString(8));
                GApruebaL.lblPorcentajeImpuesto.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(9))));
                GApruebaL.lblKilosNetos.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(10))));
                Double klNetos = Double.parseDouble(rs.getString(10));
                GApruebaL.lblKilosCompra.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(11))));
                Double klCompra = Double.parseDouble(rs.getString(11));
                GApruebaL.lblSubtotal.setText(cu.moneyFormat(Double.parseDouble(rs.getString(12))));
                GApruebaL.lblValorFomento.setText(cu.moneyFormat(Double.parseDouble(rs.getString(13))));
                GApruebaL.lblValorImpuesto.setText(cu.moneyFormat(Double.parseDouble(rs.getString(14))));
                GApruebaL.lblDesAnticipo.setText(cu.moneyFormat(Double.parseDouble(rs.getString(15))));
                GApruebaL.lblNetoPagar.setText(cu.moneyFormat(Double.parseDouble(rs.getString(16))));
                Double descuento = klNetos - klCompra;
                GApruebaL.lblKilosDescuento.setText(String.valueOf(cu.thousandsFormat(descuento)));
            }
            tbl.llenarTabla(GApruebaL.tblDetalleL, modelTiquetes, columTiquetes.length, "SELECT detalleliquidacion.idTiquete,liquidaciones.fecha,tiquete.kilosNetos,detalleliquidacion.humedad,detalleliquidacion.impureza,detalleliquidacion.castigoHumedad, detalleliquidacion.castigoImpureza,detalleliquidacion.pesoCompra,detalleliquidacion.valorCarga,detalleliquidacion.valorKilo,detalleliquidacion.valorTotal FROM detalleliquidacion,liquidaciones,tiquete WHERE liquidaciones.idLiquidaciones='" + idLiquidacion + "' AND liquidaciones.idLiquidaciones=detalleliquidacion.idliquidaciones AND detalleliquidacion.idTiquete=tiquete.idTiquete");
            formatoTblDetalle();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearModeloDetalle() {
        modelTiquetes = new DefaultTableModel(null, columTiquetes) {
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 3 || columna == 4 || columna == 8) {
                    return true;
                }
                return false;
            }
        };
        GApruebaL.tblDetalleL.setModel(modelTiquetes);
    }

    public void formatoTblDetalle() {
        int row = GApruebaL.tblDetalleL.getRowCount();
        for (int i = 0; i < row; i++) {
            String fecha = cu.dateNotTime(GApruebaL.tblDetalleL.getValueAt(i, 1).toString());
            GApruebaL.tblDetalleL.setValueAt(fecha, i, 1);
            String kgBrutos = cu.thousandsFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 2).toString()));
            GApruebaL.tblDetalleL.setValueAt(kgBrutos, i, 2);
            String humedadO = cu.thousandsFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 3).toString()));
            GApruebaL.tblDetalleL.setValueAt(humedadO, i, 3);
            String impurezaO = cu.thousandsFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 4).toString()));
            GApruebaL.tblDetalleL.setValueAt(impurezaO, i, 4);
            String castigoH = cu.thousandsFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 5).toString()));
            GApruebaL.tblDetalleL.setValueAt(castigoH, i, 5);
            String castigoI = cu.thousandsFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 6).toString()));
            GApruebaL.tblDetalleL.setValueAt(castigoI, i, 6);
            String pesoCompra = cu.thousandsFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 7).toString()));
            GApruebaL.tblDetalleL.setValueAt(pesoCompra, i, 7);
            String valorCarga = cu.moneyFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 8).toString()));
            GApruebaL.tblDetalleL.setValueAt(valorCarga, i, 8);
            String valorKg = cu.moneyFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 9).toString()));
            GApruebaL.tblDetalleL.setValueAt(valorKg, i, 9);
            String valorTotal = cu.moneyFormat(Double.parseDouble(GApruebaL.tblDetalleL.getValueAt(i, 10).toString()));
            GApruebaL.tblDetalleL.setValueAt(valorTotal, i, 10);
        }
    }

    public void operacionesDetalle() {
        int row = GApruebaL.tblDetalleL.getRowCount();

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
                    String idTiquete = GApruebaL.tblDetalleL.getValueAt(i, 0).toString();
                    rs = st.executeQuery("SELECT detalleliquidacion.idDetalleLiquidacion,detalleliquidacion.valorCarga,tiquete.kilosNetos FROM tiquete,detalleliquidacion WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND tiquete.idTiquete='" + idTiquete + "'");
                    if (rs.next()) {
                        String Vector[] = new String[columTiquetes.length];
                        String idDetalleLiquidacion = rs.getString(1);
                        //String valorCarga = rs.getString(2);
                        String kilosNetos = rs.getString(3);

                        String fecha = GApruebaL.tblDetalleL.getValueAt(i, 1).toString();
                        String kgBrutos = GApruebaL.tblDetalleL.getValueAt(i, 2).toString();
                        String humedadO = GApruebaL.tblDetalleL.getValueAt(i, 3).toString();
                        String impurezaO = GApruebaL.tblDetalleL.getValueAt(i, 4).toString();
                        String valorCarga = GApruebaL.tblDetalleL.getValueAt(i, 8).toString();

                        Vector[0] = idTiquete;
                        Vector[1] = fecha;
                        Vector[2] = kgBrutos;
                        Vector[3] = humedadO;
                        Vector[4] = impurezaO;
                        Vector[8] = valorCarga;

                        double castigoHumedad;
                        if (Double.parseDouble(humedadO) > Double.parseDouble(humedadIdeal)) {
                            castigoHumedad = (100 - Double.parseDouble(humedadO)) / (100 - Double.parseDouble(humedadIdeal));
                            double totalHumedad = Math.round(castigoHumedad * 100);
                            Vector[5] = String.valueOf(cu.thousandsFormat(totalHumedad / 100));//castigo humedad
                        } else {
                            Vector[5] = "1";
                            castigoHumedad = 1.00;
                        }
                        double castigoImpureza;
                        if (Double.parseDouble(impurezaO) > Double.parseDouble(impurezaIdeal)) {
                            castigoImpureza = (100 - Double.parseDouble(impurezaO)) / (100 - Double.parseDouble(impurezaIdeal));
                            double totalImpureza = Math.round(castigoImpureza * 100);
                            Vector[6] = String.valueOf(cu.thousandsFormat(totalImpureza / 100));//castigo impureza
                        } else {
                            Vector[6] = "1";
                            castigoImpureza = 1.00;
                        }

                        //Kilos Netos
                        double kiloNetos = Double.parseDouble(kilosNetos);

                        double pesoCompra = Math.round(castigoHumedad * castigoImpureza * kiloNetos);
                        Vector[7] = String.valueOf(cu.thousandsFormat(pesoCompra));

                        double tvalorCarga = Double.parseDouble(cu.notMoneyFormat(valorCarga));
                        Vector[8] = String.valueOf(cu.moneyFormat(tvalorCarga));

                        double valorKilo = (tvalorCarga) / 125;
                        Vector[9] = String.valueOf(cu.moneyFormat(valorKilo));

                        long valorTotal = (long) (pesoCompra * valorKilo);
                        Vector[10] = String.valueOf(cu.moneyFormat(valorTotal));

                        this.kiloNetos += kiloNetos;
                        this.totalKilosCompra += pesoCompra;
                        this.subTotal += valorTotal;

                        for (int j = 0; j < columTiquetes.length; j++) {
                            GApruebaL.tblDetalleL.setValueAt(Vector[j], i, j);
                        }
                    }
                }
                double impuesto = (Double.parseDouble(this.impuesto) / 100);
                double fomArroz = (Double.parseDouble(FomArroz) / 100);

                this.valorImpuesto = (long) ((long) this.subTotal * impuesto);
                this.valorFomArrocero = (long) (this.subTotal * fomArroz);
                this.netoPagar = (long) (subTotal - this.valorFomArrocero - this.valorImpuesto - this.desAnticipo);

                GApruebaL.lblKilosNetos.setText(String.valueOf(cu.thousandsFormat(this.kiloNetos)));
                GApruebaL.lblKilosCompra.setText(String.valueOf(cu.thousandsFormat(this.totalKilosCompra)));
                GApruebaL.lblSubtotal.setText(String.valueOf(cu.moneyFormat(this.subTotal)));
                GApruebaL.lblValorFomento.setText(String.valueOf(cu.moneyFormat(this.valorFomArrocero)));
                GApruebaL.lblValorImpuesto.setText(String.valueOf(cu.moneyFormat(this.valorImpuesto)));
                GApruebaL.lblNetoPagar.setText(String.valueOf(cu.moneyFormat(this.netoPagar)));

                long descuento = kiloNetos - totalKilosCompra;
                GApruebaL.lblKilosDescuento.setText(String.valueOf(cu.thousandsFormat(descuento)));

                Con.Desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void aprobarLiquidacion(String idLiquidacion) {
        String kilosNetos = cu.notThousandsFormat(GApruebaL.lblKilosNetos.getText());
        String kilosCompra = cu.notThousandsFormat(GApruebaL.lblKilosCompra.getText());
        String subtotal = cu.notMoneyFormat(GApruebaL.lblSubtotal.getText());
        String vlrFomento = cu.notMoneyFormat(GApruebaL.lblValorFomento.getText());
        String vlrImpuesto = cu.notMoneyFormat(GApruebaL.lblValorImpuesto.getText());
        String descuento = cu.notMoneyFormat(GApruebaL.lblDesAnticipo.getText());
        String netoPagar = cu.notMoneyFormat(GApruebaL.lblNetoPagar.getText());

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
            ext.logs("UPDATE","UPDATE liquidaciones SET kilosNeto='" + kilosNetos + "',kilosCompra='" + kilosCompra + "',subTotal='" + subtotal + "',valorFomArrocero='" + vlrFomento + "',valorImpuesto='" + vlrImpuesto + "',descuentoAnticipo='" + descuento + "',netoPagar='" + netoPagar + "',estado='aprobado' WHERE idLiquidaciones='" + idLiquidacion + "'");
            st.executeUpdate("INSERT INTO notificaciones (idNotificacion, privilegio, usuario, titulo, texto, tipo, fechaCreacion, fechaVisualizacion, origen,id) VALUES (0,'contador',NULL,'Nueva Liquidación Aprobada','Liquidación Nº "+idLiquidacion+" del agricultor "+GApruebaL.lblNomAgricultor.getText()+"','tip','"+cu.getDateTimeNow()+"',NULL,'gernete','"+idLiquidacion+"')");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarDetalleLiquidacion() {
        int row = GApruebaL.tblDetalleL.getRowCount();
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < row; i++) {
                String idTiquete = GApruebaL.tblDetalleL.getValueAt(i, 0).toString();
                String fecha = GApruebaL.tblDetalleL.getValueAt(i, 1).toString();
                String kilosNetos = cu.notThousandsFormat(GApruebaL.tblDetalleL.getValueAt(i, 2).toString());
                String humedadO = cu.notThousandsFormat(GApruebaL.tblDetalleL.getValueAt(i, 3).toString());
                String impurezaO = cu.notThousandsFormat(GApruebaL.tblDetalleL.getValueAt(i, 4).toString());
                String castigoH = cu.notThousandsFormat(GApruebaL.tblDetalleL.getValueAt(i, 5).toString());
                String castigoI = cu.notThousandsFormat(GApruebaL.tblDetalleL.getValueAt(i, 6).toString());
                String pesoCompra = cu.notThousandsFormat(GApruebaL.tblDetalleL.getValueAt(i, 7).toString());
                String valorCarga = cu.notMoneyFormat(GApruebaL.tblDetalleL.getValueAt(i, 8).toString());
                String valorKilo = cu.notMoneyFormat(GApruebaL.tblDetalleL.getValueAt(i, 9).toString());
                String valorTotal = cu.notMoneyFormat(GApruebaL.tblDetalleL.getValueAt(i, 10).toString());

                st.executeUpdate("UPDATE detalleliquidacion SET humedad='" + humedadO + "',impureza='" + impurezaO + "',castigoHumedad='" + castigoH + "',castigoImpureza='" + castigoI + "',pesoCompra='" + pesoCompra + "',valorCarga='" + valorCarga + "',valorKilo='" + valorKilo + "',valorTotal='" + valorTotal + "' WHERE idTiquete='" + idTiquete + "';");
               ext.logs("UPDATE","UPDATE detalleliquidacion SET humedad='" + humedadO + "',impureza='" + impurezaO + "',castigoHumedad='" + castigoH + "',castigoImpureza='" + castigoI + "',pesoCompra='" + pesoCompra + "',valorCarga='" + valorCarga + "',valorKilo='" + valorKilo + "',valorTotal='" + valorTotal + "' WHERE idTiquete='" + idTiquete + "';");

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
        GApruebaL.lblKilosDescuento.setText("");
    }
}
