/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;
import Interfaces.Administracion;
import Interfaces.Empleado;
//import Interfaces.Cargo;
import Interfaces.Login;
import Interfaces.Usuarios;
import Interfaces.Reportes;
/**
 *
 * @author uriel
 */
public class administracion {
     public static Administracion Admin;
     public static Empleado Empleado;
     public static Login Login;
     public static Usuarios Usuario;
     public static Reportes Reportes;
  /*   public static void abrirCargo(){
         Cargo = new Cargo();
         Cargo.setVisible(true);
    }*/
   
     public static void abrirEmpleado(){
         Empleado = new Empleado();
         Empleado.setVisible(true);
    }
     public static void abrirUsuario(){
         Usuario = new Usuarios();
         Usuario.setVisible(true);
     }
     public static void salir(){
        Login=new Login();
        Login.setVisible(true);
        
     }
     public static void abrirReportes(){
         Reportes = new Reportes();
         Reportes.setVisible(true);
     }
}
