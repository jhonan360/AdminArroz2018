/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

import Interfaces.Usuarios;
import Interfaces.Empleado;
import Logica.Extras.cargarCombo;
import Logica.Extras.tablas;
import Logica.Extras.validaciones;
import Logica.Extras.StringEncrypt;
import javax.swing.table.DefaultTableModel;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author uriel
 */
public class usuarios {

    public static DefaultTableModel modelemp, modelusu;
    public static Empleado Emp;
    public static tablas tbl;
    public static validaciones vali;
    public static Conexion Con;
    public static StringEncrypt encrip;
    public static String columnas[] = new String[]{"N", "Cedula", "Nombres", "Apellidos"};
    public static String columnasUsu[] = new String[]{"Usuario", "Contrasena", "Privilegio", "Estado"};
    public static Usuarios Usu;
    public static Statement st;
    public static ResultSet rs, rs1, rs2;
    public static String usuario, contrasena, contraencript, privilegio, estado;

    public usuarios() {
        tbl = new tablas();
        vali = new validaciones();
        crearModelo();
    }

    /* public void crearModeloEmp() {
        modelemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl.llenarTabla(Usu.jTable1, modelemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado ");

//        crearModeloUsu("0");
        desactivar_checkbox();
    }*/
    public void crearModelo() {

        modelusu = new DefaultTableModel(null, columnasUsu) {
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };

        tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios");

    }

    /* public void tablas_campos() {
        int rec = Usu.jTable1.getSelectedRow();
        Usu.lbliIdEmpleado.setText(Usu.jTable1.getValueAt(rec, 0).toString());
        //crearModeloUsu(Usu.jTable1.getValueAt(rec, 0).toString());
    }*/
    public void tabla_usuario_campos() {
        try {
            int rec = Usu.jTable2.getSelectedRow();
            Usu.txtUsuario.setText(Usu.jTable2.getValueAt(rec, 0).toString());
            Usu.txtContrasena.setText(encrip.decrypt(Usu.jTable2.getValueAt(rec, 1).toString()));
            Usu.cmbPrivilegio.setSelectedItem(Usu.jTable2.getValueAt(rec, 2).toString());
            Usu.cmbEstado.setSelectedItem(Usu.jTable2.getValueAt(rec, 3).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar_campos() {
        Usu.txtUsuario.setText("");
        Usu.txtContrasena.setText("");
        Usu.cmbPrivilegio.setSelectedIndex(0);
        Usu.cmbEstado.setSelectedIndex(0);
    }

    public void crear_usuario() {
        usuario = Usu.txtUsuario.getText();
        char[] Pass = Usu.txtContrasena.getPassword();
        contrasena = new String(Pass);
        privilegio = String.valueOf(Usu.cmbPrivilegio.getSelectedIndex() + 1);
        estado = Usu.cmbEstado.getSelectedItem().toString();
        if (!usuario.equals("") && !contrasena.equals("")) {
            // System.out.println("contraseña = "+contrasena);
            if (!vali.ValidarContrasena(contrasena)) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener números y letras con minimo 7 caracteres");
            } else {
                try {
                    contraencript = encrip.encrypt(contrasena);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //(System.out.println("sss"+hope );
                insertar_usuario(usuario, contraencript, privilegio, estado);
            }
        } else {
            JOptionPane.showMessageDialog(null, "alguno de los campos se encuentra vacio");

        }
    }

    public void insertar_usuario(String usuario, String contrasena, String privilegio, String estado) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            int conusu = 0;
            rs2 = st.executeQuery("SELECT COUNT(usuario.user) FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND usuario.user='" + usuario + "'");
            while (rs2.next()) {
                conusu = ((Number) rs2.getObject(1)).intValue();
            }
            if (conusu < 1) {
                st.executeUpdate("INSERT INTO usuario (user,contrasena,idPrivilegios,estado) VALUES('" + usuario + "','" + contrasena + "','" + privilegio + "','" + estado + "')");
                limpiar_campos();
                crearModelo();
                int respuesta = JOptionPane.showConfirmDialog(null, "El usuario a sido ingresado \n ¿desea crear el empleado del usuario?", "Confirmación", JOptionPane.CANCEL_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    Usu.cerrar = true;
                    Emp = new Empleado();
                    Emp.setVisible(true);
                    Emp.txtUsuario.setText(usuario);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ye existe un usuario con este nombre");
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar_usuario() {
        usuario = Usu.txtUsuario.getText();
        char[] Pass = Usu.txtContrasena.getPassword();
        contrasena = new String(Pass);
        privilegio = String.valueOf(Usu.cmbPrivilegio.getSelectedIndex() + 1);
        estado = Usu.cmbEstado.getSelectedItem().toString();
        if (!usuario.equals("") && !contrasena.equals("")) {
            int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion del usuario", "Confirmación", JOptionPane.CANCEL_OPTION);
            if (aceptar == JOptionPane.YES_OPTION) {
                try {
                    contraencript = encrip.encrypt(contrasena);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                actualizar_usuario(usuario, contraencript, privilegio, estado);
                contraencript = "";
                crearModelo();
                limpiar_campos();
            } else {
                JOptionPane.showMessageDialog(null, "los cambios seran descartados");
            }

        } else {
            JOptionPane.showMessageDialog(null, "alguno de los campos se encuentra vacio");
        }
    }

    public void actualizar_usuario(String usuario, String contrasena, String privilegio, String estado) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE usuario SET contrasena='" + contrasena + "',idPrivilegios='" + privilegio + "',estado='" + estado + "' WHERE usuario.user='" + usuario + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscar() {
        String usuario = Usu.txtBUsuario.getText();
        String privilegio = Usu.txtBPrivilegio.getText();
        String estado = Usu.txtBEstado.getText();
        modelusu = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (Usu.chUsuario.isSelected() == true && Usu.chPrivilegio.isSelected() == true && Usu.chEstado.isSelected() == true) {
            if (!usuario.equals("") && !privilegio.equals("") && !estado.equals("")) {
                tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND user LIKE '%" + usuario + "%' AND privilegios.nombre LIKE '%" + privilegio + "%' AND estado LIKE '%" + estado + "%'");

            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chUsuario.isSelected() == true && Usu.chEstado.isSelected() == true) {
            if (!usuario.equals("") && !estado.equals("")) {
                tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND user LIKE '%" + usuario + "%' AND estado LIKE '%" + estado + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chUsuario.isSelected() == true && Usu.chPrivilegio.isSelected() == true) {
            if (!usuario.equals("") && !privilegio.equals("")) {
                tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND user LIKE '%" + usuario + "%' AND privilegios.nombre LIKE '%" + privilegio + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chEstado.isSelected() == true && Usu.chPrivilegio.isSelected() == true) {
            if (!estado.equals("") && !privilegio.equals("")) {
                tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND privilegios.nombre LIKE '%" + privilegio + "%' AND estado LIKE '%" + estado + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chUsuario.isSelected() == true) {
            if (!usuario.equals("")) {
                tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND user LIKE '%" + usuario + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chEstado.isSelected() == true) {
            if (!estado.equals("")) {
                tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND estado LIKE '%" + estado + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chPrivilegio.isSelected() == true) {
            if (!privilegio.equals("")) {
                tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT user,contrasena,privilegios.nombre,estado FROM usuario,privilegios WHERE privilegios.idPrivilegios=usuario.idPrivilegios AND privilegios.nombre LIKE '%" + privilegio + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //crearModeloUsu("0");
    }

    public void desactivar_checkbox() {
        Usu.chUsuario.setSelected(false);
        Usu.chPrivilegio.setSelected(false);
        Usu.chEstado.setSelected(false);
    }
}
