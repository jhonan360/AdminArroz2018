/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Interfaces.BusquedasTiqueteInicial;
import Interfaces.LiquidacionesAprobadas;
import Interfaces.VerTiquetePrincipal;
import Interfaces.VerTiqueteVarios;
import Negocio.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lizeth
 */
public class logicaReportes {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static VerTiquetePrincipal VerTiqPrincipal;
    public static VerTiqueteVarios VerTiqVarios;
    public static LiquidacionesAprobadas LiqAprobadas;
    public static BusquedasTiqueteInicial BusT;
    String paths = System.getProperty("user.dir");
    /**
     * BASCULA*
     */
    public void reporteBasculaTiqPrincipal() {
        int row = VerTiqPrincipal.tblVerTiqPrincipal.getSelectedRow();
        if (row != -1) {
            String idTiquete = VerTiqPrincipal.tblVerTiqPrincipal.getValueAt(row, 0).toString();
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_tiquete", idTiquete);//colocar parametros

            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                String path =paths+"\\src\\reportes\\BasculaTiquePrincipal.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    public void reporteBasculaTiqVarios() {
        int row = VerTiqVarios.tblVerTiqVarios.getSelectedRow();
        if (row != -1) {
            String idTiquete = VerTiqVarios.tblVerTiqVarios.getValueAt(row, 0).toString();
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_tiquete", idTiquete);//colocar parametros

            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                String path =paths+"\\src\\reportes\\BasculaTiqueVarios.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    /**
     * LABORATORIO*
     */
    public void reportLaboratorioTiquete() {
        int row = BusT.tblLaboratorio.getSelectedRow();
        if (row != -1) {
            String idTiquete = BusT.tblLaboratorio.getValueAt(row, 0).toString();
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_tiquete", idTiquete);//colocar parametros

            try {
                Con = new Conexion();
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                JasperReport reporte2 = null;
                String path = paths+"\\src\\reportes\\LaboratorioTiqLab.jasper";//aqui se encuentra el archivo del reporte
                //String path2 = "src\\reportes\\BasculaTiqueVarios_subreport1.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                //reporte2 = (JasperReport) JRLoader.loadObjectFromFile(path2);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                //JasperPrint jprint2 = JasperFillManager.fillReport(reporte2, parametro, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    /**
     * CONTADURIA*
     */
    public void reporteCuotaFomento() {
        JOptionPane.showMessageDialog(null, "EN PROCESOOO");
    }

    public void reporteGerenteLiquidacion() {
        int row = LiqAprobadas.tblLiquidaciones.getSelectedRow();

        if (row != -1) {
            String idLiquidacion = LiqAprobadas.tblLiquidaciones.getValueAt(row, 0).toString();
            String nomContador = "";
            Map parametro = new HashMap();//mapeo de parametros
            parametro.put("id_liquidacion", idLiquidacion);//colocar parametros

            try {
                Con = new Conexion();
                st = Con.conexion.createStatement();
                rs = st.executeQuery("SELECT CONCAT(empleado.nombres,(' '),empleado.apellidos) FROM liquidaciones,empleado,usuario WHERE liquidaciones.user=usuario.user AND usuario.user=empleado.user AND liquidaciones.idLiquidaciones='" + idLiquidacion + "'");

                while (rs.next()) {
                    nomContador = (rs.getString(1));
                    System.out.println(nomContador);
                }
                Map parametro2 = new HashMap();//mapeo de parametros
                parametro2.put("nom_contador", new String("liz"));//colocar parametros
                Connection c = Con.ConectarReport();

                JasperReport reporte = null;
                String path = paths+"\\src\\reportes\\GerenteLiquidacionesAprobadas.jasper";//aqui se encuentra el archivo del reporte
                reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
                JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, c);//enviamos parametros
                JasperPrint jprint2 = JasperFillManager.fillReport(reporte, parametro2, c);//enviamos parametros
                JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
                view.setVisible(true);//mostrar visible el reporte
                Con.Desconectar();
            } catch (JRException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un tiquete para generarlo");
        }
    }

    /**
     * GERENTE*
     */
    public void reporteAgriculores() {
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths+"\\src\\reportes\\GerenteAgricultores.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * INVENTARIO*
     */
    public void reporteMateriaPrimaTrillada() {
        JOptionPane.showMessageDialog(null, "en proceso");
    }

    /**
     * ADMINISTRACION*
     */
    public void reporteUsuarios() {
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths+"\\src\\reportes\\AdministracionUsuarios.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reporteEmpleados() {
        try {
            Con = new Conexion();
            Connection c = Con.ConectarReport();
            JasperReport reporte = null;
            String path = paths+"\\src\\reportes\\AdministracionEmpleado.jasper";//aqui se encuentra el archivo del reporte
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);//igualamos la variable reporte y enviamos el path para cargar el reporte
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, c);//enviamos parametros
            JasperViewer view = new JasperViewer(jprint, false);//vista del reporte
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Cerrar reporte
            view.setVisible(true);//mostrar visible el reporte
        } catch (JRException ex) {
            Logger.getLogger(logicaReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
