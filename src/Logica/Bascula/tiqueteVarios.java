/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Interfaces.BusquedasTiquete;
import Negocio.Conexion;
import Interfaces.TiqueteVarios;
import static Logica.Bascula.bascula.Vehiculo;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.log;
import Logica.Extras.login;
import java.sql.SQLException;
import java.util.Calendar;

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
    public static ResultSet rs,rsconductor, rsplaca;
    public static Statement st;
    public static Conexion Con;
    public tablas tbl;
    public static DefaultTableModel modelTiqVarios;
    public static String user, fecha, destino, conductor, idConductor, placa, observacion, kilosBrutos, destare, kilosNetos;
    public static String columnas[] = new String[]{"N", "Cantidad", "Descripcion"};
    public static String idTiqVarios, cantidad, descripcion;
    public static int entradas;

    public tiqueteVarios() {
        numeroTiquete();
        fecha();
        crearModelo();
    }

    public void crearModelo() {
        modelTiqVarios = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(TiqVarios.tblEntradas, modelTiqVarios, columnas.length, "SELECT idEntradas,cantidad,descripcion FROM entradas");
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
                    System.out.println(resul);
                    TiqVarios.lblNumeroTiquete.setText(resul);
                }
            }
            Con.Desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fecha() {
        Calendar c;
        c = Calendar.getInstance();
        int d = c.get(Calendar.DATE), m = 1 + (c.get(Calendar.MONTH)), a = c.get(Calendar.YEAR);
        TiqVarios.txtFecha.setText(a + "/" + m + "/" + d);
    }

    /**public void buscarPlaca() {
        String bus = JOptionPane.showInputDialog("Ingrese la placa del vehiculo");
        boolean bandera = false;
        if (bus != null) {
            try {
                Con.Conectar();
                st = Con.conexion.createStatement();
                rsplaca = st.executeQuery("SELECT placa FROM vehiculo WHERE placa='" + bus + "'");

                while (rsplaca.next()) {
                    if (rsplaca.getString(1) == null) {
                    } else {
                        String resultado = rsplaca.getString(1);
                        // System.out.println(resultado);
                        bandera = true;
                        TiqVarios.txtPlaca.setText(resultado);

                    }
                }

                if (bandera == false) {
                    int valor = JOptionPane.showConfirmDialog(null, "La placa no pertenece a un vehiculo registrado" + "\n               Desea registrarlo ahora?", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (valor == JOptionPane.YES_OPTION) {
                        bas.abrirVehiculo();
                        Vehiculo.txtPlaca.setText(bus);
                    } else {
                        JOptionPane.showMessageDialog(null, "Se ha cancelado la operación.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

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
            int row = TiqVarios.tblEntradas.getRowCount();
            modelTiqVarios.addRow(new Object[]{row + 1, cantidad, descripcion});
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
                TiqVarios.txtPesoInicial.setText(String.valueOf(inicial));
                break;
            case 2:
                if (!TiqVarios.txtPesoInicial.getText().equals("")) {
                    TiqVarios.txtPesoFinal.setText("");
                    TiqVarios.txtPesoFinal.setText(String.valueOf(fina));
                    double ini = Double.parseDouble(TiqVarios.txtPesoInicial.getText());
                    TiqVarios.txtPesoNeto.setText(String.valueOf(ini - fina));
                } else {
                    JOptionPane.showMessageDialog(null, "Sin peso bruto");
                }
                break;
        }
    }

    public void tabla_campos() {
        int rec = TiqVarios.tblEntradas.getSelectedRow();
        idTiqVarios = TiqVarios.tblEntradas.getValueAt(rec, 0).toString();
        TiqVarios.txtDescripcion.setText(TiqVarios.tblEntradas.getValueAt(rec, 1).toString());
        TiqVarios.txtCantidad.setText(TiqVarios.tblEntradas.getValueAt(rec, 2).toString());
    }

    public void crearTiqueteVarios() {
        conductor = TiqVarios.txtConductor.getText();
        user = login.enviarUsuario();
        placa = TiqVarios.txtPlaca.getText();
        fecha = TiqVarios.txtFecha.getText();
        destino = TiqVarios.txtDestino.getText();
        observacion = TiqVarios.txtObservaciones.getText();
        kilosBrutos = TiqVarios.txtPesoInicial.getText();
        destare = TiqVarios.txtPesoFinal.getText();
        kilosNetos = TiqVarios.txtPesoNeto.getText();
        //entradas = TiqVarios.tblEntradas.getRowCount();
        System.out.println("fecha " + fecha);
        System.out.println("dest " + destino);
        System.out.println("conduc: " + conductor);
        System.out.println("placa " + placa);
        System.out.println("observacion " + observacion);
        System.out.println("kB " + kilosBrutos);
        System.out.println("destare " + destare);
        System.out.println("kN " + kilosNetos);
        
        if (kilosBrutos.equals("")){
            kilosBrutos = "0.00";
        }
        if (destare.equals("")){
            destare = "0.00";
        }
        if (kilosNetos.equals("")){
            kilosNetos = "0.00";
        }
        
        if (!fecha.equals("") && !user.equals("") &&!conductor.equals("") && !placa.equals("") && !kilosBrutos.equals("")) {
            conductor = obtenerId(conductor,1);
            placa = obtenerId(placa,2);
            
            insertar(conductor,user,placa,fecha, destino,observacion, kilosBrutos, destare, kilosNetos);//Llamado al metodo insertar
            limpiarRegistrosEntradas();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

     public static String obtenerId(String nombre, int num) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();

            switch (num) {
                case 1:
                    rsconductor = st.executeQuery("SELECT idPersonalExterno FROM personalExterno WHERE CONCAT(personalexterno.nombres,' ',personalexterno.apellidos) ='" + nombre + "'  ");
                    while (rsconductor.next()) {
                        return rsconductor.getString(1);
                    }
                    break;
                    
                case 2:
                    rsplaca = st.executeQuery("SELECT idVehiculo FROM vehiculo WHERE placa ='" + nombre + "'  ");
                    while (rsplaca.next()) {
                        return rsplaca.getString(1);
                    }
                    break;
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void insertar(String conductor,String user,String placa,String fecha,String destino,String observacion,String kilosBrutos,String destare,String kilosNetos) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO tiqueteVarios (idTiqueteVarios,idConductor,user,idVehiculo,fecha,destino,observacion,kilosBrutos,destare,kilosNetos) VALUES (0,'" + conductor + "','" + user + "','" + placa + "','" + fecha + "','" + destino + "','" + observacion + "','"+kilosBrutos+"','"+destare+"','"+kilosNetos+"')");
            JOptionPane.showMessageDialog(null, "Conductor registrado");
//          logs.logConductor("i", login.enviarUsuario(), cedula, nombres, apellidos, telefono, direccion, Integer.parseInt(ciudad));
            limpiarRegistros();
            numeroTiquete();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void limpiarRegistros() {
        TiqVarios.lblNumeroTiquete.setText("");
        TiqVarios.txtConductor.setText("");
        TiqVarios.txtPlaca.setText("");
        TiqVarios.txtFecha.setText("");
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
