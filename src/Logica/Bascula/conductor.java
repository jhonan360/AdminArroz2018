//Clase logica del conductor vinculado a la bascula
package Logica.Bascula;

import Negocio.Conexion;
import Interfaces.Conductor;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;

import Logica.Extras.login;
import static Logica.Extras.login.priv;
import static Logica.Extras.login.rsbas;
import Logica.Extras.log;
import Logica.Extras.login;

import Logica.Extras.login;
import static Logica.Extras.login.priv;
import static Logica.Extras.login.rsbas;
import Logica.Extras.login;
import static Logica.Extras.login.priv;
import static Logica.Extras.login.rsbas;




import Logica.Extras.login;
import static Logica.Extras.login.priv;
import static Logica.Extras.login.rsbas;



import Logica.Extras.login;
import static Logica.Extras.login.priv;
import static Logica.Extras.login.rsbas;


import Logica.Extras.log;
import Logica.Extras.login;


/**
 *
 * @author Lizeth
 */
public class conductor {

    //Variables a utilizar
    public static Conductor Cdt;
    public static extras ext;
    public static login login;
    public static ResultSet rs, rsc, rsc1, rsdepar, per, rsbus, rsdeparMetodo, rsmuni, rseli;
    public static Statement st;
    public static Conexion Con;
    public tablas tbl;
    public static String cedula, nombres, apellidos, telefono, ciudad, direccion, eli;
    public String idDepartamento;
    public static DefaultTableModel modelCdt;
    public static String columnas[] = new String[]{"cedula", "nombres", "apellidos", "telefono", "ciudad", "direccion"};
    public static int row;
    public static log logs;


    public String estado ="crear"; 



    


    public conductor() { //constructor de la clase
        ext = new extras();
        logs = new log();
        crearModelo();
        Cdt.btnEliminar.setVisible(false);
        ocultarbtnEliminar();
    }

    public void ocultarbtnEliminar() {
        String usuario = login.enviarUsuario();

        if (login.getPrivilegio(usuario).equals("administracion")) {
            System.out.println("administracion");
            Cdt.btnEliminar.setVisible(true);
        } else {
            System.out.println("no");
        }
    }

    public void crearModelo() {
        modelCdt = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.idMunicipio=municipios.idMunicipio");
    }

    public void crearConductor() {//Metodo para crear un nuevo conductor 

        cedula = Cdt.txtCedula.getText();
        nombres = Cdt.txtNombres.getText();
        apellidos = Cdt.txtApellidos.getText();
        telefono = Cdt.txtTelefono.getText();
        ciudad = Cdt.cmbCiudad.getSelectedItem().toString();
        direccion = Cdt.txtDireccion.getText();

        //Valida que ningun campo este vacio
        if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !telefono.equals("") && !ciudad.equals("") && !direccion.equals("")) {

            //Llamado al metodo validar enviando parametro cedula
            if (!validar(cedula)) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Cdt.cmbDepartamento.getSelectedIndex() + 1));
                insertar(cedula, nombres, apellidos, telefono, ciudad, direccion);//Llamado al metodo insertar
                crearModelo();
                limpiarRegistros();
            } else {
                JOptionPane.showMessageDialog(null, "Esta persona ya se encuentra registrada");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public static boolean validar(String cedula) {//Metodo que valida si el conductor existe
        String res;
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsc = st.executeQuery("SELECT ccConductor FROM conductor WHERE ccConductor = '" + cedula + "'");
            while (rsc.next()) {
                res = rsc.getObject(1) + "";
                if (rsc.getString(1) == null) {
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

    //Metodo que inserta un nuevo conductor
    public void insertar(String cedula, String nombres, String apellidos, String telefono, String ciudad, String direccion) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("INSERT INTO conductor (ccConductor,nombres,apellidos,telefono,Direccion,idMunicipio) VALUES ('" + cedula + "','" + nombres + "','" + apellidos + "','" + telefono + "','" + direccion + "','" + ciudad + "')");
            JOptionPane.showMessageDialog(null, "Conductor registrado");
            logs.logConductor("i", login.enviarUsuario(), cedula, nombres, apellidos, telefono, direccion, Integer.parseInt(ciudad));
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarConductor(String accion) {
        cedula = Cdt.txtCedula.getText();
        nombres = Cdt.txtNombres.getText();
        apellidos = Cdt.txtApellidos.getText();
        telefono = Cdt.txtTelefono.getText();
        ciudad = Cdt.cmbCiudad.getSelectedItem().toString();
        direccion = Cdt.txtDireccion.getText();

        //Valida que ningun campo este vacio
        if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !telefono.equals("") && !ciudad.equals("") && !direccion.equals("")) {
            //Llamado al metodo validar enviando parametro cedula
            if (validar(cedula) == false) {
                JOptionPane.showMessageDialog(null, "El conductor que intenta modificar no se encuentra en el sistema");
            } else {
                //Lamado al metodo actualizar enviando parametros a modificar
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Cdt.cmbDepartamento.getSelectedIndex() + 1));
                if (accion.equals("eliminar")) {
                    eliminar(cedula, nombres, apellidos, telefono, ciudad, direccion);
                } else {
                    actualizar(cedula, nombres, apellidos, telefono, ciudad, direccion);
                }
                crearModelo();
                limpiarRegistros();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    //Metodo que actualiza los campos a modificar
    public void actualizar(String cedula, String nombres, String apellidos, String telefono, String ciudad, String direccion) {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            logs.logConductor("a", login.enviarUsuario(), cedula, nombres, apellidos, telefono, direccion, Integer.parseInt(ciudad));
            st.executeUpdate("UPDATE conductor SET nombres='" + nombres + "',apellidos='" + apellidos + "',telefono='" + telefono + "',Direccion='" + direccion + "',idMunicipio='" + ciudad + "' WHERE ccConductor='" + cedula + "'");
            JOptionPane.showMessageDialog(null, "El conductor fue modificado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(String cedula, String nombres, String apellidos, String telefono, String ciudad, String direcion) {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            rseli = st.executeQuery("SELECT ccConductor FROM tiquete WHERE ccConductor = '" + cedula + "'");
            System.out.println("tri" + cedula);
            while (rseli.next()) {
                eli = rseli.getObject(1) + "";
            }

            if (cedula.equals(eli)) {
                System.out.println(eli);
                JOptionPane.showMessageDialog(null, "Por seguridad no puede ser eliminado");
                logs.logConductor("ie", login.enviarUsuario(), cedula, nombres, apellidos, telefono, direcion, Integer.parseInt(ciudad));
            } else {
                int decision = JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar un conductor?", "Eliminar conductor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (decision == 0) {
                    logs.logConductor("e", login.enviarUsuario(), cedula, nombres, apellidos, telefono, direcion, Integer.parseInt(ciudad));
                    st.executeUpdate("DELETE FROM conductor WHERE ccConductor='" + cedula + "'");
                    JOptionPane.showMessageDialog(null, "El conductor fue ELIMINADO");
                    Con.Desconectar();
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return "";
    }

    public void limpiarRegistros() {
        Cdt.txtCedula.setText("");
        Cdt.txtNombres.setText("");
        Cdt.txtApellidos.setText("");
        Cdt.txtTelefono.setText("");
        Cdt.cmbDepartamento.setSelectedIndex(28);
        Cdt.txtDireccion.setText("");
    }

    public void tabla_campos() {
        int rec = Cdt.jTable1.getSelectedRow();
        Cdt.txtCedula.setText(Cdt.jTable1.getValueAt(rec, 0).toString());
        Cdt.txtNombres.setText(Cdt.jTable1.getValueAt(rec, 1).toString());
        Cdt.txtApellidos.setText(Cdt.jTable1.getValueAt(rec, 2).toString());
        Cdt.txtTelefono.setText(Cdt.jTable1.getValueAt(rec, 3).toString());
        String ciudad = Cdt.jTable1.getValueAt(rec, 4).toString();
        Cdt.cmbDepartamento.setSelectedItem(getDepartamento(Cdt.txtCedula.getText()));
        Cdt.cmbCiudad.setSelectedItem(ciudad);
        Cdt.txtDireccion.setText(Cdt.jTable1.getValueAt(rec, 5).toString());
    }

    public String getDepartamento(String cedula) {// Recibe el nombre del municipio y retorna el idDepartamento
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsdeparMetodo = st.executeQuery("SELECT departamentos.nombre FROM departamentos WHERE idDepartamento=(SELECT municipios.idDepartamento FROM municipios,conductor WHERE conductor.ccConductor='" + cedula + "' and conductor.idMunicipio=municipios.idMunicipio)");
            while (rsdeparMetodo.next()) {
                return rsdeparMetodo.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void desactivar_checkbox() {
        Cdt.chCedula.setSelected(false);
        Cdt.chApellidos.setSelected(false);
        Cdt.chCiudad.setSelected(false);
    }

    public void buscar() {
        String cedula = Cdt.txtBCedula.getText();
        String apellidos = Cdt.txtBApellido.getText();
        String ciudad = Cdt.txtBCiudad.getText();

        modelCdt = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (Cdt.chCedula.isSelected() == true && Cdt.chApellidos.isSelected() == true && Cdt.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.ccConductor like '%" + cedula + "%' and conductor.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cdt.chCedula.isSelected() == true && Cdt.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.apellidos like '%" + apellidos + "%' and conductor.ccConductor like '%" + cedula + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cdt.chCedula.isSelected() == true && Cdt.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.ccConductor like '%" + cedula + "%' and municipios.Nombre like '%" + ciudad + "%' and  and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cdt.chApellidos.isSelected() == true && Cdt.chCiudad.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cdt.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.ccConductor like '%" + cedula + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cdt.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE conductor.apellidos like '%" + apellidos + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Cdt.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(Cdt.jTable1, modelCdt, columnas.length, "SELECT ccConductor,nombres,apellidos,telefono,municipios.Nombre,Direccion FROM conductor,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and conductor.idMunicipio=municipios.idMunicipio ");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        desactivar_checkbox();

    }

}