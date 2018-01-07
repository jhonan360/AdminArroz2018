//Clase logica del conductor vinculado a la bascula
package Logica.Gerencia;
import Negocio.Conexion;
import Interfaces.ConductorGerencia;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Logica.Extras.log;
import Logica.Extras.login;

/**
 *
 * @author Lizeth
 */
public class conductorGerencia {
    //Variables a utilizar
    public static ConductorGerencia Cdt;
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
    //public static login login;
    

    public conductorGerencia() { //constructor de la clase
        ext = new extras();
        logs = new log();
        crearModelo();
        //Cdt.btnEliminar.setVisible(false);
        //ocultarbtnEliminar();
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


    public void tabla_campos() {
        int rec = Cdt.jTable1.getSelectedRow();
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
