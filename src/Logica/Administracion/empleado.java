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
    public DefaultTableModel modeloemp;
    public String columnas[] = new String[]{"N", "Cédula", "Nombres", "Apellidos", "Ciudad", "Dirección", "Teléfono", "Cargo"};
    public tablas tbl;
    public int idCargo;
    public String cedula, nombres, apellidos, ciudad, direccion, telefono, cargo, departamento;
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
        tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio");
    }

    public void crearEmpleado() {
        cedula = Emp.txtCedula.getText();
        nombres = Emp.txtNombres.getText();
        apellidos = Emp.txtApellidos.getText();
        ciudad = Emp.cmbMunicipio.getSelectedItem().toString();
        System.out.print(ciudad);
        direccion = Emp.txtDireccion.getText();
        telefono = Emp.txtTelefono.getText();
        idCargo = Emp.cmbCargo.getSelectedIndex() + 1;
        System.out.print(idCargo);

        if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !ciudad.equals("") && !direccion.equals("") && !telefono.equals("")) {
            if (!validar(cedula)) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Emp.cmbDepartamento.getSelectedIndex() + 1));
                insertar(cedula, nombres, apellidos, ciudad, direccion, telefono, String.valueOf(idCargo));
                crearModelo();

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

    public void insertar(String cedula, String nombres, String apellidos, String ciudad, String direccion, String telefono, String cargo) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO empleado (idEmpleado,ccEmpleado,nombres,apellidos,direccion,telefono,idCargo,idMunicipio) VALUES (0,'" + cedula + "','" + nombres + "','" + apellidos + "','" + direccion + "','" + telefono + "'," + idCargo + "," + ciudad + ")");
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
        idCargo = Emp.cmbCargo.getSelectedIndex() + 1;
        if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !ciudad.equals("") && !direccion.equals("") && !telefono.equals("")) {
            if (!validar(cedula)) {
                JOptionPane.showMessageDialog(null, "El emplado que intenta modificar no se encuentra registrado");
            } else {
                int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion del empleado", "Confirmacion", JOptionPane.CANCEL_OPTION);
                if (aceptar == JOptionPane.YES_OPTION) {
                    ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Emp.cmbDepartamento.getSelectedIndex() + 1));
                    actualizar(cedula, nombres, apellidos, departamento, ciudad, direccion, telefono, idCargo);
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

    public void actualizar(String cedula, String nombres, String apellidos, String departamento, String ciudad, String direccion, String telefono, int idCargo) {
        try {
            Con = new Conexion();
            //Con.Conectar();
            st = Con.conexion.createStatement();
            st.executeUpdate("UPDATE empleado SET nombres='" + nombres + "',apellidos='" + apellidos + "',direccion='" + direccion + "',telefono='" + telefono + "',idCargo='" + idCargo + "',idMunicipio='" + ciudad + "' WHERE empleado.ccEmpleado='" + cedula + "'");
            JOptionPane.showMessageDialog(null, "El emplado ha sido modificado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar_registros() {
        Emp.txtCedula.setText("");
        Emp.txtNombres.setText("");
        Emp.txtApellidos.setText("");
        Emp.cmbDepartamento.setSelectedIndex(28);
        Emp.txtDireccion.setText("");
        Emp.txtTelefono.setText("");
        Emp.cmbCargo.setSelectedIndex(0);
    }

    public void tablas_campos() {
        int rec = Emp.jTable1.getSelectedRow();
        Emp.txtCedula.setText(Emp.jTable1.getValueAt(rec, 1).toString());
        Emp.txtNombres.setText(Emp.jTable1.getValueAt(rec, 2).toString());
        Emp.txtApellidos.setText(Emp.jTable1.getValueAt(rec, 3).toString());
        Emp.cmbDepartamento.setSelectedItem(getDepartamento(Emp.jTable1.getValueAt(rec, 1).toString()));
        Emp.cmbMunicipio.setSelectedItem(Emp.jTable1.getValueAt(rec, 4).toString());
        Emp.txtDireccion.setText(Emp.jTable1.getValueAt(rec, 5).toString());
        Emp.txtTelefono.setText(Emp.jTable1.getValueAt(rec, 6).toString());
        Emp.cmbCargo.setSelectedItem(Emp.jTable1.getValueAt(rec, 7).toString());
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
        Emp.chCargo.setSelected(false);
    }

    public void buscar() {
        String cedula = Emp.txtBCedula.getText();
        String apellidos = Emp.txtBApellidos.getText();
        String ciudad = Emp.txtBCiudad.getText();
        String cargo = Emp.txtBCargo.getText();
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true && Emp.chCargo.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("") && !cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and empleado.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%'  and cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and empleado.ccEmpleado like '%" + cedula + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true && Emp.chCargo.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and empleado.ccEmpleado like '%" + cedula + "%' and cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chCiudad.isSelected() == true && Emp.chCargo.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("") && !cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and municipios.Nombre like '%" + ciudad + "%' and cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true && Emp.chCargo.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("") && !cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and empleado.Apellidos like '%" + apellidos + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and empleado.ccEmpleado like '%" + cedula + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true && Emp.chCargo.isSelected() == true) {
            if (!cedula.equals("") && !cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true && Emp.chCiudad.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and empleado.apellidos like '%" + apellidos + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true && Emp.chCargo.isSelected() == true) {
            if (!apellidos.equals("") && !cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCiudad.isSelected() == true && Emp.chCargo.isSelected() == true) {
            if (!ciudad.equals("") && !cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.ccEmpleado like '%" + cedula + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE empleado.apellidos like '%" + apellidos + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Emp.chCargo.isSelected() == true) {
            if (!cargo.equals("")) {
                tbl.llenarTabla(Emp.jTable1, modeloemp, columnas.length, "SELECT idEmpleado,ccEmpleado,nombres,apellidos,municipios.Nombre,direccion,telefono,cargo.nombre FROM empleado,cargo,municipios WHERE cargo.nombre like '%" + cargo + "%' and empleado.idCargo = cargo.idCargo and empleado.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        

    }

}
