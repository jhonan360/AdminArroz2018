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
import Negocio.Conexion;
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
    public static Conexion Con;

    /*   public static void abrirCargo(){
         Cargo = new Cargo();
         Cargo.setVisible(true);
    }*/

    public static void abrirEmpleado() {
        if (Empleado != null) {
            Empleado.dispose();
            Empleado = new Empleado();
        } else {
            Empleado = new Empleado();
        }
        Empleado.setVisible(true);
    }

    public static void abrirUsuario() {
        if (Usuario != null) {
            Usuario.dispose();
            Usuario = new Usuarios();
        } else {
            Usuario = new Usuarios();
        }
        Usuario.setVisible(true);
    }

    public static void abrirReportes() {
        Reportes = new Reportes();
        Reportes.setVisible(true);
    }

    public static void abrirGestionBD() {
        if (GestionBD != null) {
            GestionBD.dispose();
            GestionBD = new GestionBD();
        } else {
            GestionBD = new GestionBD();
        }
        GestionBD.setVisible(true);
    }

    public static void salir() {
        if (Login != null) {
            Login.dispose();
            Login = new Login();
        } else {
            Login = new Login();
        }
        Login.setVisible(true);
    }   
}
