/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

/**
 *
 * @author uriel
 */
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Interfaces.Empleado;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.login;
import javax.swing.plaf.OptionPaneUI;

public class empleado {

    public static Conexion Con;
    public static ResultSet rs, rsdeparMetodo;
    public static Statement st;
    public static Empleado Emp;
    public static extras ext;
    public static login login;
    public DefaultTableModel modeloemp, modeloUsu;
    public String columnas[] = new String[]{"N°", "Usuario", "Cédula", "Nombres", "Apellidos", "Ciudad", "Dirección", "Teléfono"};
    public String headerColumnas[] = new String[]{"20", "50", "40", "90", "90", "50", "80", "50"};
    public String columnasUsu[] = new String[]{"Usuario", "Privilegio", "Estado"};
    public String headerColumnasUsu[] = new String[]{"default", "default", "default"};
    public tablas tbl;
    public int idCargo;
    public String idEmpleado, user, cedula, nombres, apellidos, ciudad, direccion, telefono, departamento;
    public String estado = "crear";

    public empleado() {
        ext = new extras();
        tbl = new tablas();
        crearModelo();
    }
    

    public void crearModelo() {
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (Emp.jTable1.getColumnModel().getColumnCount() > 0) {
            Emp.jTable1.getColumnModel().getColumn(0).setMinWidth(30);
            Emp.jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
        }
        modeloUsu = new DefaultTableModel(null, columnasUsu) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE  empleado.idMunicipio=municipios.idMunicipio");
        tbl.llenarTabla(Emp.jTable2, modeloUsu, columnasUsu.length, "SELECT usuario.user,privilegios.nombre,usuario.estado FROM privilegios,usuario LEFT JOIN empleado ON empleado.user=usuario.user WHERE empleado.idEmpleado IS null AND privilegios.idPrivilegios=usuario.idPrivilegios");
        tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
        tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
//SELECT usuario.user,privilegios.nombre,usuario.estado FROM privilegios,usuario LEFT JOIN empleado ON empleado.user=usuario.user WHERE empleado.idEmpleado IS null AND privilegios.idPrivilegios=usuario.idPrivilegios
    }

    public void crearEmpleado() {
        user = Emp.txtUsuario.getText();
        cedula = Emp.txtCedula.getText();
        nombres = Emp.txtNombres.getText();
        apellidos = Emp.txtApellidos.getText();
        ciudad = Emp.cmbMunicipio.getSelectedItem().toString();
        direccion = Emp.txtDireccion.getText();
        telefono = Emp.txtTelefono.getText();

        if (!user.equals("") && !cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !ciudad.equals("") && !direccion.equals("") && !telefono.equals("")) {
            if (!validar(cedula)) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Emp.cmbDepartamento.getSelectedIndex() + 1));
                insertar(user, cedula, nombres, apellidos, ciudad, direccion, telefono);
                crearModelo();
                desactivar_checkbox();
                limpiar_registros();
            } else {
                JOptionPane.showMessageDialog(null, "Esta persona ya se encuentra registrada");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ningun campo puede estar vacio");
        }
    }

    public static boolean validar(String cedula) {
        String res;
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT ccEmpleado FROM empleado WHERE ccEmpleado='" + cedula + "'");
            while (rs.next()) {
                res = rs.getObject(1) + "";
                if (rs.getString(1) == null) {
                    Con.Desconectar();
                    return false;
                }
                if (cedula.equals(res)) {
                    Con.Desconectar();
                    return true;
                } else {
                    Con.Desconectar();
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertar(String user, String cedula, String nombres, String apellidos, String ciudad, String direccion, String telefono) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO empleado (idEmpleado,ccEmpleado,nombres,apellidos,direccion,telefono,user,idMunicipio) VALUES (0,'" + cedula + "','" + nombres + "','" + apellidos + "','" + direccion + "','" + telefono + "','" + user + "','" + ciudad + "')");
            ext.logs("INSERT INTO empleado (idEmpleado,ccEmpleado,nombres,apellidos,direccion,telefono,user,idMunicipio) VALUES (0,'" + cedula + "','" + nombres + "','" + apellidos + "','" + direccion + "','" + telefono + "','" + user + "','" + ciudad + "')");

            JOptionPane.showMessageDialog(null, "El empleado ha sido ingresado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar() {
        cedula = Emp.txtCedula.getText();
        nombres = Emp.txtNombres.getText();
        apellidos = Emp.txtApellidos.getText();
        departamento = Emp.cmbDepartamento.getSelectedItem().toString();
        ciudad = Emp.cmbMunicipio.getSelectedItem().toString();
        direccion = Emp.txtDireccion.getText();
        telefono = Emp.txtTelefono.getText();
        if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !ciudad.equals("") && !direccion.equals("") && !telefono.equals("")) {
            if (!validar(cedula)) {
                JOptionPane.showMessageDialog(null, "El emplado que intenta modificar no se encuentra registrado");
            } else {
                int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion del empleado", "Confirmacion", JOptionPane.CANCEL_OPTION);
                if (aceptar == JOptionPane.YES_OPTION) {
                    ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Emp.cmbDepartamento.getSelectedIndex() + 1));
                    actualizar(cedula, nombres, apellidos, departamento, ciudad, direccion, telefono);
                    crearModelo();
                    limpiar_registros();
                } else {
                    JOptionPane.showMessageDialog(null, "Los cambios han sido descartados");
                    limpiar_registros();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede quedar vacio");
        }
    }

    public void actualizar(String cedula, String nombres, String apellidos, String departamento, String ciudad, String direccion, String telefono) {
        try {
            Con = new Conexion();
            //Con.Conectar();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE empleado SET nombres='" + nombres + "',apellidos='" + apellidos + "',direccion='" + direccion + "',telefono='" + telefono + "',idMunicipio='" + ciudad + "' WHERE empleado.ccEmpleado='" + cedula + "'");
            ext.logs("UPDATE empleado SET nombres='" + nombres + "',apellidos='" + apellidos + "',direccion='" + direccion + "',telefono='" + telefono + "',idMunicipio='" + ciudad + "' WHERE empleado.ccEmpleado='" + cedula + "'");
           
            JOptionPane.showMessageDialog(null, "El emplado ha sido modificado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar_registros() {
        Emp.txtUsuario.setText("");
        Emp.txtCedula.setText("");
        Emp.txtNombres.setText("");
        Emp.txtApellidos.setText("");
        Emp.cmbDepartamento.setSelectedIndex(28);
        Emp.txtDireccion.setText("");
        Emp.txtTelefono.setText("");
    }

    public void tablas_campos(String tabla) {

        switch (tabla) {
            case "empleado":
                int rec = Emp.jTable1.getSelectedRow();
                idEmpleado = Emp.jTable1.getValueAt(rec, 0).toString();
                Emp.txtUsuario.setText(Emp.jTable1.getValueAt(rec, 1).toString());
                Emp.txtCedula.setText(Emp.jTable1.getValueAt(rec, 2).toString());
                Emp.txtNombres.setText(Emp.jTable1.getValueAt(rec, 3).toString());
                Emp.txtApellidos.setText(Emp.jTable1.getValueAt(rec, 4).toString());
                Emp.cmbDepartamento.setSelectedItem(getDepartamento(Emp.jTable1.getValueAt(rec, 2).toString()));
                Emp.cmbMunicipio.setSelectedItem(Emp.jTable1.getValueAt(rec, 5).toString());
                Emp.txtDireccion.setText(Emp.jTable1.getValueAt(rec, 6).toString());
                Emp.txtTelefono.setText(Emp.jTable1.getValueAt(rec, 7).toString());
                break;
            case "usuario":
                int rec2 = Emp.jTable2.getSelectedRow();
                Emp.txtUsuario.setText(Emp.jTable2.getValueAt(rec2, 0).toString());
                Emp.txtCedula.setText("");
                Emp.txtNombres.setText("");
                Emp.txtApellidos.setText("");
                Emp.cmbDepartamento.setSelectedIndex(28);
                Emp.txtDireccion.setText("");
                Emp.txtTelefono.setText("");
                break;
        }
    }

    public String getDepartamento(String cedula) {// Recibe la cedula del empleado y retorna el nombre del departamento de recidencia 
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsdeparMetodo = st.executeQuery("SELECT departamentos.nombre FROM departamentos WHERE idDepartamento=(SELECT municipios.idDepartamento FROM municipios,empleado WHERE empleado.ccEmpleado='" + cedula + "' and empleado.idMunicipio=municipios.idMunicipio)");
            while (rsdeparMetodo.next()) {
                return rsdeparMetodo.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void desactivar_checkbox() {
        Emp.chCedula.setSelected(false);
        Emp.chCiudad.setSelected(false);
        Emp.chApellidos.setSelected(false);
        Emp.chUsuario.setSelected(false);
    }

    public void buscar() {
        String cedula = Emp.txtBCedula.getText();
        String apellidos = Emp.txtBApellidos.getText();
        String ciudad = Emp.txtBCiudad.getText();
        String user = Emp.txtBUsuario.getText();
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true && Emp.chUsuario.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("") && !user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and empleado.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%'  and user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and empleado.ccEmpleado like '%" + cedula + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true && Emp.chUsuario.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and empleado.ccEmpleado like '%" + cedula + "%' and user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chCiudad.isSelected() == true && Emp.chUsuario.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("") && !user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and municipios.Nombre like '%" + ciudad + "%' and user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true && Emp.chUsuario.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("") && !user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and empleado.Apellidos like '%" + apellidos + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and empleado.ccEmpleado like '%" + cedula + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chUsuario.isSelected() == true) {
            if (!cedula.equals("") && !user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and empleado.apellidos like '%" + apellidos + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true && Emp.chUsuario.isSelected() == true) {
            if (!apellidos.equals("") && !user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCiudad.isSelected() == true && Emp.chUsuario.isSelected() == true) {
            if (!ciudad.equals("") && !user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chUsuario.isSelected() == true) {
            if (!user.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,user,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono FROM empleado,municipios WHERE user like '%" + user + "%' and  empleado.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(Emp.jTable1, headerColumnas);
                tbl.alinearHeaderTable(Emp.jTable2, headerColumnasUsu);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }

    }

}
