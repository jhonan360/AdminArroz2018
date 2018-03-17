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
import Logica.Extras.login;
import Interfaces.Agricultor;
import static Logica.Administracion.empleado.Emp;
import static Logica.Administracion.empleado.login;
import Logica.Extras.log;

public class agricultor {

    //Variables a utilizar
    public static Conexion Con;
    public static ResultSet rs, rseli;
    public static Statement st;
    public static Agricultor Agr;
    public static extras ext;
    public DefaultTableModel modeloagr;
    public DefaultTableModel modeloagrTel;
    public String columnas[] = new String[]{"N°", "Cedula", "Nombres", "Apellidos", "Municipio", "Direccion", "Teléfono 1", "Teléfono 2", "Teléfono 3"};
    public String headerColumnas[] = new String[]{"30", "90", "125", "125", "default", "125", "90", "90", "90"};
    public String camposColumnas[] = new String[]{"center", "left", "left", "left", "left", "left", "left", "left", "left"};
    public tablas tbl;
    public String idAgricultor, cedula, nombres, apellidos, ciudad, direccion, departamento, telefono, telefono2, telefono3, eli;
    public static int contadortel = 0;
    //public static log logs;
    public static login login;
    public String estado = "crear";

    public agricultor() {//constructor de la clase
        ext = new extras();
        // logs = new log();
        crearModelo();
        Agr.btnEliminar.setVisible(false);
        ocultarbtnEliminar();
    }

    public void botones() {
        Agr.btnEliminar.setVisible(false);
    }

    public void ocultarbtnEliminar() {
        String usuario = login.enviarUsuario();

        if (login.getPrivilegio(usuario).equals("administracion")) {
            //System.out.println("administracion");
            Agr.btnEliminar.setVisible(true);
        } else {
            Agr.btnEliminar.setVisible(false);
        }
    }

    public void crearModelo() {// crea los modelos de las tablas
        modeloagr = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl = new tablas();
        tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,municipios.nombre,direccion,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE tipo='agricultor' AND personalexterno.idMunicipio=municipios.idMunicipio");
        tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
        tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
    }

    public void crearAgricultor() {//crear el agricultor se ejecuta cuando se da clic el boton crear
        //Asignación de variables
        cedula = Agr.txtCedula.getText();
        nombres = Agr.txtNombres.getText();
        apellidos = Agr.txtApellidos.getText();
        ciudad = Agr.cmbMunicipio.getSelectedItem().toString();
        direccion = Agr.txtDireccion.getText();
        telefono = Agr.txtTelefono1.getText();
        telefono2 = Agr.txtTelefono2.getText();
        telefono3 = Agr.txtTelefono3.getText();

        if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !ciudad.equals("") && !direccion.equals("") && !telefono.equals("")) {
            if (!ext.validar("a", cedula)) {
                ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Agr.cmbDepartamento.getSelectedIndex() + 1));
                if (ext.validarTelefonos(telefono, telefono2, telefono3)) {
                    insertar(cedula, nombres, apellidos, direccion, ciudad, telefono, telefono2, telefono3);
                    limpiar_registros();
                    crearModelo();
                } else {
                    JOptionPane.showMessageDialog(null, "Los telefonos no pueden ser iguales");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Esta persona ya se encuentra registrada");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    //Recibe los datos del agricultor para su creación
    public void insertar(String cedula, String nombres, String apellidos, String direccion, String ciudad, String telefono, String telefono2, String telefono3) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("Insert INTO personalexterno (idPersonalExterno,cedula,nombres,apellidos,direccion,telefono,telefono2,telefono3,idMunicipio,tipo) VALUES (0,'" + cedula + "','" + nombres + "','" + apellidos + "','" + direccion + "','" + telefono + "','" + telefono2 + "','" + telefono3 + "','" + ciudad + "','agricultor')");
            JOptionPane.showMessageDialog(null, "Agricultor registrado");
            //logs.logAgricultor("i", login.enviarUsuario(), cedula, nombres, apellidos, direccion, Integer.parseInt(ciudad));
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Limpia las cajas de texto y cambia el combo box departamento al tolima
    public void limpiar_registros() {
        Agr.txtCedula.setText("");
        Agr.txtNombres.setText("");
        Agr.txtApellidos.setText("");
        Agr.cmbDepartamento.setSelectedIndex(28);
        Agr.txtDireccion.setText("");
        Agr.txtTelefono1.setText("");
        Agr.txtTelefono2.setText("");
        Agr.txtTelefono3.setText("");
    }

    public void tabla_campos() { // rellenan campos cuando se selecciona una fila en la tabla 
        int rec = Agr.jTable1.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla

        String cc = Agr.jTable1.getValueAt(rec, 1).toString();
        String ciudad = Agr.jTable1.getValueAt(rec, 4).toString();

        idAgricultor = Agr.jTable1.getValueAt(rec, 0).toString();
        Agr.txtCedula.setText(Agr.jTable1.getValueAt(rec, 1).toString());
        Agr.txtNombres.setText(Agr.jTable1.getValueAt(rec, 2).toString());
        Agr.txtApellidos.setText(Agr.jTable1.getValueAt(rec, 3).toString());
        Agr.cmbDepartamento.setSelectedItem(ext.getDepartamento("a", cc)); // devuelve y asigna el departamento en el combo box
        Agr.cmbMunicipio.setSelectedItem(ciudad);
        Agr.txtDireccion.setText(Agr.jTable1.getValueAt(rec, 5).toString());
        Agr.txtTelefono1.setText(Agr.jTable1.getValueAt(rec, 6).toString());
        Agr.txtTelefono2.setText(Agr.jTable1.getValueAt(rec, 7).toString());
        Agr.txtTelefono3.setText(Agr.jTable1.getValueAt(rec, 8).toString());
    }

    // metodo llamado cuando se da clic en el boton modificar. Realiza verificaciones y llama el metodo actualizar
    public void modificar(String accion) {
        cedula = Agr.txtCedula.getText();
        nombres = Agr.txtNombres.getText();
        apellidos = Agr.txtApellidos.getText();
        ciudad = Agr.cmbMunicipio.getSelectedItem().toString();
        direccion = Agr.txtDireccion.getText();
        telefono = Agr.txtTelefono1.getText();
        telefono2 = Agr.txtTelefono2.getText();
        telefono3 = Agr.txtTelefono3.getText();
        int row = Agr.jTable1.getSelectedRow(); // Devuelve la fila de la seleccion en la tabla

        if (!cedula.equals("") && !nombres.equals("") && !apellidos.equals("") && !ciudad.equals("") && !direccion.equals("") && !telefono.equals("")) {

            ciudad = ext.getIdMunicipio(ciudad, String.valueOf(Agr.cmbDepartamento.getSelectedIndex() + 1)); // devuelve y asigna el ID del municipio

            if (!ext.validar("a", cedula)) {
                JOptionPane.showMessageDialog(null, "El Agricultor que intenta modificar no se encuantra registrado en el sistema");
            } else if (accion.equals("eliminar")) {
                eliminar(cedula, nombres, apellidos, direccion, ciudad);
            } else if (!ext.validarTelefonos(telefono, telefono2, telefono3)) {
                JOptionPane.showMessageDialog(null, "Los telefonos no pueden ser iguales");
                return;
            } else {
                int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere modificar la informacion del agricultor", "Confirmacion", JOptionPane.CANCEL_OPTION);
                if (aceptar == JOptionPane.YES_OPTION) {
                    if (accion.equals("modificar")) {
                        actualizar(cedula, nombres, apellidos, direccion, ciudad, telefono, telefono2, telefono3);
                        tabla_campos(); // se llama el metodo para que se pueda ver los cambios que se realizaron
                        limpiar_registros();
                        crearModelo();
                        JOptionPane.showMessageDialog(null, "Los cambios han sido aplicados");
                        //tabla_campos(); // se llama el metodo para que se pueda ver los cambios que se realizaron
                        Agr.jTable1.changeSelection(row, 1, false, false); // cambia la selección del la tabla
                    }
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

    public void eliminar(String cedula, String nombres, String apellidos, String direccion, String ciudad) {
        try {
            Con.Conectar();
            st = Con.conexion.createStatement();
            rseli = st.executeQuery("SELECT idPersonlExterno FROM tiquete WHERE idPersonlExterno = '" + idAgricultor + "'");
            System.out.println("tri" + cedula);
            while (rseli.next()) {
                eli = rseli.getObject(1) + "";
            }

            if (cedula.equals(eli)) {
                System.out.println(eli);
                //logs.logAgricultor("ie", login.enviarUsuario(), cedula, nombres, apellidos, direccion, Integer.parseInt(ciudad));
                JOptionPane.showMessageDialog(null, "Por seguridad no puede ser eliminado");
            } else {
                int decision = JOptionPane.showConfirmDialog(null, "Realmente desea Eliminar un Agricultor?", "Eliminar agricultor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (decision == 0) {
                    //eliminarTelefono(cedula);
                    String[] idsTel = new String[3];
                    idsTel = ext.getIdTelefonos(cedula, 3);
                    for (int i = 0; i < idsTel.length; i++) {
                        if (idsTel[i] != null) {
                            //logs.logTelAgri("e",login.enviarUsuario(),Integer.parseInt(idsTel[i]),"",cedula);   
                        }
                    }
                    //st.executeUpdate("DELETE FROM telagri WHERE idAgricultor='" + idAgricultor + "'");
                    // logs.logAgricultor("e", login.enviarUsuario(), cedula, nombres, apellidos, direccion, Integer.parseInt(ciudad));
                    st.executeUpdate("DELETE FROM personalexterno WHERE idPersonalExterno='" + idAgricultor + "'");
                    JOptionPane.showMessageDialog(null, "El agricultor fue ELIMINADO");
                    Con.Desconectar();
                    crearModelo();
                    limpiar_registros();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return "";
    }

    // recibe los datos para actualizar el agricultor
    public void actualizar(String cedula, String nombres, String apellidos, String direccion, String ciudad, String telefono, String telefono2, String telefono3) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            //logs.logAgricultor("a", login.enviarUsuario(), cedula, nombres, apellidos, direccion, Integer.parseInt(ciudad));
            st.executeUpdate("UPDATE personalexterno SET nombres='" + nombres + "',apellidos='" + apellidos + "',direccion='" + direccion + "',idMunicipio='" + ciudad + "',telefono='" + telefono + "',telefono2='" + telefono2 + "',telefono3='" + telefono3 + "' WHERE personalexterno.idPersonalExterno='" + idAgricultor + "'");
            JOptionPane.showMessageDialog(null, "El Agriculto fue modificado");
            Con.Desconectar();
            limpiar_registros();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivar_checkbox() { // desactiva las checkbox
        Agr.chCedula.setSelected(false);
        Agr.chCiudad.setSelected(false);
        Agr.chApellidos.setSelected(false);
    }

    // metodo para busqueda con diferentes criterios se agrupa por ccAgricultor 
    public void buscar() { // ATENCION tengo dudas con % telefono% no sale ningun resultado. si sale con  telefono%
        String cedula = Agr.txtBCedula.getText();
        String apellidos = Agr.txtBApellidos.getText();
        String ciudad = Agr.txtBCiudad.getText();

        modeloagr = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        if (Agr.chCedula.isSelected() == true && Agr.chApellidos.isSelected() == true && Agr.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,municipios.Nombre,direccion,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.cedula LIKE '%" + cedula + "%' AND personalexterno.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
                tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chApellidos.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,municipios.Nombre,direccion,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.cedula LIKE '%" + cedula + "%' AND personalexterno.Apellidos LIKE '%" + apellidos + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
                tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true && Agr.chCiudad.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,municipios.Nombre,direccion,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.cedula LIKE '%" + cedula + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
                tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chApellidos.isSelected() == true && Agr.chCiudad.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,personalexterno.cedula,nombres,apellidos,municipios.Nombre,direccion FROM personalexterno,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.apellidos LIKE '%" + apellidos + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
                tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,municipios.Nombre,direccion,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.cedula LIKE '%" + cedula + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
                tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chApellidos.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,municipios.Nombre,direccion,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE personalexterno.apellidos LIKE '%" + apellidos + "%' AND personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
                tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Agr.chCiudad.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(Agr.jTable1, modeloagr, columnas.length, "SELECT idPersonalExterno,cedula,nombres,apellidos,municipios.Nombre,direccion,telefono,telefono2,telefono3 FROM personalexterno,municipios WHERE municipios.Nombre LIKE '" + ciudad + "' AND  personalexterno.idMunicipio=municipios.idMunicipio AND tipo='agricultor' GROUP BY cedula");
                tbl.alinearHeaderTable(Agr.jTable1, headerColumnas);
                tbl.alinearCamposTable(Agr.jTable1, camposColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();
    }
}
