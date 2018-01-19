/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;
import Logica.Extras.tablas;
import Interfaces.BusquedasTiquete;
import Interfaces.BusquedasTiqueteInicial;
import Interfaces.Bascula;
import Interfaces.Laboratorio_tiquete_inicial;
import Logica.Laboratorio.laboratorio_tiquete_inicial;
import Interfaces.Vehiculo;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author uriel
 */
public class busquedaLaboratorio {
    public static BusquedasTiquete BusTiquete;
    public static BusquedasTiqueteInicial BusT;
    public static Bascula Bas;
    public static Laboratorio_tiquete_inicial LabT;
   // public static bascula bas;
    public static laboratorio_tiquete_inicial labt;
    
    public static Vehiculo Vehiculo;
    public static String nomConductor,ccAgricultor;
    public static tablas tbl;
    public static DefaultTableModel modeloagr, modelCdt;
    public static String columnasAgri[] = new String[]{"id","Cedula", "Nombres", "Apellidos", "Direccion", "Municipio"};
    public static String columnas[] = new String[]{"Cedula", "Nombres", "Apellidos","Telefono", "Direccion", "Municipio"};

    public busquedaLaboratorio() {
        crearModeloAgricultor();
        
        
        //tabla_camposAgricultor();
    }

    /**
     * Busqueda Agricultor
     */
    public void cerrar(){
        
        LabT.btnBuscarAgricultor.setEnabled(true);
      
    }
    
  
    public void crearModeloAgricultor() {
        //System.out.println("2");
        modeloagr = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas(); 
        tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT idAgricultor,ccAgricultor,nombres,apellidos,municipios.nombre,direccion FROM agricultor,municipios WHERE agricultor.idMunicipio=municipios.idMunicipio");
    }

    public void tabla_camposAgricultor() {
            int rec = BusT.jTable2.getSelectedRow();
            LabT.txtAgricultor.setText((BusT.jTable2.getValueAt(rec, 3).toString()) + (" " + BusT.jTable2.getValueAt(rec, 2).toString()));
            labt.ccAgricultor = BusT.jTable2.getValueAt(rec, 0).toString();
            //System.out.println("jjj"+ccAgricultor);
                      
    }
        
    public static void desactivar_checkboxAgricultor() { // desactiva las checkbox
        BusT.chCedulaAgricultor.setSelected(false);
        BusT.chApellidosAgricultor.setSelected(false);
        BusT.chCiudadAgricultor.setSelected(false);
        
    }
    

    public void buscarAgricultor() {
        String cedula = BusT.txtBCedulaAgricultor.getText();
        String apellidos = BusT.txtBApellidosAgricultor.getText();
        String ciudad = BusT.txtBCiudadAgricultor.getText();

        modeloagr = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (BusT.chCedulaAgricultor.isSelected() == true && BusT.chApellidosAgricultor.isSelected() == true && BusT.chCiudadAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chCedulaAgricultor.isSelected() == true && BusT.chApellidosAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chCedulaAgricultor.isSelected() == true && BusT.chCiudadAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chApellidosAgricultor.isSelected() == true && BusT.chCiudadAgricultor.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chCedulaAgricultor.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chApellidosAgricultor.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusT.chCiudadAgricultor.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(BusT.jTable2, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        desactivar_checkboxAgricultor();
    }

    /**
     * Busqueda Conductor
     */
    
}
