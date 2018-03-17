/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * AND open the template in the editor.
 */
package Logica.Bascula;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Interfaces.Lote;
import Logica.Extras.log;
import Logica.Extras.login;

public class lote {

    //Variables a utilizar
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static Lote Lote;
    public static extras ext;
    public DefaultTableModel modelolote;
    public static String columnas[] = new String[]{"N°", "Lote", "Descripción", "Departamento", "Ciudad"};
    public static String headerColumnas[] = new String[]{"30", "150", "600", "150", "150"};
    public static String camposColumnas[] = new String[]{"center", "left", "left", "left", "left"};
    public tablas tbl;
    public String nombre, descripcion, idLote, ciudad, idDepartamento;
    public static log logs;
    public static login login;
    public static bascula bas;

    public lote() {
        ext = new extras();
        logs = new log();
        crearModelo();

    }

    public void crearModelo() {// crea los modelos de las tablas
        modelolote = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT idLote,lote.nombre,descripcion,departamentos.nombre,municipios.Nombre FROM lote,municipios,departamentos WHERE idLote=idLote AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento");
        tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
        tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
    }

    //se encarga de limpiar los campos de texto de la clase Lote y restablecer los combo box
    public void limpiar_registros() {
        Lote.txtNombre.setText("");
        Lote.txtDescripcion.setText("");
        Lote.cmbDepartamento.setSelectedIndex(28);
    }

    public void tabla_campos() {// rellenan campos cuando se selecciona una fila en la tabla 
        int rec = Lote.jTable1.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla
        idLote = Lote.jTable1.getValueAt(rec, 0).toString();
        Lote.txtNombre.setText(Lote.jTable1.getValueAt(rec, 1).toString());
        Lote.txtDescripcion.setText(Lote.jTable1.getValueAt(rec, 2).toString());
        Lote.cmbDepartamento.setSelectedItem(ext.getDepartamento("z", idLote));// devuelve y asigna el departamento en el combo box
        Lote.cmbMunicipio.setSelectedItem(Lote.jTable1.getValueAt(rec, 4).toString());

    }

    public void crearLote() {
        nombre = Lote.txtNombre.getText();//trae la informacion de los campos de texto de la clase Lote
        descripcion = Lote.txtDescripcion.getText();//trae la informacion de los campos de texto de la clase Lote
        ciudad = Lote.cmbMunicipio.getSelectedItem().toString();
        if (!nombre.equals("") && !descripcion.equals("")) {
            if (!ext.validar("z", nombre)) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Lote.cmbDepartamento.getSelectedIndex() + 1)); // devuelve y asigna el ID del municipio
                insertar(nombre, descripcion, ciudad);
                limpiar_registros();
                crearModelo();
            } else {
                JOptionPane.showMessageDialog(null, "Este lote ya se encuentra registrado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ningun campo puede estar vacio");
        }
    }

    public void insertar(String nombre, String descripcion, String ciudad) {//Metodo encargado de crear el nuevo tipo de arroz con su Descripcion
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            //ACA FUE
            //logs.logLote("i", login.enviarUsuario(), ext.getNextIndex("lote"), nombre, descripcion,Integer.parseInt(ciudad));
            st.executeUpdate("INSERT INTO lote (idLote,nombre,descripcion,idMunicipio)VALUES(0,'" + nombre + "','" + descripcion + "','" + ciudad + "')");//inserta el nombre, descripcion y el id del municipio que se le envio en el metodo crear tipo luego de validarse en el metodo validar
            JOptionPane.showMessageDialog(null, "lote arrocero creado registrado");//mensaje de verificacion de creacion
            bas = new bascula();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();//captura los errores generados en la consulta
        }

    }

    public void modificar() {
        nombre = Lote.txtNombre.getText();
        descripcion = Lote.txtDescripcion.getText();
        ciudad = Lote.cmbMunicipio.getSelectedItem().toString();
        int row = Lote.jTable1.getSelectedRow(); // Devuelve la fila de la seleccion en la tabla
        if (!nombre.equals("") && !descripcion.equals("") && !ciudad.equals("")) {
            int opcion = JOptionPane.showConfirmDialog(null, "Desea realmente modificar el lote ", "Guardar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Lote.cmbDepartamento.getSelectedIndex() + 1));
                actualizar(nombre, descripcion, ciudad);
                limpiar_registros();
                crearModelo();
                Lote.jTable1.changeSelection(row, 1, false, false); // cambia la selección del la tabla
                tabla_campos(); // se llama el metodo para que se pueda ver los cambios que se realizaron
            } else {
                JOptionPane.showMessageDialog(null, "Lo datos seran descartados");
                limpiar_registros();
                crearModelo();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void actualizar(String nombre, String descripcion, String ciudad) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            System.out.println(nombre);
            System.out.println(descripcion);
            System.out.println(idLote);
            //logs.logLote("a", login.enviarUsuario(), Integer.parseInt(idLote), nombre, descripcion,Integer.parseInt(ciudad));
            st.executeUpdate("UPDATE lote SET nombre='" + nombre + "',descripcion='" + descripcion + "',idMunicipio='" + ciudad + "' WHERE lote.idLote='" + idLote + "'");
            JOptionPane.showMessageDialog(null, "La informacion fue modificada");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscar() { // ATENCION tengo dudas con % telefono% no sale ningun resultado. si sale con  telefono%
        String nombre = Lote.txtBNombre.getText();
        String departamento = Lote.txtBDepartamento.getText();
        String ciudad = Lote.txtBCiudad.getText();
        modelolote = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (Lote.chNombre.isSelected() == true && Lote.chDepartamento.isSelected() == true && Lote.chCiudad.isSelected() == true) {
            if (!nombre.equals("") && !departamento.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT lote.idLote,lote.nombre,lote.descripcion,departamentos.nombre,municipios.Nombre FROM lote,departamentos,municipios WHERE lote.nombre LIKE '%" + nombre + "%' AND departamentos.nombre LIKE '%" + departamento + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY iLote");
                tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
                tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Lote.chNombre.isSelected() == true && Lote.chDepartamento.isSelected()) {
            if (!nombre.equals("") && !departamento.equals("")) {
                tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT lote.idLote,lote.nombre,lote.descripcion,departamentos.nombre,municipios.Nombre FROM lote,departamentos,municipios WHERE lote.nombre LIKE '%" + nombre + "%' AND departamentos.nombre LIKE '%" + departamento + "%'AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idLote");
                tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
                tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Lote.chNombre.isSelected() == true && Lote.chCiudad.isSelected()) {
            if (!nombre.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT lote.idLote,lote.nombre,lote.descripcion,departamentos.nombre,municipios.Nombre FROM lote,departamentos,municipios WHERE lote.nombre LIKE '%" + nombre + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idLote");
                tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
                tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Lote.chDepartamento.isSelected() == true && Lote.chCiudad.isSelected()) {
            if (!departamento.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT lote.idLote,lote.nombre,lote.descripcion,departamentos.nombre,municipios.Nombre FROM lote,departamentos,municipios WHERE departamentos.nombre LIKE '%" + departamento + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idLote");
                tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
                tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Lote.chNombre.isSelected() == true) {
            if (!nombre.equals("")) {
                tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT lote.idLote,lote.nombre,lote.descripcion,departamentos.nombre,municipios.Nombre FROM lote,departamentos,municipios WHERE lote.nombre LIKE '%" + nombre + "%' AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idLote");
                tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
                tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Lote.chDepartamento.isSelected() == true) {
            if (!departamento.equals("")) {
                tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT lote.idLote,lote.nombre,lote.descripcion,departamentos.nombre,municipios.Nombre FROM lote,departamentos,municipios WHERE departamentos.nombre LIKE '%" + departamento + "%' AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idLote");
                tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
                tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Lote.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(Lote.jTable1, modelolote, columnas.length, "SELECT lote.idLote,lote.nombre,lote.descripcion,departamentos.nombre,municipios.Nombre FROM lote,departamentos,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND lote.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idLote");
                tbl.alinearHeaderTable(Lote.jTable1, headerColumnas);
                tbl.alinearCamposTable(Lote.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
        //desactivar_checkbox();

    }

    public void desactivar_checkbox() { // desactiva las checkbox
        Lote.chNombre.setSelected(false);
        Lote.chDepartamento.setSelected(false);
        Lote.chCiudad.setSelected(false);
    }
}
