/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

import Interfaces.Administracion;
import Interfaces.Empleado;
import Interfaces.GestionBD;
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
    public static GestionBD GestionBD;

    /*   public static void abrirCargo(){
         Cargo = new Cargo();
         Cargo.setVisible(true);
    }*/
    public static void abrirEmpleado() {
        if (!(Empleado instanceof Empleado)) {
            Empleado = new Empleado();
            Empleado.setVisible(true);
        } else {
            Empleado.setVisible(true);
        }

    }

    public static void abrirUsuario() {
        if (!(Usuario instanceof Usuarios)) {
            Usuario = new Usuarios();
            Usuario.setVisible(true);
        } else {
            Usuario.setVisible(true);
        }
    }
    
    public static void abrirReportes() {
        Reportes = new Reportes();
        Reportes.setVisible(true);
    }

    public static void abrirGestionBD() {
        GestionBD = new GestionBD();
        GestionBD.setVisible(true);
    }

    public static void salir() {
        Login = new Login();
        Login.setVisible(true);

    }

    
}
