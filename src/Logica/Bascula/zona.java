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
import Interfaces.Zona;
import Logica.Extras.log;
import Logica.Extras.login;

public class zona {

    //Variables a utilizar
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static Zona Zona;
    public static extras ext;
    public DefaultTableModel modelozona;
    public static String columnas[] = new String[]{"ID", "Nombre", "Descripción", "Departamento", "Ciudad"};
    public tablas tbl;
    public String nombre, descripcion, idZona, ciudad, idDepartamento;
    public static log logs;
    public static login login;
    public static bascula bas;

    public zona() {
        ext = new extras();
        logs = new log();
        crearModelo();

    }

    public void crearModelo() {// crea los modelos de las tablas
        modelozona = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT idZona,zona.nombre,descripcion,departamentos.nombre,municipios.Nombre FROM zona,municipios,departamentos WHERE idZona=idZona AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento");
    }

    //se encarga de limpiar los campos de texto de la clase Zona y restablecer los combo box
    public void limpiar_registros() {
        Zona.txtNombre.setText("");
        Zona.txtDescripcion.setText("");
        Zona.cmbDepartamento.setSelectedIndex(28);
    }

    public void tabla_campos() {// rellenan campos cuando se selecciona una fila en la tabla 
        int rec = Zona.jTable1.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla
        idZona = Zona.jTable1.getValueAt(rec, 0).toString();
        Zona.txtNombre.setText(Zona.jTable1.getValueAt(rec, 1).toString());
        Zona.txtDescripcion.setText(Zona.jTable1.getValueAt(rec, 2).toString());
        Zona.cmbDepartamento.setSelectedItem(ext.getDepartamento("z", idZona));// devuelve y asigna el departamento en el combo box
        Zona.cmbMunicipio.setSelectedItem(Zona.jTable1.getValueAt(rec, 4).toString());

    }

    public void crearZona() {
        nombre = Zona.txtNombre.getText();//trae la informacion de los campos de texto de la clase Zona
        descripcion = Zona.txtDescripcion.getText();//trae la informacion de los campos de texto de la clase Zona
        ciudad = Zona.cmbMunicipio.getSelectedItem().toString();
        if (!nombre.equals("") && !descripcion.equals("")) {
            if (!ext.validar("z", nombre)) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Zona.cmbDepartamento.getSelectedIndex() + 1)); // devuelve y asigna el ID del municipio
                insertar(nombre, descripcion, ciudad);
                limpiar_registros();
                crearModelo();
            } else {
                JOptionPane.showMessageDialog(null, "Esta zona ya se encuentra registrada");
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
            //logs.logZona("i", login.enviarUsuario(), ext.getNextIndex("zona"), nombre, descripcion,Integer.parseInt(ciudad));
            st.executeUpdate("INSERT INTO zona (idZona,nombre,descripcion,idMunicipio)VALUES(0,'" + nombre + "','" + descripcion + "','" + ciudad + "')");//inserta el nombre, descripcion y el id del municipio que se le envio en el metodo crear tipo luego de validarse en el metodo validar
            JOptionPane.showMessageDialog(null, "Zona arrocera creada registrado");//mensaje de verificacion de creacion
            bas = new bascula();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();//captura los errores generados en la consulta
        }

    }

    public void modificar() {
        nombre = Zona.txtNombre.getText();
        descripcion = Zona.txtDescripcion.getText();
        ciudad = Zona.cmbMunicipio.getSelectedItem().toString();
        int row = Zona.jTable1.getSelectedRow(); // Devuelve la fila de la seleccion en la tabla
        if (!nombre.equals("") && !descripcion.equals("") && !ciudad.equals("")) {
            int opcion = JOptionPane.showConfirmDialog(null, "Desea realmente modificar la zona ", "Guardar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Zona.cmbDepartamento.getSelectedIndex() + 1));
                actualizar(nombre, descripcion, ciudad);
                limpiar_registros();
                crearModelo();
                Zona.jTable1.changeSelection(row, 1, false, false); // cambia la selección del la tabla
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
            System.out.println(idZona);
            //logs.logZona("a", login.enviarUsuario(), Integer.parseInt(idZona), nombre, descripcion,Integer.parseInt(ciudad));
            st.executeUpdate("UPDATE zona SET nombre='" + nombre + "',descripcion='" + descripcion + "',idMunicipio='" + ciudad + "' WHERE zona.idZona='" + idZona + "'");
            JOptionPane.showMessageDialog(null, "La informacion fue modificada");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscar() { // ATENCION tengo dudas con % telefono% no sale ningun resultado. si sale con  telefono%
        String nombre = Zona.txtBNombre.getText();
        String departamento = Zona.txtBDepartamento.getText();
        String ciudad = Zona.txtBCiudad.getText();
        modelozona = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (Zona.chNombre.isSelected() == true && Zona.chDepartamento.isSelected() == true && Zona.chCiudad.isSelected() == true) {
            if (!nombre.equals("") && !departamento.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT zona.idZona,zona.nombre,zona.descripcion,departamentos.nombre,municipios.Nombre FROM zona,departamentos,municipios WHERE zona.nombre LIKE '%" + nombre + "%' AND departamentos.nombre LIKE '%" + departamento + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idZona");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Zona.chNombre.isSelected() == true && Zona.chDepartamento.isSelected()) {
            if (!nombre.equals("") && !departamento.equals("")) {
                tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT zona.idZona,zona.nombre,zona.descripcion,departamentos.nombre,municipios.Nombre FROM zona,departamentos,municipios WHERE zona.nombre LIKE '%" + nombre + "%' AND departamentos.nombre LIKE '%" + departamento + "%'AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idZona");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Zona.chNombre.isSelected() == true && Zona.chCiudad.isSelected()) {
            if (!nombre.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT zona.idZona,zona.nombre,zona.descripcion,departamentos.nombre,municipios.Nombre FROM zona,departamentos,municipios WHERE zona.nombre LIKE '%" + nombre + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idZona");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Zona.chDepartamento.isSelected() == true && Zona.chCiudad.isSelected()) {
            if (!departamento.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT zona.idZona,zona.nombre,zona.descripcion,departamentos.nombre,municipios.Nombre FROM zona,departamentos,municipios WHERE departamentos.nombre LIKE '%" + departamento + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idZona");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Zona.chNombre.isSelected() == true) {
            if (!nombre.equals("")) {
                tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT zona.idZona,zona.nombre,zona.descripcion,departamentos.nombre,municipios.Nombre FROM zona,departamentos,municipios WHERE zona.nombre LIKE '%" + nombre + "%' AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idZona");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Zona.chDepartamento.isSelected() == true) {
            if (!departamento.equals("")) {
                tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT zona.idZona,zona.nombre,zona.descripcion,departamentos.nombre,municipios.Nombre FROM zona,departamentos,municipios WHERE departamentos.nombre LIKE '%" + departamento + "%' AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idZona");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Zona.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(Zona.jTable1, modelozona, columnas.length, "SELECT zona.idZona,zona.nombre,zona.descripcion,departamentos.nombre,municipios.Nombre FROM zona,departamentos,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND zona.idMunicipio=municipios.idMunicipio AND municipios.idDepartamento=departamentos.idDepartamento GROUP BY idZona");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
        desactivar_checkbox();

    }

    public void desactivar_checkbox() { // desactiva las checkbox
        Zona.chNombre.setSelected(false);
        Zona.chDepartamento.setSelected(false);
        Zona.chCiudad.setSelected(false);
    }
}
