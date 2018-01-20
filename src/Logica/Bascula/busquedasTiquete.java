/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Logica.Extras.tablas;
import Interfaces.BusquedasTiquete;
import Interfaces.Bascula;
import Interfaces.Vehiculo;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andre
 */
public class busquedasTiquete {

    public static BusquedasTiquete BusTiquete;
    public static Bascula Bas;
    public static bascula bas;
    public static agricultor agri;
    public static Vehiculo Vehiculo;
    public static String nomConductor,ccAgricultor;
    public static tablas tbl;
    public static DefaultTableModel modeloagr, modelCdt;
    public static String columnasAgri[] = new String[]{"Cedula", "Nombres", "Apellidos", "Direccion", "Municipio"};
    public static String columnas[] = new String[]{"Cedula", "Nombres", "Apellidos","Telefono", "Direccion", "Municipio"};

    public busquedasTiquete() {
        crearModeloAgricultor();
        crearModeloConductor();
        
        //tabla_camposAgricultor();
    }

    /**
     * Busqueda Agricultor
     */
    public void cerrar(){
        Bas.btnBuscarConductor.setEnabled(true);
        Bas.btnEditarAgricultor.setEnabled(true);
        Bas.btnBuscarPlaca.setEnabled(true);
    }
    
  
    public void crearModeloAgricultor() {
        //System.out.println("2");
        modeloagr = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT ccAgricultor,nombres,apellidos,municipios.nombre,direccion FROM agricultor,municipios WHERE agricultor.idMunicipio=municipios.idMunicipio");
    }

    public void tabla_camposAgricultor() {
        
            
            //System.out.println("jjj"+ccAgricultor);
                      
    }
        
    public static void desactivar_checkboxAgricultor() { // desactiva las checkbox
        BusTiquete.chCedulaAgricultor.setSelected(false);
        BusTiquete.chApellidosAgricultor.setSelected(false);
        BusTiquete.chCiudadAgricultor.setSelected(false);
        
    }

    public void buscarAgricultor() {
        String cedula = BusTiquete.txtBCedulaAgricultor.getText();
        String apellidos = BusTiquete.txtBApellidosAgricultor.getText();
        String ciudad = BusTiquete.txtBCiudadAgricultor.getText();

        modeloagr = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (BusTiquete.chCedulaAgricultor.isSelected() == true && BusTiquete.chApellidosAgricultor.isSelected() == true && BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaAgricultor.isSelected() == true && BusTiquete.chApellidosAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaAgricultor.isSelected() == true && BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosAgricultor.isSelected() == true && BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaAgricultor.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosAgricultor.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
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
    public void crearModeloConductor() {
        modelCdt = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.idMunicipio=municipios.idMunicipio");
    }

    public void tabla_camposConductor() {
            //int rec = BusTiquete.jTable2.getSelectedRow();
        
            //  Bas.txtConductor.setText((BusTiquete.jTable2.getValueAt(rec, 2).toString()) + (" " + BusTiquete.jTable2.getValueAt(rec, 1).toString()));
            //Bas.txtConductor.setText((BusTiquete.jTable2.getValueAt(rec, 1).toString()));
            //bas.ccConductor = BusTiquete.jTable2.getValueAt(rec, 0).toString();
            //nomConductor =BusTiquete.jTable2.getValueAt(rec, 1).toString();
            //System.out.println("Log"+nomConductor);
            
    }

    public void desactivar_checkboxConductor() { // desactiva las checkbox
        BusTiquete.chCedulaConductor.setSelected(false);
        BusTiquete.chApellidosConductor.setSelected(false);
        BusTiquete.chCiudadConductor.setSelected(false);
    }

    public void buscarConductor() {
        String cedula = BusTiquete.txtBCedulaConductor.getText();
        String apellidos = BusTiquete.txtBApellidosConductor.getText();
        String ciudad = BusTiquete.txtBCiudadConductor.getText();

        modelCdt = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (BusTiquete.chCedulaConductor.isSelected() == true && BusTiquete.chApellidosConductor.isSelected() == true && BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.ccConductor like '%" + cedula + "%' and conductor.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaConductor.isSelected() == true && BusTiquete.chApellidosConductor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.apellidos like '%" + apellidos + "%' and conductor.ccConductor like '%" + cedula + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaConductor.isSelected() == true && BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.ccConductor like '%" + cedula + "%' and municipios.Nombre like '%" + ciudad + "%' and  and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosConductor.isSelected() == true && BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaConductor.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.ccConductor like '%" + cedula + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosConductor.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.apellidos like '%" + apellidos + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        desactivar_checkboxConductor();
    }

}
