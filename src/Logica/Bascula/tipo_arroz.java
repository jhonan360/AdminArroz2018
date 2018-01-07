/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Logica.Extras.cargarCombo;
import Logica.Extras.extras;
import Logica.Extras.tablas;
import Interfaces.Tipo_Arroz;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Negocio.Conexion;
import Logica.Extras.validaciones;

/**
 *
 * @author uriel
 */
public class tipo_arroz {

    public static cargarCombo combos;
    public static extras extras;
    public static Tipo_Arroz Tipo_Arroz;
    public static Conexion Con;
    public static DefaultTableModel modeloTabla;
    public String columnas[] = new String[]{"Tipo de arroz", "Descripcion"};
    public String idGlobal;
    public static ResultSet rs, rsid;
    public static Statement st, stvalidar, stmodificar, stid;
    public static tablas tablas;
    public static String nombre, descripcion;
    public static validaciones val;
    public static bascula bas;
    public tipo_arroz() {
        crearModelo();
    }

    public void crearModelo() {
        modeloTabla = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };

        tablas = new tablas();
        tablas.llenarTabla(Tipo_Arroz.jTable1, modeloTabla, columnas.length, "SELECT nombre,descripcion FROM tipodearroz");
    }

    public void crear_tipo_de_arroz() {
        nombre = Tipo_Arroz.txtNombre.getText();
        descripcion = Tipo_Arroz.txtDescripcion.getText();
        if (!nombre.equals("") && !descripcion.equals("")) {
            if (validar(nombre) == false) {
                insertar(nombre, descripcion);
                crearModelo();
            } else {
                JOptionPane.showMessageDialog(null, "El tipo de arroz que intenta crear ya se encuentra registrado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede quedar vacio");
        }
    }

    public boolean validar(String nombre) {
        String resultado;
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT nombre FROM tipodearroz WHERE nombre='" + nombre + "'");
            while (rs.next()) {
                resultado = rs.getObject(1) + "";
                if (rs.getString(1) == null) {
                    Con.Desconectar();
                    return false;
                }
                if (nombre.equals(resultado)) {
                    Con.Desconectar();
                    return true;
                } else {
                    Con.Desconectar();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Con.Desconectar();
        return false;
    }

    public void insertar(String nombre, String descripcion) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO tipodearroz (idTipoDeArroz,nombre,descripcion) VALUES (0,'" + nombre + "','" + descripcion + "')");
            JOptionPane.showMessageDialog(null, "El tipo de arroz ha sido ingresado");
            bas = new bascula();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar() {
        nombre = Tipo_Arroz.txtNombre.getText();
        descripcion = Tipo_Arroz.txtDescripcion.getText();
        if (!nombre.equals("") && !descripcion.equals("")) {
            actualizar(nombre, descripcion);
            crearModelo();
            limpiar_campos();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede quedar vacio");
        }

    }

    public void actualizar(String nombre, String descripcion) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE tipodearroz SET nombre='" + nombre + "',descripcion='" + descripcion + "'  WHERE idTipoDeArroz = '" + idGlobal + "' ");
            JOptionPane.showMessageDialog(null, "EL tipo de arroz ha sido modificado ");
            idGlobal = "";
            System.out.println("id" + idGlobal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar_campos() {
        Tipo_Arroz.txtNombre.setText("");
        Tipo_Arroz.txtDescripcion.setText("");
        Tipo_Arroz.txtBuscar.setText("");
        Tipo_Arroz.txtNombre.setEnabled(true);
    }

    public void tablas_campos() {
        int rec = Tipo_Arroz.jTable1.getSelectedRow();
        Tipo_Arroz.txtNombre.setText(Tipo_Arroz.jTable1.getValueAt(rec, 0).toString());
        Tipo_Arroz.txtDescripcion.setText(Tipo_Arroz.jTable1.getValueAt(rec, 1).toString());
        idGlobal = idArroz(Tipo_Arroz.jTable1.getValueAt(rec, 0).toString(), Tipo_Arroz.jTable1.getValueAt(rec, 1).toString());
        System.out.println("El idGlobal es=" + idGlobal);
        Tipo_Arroz.txtNombre.setEnabled(false);
    }

    public String idArroz(String nombre, String descripcion) {
        try {
            Con = new Conexion();
            stid = Con.conexion.createStatement();
            rsid = stid.executeQuery("SELECT idTipoDeArroz FROM tipodearroz WHERE nombre='" + nombre + "' and descripcion='" + descripcion + "'");
            while (rsid.next()) {
                return rsid.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void buscar() {
        String nombre = Tipo_Arroz.txtBuscar.getText();
        modeloTabla = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };
        tablas = new tablas();
        tablas.llenarTabla(Tipo_Arroz.jTable1, modeloTabla, columnas.length,"SELECT nombre,descripcion FROM tipodearroz WHERE nombre like '%"+nombre+"%'");
        limpiar_campos();
    }

}
