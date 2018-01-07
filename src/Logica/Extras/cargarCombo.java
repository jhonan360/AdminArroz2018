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
    public static Statement st, st1,st2,st3;
    public static ResultSet rs, rs1,rs2,rs3;
    public static String idDepartamento;// tener encuenta  
    
    public boolean CargarDepa(JComboBox combo){
        try{
            Con = new Conexion();
            Con.Conectar();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("select nombre from departamentos");
            while(rs.next()){
                
                Object objeto = new Object();
                objeto = rs.getObject(1);
                // System.out.println(rs.getString(1));
                combo.addItem(objeto);
            }
            combo.setSelectedItem("Tolima");
            Con.Desconectar();
            return true;
        }
        catch(Exception ex){
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
            if (depar.equals("29")){
                combo.setSelectedItem("Espinal");    
            }
            Con.Desconectar();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void cargos(JComboBox combo){
        try{
            Con = new Conexion();
             st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT nombre FROM cargo");

            combo.removeAllItems();
            
            while (rs.next()) {
             combo.addItem(rs.getString(1));
                System.out.println(rs.getString(1));
            }
            Con.Desconectar();
            
        }catch (Exception e){
        e.printStackTrace();
    }
        
    }
    
    public void privilegios(JComboBox combo){
        try{
            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            rs2 = st2.executeQuery("SELECT nombre FROM privilegios");
            combo.removeAllItems();
            
            while(rs2.next()){
                combo.addItem(rs2.getString(1));
                //System.out.println(rs.getString(1));
            }
            Con.Desconectar();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void usuarios(JComboBox combo){
        try {
            Con = new Conexion();
            st3 = Con.conexion.createStatement();
            rs3=st3.executeQuery("SELECT user FROM usuario");
            combo.removeAllItems();
            while(rs3.next()){
                combo.addItem(rs3.getString(1));
                //System.out.println(rs.getString(1));
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
