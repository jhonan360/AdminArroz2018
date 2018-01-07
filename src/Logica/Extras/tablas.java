/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author uriel
 */
public class tablas {
    Conexion Con;
    public static Statement st;
    public static ResultSet rs;
    
    public void llenarTabla(JTable tabla, DefaultTableModel modelo, int columnas,String sentencia){
        try{
         Con = new Conexion();
         
   
        st = Con.conexion.createStatement();
            rs = st.executeQuery(sentencia);
            //ResultSetMetaData rsc3md = rsc3.getMetaData();
            while (rs.next()) {
                String datos[] = new String[columnas];
                for (int i = 0; i < columnas; i++) {
                    datos[i] = rs.getString(i + 1);
                }
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

