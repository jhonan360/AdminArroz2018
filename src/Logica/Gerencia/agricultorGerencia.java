package Logica.Gerencia;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
import Interfaces.AgricultorGerencia;


public class agricultorGerencia {

    //Variables a utilizar
    public static Conexion Con;
    public static ResultSet rs, rseli;
    public static Statement st;
    public static AgricultorGerencia Agr;
    public static extras ext;
    public DefaultTableModel modeloagr;
    public DefaultTableModel modeloagrTel;
    public String columnas[] = new String[]{"Cedula", "Nombres", "Apellidos", "Municipio", "Direccion"};
    public String columnasTel[] = new String[]{"Telefono"};
    public tablas tbl;
    public String cedula, nombres, apellidos, ciudad, direccion, departamento, telefono1, telefono2, telefono3, eli;
    public static int contadortel = 0;

    public agricultorGerencia() {//constructor de la clase
        ext = new extras();
        crearModelo();
    }
    
    public void crearModelo() {// crea los modelos de las tablas
        modeloagr = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modeloagrTel = new DefaultTableModel(null, columnasTel) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl = new tablas();
        tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT ccAgricultor,nombres,apellidos,municipios.nombre,direccion FROM agricultor,municipios WHERE agricultor.idMunicipio=municipios.idMunicipio");
        Agr.jTable2.setModel(modeloagrTel);
    }


    // muestra los telefonos que estan ocultos
    public void VisibleTel() {
        contadortel = contadortel + 1;// Contador de los telefonos visibles
    }


    public void tabla_campos() { // rellenan campos cuando se selecciona una fila en la tabla 
        int rec = Agr.jTable1.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla
        tablasTelefonos(Agr.jTable1.getValueAt(rec, 0).toString());
    }

    // Recibe la cedula y asigna los telefonos a la tabla
    public void tablasTelefonos(String cedula) {
        try {
            modeloagrTel = new DefaultTableModel(null, columnasTel);//ATENCION
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT telefono FROM telagri WHERE ccAgricultor = '" + cedula + "'");
            int cuentaTel = 0;
            while (rs.next()) {
                cuentaTel += 1;
                String dato[] = new String[1];
                dato[0] = rs.getString(1);
                modeloagrTel.addRow(dato);
            }
            Agr.jTable2.setModel(modeloagrTel);
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // recibe cedula, telefono y codigo del telefono
    public boolean validarTelefono(String cedula, String telefono, String idCodTel) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            // se realiza el select pero no se incluye el id del telefono a comparar devuelve true si esta repetido
            rs = st.executeQuery("SELECT telefono FROM telagri WHERE ccAgricultor = '" + cedula + "' AND idTelAgri NOT IN ('" + idCodTel + "')");
            while (rs.next()) {
                if (rs.getString(1).equals(telefono)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void desactivar_checkbox() { // desactiva las checkbox
        Agr.chCedula.setSelected(false);
        Agr.chCiudad.setSelected(false);
        Agr.chApellidos.setSelected(false);
        Agr.chTelefono.setSelected(false);
    }

    // metodo para busqueda con diferentes criterios se agrupa por ccAgricultor 
    public void buscar() { // ATENCION tengo dudas con % telefono% no sale ningun resultado. si sale con  telefono%
        String cedula = Agr.txtBCedula.getText();
        String apellidos = Agr.txtBApellidos.getText();
        String ciudad = Agr.txtBCiudad.getText();
        String telefono = Agr.txtBTelefono.getText();
        modeloagr = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modeloagrTel = new DefaultTableModel(null, columnasTel) { // ATENCION crear metodo de crear modelo tel
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (Agr.chCedula.isSelected() == true && Agr.chApellidos.isSelected() == true && Agr.chCiudad.isSelected() == true && Agr.chTelefono.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("") && !telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%'  AND telagri.telefono LIKE '" + telefono + "%' AND telagri.ccAgricultor = agricultor.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chApellidos.isSelected() == true && Agr.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chApellidos.isSelected() == true && Agr.chTelefono.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND agricultor.ccAgricultor LIKE '%" + cedula + "%' AND telagri.telefono LIKE '" + telefono + "%' AND agricultor.ccAgricultor = telagri.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chCiudad.isSelected() == true && Agr.chTelefono.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("") && !telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND telagri.telefono LIKE '" + telefono + "%' AND agricultor.ccAgricultor = telagri.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chApellidos.isSelected() == true && Agr.chCiudad.isSelected() == true && Agr.chTelefono.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("") && !telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND telagri.telefono LIKE '" + telefono + "%' AND agricultor.ccAgricultor = telagri.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.Apellidos LIKE '%" + apellidos + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chTelefono.isSelected() == true) {
            if (!cedula.equals("") && !telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND telagri.telefono LIKE '" + telefono + "%' AND agricultor.ccAgricultor = telagri.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chApellidos.isSelected() == true && Agr.chCiudad.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND agricultor.apellidos LIKE '%" + apellidos + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chApellidos.isSelected() == true && Agr.chTelefono.isSelected() == true) {
            if (!apellidos.equals("") && !telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND telagri.telefono LIKE '" + telefono + "%' AND agricultor.ccAgricultor = telagri.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCiudad.isSelected() == true && Agr.chTelefono.isSelected() == true) {
            if (!ciudad.equals("") && !telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND telagri.telefono LIKE '" + telefono + "%' AND agricultor.ccAgricultor = telagri.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.ccAgricultor LIKE '%" + cedula + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE agricultor.apellidos LIKE '%" + apellidos + "%' AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,municipios WHERE municipios.Nombre LIKE '" + ciudad + "' AND  agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chTelefono.isSelected() == true) {
            if (!telefono.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT agricultor.ccAgricultor,nombres,apellidos,municipios.Nombre,direccion FROM agricultor,telagri,municipios WHERE telagri.telefono LIKE '" + telefono + "%' AND agricultor.ccAgricultor = telagri.ccAgricultor AND agricultor.idMunicipio=municipios.idMunicipio GROUP BY ccAgricultor");
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        desactivar_checkbox();
    }
}
