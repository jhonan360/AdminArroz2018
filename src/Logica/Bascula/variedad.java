/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;
import Logica.Extras.tablas;
import Interfaces.Variedad;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Negocio.Conexion;
import Interfaces.Tipo_Arroz;
/**
 *
 * @author jhonansmith
 */
public class variedad {
    public static Variedad Variedad;
    public static Tipo_Arroz Tipo_Arroz;
    public static Conexion Con;
    public static DefaultTableModel modeloTabla;
    public String columnas[] = new String[]{"N°", "Variedad"};
    public String headerColumnas[] = new String[]{"30", "500"};
    public String camposColumnas[] = new String[]{"center", "left"};
    public static ResultSet rs;
    public static Statement st;
    public static tablas tbl;
    public static String idVariedad,nombre;
     public variedad() {
        tbl = new tablas();
        crearModelo();
    }
     
     public void crearModelo() {
        modeloTabla = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };
        tbl.llenarTabla(Variedad.tblVariedad, modeloTabla, columnas.length, "SELECT idVariedad,nombre FROM variedad");
        tbl.alinearHeaderTable(Variedad.tblVariedad, headerColumnas);
        tbl.alinearCamposTable(Variedad.tblVariedad, camposColumnas);
     }
     
     public void crear_variedad() {
        nombre = Variedad.txtNombre.getText();
        if (!nombre.equals("")) {
            if (!validar(nombre)) {
                insertar(nombre);
                Tipo_Arroz.cargar.variedad(Tipo_Arroz.cmbVariedad);
                crearModelo();
                limpiar_campos();
                
            } else {
                JOptionPane.showMessageDialog(null, "La variedad que intenta crear ya se encuentra registrado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo nombre no dee quedar vacio");
        }
    }
     
     public boolean validar(String nombre) {
        String resultado;
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT nombre FROM variedad WHERE nombre='" + nombre + "'");
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
         public void insertar(String nombre) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO variedad (idVariedad, nombre) VALUES (0,'" + nombre + "')");
            JOptionPane.showMessageDialog(null, "La variedad ha sido ingresada");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         public void modificar() {
        nombre = Variedad.txtNombre.getText();
        if (!nombre.equals("")) {
            actualizar(nombre);
            crearModelo();
            limpiar_campos();
        } else {
            JOptionPane.showMessageDialog(null, "El campo nombre no debe quedar vacio");
        }

    }

    public void actualizar(String nombre) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE variedad SET nombre ='" + nombre + "'WHERE idTipoDeArroz = '" + idVariedad + "'");
            JOptionPane.showMessageDialog(null, "EL tipo de arroz ha sido modificado ");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar_campos() {
        Variedad.txtNombre.setText("");
    }

    public void tablas_campos() {
        int rec = Variedad.tblVariedad.getSelectedRow();
        idVariedad = Variedad.tblVariedad.getValueAt(rec, 0).toString();
        Variedad.txtNombre.setText(Variedad.tblVariedad.getValueAt(rec, 1).toString());
    }
}
