/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Interfaces.BusquedasTiquete;
import Negocio.Conexion;
import Interfaces.TiqueteVarios;
import static Interfaces.TiqueteVarios.tiqVarios;
import Logica.Extras.currencyFormat;
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
    public static String columnas[] = new String[]{"N°", "Cantidad", "Descripcion"};
    public static String headerColumnas[] = new String[]{"10", "40", "225"};
    public static String camposColumnas[] = new String[]{"center", "right", "left"};
    public static String idTiqVarios, cantidad, descripcion, idTiqueteEspera="", estadoTiquete = "", idEntradas, kl, cant;
    public static int entradas;
    public static String columSegundoPesaje[] = new String[]{"N°", "Conductor", "Kl Brutos"};
    public static String headerColumSegundoPesaje[] = new String[]{"7", "165", "60"};
    public static String camposColumSegundoPesaje[] = new String[]{"center", "left", "right"};
    public static ConexionBascula ConBascula;
    public static currencyFormat cu;

    public tiqueteVarios() {
        ConBascula=bas.ConBascula;
        ext = new extras();
        tbl = new tablas();
        cu = new currencyFormat();
        TiqVarios.lblNumeroTiquete.setText(String.valueOf(ext.getNextIndex("tiquetevarios")));
        TiqVarios.txtFecha.setText(cu.dateNotTime(ext.fecha()));
        tiquetesVariosEsperandoSegundoPesaje(true);
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
        tbl.llenarTabla(TiqVarios.tblSegundoPesaje, modeloSegundoPesaje, columSegundoPesaje.length, "SELECT tiquetevarios.idTiqueteVarios, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos), tiquetevarios.kilosBrutos FROM tiquetevarios,personalexterno WHERE tiquetevarios.destare=0.00 AND tiquetevarios.kilosNetos=0.00 AND tiquetevarios.idConductor=personalexterno.idPersonalExterno ORDER BY tiquetevarios.idTiqueteVarios ASC;");
        tbl.alinearHeaderTable(TiqVarios.tblSegundoPesaje, headerColumSegundoPesaje);
        tbl.alinearCamposTable(TiqVarios.tblSegundoPesaje, camposColumSegundoPesaje);
        formatoTiqVariosEsperandoSegundoPesaje();
    }

    public static void formatoTiqVariosEsperandoSegundoPesaje() {
        int row = TiqVarios.tblSegundoPesaje.getRowCount();
        for (int i = 0; i < row; i++) {
            kl = TiqVarios.tblSegundoPesaje.getValueAt(i, 2).toString();
            kl = cu.thousandsFormat(Double.parseDouble(kl));
            TiqVarios.tblSegundoPesaje.setValueAt(kl, i, 2);
        }
    }

    public void tablaCampos_SegundoPesaje(String SegundoPeso) {
        int rec = TiqVarios.tblSegundoPesaje.getSelectedRow();
        String idTiquete =TiqVarios.tblSegundoPesaje.getValueAt(rec, 0).toString(); 
        if(!idTiqueteEspera.equals("")){
            if(!idTiqueteEspera.equals(idTiquete)){
              int respuesta = JOptionPane.showConfirmDialog(null, "¿desea cambiar de tiquete?", "Confirmación", JOptionPane.CANCEL_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    cambiarTiq(rec, idTiquete);
                }
            }
        }else{
            cambiarTiq(rec,idTiquete);
        }
    }
    
    public void cambiarTiq(int rec,String idTiquete){
        limpiarRegistros();
        crearModelo();
        TiqVarios.btnCapturarKilosBrutos.setEnabled(false);
        TiqVarios.btnCapturarDestare.setEnabled(true);
        idTiqueteEspera = idTiquete;
        TiqVarios.lblNumeroTiquete.setText(idTiqueteEspera);
        TiqVarios.txtConductor.setText(TiqVarios.tblSegundoPesaje.getValueAt(rec, 1).toString());
        TiqVarios.txtPesoInicial.setText(TiqVarios.tblSegundoPesaje.getValueAt(rec, 2).toString());

        /*Campos Entradas deshabilitados*/
        TiqVarios.txtCantidad.setEnabled(false);
        TiqVarios.txtDescripcion.setEnabled(false);
        TiqVarios.btnAgregar.setEnabled(false);
        TiqVarios.btnEliminar.setEnabled(false);
        TiqVarios.btnModificar.setEnabled(false);
        TiqVarios.btnLimpiar.setEnabled(false);
        TiqVarios.tblEntradas.setEnabled(false);

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();

            rsTiquete = st.executeQuery("SELECT tiquetevarios.destino,vehiculo.placa,tiquetevarios.observacion,tiquetevarios.idConductor,tiquetevarios.fecha FROM tiquetevarios,vehiculo,personalexterno WHERE tiquetevarios.idTiqueteVarios='" + idTiqueteEspera + "' AND tiquetevarios.idVehiculo=vehiculo.idVehiculo AND tiquetevarios.idConductor=personalexterno.idPersonalExterno");

            while (rsTiquete.next()) {

                TiqVarios.txtDestino.setText(rsTiquete.getString(1));
                TiqVarios.txtPlaca.setText(rsTiquete.getString(2));
                TiqVarios.txtObservaciones.setText(rsTiquete.getString(3));
                idConductor = rsTiquete.getString(4);
                TiqVarios.txtFecha.setText(cu.dateNotTime(rsTiquete.getString(5)));
                estadoTiquete = "segundoPesaje";
                tbl.llenarTabla(TiqVarios.tblEntradas, modelTiqVarios, columnas.length, "SELECT idEntradas,cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteEspera + "'");
                formatoTblEntradas();
            }
            rsEntradas = st.executeQuery("SELECT cantidad,descripcion FROM entradas WHERE entradas.idTiqueteVarios='" + idTiqueteEspera + "'");
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
        if (TiqVarios.tblEntradas.getRowCount() != 0 && TiqVarios.tblEntradas.getSelectedRow() != -1) {
        } else {
            TiqVarios.btnModificar.setEnabled(false);
            TiqVarios.btnEliminar.setEnabled(false);
            TiqVarios.btnLimpiar.setEnabled(false);
        }
        modelTiqVarios = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        TiqVarios.tblEntradas.setModel(modelTiqVarios);
        tbl.alinearHeaderTable(TiqVarios.tblEntradas, headerColumnas);
        tbl.alinearCamposTable(TiqVarios.tblEntradas, camposColumnas);
        formatoTblEntradas();
    }

    public void formatoTblEntradas() {
        int row = TiqVarios.tblEntradas.getRowCount();
        for (int i = 0; i < row; i++) {
            cant = TiqVarios.tblEntradas.getValueAt(i, 1).toString();
            cant = cu.thousandsFormat(Double.parseDouble(cant));
            TiqVarios.tblEntradas.setValueAt(cant, i, 1);
            TiqVarios.tblEntradas.setValueAt(i + 1, i, 0);
        }
    }

    public static void abrirBusquedasTiquete(int num, String tiquete) {

        BusTiquete = new BusquedasTiquete(tiquete);
        BusTiquete.setVisible(true);

        switch (num) {
            case 2:
                BusTiquete.panel.setEnabledAt(0, false);
                BusTiquete.panel.setEnabledAt(1, true);
                BusTiquete.panel.setEnabledAt(2, false);
                TiqVarios.btnBuscarConductor.setEnabled(false);
                TiqVarios.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(1);
                break;

            case 3:
                BusTiquete.panel.setEnabledAt(0, false);
                BusTiquete.panel.setEnabledAt(1, false);
                BusTiquete.panel.setEnabledAt(2, true);
                TiqVarios.btnBuscarConductor.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(2);
                break;
        }
    }

    public void crearEntradas() {
        cantidad = TiqVarios.txtCantidad.getText();
        descripcion = TiqVarios.txtDescripcion.getText();

        if (!cantidad.equals("") && !descripcion.equals("")) {
            int row = TiqVarios.tblEntradas.getRowCount();
            modelTiqVarios.addRow(new Object[]{row + 1, cantidad, descripcion});
            limpiarRegistrosEntradas();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void eliminarEntradas() {
        if (TiqVarios.tblEntradas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una entrada para poder eliminarla");
        } else {
            int rec = TiqVarios.tblEntradas.getSelectedRow();
            modelTiqVarios.removeRow(rec);
            formatoTblEntradas();
            limpiarRegistrosEntradas();
            int cont = TiqVarios.tblEntradas.getRowCount();
            if (cont == 0) {
                TiqVarios.btnModificar.setEnabled(false);
                TiqVarios.btnLimpiar.setEnabled(false);
                TiqVarios.btnEliminar.setEnabled(false);
            }
        }

    }

    public static void capturarPeso(int opc) {
        double inicial = (double) Math.floor(Math.random() * (5000 - 1500 + 1) + 1500);
        double fina = (double) Math.floor(Math.random() * (1400 - 800) + 800);

        switch (opc) {
            case 1:
                TiqVarios.txtPesoInicial.setText("");
                TiqVarios.txtPesoInicial.setText(cu.thousandsFormat(Double.parseDouble(ConBascula.getPeso("0"))));
                //TiqVarios.txtPesoInicial.setText(String.valueOf(cu.thousandsFormat(inicial)));
                if (!TiqVarios.txtPesoInicial.getText().equals("")) {
                    TiqVarios.btnCapturarKilosBrutos.setEnabled(false);
                }
                break;
            case 2:
                if (!TiqVarios.txtPesoInicial.getText().equals("")) {
                    TiqVarios.txtPesoFinal.setText("");
                    //TiqVarios.txtPesoFinal.setText(String.valueOf(cu.thousandsFormat(fina)));
                    TiqVarios.txtPesoFinal.setText(cu.thousandsFormat(Double.parseDouble(ConBascula.getPeso(TiqVarios.txtPesoInicial.getText()))));
                    double ini = Double.parseDouble(cu.notThousandsFormat(TiqVarios.txtPesoInicial.getText()));
                    if (!TiqVarios.txtPesoFinal.getText().equals("")) {
                        fina = Double.parseDouble(cu.notThousandsFormat(TiqVarios.txtPesoFinal.getText()));
                        TiqVarios.txtPesoNeto.setText(String.valueOf(cu.thousandsFormat(ini - fina)));
                        TiqVarios.btnCapturarDestare.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sin peso bruto");
                }
                break;
        }
    }

    public void tabla_campos() {
        int row = TiqVarios.tblEntradas.getRowCount();
        if (row > 0) {
            if (TiqVarios.tblEntradas.isEnabled()) {
                int rec = TiqVarios.tblEntradas.getSelectedRow();
                if (rec < 0) {
                    TiqVarios.btnModificar.setEnabled(false);
                    TiqVarios.btnEliminar.setEnabled(false);
                    TiqVarios.btnLimpiar.setEnabled(false);
                    System.out.println("---1");
                } else {
                    TiqVarios.btnModificar.setEnabled(true);
                    TiqVarios.btnEliminar.setEnabled(true);
                    TiqVarios.btnLimpiar.setEnabled(true);
                    TiqVarios.txtCantidad.setText(TiqVarios.tblEntradas.getValueAt(rec, 1).toString());
                    TiqVarios.txtDescripcion.setText(TiqVarios.tblEntradas.getValueAt(rec, 2).toString());
                }
            } else {
                TiqVarios.btnModificar.setEnabled(false);
                TiqVarios.btnEliminar.setEnabled(false);
                TiqVarios.btnLimpiar.setEnabled(false);
            }
        } else {
            TiqVarios.btnModificar.setEnabled(false);
            TiqVarios.btnEliminar.setEnabled(false);
            TiqVarios.btnLimpiar.setEnabled(false);
        }
    }

    public void crearTiqueteVarios() {
        int row = TiqVarios.tblEntradas.getRowCount();
        idTiqueteVarios = TiqVarios.lblNumeroTiquete.getText();
        conductor = idConductor;
        user = login.enviarUsuario();
        placa = TiqVarios.txtPlaca.getText();
        fecha = ext.fecha();
        destino = TiqVarios.txtDestino.getText();
        observacion = TiqVarios.txtObservaciones.getText();
        kilosBrutos = cu.notThousandsFormat(TiqVarios.txtPesoInicial.getText());
        destare = cu.notThousandsFormat(TiqVarios.txtPesoFinal.getText());
        kilosNetos = cu.notThousandsFormat(TiqVarios.txtPesoNeto.getText());

        System.out.println("user " + user);
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
                tiquetesVariosEsperandoSegundoPesaje(true);
                TiqVarios.btnCapturarKilosBrutos.setEnabled(true);
                TiqVarios.btnCapturarDestare.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
            }
        } else if (!user.equals("") && !conductor.equals("") && !placa.equals("") && !kilosBrutos.equals("")) {
            if (row != 0) {
                placa = ext.getIdPlaca(placa);
                insertar(estadoTiquete, conductor, user, placa, fecha, destino, observacion, kilosBrutos, destare, kilosNetos);//Llamado al metodo insertar
                insertarEntrada(estadoTiquete);
                tiquetesVariosEsperandoSegundoPesaje(true);
                TiqVarios.btnCapturarKilosBrutos.setEnabled(true);
                TiqVarios.btnCapturarDestare.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Recuerde ingresar como minimo una entrada");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void insertar(String estadoTiquete, String conductor, String user, String placa, String fecha, String destino, String observacion, String kilosBrutos, String destare, String kilosNetos) {
        try {
            Con = new Conexion();

            if (estadoTiquete.equals("segundoPesaje")) {
                PreparedStatement ps = Con.conexion.prepareStatement("UPDATE tiqueteVarios SET idConductor='" + conductor + "', user = '" + user + "', idVehiculo = '" + placa + "', destino='" + destino + "',observacion ='" + observacion + "',kilosBrutos='" + kilosBrutos + "',destare='" + destare + "',kilosNetos='" + kilosNetos + "' WHERE idTiqueteVarios = '" + idTiqueteVarios + "'", PreparedStatement.RETURN_GENERATED_KEYS);
               
                ps.execute();
                ext.logs("UPDATE tiqueteVarios SET idConductor='" + conductor + "', user = '" + user + "', idVehiculo = '" + placa + "', destino='" + destino + "',observacion ='" + observacion + "',kilosBrutos='" + kilosBrutos + "',destare='" + destare + "',kilosNetos='" + kilosNetos + "' WHERE idTiqueteVarios = '" + idTiqueteVarios + "'");
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idTiqVarios = String.valueOf(rs.getInt(1));
                }
            } else {
                PreparedStatement ps = Con.conexion.prepareStatement("INSERT INTO tiqueteVarios (idTiqueteVarios,idConductor,user,idVehiculo,fecha,destino,observacion,kilosBrutos,destare,kilosNetos) VALUES (0,'" + conductor + "','" + user + "','" + placa + "','" + fecha + "','" + destino + "','" + observacion + "','" + kilosBrutos + "','" + destare + "','" + kilosNetos + "')", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.execute();
                ext.logs("INSERT INTO tiqueteVarios (idTiqueteVarios,idConductor,user,idVehiculo,fecha,destino,observacion,kilosBrutos,destare,kilosNetos) VALUES (0,'" + conductor + "','" + user + "','" + placa + "','" + fecha + "','" + destino + "','" + observacion + "','" + kilosBrutos + "','" + destare + "','" + kilosNetos + "')");
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
                String cantidad = TiqVarios.tblEntradas.getValueAt(i, 1).toString();
                String descripcion = TiqVarios.tblEntradas.getValueAt(i, 2).toString();

                if (estadoTiquete.equals("segundoPesaje")) {
                    st.executeUpdate("UPDATE entradas SET cantidad = '" + cantidad + "',descripcion = '" + descripcion + "' WHERE entradas.idEntradas='" + idEntradas + "' AND entradas.idTiqueteVarios='" + idTiqueteVarios + "'");
                    ext.logs("UPDATE entradas SET cantidad = '" + cantidad + "',descripcion = '" + descripcion + "' WHERE entradas.idEntradas='" + idEntradas + "' AND entradas.idTiqueteVarios='" + idTiqueteVarios + "'");
                } else {
                    st.executeUpdate("INSERT INTO entradas (idEntradas,idTiqueteVarios,cantidad,descripcion) VALUES  (0,'" + idTiqVarios + "','" + cantidad + "','" + descripcion + "')");
                    ext.logs("INSERT INTO entradas (idEntradas,idTiqueteVarios,cantidad,descripcion) VALUES  (0,'" + idTiqVarios + "','" + cantidad + "','" + descripcion + "')");
                }
            }
            JOptionPane.showMessageDialog(null, "Tiquete registrado");
            refrescar();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarEntradas() {
        int rec = TiqVarios.tblEntradas.getSelectedRow();
        cantidad = TiqVarios.txtCantidad.getText();
        descripcion = TiqVarios.txtDescripcion.getText();

        if (!cantidad.equals("") && !descripcion.equals("")) {
            TiqVarios.tblEntradas.setValueAt(cantidad, rec, 1);
            TiqVarios.tblEntradas.setValueAt(descripcion, rec, 2);
            limpiarRegistrosEntradas();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
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

    public void refrescar() {
        TiqVarios.btnModificar.setEnabled(false);
        TiqVarios.btnEliminar.setEnabled(false);
        TiqVarios.btnLimpiar.setEnabled(false);
        TiqVarios.txtFecha.setText(cu.dateNotTime(ext.fecha()));
        limpiarRegistros();
        limpiarRegistrosEntradas();
        tiquetesVariosEsperandoSegundoPesaje(true);
        crearModelo();
        TiqVarios.lblNumeroTiquete.setText(String.valueOf(ext.getNextIndex("tiquetevarios")));
        TiqVarios.btnCapturarKilosBrutos.setEnabled(true);
        TiqVarios.btnCapturarDestare.setEnabled(true);
        /*Campos Entradas habilitados*/
        TiqVarios.txtCantidad.setEnabled(true);
        TiqVarios.txtDescripcion.setEnabled(true);
        TiqVarios.btnAgregar.setEnabled(true);
        TiqVarios.tblEntradas.setEnabled(true);
    }
}
