/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

import Interfaces.Usuarios;
import Logica.Extras.cargarCombo;
import Logica.Extras.tablas;
import Logica.Extras.encriptar;
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
    
    public static tablas tbl;
    public static Conexion Con;
    public static encriptar encrip;
    public static String columnas[] = new String[]{"N", "Cedula", "Nombres", "Apellidos"};
    public static String columnasUsu[] = new String[]{"Usuario", "Contrasena", "Privilegio", "Estado"};
    public static Usuarios Usu;
    public static Statement st;
    public static ResultSet rs, rs1, rs2;
    public static String idEmpleado, cedula, usuario, contrasena, contraencript, privilegio, estado;

    public usuarios() {
        crearModelo();
        tbl = new tablas();
    }

    public void crearModelo() {
        modelemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
       
        tbl = new tablas();
        tbl.llenarTabla(Usu.jTable1, modelemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado ");
        
        crearModeloUsu("0");
        desactivar_checkbox();
    }

    public void crearModeloUsu(String idempleado) {

        modelusu = new DefaultTableModel(null, columnasUsu) {
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };

        tbl.llenarTabla(Usu.jTable2, modelusu, columnasUsu.length, "SELECT usuario.user,usuario.contrasena,privilegios.nombre,usuario.estado FROM usuario,privilegios WHERE idEmpleado='" + idempleado + "' AND usuario.idPrivilegios=privilegios.idPrivilegios");

    }

    public void tablas_campos() {
        int rec = Usu.jTable1.getSelectedRow();
        Usu.lbliIdEmpleado.setText(Usu.jTable1.getValueAt(rec, 0).toString());
        Usu.txtCedula.setText(Usu.jTable1.getValueAt(rec, 1).toString());
        Usu.txtNombre.setText(Usu.jTable1.getValueAt(rec, 2).toString());
        Usu.txtApellidos.setText(Usu.jTable1.getValueAt(rec, 3).toString());
        crearModeloUsu(Usu.jTable1.getValueAt(rec, 0).toString());
    }

    public void tabla_usuario_campos() {
        int rec = Usu.jTable2.getSelectedRow();
        Usu.txtUsuario.setText(Usu.jTable2.getValueAt(rec, 0).toString());
        Usu.txtContrasena.setText(Usu.jTable2.getValueAt(rec, 1).toString());
        Usu.cmbPrivilegio.setSelectedItem(Usu.jTable2.getValueAt(rec, 2).toString());
        Usu.cmbEstado.setSelectedItem(Usu.jTable2.getValueAt(rec, 3).toString());
        tablas_campos();
    }

    public void limpiar_campos() {
        Usu.txtCedula.setText("");
        Usu.txtNombre.setText("");
        Usu.txtApellidos.setText("");
        Usu.txtUsuario.setText("");
        Usu.txtContrasena.setText("");
        Usu.cmbPrivilegio.setSelectedIndex(0);
        Usu.cmbEstado.setSelectedIndex(0);
    }

    public void crear_usuario() {
        idEmpleado = Usu.lbliIdEmpleado.getText();
        cedula = Usu.txtCedula.getText();
        usuario = Usu.txtUsuario.getText();
        char[] Pass = Usu.txtContrasena.getPassword();
        contrasena = new String(Pass);
        privilegio = String.valueOf(Usu.cmbPrivilegio.getSelectedIndex() + 1);
        estado = Usu.cmbEstado.getSelectedItem().toString();
        if (!cedula.equals("") && !usuario.equals("") && !contrasena.equals("")) {
            // System.out.println("contraseña = "+contrasena);
            try {
                contraencript = encrip.main(contrasena);
                //   System.out.println("contra"+hope);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //(System.out.println("sss"+hope );

            insertar_usuario(idEmpleado, usuario, contraencript, privilegio, estado);

        } else {
            JOptionPane.showMessageDialog(null, "alguno de los campos se encuentra vacio");
        }
    }

    public void insertar_usuario(String idEmpleado, String usuario, String contrasena, String privilegio, String estado) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            int conusu = 0;
            rs2 = st.executeQuery("SELECT COUNT(usuario.user) FROM usuario WHERE usuario.user='" + usuario + "'");
            while (rs2.next()) {
                conusu = ((Number) rs2.getObject(1)).intValue();
            }
            if (conusu < 1) {
                int contador = 0;
                rs1 = st.executeQuery("SELECT count(privilegios.nombre) from usuario,privilegios WHERE idEmpleado ='" + idEmpleado + "' AND usuario.idPrivilegios='" + privilegio + "'  AND usuario.idPrivilegios=privilegios.idPrivilegios");
                while (rs1.next()) {
                    contador = ((Number) rs1.getObject(1)).intValue();
                    contraencript = "";
                }
                if (contador == 0) {
                    st.executeUpdate("INSERT INTO usuario (user,contrasena,idEmpleado,idPrivilegios,estado) VALUES('" + usuario + "','" + contrasena + "','" + idEmpleado + "','" + privilegio + "','" + estado + "')");
                    JOptionPane.showMessageDialog(null, "El usuario a sido ingresado");
                    limpiar_campos();
                    crearModeloUsu(idEmpleado);
                } else {
                    JOptionPane.showMessageDialog(null, "El empleado ya cuenta con un usuario de este tipo");
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
        idEmpleado = Usu.lbliIdEmpleado.getText();
        cedula = Usu.txtCedula.getText();
        usuario = Usu.txtUsuario.getText();
        char[] Pass = Usu.txtContrasena.getPassword();
        contrasena = new String(Pass);
        privilegio = String.valueOf(Usu.cmbPrivilegio.getSelectedIndex() + 1);
        estado = Usu.cmbEstado.getSelectedItem().toString();
        if (!cedula.equals("") && !usuario.equals("") && !contrasena.equals("")) {
            int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion del usuario", "Confirmacion", JOptionPane.CANCEL_OPTION);
            if (aceptar == JOptionPane.YES_OPTION) {
                try {
                    contraencript = encrip.main(contrasena);
                    //   System.out.println("contra"+hope);
                } catch (Exception e) {
                }
                actualizar_usuario(idEmpleado, usuario, contraencript, privilegio, estado);
                contraencript = "";
                crearModeloUsu(idEmpleado);
                limpiar_campos();
            } else {
                JOptionPane.showMessageDialog(null, "los cambios seran descartados");
            }

        } else {
            JOptionPane.showMessageDialog(null, "alguno de los campos se encuentra vacio");
        }
    }

    public void actualizar_usuario(String idEmpleado, String usuario, String contrasena, String privilegio, String estado) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE usuario SET contrasena='" + contrasena + "',idPrivilegios='" + privilegio + "',estado='" + estado + "' WHERE usuario.idEmpleado='" + idEmpleado + "' AND usuario.user='" + usuario + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscar() {
        String cedula = Usu.txtBCedula.getText();
        String nombres = Usu.txtBNombre.getText();
        String apellidos = Usu.txtBApellido.getText();

        modelusu = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (Usu.chCedula.isSelected() == true && Usu.chNombres.isSelected() == true && Usu.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(Usu.jTable1, modelusu, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado WHERE ccEmpleado LIKE '%" + cedula + "%' AND nombres LIKE '%" + nombres + "%' AND apellidos LIKE '%" + apellidos + "%'");

            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chCedula.isSelected() == true && Usu.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(Usu.jTable1, modelusu, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado WHERE ccEmpleado LIKE '%" + cedula + "%' AND apellidos LIKE '%" + apellidos + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chCedula.isSelected() == true && Usu.chNombres.isSelected() == true) {
            if (!cedula.equals("") && !nombres.equals("")) {
                tbl.llenarTabla(Usu.jTable1, modelusu, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado WHERE ccEmpleado LIKE '%" + cedula + "%' AND nombres LIKE '%" + nombres + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chApellidos.isSelected() == true && Usu.chNombres.isSelected() == true) {
            if (!apellidos.equals("") && !nombres.equals("")) {
                tbl.llenarTabla(Usu.jTable1, modelusu, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado WHERE nombres LIKE '%" + nombres + "%' AND apellidos LIKE '%" + apellidos + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Usu.jTable1, modelusu, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado WHERE ccEmpleado LIKE '%" + cedula + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(Usu.jTable1, modelusu, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado WHERE apellidos LIKE '%" + apellidos + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Usu.chNombres.isSelected() == true) {
            if (!nombres.equals("")) {
                tbl.llenarTabla(Usu.jTable1, modelusu, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos FROM empleado WHERE nombres LIKE '%" + nombres + "%'");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        crearModeloUsu("0");
    }

    public void desactivar_checkbox() {
        Usu.chCedula.setSelected(false);
        Usu.chNombres.setSelected(false);
        Usu.chApellidos.setSelected(false);
    }
}
