/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Liquidacion;

import Interfaces.BusquedasTiquete;
import Interfaces.Liquidacion;
import Logica.Extras.tablas;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import static Logica.Laboratorio.laboratorio_tiquete_inicial.BusTiquete;
import java.text.DecimalFormat;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jhonansmith
 */
public class liquidacion {

    public static Liquidacion Liqui;
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static extras ext;
    public DefaultTableModel modeloSeleccionTiquete, modeloSeleccionLiquidacion;
    public String columnasSeleccionTiquete[] = new String[]{"N", "Fecha", "Agricultor", "Valor Carga", "Kilos Netos"};
    public String columnasLiquidacion[] = new String[]{"N", "Kilos Brutos", "Humedad", "Impureza", "Castigo Humedad", "Castigo Impureza", "Peso Compra", "Valor Kilo", "Valor Total"};
    public tablas tbl;
    public String idAgricultor;
    public static String idLiquidaciones, fecha, humedadIdeal, impurezaIdeal, FomArroz, impuesto, tipoImpuesto;
    long kiloNetos, totalKilosCompra, subTotal, valorImpuesto, valorFomArrocero, desAnticipo, netoPagar;
    DecimalFormat ft = new DecimalFormat("###,###.##");

    public liquidacion() {
        ext = new extras();
        tbl = new tablas();
        limpiar();
        cargarValorInicio();
    }

    public static void abrirBusquedasTiquete(String TiqLiqui) {
        BusTiquete = new BusquedasTiquete(TiqLiqui);
        BusTiquete.setVisible(true);
        //panel agricultor
        BusTiquete.panel.setEnabledAt(0, true);
        BusTiquete.panel.setEnabledAt(1, false);
        BusTiquete.panel.setEnabledAt(2, false);;
        BusTiquete.panel.setSelectedIndex(0);

    }

    public void crearModelo() {
        if (!idAgricultor.equals("")) {
            modeloSeleccionTiquete = new DefaultTableModel(null, columnasSeleccionTiquete) {
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            tbl.llenarTabla(Liqui.tblSeleccionTiquete, modeloSeleccionTiquete, columnasSeleccionTiquete.length, "SELECT tiquete.idTiquete,tiquete.fecha,CONCAT(personalexterno.apellidos,' ',personalexterno.nombres),detalleliquidacion.valorCarga,tiquete.kilosNetos FROM tiquete,detalleliquidacion,personalexterno WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND detalleliquidacion.idliquidaciones IS NULL AND personalexterno.idPersonalExterno=tiquete.idAgricultor AND tiquete.idAgricultor='" + idAgricultor + "'");
            modeloSeleccionLiquidacion = new DefaultTableModel(null, columnasLiquidacion) {
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Liqui.tblSeleccionLiquidacion.setModel(modeloSeleccionLiquidacion);
        }
    }

    public void moverTabla(String movimiento) {
        humedadIdeal = Liqui.txtHumedadIdeal.getText();
        impurezaIdeal = Liqui.txtImpurezaIdeal.getText();
        FomArroz = Liqui.txtFomArroz.getText();
        impuesto = Liqui.txtImpuesto.getText();
        if (!humedadIdeal.equals("") && !impurezaIdeal.equals("") && !FomArroz.equals("") && !impuesto.equals("")) {

            switch (movimiento) {
                case "todoDerecha":
                    int row = Liqui.tblSeleccionTiquete.getRowCount();
                    if (row > 0) {
                        for (int i = 0; i < row; i++) {
                            String Vector[] = new String[columnasLiquidacion.length];
                            // for (int j = 0; j < columnasLiquidacion.length; j++) {
                            Vector[0] = Liqui.tblSeleccionTiquete.getValueAt(i, 0).toString();
                            //}
                            modeloSeleccionLiquidacion.addRow(Vector);
                        }
                        for (int i = row - 1; i >= 0; i--) {
                            modeloSeleccionTiquete.removeRow(i);
                        }
                        completarTablaLiquidacion();
                    }
                    break;
                case "unoDerecha":
                    int filaSeleccionadaD = Liqui.tblSeleccionTiquete.getSelectedRow();
                    if (filaSeleccionadaD >= 0) {
                        String Vector[] = new String[columnasSeleccionTiquete.length];
                        // for (int j = 0; j < columnasSeleccionTiquete.length; j++) {
                        Vector[0] = Liqui.tblSeleccionTiquete.getValueAt(filaSeleccionadaD, 0).toString();
                        // }
                        modeloSeleccionLiquidacion.addRow(Vector);
                        // Liqui.tblSeleccionLiquidacion.setModel(modeloSeleccionLiquidacion);
                        modeloSeleccionTiquete.removeRow(filaSeleccionadaD);
                        completarTablaLiquidacion();
                    }
                    break;
                case "todoIzquierda":
                    crearModelo();
                    completarTablaTiquete();
                    break;
                case "unoIzquierda":
                    int filaSeleccionadaI = Liqui.tblSeleccionLiquidacion.getSelectedRow();
                    if (filaSeleccionadaI >= 0) {
                        String Vector[] = new String[columnasSeleccionTiquete.length];
                        //for (int j = 0; j < columnasSeleccionTiquete.length; j++) {
                        Vector[0] = Liqui.tblSeleccionLiquidacion.getValueAt(filaSeleccionadaI, 0).toString();
                        //}
                        modeloSeleccionTiquete.addRow(Vector);
                        // Liqui.tblSeleccionLiquidacion.setModel(modeloSeleccionLiquidacion);
                        modeloSeleccionLiquidacion.removeRow(filaSeleccionadaI);
                        completarTablaTiquete();
                        completarTablaLiquidacion();
                    }
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los cambios no deben de estar vacios.");
        }
    }

    public void completarTablaLiquidacion() {
        int row = Liqui.tblSeleccionLiquidacion.getRowCount();
        if (row > 0) {
            humedadIdeal = Liqui.txtHumedadIdeal.getText();
            impurezaIdeal = Liqui.txtImpurezaIdeal.getText();
            FomArroz = Liqui.txtFomArroz.getText();
            impuesto = Liqui.txtImpuesto.getText();
            kiloNetos = 0;
            totalKilosCompra = 0;
            subTotal = 0;
            valorImpuesto = 0;
            valorFomArrocero = 0;
            desAnticipo = 0;
            netoPagar = 0;
            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                for (int i = 0; i < row; i++) {
                    String idTiquete = Liqui.tblSeleccionLiquidacion.getValueAt(i, 0).toString();
                    rs = st.executeQuery("SELECT detalleliquidacion.idDetalleLiquidacion,tiquete.humedadUno,tiquete.impurezaUno,detalleliquidacion.valorCarga,tiquete.kilosNetos FROM tiquete,detalleliquidacion WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND tiquete.idTiquete='" + idTiquete + "'");
                    if (rs.next()) {
                        String Vector[] = new String[columnasLiquidacion.length];
                        String idDetalleLiquidacion = rs.getString(1);
                        String humedad = rs.getString(2);
                        String impureza = rs.getString(3);
                        String valorCarga = rs.getString(4);
                        String kilosNetos = rs.getString(5);
                        Vector[0] = idTiquete;
                        Vector[1] = kilosNetos;
                        Vector[2] = humedad;
                        Vector[3] = impureza;
                        double castigoHumedad;
                        if (Double.parseDouble(humedad) > Double.parseDouble(humedadIdeal)) {
                            castigoHumedad = (100 - Double.parseDouble(humedad)) / (100 - Double.parseDouble(humedadIdeal));
                            double totalHumedad = Math.round(castigoHumedad * 100);
                            Vector[4] = String.valueOf(totalHumedad / 100);//castigo humedad
                        } else {
                            Vector[4] = "1.00";
                            castigoHumedad = 1.00;
                        }
                        double castigoImpureza;
                        if (Double.parseDouble(impureza) > Double.parseDouble(impurezaIdeal)) {
                            castigoImpureza = (100 - Double.parseDouble(impureza)) / (100 - Double.parseDouble(impurezaIdeal));
                            double totalImpureza = Math.round(castigoImpureza * 100);
                            Vector[5] = String.valueOf(totalImpureza / 100);//castigo impureza
                        } else {
                            Vector[5] = "1.00";
                            castigoImpureza = 1.00;
                        }
                        double kiloNetos = Double.parseDouble(kilosNetos);
                        double pesoCompra = Math.round(castigoHumedad * castigoImpureza * kiloNetos);
                        Vector[6] = String.valueOf(ft.format(pesoCompra));
                        double valorKilo = Double.parseDouble(valorCarga) / 125;
                        Vector[7] = String.valueOf(ft.format(valorKilo));
                        long valorTotal = (long) (pesoCompra * valorKilo);
                        Vector[8] = String.valueOf(ft.format(valorTotal));
                        this.kiloNetos += kiloNetos;
                        this.totalKilosCompra += pesoCompra;
                        this.subTotal += valorTotal;
                        for (int j = 0; j < columnasLiquidacion.length; j++) {
                            Liqui.tblSeleccionLiquidacion.setValueAt(Vector[j], i, j);
                        }
                    }
                }
                double impuesto = (Double.parseDouble(this.impuesto) / 100);
                System.out.println(impuesto);
                double fomArroz = (Double.parseDouble(FomArroz) / 100);
                this.valorImpuesto = (long) ((long) this.subTotal * impuesto);
                this.valorFomArrocero = (long) (this.subTotal * fomArroz);
                if (Liqui.txtDesAnticipo.getText().equals("")) {
                    this.desAnticipo = 0;
                } else {
                    this.desAnticipo = (long) Double.parseDouble(Liqui.txtDesAnticipo.getText());
                }
                this.netoPagar = (long) (subTotal - this.valorFomArrocero - this.valorImpuesto - this.desAnticipo);
                Liqui.txtTotalKilosNeto.setText(String.valueOf(ft.format(this.kiloNetos)));
                Liqui.txtTotalKilosCompra.setText(String.valueOf(ft.format(this.totalKilosCompra)));
                Liqui.txtSubTotal.setText(String.valueOf(ft.format(this.subTotal)));
                Liqui.txtFomArroceroLiqui.setText(String.valueOf(ft.format(this.valorFomArrocero)));
                Liqui.txtImpuestoLiqui.setText(String.valueOf(ft.format(this.valorImpuesto)));
                Liqui.txtNetoPagar.setText(String.valueOf(ft.format(this.netoPagar)));
                Con.Desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void completarTablaTiquete() {
        int row = Liqui.tblSeleccionTiquete.getRowCount();
        if (row > 0) {
            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                for (int i = 0; i < row; i++) {
                    String idTiquete = Liqui.tblSeleccionTiquete.getValueAt(i, 0).toString();
                    rs = st.executeQuery("SELECT tiquete.idTiquete,tiquete.fecha,CONCAT(personalexterno.apellidos,' ',personalexterno.nombres),detalleliquidacion.valorCarga,tiquete.kilosNetos FROM tiquete,detalleliquidacion,personalexterno WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND detalleliquidacion.idliquidaciones IS NULL AND personalexterno.idPersonalExterno=tiquete.idAgricultor AND tiquete.idTiquete='" + idTiquete + "'");
                    if (rs.next()) {
                        String Vector[] = new String[columnasSeleccionTiquete.length];
                        Vector[0] = rs.getString(1);
                        Vector[1] = rs.getString(2);
                        Vector[2] = ft.format(rs.getString(3));
                        Vector[3] = ft.format(rs.getString(4));
                        for (int j = 0; j < columnasSeleccionTiquete.length; j++) {
                            Liqui.tblSeleccionTiquete.setValueAt(Vector[j], i, j);
                        }
                    }
                }
                Con.Desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getImpuesto() {
        String nombre = Liqui.cmbImpuesto.getSelectedItem().toString();
        Liqui.lblImpuestoLiqui.setText(nombre + ":");
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT valor FROM parametros WHERE nombre='" + nombre + "'");
            if (rs.next()) {
                Liqui.txtImpuesto.setText(rs.getString(1));
                completarTablaLiquidacion();
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarValorInicio() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT valor FROM parametros WHERE nombre='Humedad'");
            if (rs.next()) {
                Liqui.txtHumedadIdeal.setText(rs.getString(1));
            }
            rs = st.executeQuery("SELECT valor FROM parametros WHERE nombre='Impureza'");
            if (rs.next()) {
                Liqui.txtImpurezaIdeal.setText(rs.getString(1));
            }
            rs = st.executeQuery("SELECT valor FROM parametros WHERE nombre='Fomento Arrocero'");
            if (rs.next()) {
                Liqui.txtFomArroz.setText(rs.getString(1));
            }
            String nombre = Liqui.cmbImpuesto.getSelectedItem().toString();
            rs = st.executeQuery("SELECT valor FROM parametros WHERE nombre='" + nombre + "'");
            if (rs.next()) {
                Liqui.txtImpuesto.setText(rs.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearLiquidacion() {
        fecha();
        humedadIdeal = Liqui.txtHumedadIdeal.getText();
        impurezaIdeal = Liqui.txtImpurezaIdeal.getText();
        FomArroz = Liqui.txtFomArroz.getText();
        tipoImpuesto = Liqui.cmbImpuesto.getSelectedItem().toString();
        impuesto = Liqui.txtImpuesto.getText();
        if (!humedadIdeal.equals("") && !impurezaIdeal.equals("") && !FomArroz.equals("") && !tipoImpuesto.equals("") && !impuesto.equals("")) {
            if (Liqui.tblSeleccionLiquidacion.getRowCount() > 0) {
                insertarLiquidacion();
                insertarDetalleLiquidacion();
            } else {
                JOptionPane.showMessageDialog(null, "No hay tiquetes para liquidar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void insertarLiquidacion() {
        try {
            PreparedStatement ps = Con.conexion.prepareStatement("INSERT INTO liquidaciones(idLiquidaciones, fecha, humedadIdeal, impurezaIdeal, kilosNeto, kilosCompra, subTotal, fomArrocero, valorFomArrocero, impuesto, porcenImpuesto, valorImpuesto, descuentoAnticipo, estado, netoPagar) VALUES (0,'" + fecha + "','" + humedadIdeal + "','" + impurezaIdeal + "','" + kiloNetos + "','" + totalKilosCompra + "','" + subTotal + "','" + FomArroz + "','" + tipoImpuesto + "','" + impuesto + "','" + valorImpuesto + "','" + desAnticipo + "','" + netoPagar + "'))", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.execute();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idLiquidaciones = String.valueOf(rs.getInt(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertarDetalleLiquidacion() {
        int row = Liqui.tblSeleccionLiquidacion.getRowCount();
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < row; i++) {
                String idTiquete = Liqui.tblSeleccionLiquidacion.getValueAt(i, 0).toString();
                String kilosNetos = Liqui.tblSeleccionLiquidacion.getValueAt(i, 1).toString();
                String humedad = Liqui.tblSeleccionLiquidacion.getValueAt(i, 2).toString();
                String impureza = Liqui.tblSeleccionLiquidacion.getValueAt(i, 3).toString();
                String castigoHumedad = Liqui.tblSeleccionLiquidacion.getValueAt(i, 4).toString();
                String castigoImpureza = Liqui.tblSeleccionLiquidacion.getValueAt(i, 5).toString();
                String pesoCompra = Liqui.tblSeleccionLiquidacion.getValueAt(i, 6).toString();
                String valorKilo = Liqui.tblSeleccionLiquidacion.getValueAt(i, 7).toString();
                String valorTotal = Liqui.tblSeleccionLiquidacion.getValueAt(i, 8).toString();
                st.executeUpdate("UPDATE detalleliquidacion SET idliquidaciones='" + idLiquidaciones + "'humedad='" + humedad + "',impureza='" + impureza + "',castigoHumedad='" + castigoHumedad + "',castigoImpureza='" + castigoImpureza + "',pesoCompra='" + pesoCompra + "',valorKilo='" + valorKilo + "',valorTotal='" + valorTotal + "' WHERE idTiquete='" + idTiquete + "';");
                if (i == row - 1) {
                    JOptionPane.showMessageDialog(null, "Creación exitosa");
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fecha() {
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        java.util.Date date = new Date();
        String fec2 = formato.format(date);
        fecha = fec2;
    }

    public void limpiar() {
        idAgricultor = "";
        idLiquidaciones = "";
        Liqui.cmbImpuesto.setSelectedIndex(0);
        Liqui.txtAgricultor.setText("");
        Liqui.txtDesAnticipo.setText("");
        Liqui.txtFomArroceroLiqui.setText("");
        Liqui.txtFomArroz.setText("");
        Liqui.txtHumedadIdeal.setText("");
        Liqui.txtImpuesto.setText("");
        Liqui.txtImpuestoLiqui.setText("");
        Liqui.txtImpurezaIdeal.setText("");
        Liqui.txtNetoPagar.setText("");
        Liqui.txtSubTotal.setText("");
        Liqui.txtTotalKilosCompra.setText("");
        Liqui.txtTotalKilosNeto.setText("");
        modeloSeleccionTiquete = new DefaultTableModel(null, columnasSeleccionTiquete) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modeloSeleccionLiquidacion = new DefaultTableModel(null, columnasLiquidacion) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }
}
