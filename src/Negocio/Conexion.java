/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author uriel
 */


public class Conexion {
    
    public static Connection conexion;
    public static Connection conexionInformation;
    public Conexion(){
        Conectar();
    }
    
    public void Conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();

<<<<<<< Updated upstream
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/admiarroz","root","molino");
=======
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/admiarroz","root","root");
>>>>>>> Stashed changes
            //JOptionPane.showMessageDialog(null, "Conexion Exitosa");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
  
    public void Desconectar(){
        try{
            if(conexion != null){
                conexion.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
      
    /*public void ConectarInformation(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexionInformation = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/information_schema","root","");
            //JOptionPane.showMessageDialog(null, "Conexion Exitosa");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void DesconectarInformation(){
        try{
            if(conexionInformation != null){
                conexionInformation.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    
    
    
}
