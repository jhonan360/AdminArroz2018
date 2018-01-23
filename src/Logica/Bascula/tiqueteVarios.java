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
    public static ResultSet rs, rsplaca;
    public static Statement st;
    public static Conexion Con;
    public tablas tbl;
    public static DefaultTableModel modelTiqVarios;
    public static String columnas[] = new String[]{"N", "Cantidad", "Descripcion"};
    public static String idTiqVarios, cantidad, descripcion;

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

    public static void numeroTiquete() {
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

    public void buscarPlaca() {
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
    }

    public static void abrirBusquedasTiquete(int num, String tiquete) {

        BusTiquete = new BusquedasTiquete(tiquete);
        BusTiquete.setVisible(true);

        switch (num) {
            case 2:
                BusTiquete.panel.setEnabledAt(0, false);
                //BusTiquete.panel.remove(0);
                BusTiquete.panel.setEnabledAt(1, true);
                TiqVarios.btnBuscarConductor.setEnabled(false);
                TiqVarios.btnBuscarPlaca.setEnabled(false);
                BusTiquete.panel.setSelectedIndex(1);
                break;
        }
    }

    public void crearEntradas() {//Metodo para crear un nuevo conductor 
        cantidad = TiqVarios.txtCantidad.getText();
        descripcion = TiqVarios.txtDescripcion.getText();

        //Valida que ningun campo este vacio
        if (!cantidad.equals("") && !descripcion.equals("")) {
            //insertar(cantidad, descripcion);//Llamado al metodo insertar
            modelTiqVarios.addRow(new Object[]{"1", cantidad, descripcion});
            limpiarRegistros();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void eliminarEntradas() {
        modelTiqVarios.removeRow(0);
    }

    public void limpiarRegistros() {
        TiqVarios.txtCantidad.setText("");
        TiqVarios.txtDescripcion.setText("");
    }

    public void tabla_campos() {
        int rec = TiqVarios.tblEntradas.getSelectedRow();
        idTiqVarios = TiqVarios.tblEntradas.getValueAt(rec, 0).toString();
        TiqVarios.txtDescripcion.setText(TiqVarios.tblEntradas.getValueAt(rec, 1).toString());
        TiqVarios.txtCantidad.setText(TiqVarios.tblEntradas.getValueAt(rec, 2).toString());
    }
}
