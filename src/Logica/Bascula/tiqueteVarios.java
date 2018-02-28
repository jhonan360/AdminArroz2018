/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Interfaces.BusquedasTiquete;
import Negocio.Conexion;
import Interfaces.TiqueteVarios;
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
import java.util.Calendar;
import java.util.Date;
import Negocio.ConexionBascula;

/**
 *
 * @author Lizeth
 */
public class tiqueteVarios {

    public static TiqueteVarios TiqVarios;
    public static bascula bas;
    public static BusquedasTiquete BusTiquete;
    public static extras ext;
    public static login login;
    public static ResultSet rs, rsconductor, rsplaca, rsTiquete, rsEntradas;
    public static Statement st;
    public static Conexion Con;
    public static tablas tbl;
    public static DefaultTableModel modelTiqVarios, modeloSegundoPesaje;
    public static String idTiqueteVarios, user, fecha, destino, conductor, idConductor, placa, observacion, kilosBrutos, destare, kilosNetos;
    public static String columnas[] = new String[]{"Cantidad", "Descripcion"};
    public static String idTiqVarios, cantidad, descripcion, idTiqueteEspera, estadoTiquete = "", idEntradas;
    public static int entradas;
    public static String columSegundoPesaje[] = new String[]{"N", "Conductor", "KL Brutos"};
    public static ConexionBascula ConBascula;

    public tiqueteVarios() {
        //ConBascula = new ConexionBascula();
        ext = new extras();
        tbl = new tablas();
        numeroTiquete();
        fecha();
        crearModelo();
    }

    /**
     *
     * TIQUETES VARIOS PENDIENTES POR REGISTRAR SEGUNDO PESAJE
     */
    public static void tiquetesVariosEsperandoSegundoPesaje(boolean visible) {

        modeloSegundoPesaje = new DefaultTableModel(null, columSegundoPesaje) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(TiqVarios.tblSegundoPesaje, modeloSegundoPesaje, columSegundoPesaje.length, "SELECT tiquetevarios.idTiqueteVarios, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos), tiquetevarios.kilosBrutos FROM tiquetevarios,personalexterno WHERE tiquetevarios.destare=0.00 AND tiquetevarios.kilosNetos=0.00 AND tiquetevarios.idConductor=personalexterno.idPersonalExterno ORDER BY tiquetevarios.idTiqueteVarios DESC;");
    }

    public void tablaCampos_SegundoPesaje(String SegundoPeso) {
        //estado = false;
        int rec = TiqVarios.tblSegundoPesaje.getSelectedRow();
        TiqVarios.btnCapturarKilosBrutos.setEnabled(false);
        TiqVarios.btnCapturarDestare.setEnabled(true);
        idTiqueteEspera = TiqVarios.tblSegundoPesaje.getValueAt(rec, 0).toString();
        TiqVarios.lblNumeroTiquete.setText(idTiqueteEspera);
        TiqVarios.txtConductor.setText(TiqVarios.tblSegundoPesaje.getValueAt(rec, 1).toString());
        TiqVarios.txtPesoInicial.setText(TiqVarios.tblSegundoPesaje.getValueAt(rec, 2).toString());

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();

            rsTiquete = st.executeQuery("SELECT tiquetevarios.destino,vehiculo.placa,tiquetevarios.observacion,tiquetevarios.idConductor FROM tiquetevarios,vehiculo,personalexterno WHERE tiquetevarios.idTiqueteVarios='" + idTiqueteEspera + "' AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.idConductor=personalexterno.idPersonalExterno");

            while (rsTiquete.next()) {

                TiqVarios.txtDestino.setText(rsTiquete.getString(1));
                TiqVarios.txtPlaca.setText(rsTiquete.getString(2));
                TiqVarios.txtObservaciones.setText(rsTiquete.getString(3));
                idConductor = rsTiquete.getString(4);
                estadoTiquete = "segundoPesaje";
                tbl = new tablas();
                tbl.llenarTabla(TiqVarios.tblEntradas, modelTiqVarios, columnas.length, "SELECT cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteEspera + "'");
            }

            rsEntradas = st.executeQuery("SELECT idEntradas,cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteEspera + "'");
            while (rsEntradas.next()) {
                idEntradas = rsEntradas.getString(1);
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Tiquete Varios
     */
    public void crearModelo() {
        modelTiqVarios = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return true;
            }
        };
        TiqVarios.tblEntradas.setModel(modelTiqVarios);
        //tbl = new tablas();
        //tbl.llenarTabla(TiqVarios.tblEntradas, modelTiqVarios, columnas.length, "SELECT idEntradas,cantidad,descripcion FROM entradas");
    }

    public void numeroTiquete() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement(); //System.out.println("1"); 
            rs = st.executeQuery("SELECT idTiqueteVarios FROM tiqueteVarios ORDER BY idTiqueteVarios ASC");

            while (rs.next()) {
                if (rs.getString(1) == null) {
                } else {
                    String resultado = rs.getString(1);
                    int numero = Integer.parseInt(resultado);
                    int num2 = numero + 1;
                    String resul = Integer.toString(num2);
                    //System.out.println(resul);
                    TiqVarios.lblNumeroTiquete.setText(resul);
                }
            }
            Con.Desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String fecha() {
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        SimpleDateFormat formato2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        java.util.Date fecha = new Date();
        String fec = formato.format(fecha);
        String fec2 = formato2.format(fecha);
        TiqVarios.txtFecha.setText(fec);
        return fec2;
    }

    public static void abrirBusquedasTiquete(int num, String tiquete) {

        BusTiquete = new BusquedasTiquete(tiquete);
        BusTiquete.setVisible(true);

        switch (num) {
            case 2:
                BusTiquete.panel.setEnabledAt(0, false);
                //BusTiquete.panel.remove(0);
                BusTiquete.panel.setEnabledAt(1, true);
                BusTiquete.panel.setEnabledAt(2, false);
                TiqVarios.btnBuscarConductor.setEnabled(false);
                TiqVarios.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(1);
                break;

            case 3:
                //panel vehiculo
                BusTiquete.panel.setEnabledAt(0, false);
                BusTiquete.panel.setEnabledAt(1, false);
                BusTiquete.panel.setEnabledAt(2, true);
                TiqVarios.btnBuscarConductor.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(2);
                break;
        }
    }

    public void crearEntradas() {//Metodo para crear un nuevo conductor 
        cantidad = TiqVarios.txtCantidad.getText();
        descripcion = TiqVarios.txtDescripcion.getText();

        //Valida que ningun campo este vacio
        if (!cantidad.equals("") && !descripcion.equals("")) {
            //insertar(cantidad, descripcion);//Llamado al metodo insertar
            //int row = TiqVarios.tblEntradas.getRowCount();
            modelTiqVarios.addRow(new Object[]{cantidad, descripcion});
            limpiarRegistrosEntradas();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void eliminarEntradas() {
        int rec = TiqVarios.tblEntradas.getSelectedRow();
        modelTiqVarios.removeRow(rec);
        limpiarRegistrosEntradas();
    }

    public static void capturarPeso(int opc) {
        double inicial = (double) Math.floor(Math.random() * (5000 - 1500 + 1) + 1500);
        double fina = (double) Math.floor(Math.random() * (1400 - 800) + 800);

        switch (opc) {
            case 1:
                TiqVarios.txtPesoInicial.setText("");
                //TiqVarios.txtPesoInicial.setText(ConBascula.getPeso("0"));
                TiqVarios.txtPesoInicial.setText(String.valueOf(inicial));
                if (!TiqVarios.txtPesoInicial.getText().equals("")) {
                    TiqVarios.btnCapturarKilosBrutos.setEnabled(false);
                }
                break;
            case 2:
                if (!TiqVarios.txtPesoInicial.getText().equals("")) {
                    TiqVarios.txtPesoFinal.setText("");
                    TiqVarios.txtPesoFinal.setText(String.valueOf(fina));
                    //TiqVarios.txtPesoFinal.setText(ConBascula.getPeso(TiqVarios.txtPesoInicial.getText()));
                    double ini = Double.parseDouble(TiqVarios.txtPesoInicial.getText());
                    if (!TiqVarios.txtPesoFinal.getText().equals("")) {
                        fina = Double.parseDouble(TiqVarios.txtPesoFinal.getText());
                        TiqVarios.txtPesoNeto.setText(String.valueOf(ini - fina));
                        TiqVarios.btnCapturarDestare.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sin peso bruto");
                }
                break;
        }
    }

    public void tabla_campos() {
        int rec = TiqVarios.tblEntradas.getSelectedRow();
        TiqVarios.txtCantidad.setText(TiqVarios.tblEntradas.getValueAt(rec, 0).toString());
        TiqVarios.txtDescripcion.setText(TiqVarios.tblEntradas.getValueAt(rec, 1).toString());
    }

    public void crearTiqueteVarios() {
        idTiqueteVarios = TiqVarios.lblNumeroTiquete.getText();
        conductor = idConductor;
        user = login.enviarUsuario();
        placa = TiqVarios.txtPlaca.getText();
        fecha = fecha();
        destino = TiqVarios.txtDestino.getText();
        observacion = TiqVarios.txtObservaciones.getText();
        kilosBrutos = TiqVarios.txtPesoInicial.getText();
        destare = TiqVarios.txtPesoFinal.getText();
        kilosNetos = TiqVarios.txtPesoNeto.getText();
        //entradas = TiqVarios.tblEntradas.getRowCount();
        System.out.println("fecha " + fecha);
        System.out.println("dest " + destino);
        System.out.println("conduc: " + conductor);
        System.out.println("observacion " + observacion);
        System.out.println("kB " + kilosBrutos);
        System.out.println("destare " + destare);
        System.out.println("kN " + kilosNetos);

        if (destare.equals("")) {
            destare = "0.00";
        }
        if (kilosNetos.equals("")) {
            kilosNetos = "0.00";
        }

        if (estadoTiquete.equals("segundoPesaje")) {
            if (!user.equals("") && !conductor.equals("") && !placa.equals("") && !kilosBrutos.equals("") && !destare.equals("0.00") && !kilosNetos.equals("0.00")) {
                placa = ext.getIdPlaca(placa);
                insertar(estadoTiquete, conductor, user, placa, fecha, destino, observacion, kilosBrutos, destare, kilosNetos);//Llamado al metodo insertar
                insertarEntrada(estadoTiquete);
            } else {
                JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
            }
        } else if (!user.equals("") && !conductor.equals("") && !placa.equals("") && !kilosBrutos.equals("")) {
            placa = ext.getIdPlaca(placa);
            insertar(estadoTiquete, conductor, user, placa, fecha, destino, observacion, kilosBrutos, destare, kilosNetos);//Llamado al metodo insertar
            insertarEntrada(estadoTiquete);
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void insertar(String estadoTiquete, String conductor, String user, String placa, String fecha, String destino, String observacion, String kilosBrutos, String destare, String kilosNetos) {
        try {
            Con = new Conexion();

            if (estadoTiquete.equals("segundoPesaje")) {
                PreparedStatement ps = Con.conexion.prepareStatement("UPDATE tiqueteVarios SET idConductor='" + conductor + "', user = '" + user + "', idVehiculo = '" + placa + "', fecha = '" + fecha + "', destino='" + destino + "',observacion ='" + observacion + "',kilosBrutos='" + kilosBrutos + "',destare='" + destare + "',kilosNetos='" + kilosNetos + "' WHERE idTiqueteVarios = '" + idTiqueteVarios + "'", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.execute();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idTiqVarios = String.valueOf(rs.getInt(1));
                }
            } else {
                PreparedStatement ps = Con.conexion.prepareStatement("INSERT INTO tiqueteVarios (idTiqueteVarios,idConductor,user,idVehiculo,fecha,destino,observacion,kilosBrutos,destare,kilosNetos) VALUES (0,'" + conductor + "','" + user + "','" + placa + "','" + fecha + "','" + destino + "','" + observacion + "','" + kilosBrutos + "','" + destare + "','" + kilosNetos + "')", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.execute();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idTiqVarios = String.valueOf(rs.getInt(1));
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertarEntrada(String estadoTiquete) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < TiqVarios.tblEntradas.getRowCount(); i++) {
                String cantidad = TiqVarios.tblEntradas.getValueAt(i, 0).toString();
                String descripcion = TiqVarios.tblEntradas.getValueAt(i, 1).toString();

                if (estadoTiquete.equals("segundoPesaje")) {
                    st.executeUpdate("UPDATE entradas SET cantidad = '" + cantidad + "',descripcion = '" + descripcion + "' WHERE entradas.idEntradas='" + idEntradas + "' AND entradas.idTiqueteVarios='" + idTiqueteVarios + "'");

                } else {
                    st.executeUpdate("INSERT INTO entradas (idEntradas,idTiqueteVarios,cantidad,descripcion) VALUES  (0,'" + idTiqVarios + "','" + cantidad + "','" + descripcion + "')");
                }
            }
            JOptionPane.showMessageDialog(null, "Tiquete registrado");
            limpiarRegistros();
            limpiarRegistrosEntradas();
            numeroTiquete();
            crearModelo();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void modificarEntradas(){
        
    }

    public void limpiarRegistros() {
        TiqVarios.lblNumeroTiquete.setText("");
        TiqVarios.txtConductor.setText("");
        TiqVarios.txtPlaca.setText("");
        TiqVarios.txtDestino.setText("");
        TiqVarios.txtObservaciones.setText("");
        TiqVarios.txtPesoInicial.setText("");
        TiqVarios.txtPesoFinal.setText("");
        TiqVarios.txtPesoNeto.setText("");
    }

    public void limpiarRegistrosEntradas() {
        TiqVarios.txtCantidad.setText("");
        TiqVarios.txtDescripcion.setText("");
    }
}
