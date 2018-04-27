/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Liquidacion;

import Interfaces.BusquedasTiquete;
import Interfaces.CuotaFomento;
import Interfaces.Liquidacion;
import Interfaces.LiquidacionesAprobadas;
import Interfaces.Login;
import static Logica.Administracion.etapaInventario.ext;
import Logica.Extras.tablas;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import static Logica.Laboratorio.laboratorioTiqueteInicial.BusTiquete;
import java.text.DecimalFormat;
import java.sql.PreparedStatement;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import Logica.Extras.currencyFormat;
import Logica.Extras.login;
import javax.swing.JFrame;

/**
 *
 * @author jhonansmith
 */
public class liquidacion {

    public static LiquidacionesAprobadas LiqAprobadas;
    public static CuotaFomento Fomento;
    public static Liquidacion Liqui;
    public static Login Login;
    public static login login;
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static extras ext;
    public DefaultTableModel modeloSeleccionTiquete, modeloSeleccionLiquidacion;
    public String columnasSeleccionTiquete[] = new String[]{"N° Tiquete", "Fecha", "Agricultor", "Valor Carga", "Kilos Netos"};
    public String headeColumnasSeleccionTiquete[] = new String[]{"30", "30", "170", "40", "40"};
    public String camposCcolumnasSeleccionTiquete[] = new String[]{"center", "center", "left", "right", "right"};
    public String columnasLiquidacion[] = new String[]{"N° Tiquete", "Kg Brutos", "Humedad", "Impureza", "% Castigo H", "% Castigo I", "Peso Compra", "Vlr Kg", "Vlr Total"};
    public String headerColumnasLiquidacion[] = new String[]{"default", "default", "default", "default", "default", "default", "default", "default", "default"};
    public String camposColumnasLiquidacion[] = new String[]{"center", "right", "riht", "right", "right", "right", "right", "right", "right"};
    public static tablas tbl;
    public String idAgricultor, user;
    public static String idLiquidaciones, fecha, humedadIdeal, impurezaIdeal, FomArroz, impuesto, tipoImpuesto;
    long kiloNetos, totalKilosCompra, subTotal, valorImpuesto, valorFomArrocero, desAnticipo, netoPagar;
    currencyFormat cu;

    public liquidacion() {
        cu = new currencyFormat();
        ext = new extras();
        tbl = new tablas();
        Liqui.lblNumLiquidacion.setText(String.valueOf(ext.getNextIndex("liquidaciones")));
        Liqui.lblFechaLiquidacion.setText(cu.dateNotTime(ext.fecha()));
        tbl.alinearHeaderTable(Liqui.tblSeleccionTiquete, headeColumnasSeleccionTiquete);
        tbl.alinearHeaderTable(Liqui.tblSeleccionLiquidacion, headerColumnasLiquidacion);
        limpiar();
    }

    public static void abrirBusquedasTiquete(String TiqLiqui) {
        BusTiquete = new BusquedasTiquete(TiqLiqui);
        BusTiquete.setVisible(true);
        //panel agricultor
        BusTiquete.panel.setEnabledAt(0, true);
        BusTiquete.panel.setEnabledAt(1, false);
        BusTiquete.panel.setEnabledAt(2, false);;
        BusTiquete.panel.setSelectedIndex(0);
        ext = new extras();

    }

    public void crearModelo() {
        encabezadoLiquidacion();
        if (!idAgricultor.equals("")) {
            modeloSeleccionTiquete = new DefaultTableModel(null, columnasSeleccionTiquete) {
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            tbl.llenarTabla(Liqui.tblSeleccionTiquete, modeloSeleccionTiquete, columnasSeleccionTiquete.length, "SELECT tiquete.idTiquete,tiquete.fecha,CONCAT(personalexterno.apellidos,' ',personalexterno.nombres),detalleliquidacion.valorCarga,tiquete.kilosNetos FROM tiquete,detalleliquidacion,personalexterno WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND detalleliquidacion.idliquidaciones IS NULL AND personalexterno.idPersonalExterno=tiquete.idAgricultor AND tiquete.idCuenta=1 AND tiquete.idAgricultor='" + idAgricultor + "'");
            tbl.alinearCamposTable(Liqui.tblSeleccionTiquete, camposCcolumnasSeleccionTiquete);
            completarTablaTiquete();
            modeloSeleccionLiquidacion = new DefaultTableModel(null, columnasLiquidacion) {
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Liqui.tblSeleccionLiquidacion.setModel(modeloSeleccionLiquidacion);
        }
    }

    public void moverTabla(String movimiento) {
        tbl.alinearCamposTable(Liqui.tblSeleccionLiquidacion, camposColumnasLiquidacion);
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
        if (Liqui.txtDesAnticipo.getText().equals("")) {
            this.desAnticipo = 0;
        } else if (!Liqui.txtDesAnticipo.getText().contains(",")) {
            this.desAnticipo = (long) Double.parseDouble(Liqui.txtDesAnticipo.getText());
            Liqui.txtDesAnticipo.setText(cu.moneyFormat(desAnticipo));
        } else {
            desAnticipo = (long) Double.parseDouble(cu.notMoneyFormat(Liqui.txtDesAnticipo.getText()));
            Liquidacion.txtDesAnticipo.setText(cu.moneyFormat(desAnticipo));
        }
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
                        Vector[1] = cu.thousandsFormat(Double.parseDouble(kilosNetos));
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
                        Vector[6] = String.valueOf(cu.thousandsFormat(pesoCompra));
                        double valorKilo = Double.parseDouble(valorCarga) / 125;
                        Vector[7] = String.valueOf(cu.moneyFormat(valorKilo));
                        long valorTotal = (long) (pesoCompra * valorKilo);
                        Vector[8] = String.valueOf(cu.moneyFormat(valorTotal));
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
                this.netoPagar = (long) (subTotal - this.valorFomArrocero - this.valorImpuesto - this.desAnticipo);
                Liqui.txtTotalKilosNeto.setText(String.valueOf(cu.thousandsFormat(this.kiloNetos)));
                Liqui.txtTotalKilosCompra.setText(String.valueOf(cu.thousandsFormat(this.totalKilosCompra)));
                Liqui.txtSubTotal.setText(String.valueOf(cu.moneyFormat(this.subTotal)));
                Liqui.txtFomArroceroLiqui.setText(String.valueOf(cu.moneyFormat(this.valorFomArrocero)));
                Liqui.txtImpuestoLiqui.setText(String.valueOf(cu.moneyFormat(this.valorImpuesto)));
                Liqui.txtNetoPagar.setText(String.valueOf(cu.moneyFormat(this.netoPagar)));
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
                        Vector[1] = cu.dateNotTime(rs.getString(2));
                        Vector[2] = rs.getString(3);
                        Vector[3] = cu.moneyFormat(Double.parseDouble(rs.getString(4)));
                        Vector[4] = cu.thousandsFormat(Double.parseDouble(rs.getString(5)));
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
        user = login.enviarUsuario();
        if (!humedadIdeal.equals("") && !impurezaIdeal.equals("") && !FomArroz.equals("") && !tipoImpuesto.equals("") && !impuesto.equals("")) {
            if (Liqui.tblSeleccionLiquidacion.getRowCount() > 0) {
                insertarLiquidacion();
                insertarDetalleLiquidacion();
                Liqui.lblNumLiquidacion.setText(String.valueOf(ext.getNextIndex("liquidaciones")));
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No hay tiquetes para liquidar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos obligatorios puede estar vacio");
        }
    }

    public void insertarLiquidacion() {
        try {
            Con = new Conexion();
            PreparedStatement ps = Con.conexion.prepareStatement("INSERT INTO liquidaciones(idLiquidaciones,user, fecha, humedadIdeal, impurezaIdeal, kilosNeto, kilosCompra, subTotal, fomArrocero, valorFomArrocero, impuesto, porcenImpuesto, valorImpuesto, descuentoAnticipo, estado, netoPagar) VALUES (0,'"+user+"','" + fecha + "','" + humedadIdeal + "','" + impurezaIdeal + "','" + kiloNetos + "','" + totalKilosCompra + "','" + subTotal + "','" + FomArroz + "','" + valorFomArrocero + "','" + tipoImpuesto + "','" + impuesto + "','" + valorImpuesto + "','" + desAnticipo + "','en proceso','" + netoPagar + "')", PreparedStatement.RETURN_GENERATED_KEYS);
            ext.logs("INSERT", "INSERT INTO liquidaciones(idLiquidaciones,user, fecha, humedadIdeal, impurezaIdeal, kilosNeto, kilosCompra, subTotal, fomArrocero, valorFomArrocero, impuesto, porcenImpuesto, valorImpuesto, descuentoAnticipo, estado, netoPagar) VALUES (0,'"+user+"','" + fecha + "','" + humedadIdeal + "','" + impurezaIdeal + "','" + kiloNetos + "','" + totalKilosCompra + "','" + subTotal + "','" + FomArroz + "','" + valorFomArrocero + "','" + tipoImpuesto + "','" + impuesto + "','" + valorImpuesto + "','" + desAnticipo + "','en proceso','" + netoPagar + "')");
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
                String kilosNetos = cu.notThousandsFormat(Liqui.tblSeleccionLiquidacion.getValueAt(i, 1).toString());
                String humedad = Liqui.tblSeleccionLiquidacion.getValueAt(i, 2).toString();
                String impureza = Liqui.tblSeleccionLiquidacion.getValueAt(i, 3).toString();
                String castigoHumedad = Liqui.tblSeleccionLiquidacion.getValueAt(i, 4).toString();
                String castigoImpureza = Liqui.tblSeleccionLiquidacion.getValueAt(i, 5).toString();
                String pesoCompra = cu.notThousandsFormat(Liqui.tblSeleccionLiquidacion.getValueAt(i, 6).toString());
                String valorKilo = cu.notMoneyFormat(Liqui.tblSeleccionLiquidacion.getValueAt(i, 7).toString());
                String valorTotal = cu.notMoneyFormat(Liqui.tblSeleccionLiquidacion.getValueAt(i, 8).toString());
                st.executeUpdate("UPDATE detalleliquidacion SET idliquidaciones='" + idLiquidaciones + "',humedad='" + humedad + "',impureza='" + impureza + "',castigoHumedad='" + castigoHumedad + "',castigoImpureza='" + castigoImpureza + "',pesoCompra='" + pesoCompra + "',valorKilo='" + valorKilo + "',valorTotal='" + valorTotal + "' WHERE idTiquete='" + idTiquete + "'");
                ext.logs("UPDATE", "UPDATE detalleliquidacion SET idliquidaciones='" + idLiquidaciones + "',humedad='" + humedad + "',impureza='" + impureza + "',castigoHumedad='" + castigoHumedad + "',castigoImpureza='" + castigoImpureza + "',pesoCompra='" + pesoCompra + "',valorKilo='" + valorKilo + "',valorTotal='" + valorTotal + "' WHERE idTiquete='" + idTiquete + "'");

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
        Liqui.lblNumLiquidacion.setText(String.valueOf(ext.getNextIndex("liquidaciones")));
        Liqui.lblNomAgricultor.setText("");
        Liqui.lblCedAgricultor.setText("");
        Liqui.lblCelAgricultor.setText("");
        Liqui.lblDirAgricultor.setText("");
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
        getImpuesto();
        cargarValorInicio();
        modeloSeleccionTiquete = new DefaultTableModel(null, columnasSeleccionTiquete) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        Liqui.tblSeleccionTiquete.setModel(modeloSeleccionTiquete);
        modeloSeleccionLiquidacion = new DefaultTableModel(null, columnasLiquidacion) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        Liqui.tblSeleccionLiquidacion.setModel(modeloSeleccionLiquidacion);
    }

    public static void mnGenerarLiquiacion() {
        if (!(LiqAprobadas instanceof LiquidacionesAprobadas)) {
            LiqAprobadas = new LiquidacionesAprobadas();
            LiqAprobadas.setVisible(true);
        } else {
            LiqAprobadas.setVisible(true);
        }
    }
    
    public static void mnReporCuotaFomento(JFrame form){
        if (!(Fomento instanceof CuotaFomento)) {
            Fomento =new CuotaFomento(form, true);
            Fomento.setVisible(true);
        } else {
            Fomento.setVisible(true);
        }
    }

    public static void salir() {
        Login = new Login();
        Login.setVisible(true);

    }

    public void encabezadoLiquidacion() {
        System.out.println("id" + idAgricultor);
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT CONCAT(personalexterno.nombres,' ',personalexterno.apellidos),personalexterno.cedula, CONCAT( telefono, ' ', telefono2, ' ', telefono3 ),direccion FROM personalexterno WHERE idPersonalExterno='" + idAgricultor + "'");
            while (rs.next()) {
                Liqui.lblNomAgricultor.setText(rs.getString(1));
                Liqui.lblCedAgricultor.setText(rs.getString(2));
                Liqui.lblCelAgricultor.setText(rs.getString(3));
                Liqui.lblDirAgricultor.setText(rs.getString(4));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
// select busqueda agricultor
//SELECT cedula,nombres,apellidos,direccion,municipios.nombre FROM personalexterno,municipios,tiquete,detalleliquidacion WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND detalleliquidacion.idliquidaciones IS NULL AND personalexterno.idPersonalExterno=tiquete.idAgricultor AND personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='agricultor' GROUP BY personalexterno.cedula
