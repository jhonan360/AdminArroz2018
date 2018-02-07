/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Gerencia;

import static Logica.Administracion.administracion.Login;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Interfaces.Login;
import Interfaces.GerenteApruebaLiquidaciones;

public class gerencia {
    
    public static GerenteApruebaLiquidaciones GApruebaL;
    public static void salir(){
        Login=new Login();
        Login.setVisible(true);
        
     }
    
     public static void liqPorAprobar() {
        if (!(GApruebaL instanceof GerenteApruebaLiquidaciones)) {
            GApruebaL = new GerenteApruebaLiquidaciones();
            GApruebaL.setVisible(true);
        } else {
            GApruebaL.setVisible(true);
        }
    }
  
}
