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
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

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

    public void reporteUsuarios() {
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = "src\\reportes\\AdministracionUsuarios.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(administracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void reporteEmpleados() {
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = "src\\reportes\\AdministracionEmpleado.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(administracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
