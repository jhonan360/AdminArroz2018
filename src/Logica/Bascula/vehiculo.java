/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Negocio.Conexion;
import Interfaces.Vehiculo;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.log;
import Logica.Extras.login;
/**
 *
 * @author Lizeth
 */
public class vehiculo {
    public static Conexion Con;
    public static ResultSet rs,rsc;
    public static Statement st;
    public static Vehiculo Vhc;
    public static extras ext;
    public tablas tbl;
    public static String placa, color, modelo, marca, idVehiculo;
    public static DefaultTableModel modelVhc;
    public static String columnas[] = new String[]{"N","Placa","Marca","Modelo","Color"};
    public static int row;
    //public static log logs;
    public static login login;
    
    public vehiculo(){
        ext = new extras();
        //logs = new log();
        crearModelo();
    }
    
    public void crearModelo(){
        modelVhc = new DefaultTableModel(null, columnas) {  
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
        };
        tbl = new tablas();
        tbl.llenarTabla(Vhc.jTable1,modelVhc,columnas.length,"SELECT idVehiculo,placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.idMarca=marca.idMarca");
    }
    
    public void crearVehiculo() {
        placa = Vhc.txtPlaca.getText();
        color = Vhc.txtColor.getText();
        modelo = Vhc.txtModelo.getText();
        marca = Vhc.cmbMarca.getSelectedItem().toString();
        
        //Valida que ningun campo este vacio
        if (!placa.equals("") && !color.equals("") && !modelo.equals("") && !marca.equals("")) {
            
            //Llamado al metodo validar enviando parametro placa
            if (!ext.validar("v",placa)) {
                marca=String.valueOf(Vhc.cmbMarca.getSelectedIndex()+1);
                insertar(placa, color, modelo, marca);//Llamado al metodo insertar
                crearModelo();
                limpiarRegistros();
            } else {
                JOptionPane.showMessageDialog(null, "Este vehiculo ya se encuentra registrado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }
    
    /**public static boolean validar(String placa) {
        String res;
        try {
            Con= new Conexion();
            st = Con.conexion.createStatement();
            rsc = st.executeQuery("SELECT placa FROM vehiculo WHERE placa = '" + placa + "'");
            while (rsc.next()) {
                res = rsc.getObject(1) + "";
                if (rsc.getString(1) == null) {
                    Con.Desconectar();
                    return false;
                }
                if (placa.equals(res)) {
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
        return false;
    }**/
    
    public void insertar(String placa, String color, String modelo, String marca) {
        try {
            Con =new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO vehiculo(idVehiculo,idMarca,modelo,placa,color) VALUES (0,'" + marca + "','" + modelo + "','" + placa + "','" + color +"')");
            JOptionPane.showMessageDialog(null, "Vehiculo registrado");
            //logs.logVehiculo("i", login.enviarUsuario(), placa, color, modelo, marca);
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void modificarVehiculo() {
        placa = Vhc.txtPlaca.getText();
        color = Vhc.txtColor.getText();
        modelo = Vhc.txtModelo.getText();
        marca = Vhc.cmbMarca.getSelectedItem().toString();

        //Valida que ningun campo este vacio
        if (!placa.equals("") && !color.equals("") && !modelo.equals("") && !marca.equals("")) {
            //Llamado al metodo validar enviando parametro cedula
            
            if (!ext.validar("v",placa)) {
                JOptionPane.showMessageDialog(null, "La informacion del vehiculo que intenta modificar no se encuentra en el sistema");
            } else {
                //Lamado al metodo actualizar enviando parametros a modificar
                marca=String.valueOf(Vhc.cmbMarca.getSelectedIndex()+1);
                actualizar(placa,color,modelo,marca);
                tabla_campos();
                crearModelo();
                limpiarRegistros();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }
    
    public void actualizar(String placa, String color, String modelo, String marca) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            //logs.logVehiculo("a", login.enviarUsuario(), placa, color, modelo, marca);
            st.executeUpdate("UPDATE vehiculo SET idMarca='" + marca + "',modelo='" + modelo +"',placa='" + placa + "',color='" + color + "' WHERE idVehiculo='" + idVehiculo + "'");
            JOptionPane.showMessageDialog(null, "El vehiculo fue modificado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void limpiarRegistros() {
        Vhc.txtPlaca.setText("");
        Vhc.txtColor.setText("");
        Vhc.txtModelo.setText("");
        Vhc.cmbMarca.setSelectedIndex(0);
    }
    
    public void tabla_campos() {
        int rec = Vhc.jTable1.getSelectedRow();
     
        idVehiculo=Vhc.jTable1.getValueAt(rec,0).toString();
        String marca=Vhc.jTable1.getValueAt(rec,1).toString();
        Vhc.cmbMarca.setSelectedItem(marca);
        Vhc.txtModelo.setText(Vhc.jTable1.getValueAt(rec, 2).toString());
        Vhc.txtPlaca.setText(Vhc.jTable1.getValueAt(rec, 3).toString());
        Vhc.txtColor.setText(Vhc.jTable1.getValueAt(rec, 4).toString());
        
    }   
        
    public void desactivar_checkbox(){
        Vhc.chPlaca.setSelected(false);
        Vhc.chModelo.setSelected(false);
        Vhc.chMarca.setSelected(false);
    }
    
    public void buscar() {
        String placa = Vhc.txtBPlaca.getText();
        String modelo = Vhc.txtBModelo.getText();
        String marca= Vhc.txtBMarca.getText();
        
        modelVhc = new DefaultTableModel(null, columnas) {
                    public boolean isCellEditable(int fila, int columna) {
                        return false;
                    }
                };
                tbl = new tablas();

                
        if (Vhc.chPlaca.isSelected() == true && Vhc.chModelo.isSelected() == true && Vhc.chMarca.isSelected() == true ) {
            if (!placa.equals("") && !modelo.equals("") && !marca.equals("") ) {
                tbl.llenarTabla(Vhc.jTable1, modelVhc, columnas.length, "SELECT idVehiculo,marca.marca,modelo,placa,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%' and vehiculo.modelo like '%" + modelo  + "%' and marca.marca like '%" + marca + "%' and vehiculo.idMarca=marca.idMarca");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }  else if (Vhc.chPlaca.isSelected() == true && Vhc.chModelo.isSelected() == true ) {
            if (!placa.equals("") && !modelo.equals("")) {
               tbl.llenarTabla(Vhc.jTable1, modelVhc, columnas.length, "SELECT idVehiculo,marca.marca,modelo,placa,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%' and vehiculo.modelo like '%" + modelo  + "%'and vehiculo.idMarca=marca.idMarca");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Vhc.chPlaca.isSelected() == true && Vhc.chMarca.isSelected() == true ) {
            if (!placa.equals("") && !marca.equals("")) {
               tbl.llenarTabla(Vhc.jTable1, modelVhc, columnas.length, "SELECT idVehiculo,marca.marca,modelo,placa,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%' and marca.marca like '%" + marca + "%'and vehiculo.idMarca=marca.idMarca");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Vhc.chModelo.isSelected() == true && Vhc.chMarca.isSelected() == true) {
            if (!modelo.equals("") && !marca.equals("") ) {
                tbl.llenarTabla(Vhc.jTable1, modelVhc, columnas.length, "SELECT idVehiculo,marca.marca,modelo,placa,color FROM vehiculo,marca WHERE vehiculo.modelo like '%" + modelo  + "%' and marca.marca like '%" + marca + "%'and vehiculo.idMarca=marca.idMarca");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }  else if (Vhc.chPlaca.isSelected() == true) {
             if (!placa.equals("")) {
                tbl.llenarTabla(Vhc.jTable1, modelVhc, columnas.length, "SELECT idVehiculo,marca.marca,modelo,placa,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%'and vehiculo.idMarca=marca.idMarca");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Vhc.chModelo.isSelected() == true) {
              if (!modelo.equals("")) {
                  tbl.llenarTabla(Vhc.jTable1, modelVhc, columnas.length, "SELECT idVehiculo,marca.marca,modelo,placa,color FROM vehiculo,marca WHERE vehiculo.modelo like '%" + modelo  + "%'and vehiculo.idMarca=marca.idMarca");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Vhc.chMarca.isSelected() == true) {
              if (!marca.equals("")) {
                 tbl.llenarTabla(Vhc.jTable1, modelVhc, columnas.length, "SELECT idVehiculo,marca.marca,modelo,placa,color FROM vehiculo,marca WHERE marca.marca like '%" + marca + "%'and vehiculo.idMarca=marca.idMarca");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();
        
    }

}
