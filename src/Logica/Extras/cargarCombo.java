/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;

/**
 *
 * @author uriel
 */
public class cargarCombo {

    public static Conexion Con;
    public static Statement st, st1, st2, st3, st4, st5, st6, st7, st8;
    public static ResultSet rs, rs1, rs2, rs3, rs4, rs5, rs6, rs7, rs8;
    public static String idDepartamento;// tener encuenta  

    public boolean CargarDepa(JComboBox combo) {
        try {
            Con = new Conexion();
            Con.Conectar();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("select nombre from departamentos");
            while (rs.next()) {

                Object objeto = new Object();
                objeto = rs.getObject(1);
                // System.out.println(rs.getString(1));
                combo.addItem(objeto);
            }
            combo.setSelectedItem("Tolima");
            Con.Desconectar();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean cargarMunicipio(String depar, JComboBox combo) {

        try {
            Con = new Conexion();
            st1 = Con.conexion.createStatement();
            rs1 = st1.executeQuery("SELECT nombre FROM municipios WHERE idDepartamento='" + depar + "' ");
            combo.removeAllItems();
            while (rs1.next()) {
                combo.addItem(rs1.getString(1));

            }
            if (depar.equals("29")) {
                combo.setSelectedItem("Espinal");
            }
            Con.Desconectar();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cargos(JComboBox combo) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT nombre FROM cargo");

            combo.removeAllItems();

            while (rs.next()) {
                combo.addItem(rs.getString(1));
                System.out.println(rs.getString(1));
            }
            Con.Desconectar();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void privilegios(JComboBox combo) {
        try {
            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            rs2 = st2.executeQuery("SELECT nombre FROM privilegios");
            combo.removeAllItems();

            while (rs2.next()) {
                combo.addItem(rs2.getString(1));
                //System.out.println(rs.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void usuarios(JComboBox combo) {
        try {
            Con = new Conexion();
            st3 = Con.conexion.createStatement();
            rs3 = st3.executeQuery("SELECT user FROM usuario");
            combo.removeAllItems();

            while (rs3.next()) {
                combo.addItem(rs3.getString(1));
                //System.out.println(rs.getString(1));
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void marcas(JComboBox combo) {
        try {
            Con = new Conexion();
            st4 = Con.conexion.createStatement();
            rs4 = st4.executeQuery("SELECT marca FROM marca");
            combo.removeAllItems();
            while (rs4.next()) {
                combo.addItem(rs4.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lote(JComboBox combo) {
        try {
            Con = new Conexion();
            st5 = Con.conexion.createStatement();
            rs5 = st5.executeQuery("SELECT nombre FROM lote");
            combo.removeAllItems();
            while (rs5.next()) {
                combo.addItem(rs5.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tipoArroz(JComboBox combo) {
        try {
            Con = new Conexion();
            st6 = Con.conexion.createStatement();
            rs6 = st6.executeQuery("SELECT CONCAT(tipodearroz.nombre,' - ',variedad.nombre) FROM tipodearroz,variedad WHERE tipodearroz.idVariedad=variedad.idVariedad");
            combo.removeAllItems();
            while (rs6.next()) {
                combo.addItem(rs6.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void variedad(JComboBox combo) {
        try {
            Con = new Conexion();
            st7 = Con.conexion.createStatement();
            rs7 = st7.executeQuery("SELECT nombre FROM variedad");
            combo.removeAllItems();
            while (rs7.next()) {
                combo.addItem(rs7.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void silos(JComboBox combo) {
        try {
            Con = new Conexion();
            st7 = Con.conexion.createStatement();
            rs7 = st7.executeQuery("SELECT silos.numero FROM silos");
            combo.removeAllItems();
            while (rs7.next()) {
                combo.addItem(rs7.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cargarBaterias(JComboBox combo) {
        try {
            Con = new Conexion();
            st8 = Con.conexion.createStatement();
            rs8 = st8.executeQuery("SELECT nombre from bateria");
            combo.removeAllItems();
            while (rs8.next()) {
                Object objeto = new Object();
                objeto = rs8.getObject(1);
                combo.addItem(objeto);
            }
            combo.setSelectedItem("A");
            Con.Desconectar();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cargarSilos(String bateria, JComboBox combo) {

        try {
            Con = new Conexion();
            st1 = Con.conexion.createStatement();
            rs1 = st1.executeQuery("SELECT silos.numero from silos,secadora,bateria where bateria.idBateria='" + bateria + "' and silos.idSecadora=secadora.idSecadora AND secadora.idBateria=bateria.idBateria ");
            combo.removeAllItems();
            while (rs1.next()) {
                combo.addItem(rs1.getString(1));

            }
            if (bateria.equals("1")) {
                combo.setSelectedItem("1");
            }
            Con.Desconectar();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void cargarCuentas(JComboBox combo){
        try {
            Con = new Conexion();
            st7 = Con.conexion.createStatement();
            rs7 = st7.executeQuery("SELECT cuentas.nombre FROM cuentas");
            combo.removeAllItems();
            while (rs7.next()) {
                combo.addItem(rs7.getString(1));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
