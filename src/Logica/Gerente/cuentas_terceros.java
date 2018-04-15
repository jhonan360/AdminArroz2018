/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * AND open the template in the editor.
 */
package Logica.Gerente;

import Logica.Bascula.*;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.login;
import Interfaces.Cuentas_Terceros;

import Logica.Extras.log;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import Logica.Extras.login;
import java.util.Date;

public class cuentas_terceros {

    //Variables a utilizar
    public static Conexion Con;
    public static ResultSet rs, rseli;
    public static Statement st;
    public static Cuentas_Terceros Cuentas_Terceros;
    public static extras ext;
    public static Date fecha;
    public DefaultTableModel modeloagr;
    public DefaultTableModel modeloagrTel;
    public String columnas[] = new String[]{"N°", "Cedula", "Nombres", "Apellidos", "Municipio"};
    public String headerColumnas[] = new String[]{"30", "90", "125", "125", "default"};
    public String camposColumnas[] = new String[]{"center", "left", "left", "left", "left"};
    public tablas tbl;
    public String idAgricultor, cedula, nombres,direccion,telefono;
   
    //public static log logs;
    public static login login;
   
    // final static Logger logger = Logger.getLogger(agricultor.class); 

    public cuentas_terceros() {//constructor de la clase
        //   PropertyConfigurator.configure("log4j.properties");
        fecha = new Date();

        ext = new extras();
        // logs = new log();
        crearModelo();
      
    }


    public void crearModelo() {// crea los modelos de las tablas
        modeloagr = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl = new tablas();
        tbl.llenarTabla(Cuentas_Terceros.jTable1, modeloagr, columnas.length, "SELECT cuentas.idCuenta,cuentas.nit,cuentas.nombre,cuentas.telefono,cuentas.direccion FROM cuentas");
        tbl.alinearHeaderTable(Cuentas_Terceros.jTable1, headerColumnas);
        tbl.alinearCamposTable(Cuentas_Terceros.jTable1, camposColumnas);
        tbl.rowNumberTabel(Cuentas_Terceros.jTable1);
    }

    public void crearCuenta() {//crear el agricultor se ejecuta cuando se da clic el boton crear
        //Asignación de variables
        cedula = Cuentas_Terceros.txtCedula.getText();
        nombres = Cuentas_Terceros.txtNombres.getText();
       
        direccion = Cuentas_Terceros.txtDireccion.getText();
        telefono = Cuentas_Terceros.txtTelefono1.getText();
        

        if (!cedula.equals("") && !nombres.equals("") && !direccion.equals("") && !telefono.equals("")) {
            if (!ext.validar("x", cedula)) {
                
                
                    insertar(cedula, nombres,direccion,telefono);
                    limpiar_registros();
                    crearModelo();
                
            } else {
                JOptionPane.showMessageDialog(null, "Esta persona ya se encuentra registrada");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    //Recibe los datos del agricultor para su creación
    public void insertar(String cedula, String nombres,String direccion,String telefono) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("Insert INTO cuentas (idCuenta,nit,nombre,telefono,direccion) VALUES (0,'" + cedula + "','" + nombres + "','" + telefono + "','" + direccion +"')");

            //logger.info("Insert INTO personalexterno (idPersonalExterno,cedula,nombres,apellidos,direccion,telefono,telefono2,telefono3,idMunicipio,tipo) VALUES (0,'" + cedula + "','" + nombres + "','" + apellidos + "','" + direccion + "','" + telefono + "','" + telefono2 + "','" + telefono3 + "','" + ciudad + "','agricultor')+ fecha " +fecha);
            ext.logs("INSERT", "Insert INTO cuentas (idCuenta,nit,nombre,telefono,direccion) VALUES (0,'" + cedula + "','" + nombres + "','" + telefono + "','" + direccion + "')");
            JOptionPane.showMessageDialog(null, "Cuenta nueva registrada");
            //logs.logAgricultor("i", login.enviarUsuario(), cedula, nombres, apellidos, direccion, Integer.parseInt(ciudad));
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Limpia las cajas de texto y cambia el combo box departamento al tolima
    public void limpiar_registros() {
        Cuentas_Terceros.txtCedula.setText("");
        Cuentas_Terceros.txtNombres.setText("");
        
        Cuentas_Terceros.txtDireccion.setText("");
        Cuentas_Terceros.txtTelefono1.setText("");
       
    }

    public void tabla_campos() { // rellenan campos cuando se selecciona una fila en la tabla 
        int rec = Cuentas_Terceros.jTable1.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla

        

        idAgricultor = Cuentas_Terceros.jTable1.getValueAt(rec, 0).toString();
        Cuentas_Terceros.txtCedula.setText(Cuentas_Terceros.jTable1.getValueAt(rec, 1).toString());
        Cuentas_Terceros.txtNombres.setText(Cuentas_Terceros.jTable1.getValueAt(rec, 2).toString());
       
        
        Cuentas_Terceros.txtDireccion.setText(Cuentas_Terceros.jTable1.getValueAt(rec, 4).toString());
        Cuentas_Terceros.txtTelefono1.setText(Cuentas_Terceros.jTable1.getValueAt(rec, 3).toString());
       
    }

    // metodo llamado cuando se da clic en el boton modificar. Realiza verificaciones y llama el metodo actualizar
    public void modificar() {
        cedula = Cuentas_Terceros.txtCedula.getText();
        nombres = Cuentas_Terceros.txtNombres.getText();
       
        direccion = Cuentas_Terceros.txtDireccion.getText();
        telefono = Cuentas_Terceros.txtTelefono1.getText();
        
        int row = Cuentas_Terceros.jTable1.getSelectedRow(); // Devuelve la fila de la seleccion en la tabla

        if (!cedula.equals("") && !nombres.equals("") && !direccion.equals("") && !telefono.equals("")) {

           
            if (!ext.validar("x", cedula)) {
                JOptionPane.showMessageDialog(null, "La cuenta que intenta modificar no se encuantra registrada en el sistema");
                        } else {
                int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion de la cuenta", "Confirmacion", JOptionPane.CANCEL_OPTION);
                if (aceptar == JOptionPane.YES_OPTION) {
                   
                        actualizar(cedula, nombres,direccion,telefono);
                        tabla_campos(); // se llama el metodo para que se pueda ver los cambios que se realizaron
                        limpiar_registros();
                        crearModelo();
                        JOptionPane.showMessageDialog(null, "Los cambios han sido aplicados");
                        //tabla_campos(); // se llama el metodo para que se pueda ver los cambios que se realizaron
                        Cuentas_Terceros.jTable1.changeSelection(row, 1, false, false); // cambia la selección del la tabla
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Los cambios han sido descartados");
                    limpiar_registros();
                    crearModelo();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos debe quedar vacio");
        }
    }

   
    // recibe los datos para actualizar el agricultor
    public void actualizar(String cedula, String nombres,String direccion,String telefono) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            //logs.logAgricultor("a", login.enviarUsuario(), cedula, nombres, apellidos, direccion, Integer.parseInt(ciudad));
            st.executeUpdate("UPDATE cuentas SET nombre='" + nombres + "',telefono='" + telefono + "',direccion='" + direccion + "' WHERE cuentas.nit='" + cedula + "'");
            ext.logs("UPDATE","UPDATE cuentas SET nombre='" + nombres + "',telefono='" + telefono + "',direccion='" + direccion + "' WHERE cuentas.nit='" + cedula + "'");
            JOptionPane.showMessageDialog(null, "La cuenta fue modificada");
            Con.Desconectar();
            limpiar_registros();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivar_checkbox() { // desactiva las checkbox
        Cuentas_Terceros.chCedula.setSelected(false);
        
        Cuentas_Terceros.chNombres.setSelected(false);
    }

    // metodo para busqueda con diferentes criterios se agrupa por ccAgricultor 
    
    public void buscar() { // ATENCION tengo dudas con % telefono% no sale ningun resultado. si sale con  telefono%
        String cedula = Cuentas_Terceros.txtBCedula.getText();
        String nombres = Cuentas_Terceros.txtBNombres.getText();
       
        modeloagr = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        
     if (Cuentas_Terceros.chCedula.isSelected() == true && Cuentas_Terceros.chNombres.isSelected() == true) {
            if (!cedula.equals("") && !nombres.equals("")) {
                tbl.llenarTabla(Cuentas_Terceros.jTable1, modeloagr, columnas.length, "SELECT idCuenta,nit,nombre,telefono,direccion FROM cuentas WHERE cuentas.nit LIKE '%" + cedula + "%' AND cuentas.nombre LIKE '%" + nombres + "%'");
                tbl.alinearHeaderTable(Cuentas_Terceros.jTable1, headerColumnas);
                tbl.alinearCamposTable(Cuentas_Terceros.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cuentas_Terceros.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Cuentas_Terceros.jTable1, modeloagr, columnas.length, "SELECT idCuenta,nit,nombre,telefono,direccion FROM cuentas WHERE cuentas.nit LIKE '%" + cedula + "%'");
                tbl.alinearHeaderTable(Cuentas_Terceros.jTable1, headerColumnas);
                tbl.alinearCamposTable(Cuentas_Terceros.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cuentas_Terceros.chNombres.isSelected() == true) {
            if (!nombres.equals("")) {
                tbl.llenarTabla(Cuentas_Terceros.jTable1, modeloagr, columnas.length, "SELECT idCuenta,nit,nombre,telefono,direccion FROM cuentas WHERE cuentas.nombre LIKE '%" + nombres + "%'");
                tbl.alinearHeaderTable(Cuentas_Terceros.jTable1, headerColumnas);
                tbl.alinearCamposTable(Cuentas_Terceros.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();
        tbl.rowNumberTabel(Cuentas_Terceros.jTable1);
    }
    
}
