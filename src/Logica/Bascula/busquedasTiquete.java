/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Bascula;

import Logica.Extras.tablas;
import Interfaces.BusquedasTiquete;
import Interfaces.Bascula;
import static Interfaces.Bascula.busTiquete;
import Interfaces.TiqueteVarios;
import Interfaces.Vehiculo;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andre
 */
public class busquedasTiquete {

    public static BusquedasTiquete BusTiquete;
    public static Bascula Bas;
    public static bascula bas;
    public static TiqueteVarios TiqVarios;
    public static agricultor agri;
    public static Vehiculo Vehiculo;
    public static String nomConductor, ccAgricultor;
    public static tablas tbl;
    public static DefaultTableModel modeloagr, modelCdt, modelVhc;
    public static String columnasAgri[] = new String[]{"Cedula", "Nombres", "Apellidos", "Direccion", "Municipio"};
    public static String headerColumnasAgri[] = new String[]{"default", "default", "default", "default", "default"};
    public static String columnas[] = new String[]{"Cedula", "Nombres", "Apellidos", "Telefono", "Direccion", "Municipio"};
    public static String headerColumnas[] = new String[]{"default", "default", "default", "default", "default", "default"};
    public static String columnasVhc[] = new String[]{"Placa", "Marca", "Modelo", "Color"};
    public static String headerColumnasVhc[] = new String[]{"default", "default", "default", "default"};
    public static String claseTiquete;

    public busquedasTiquete(String claseTiquete) {
        crearModeloAgricultor(claseTiquete);
        crearModeloConductor();
        crearModeloVehiculo();
        

        //tabla_camposAgricultor();
    }

    public busquedasTiquete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Busqueda Agricultor
     */
    public void cerrar(String tiquete) {

        switch (tiquete) {
            case "TiqPrincipal":
                Bas.btnBuscarConductor.setEnabled(true);
                Bas.btnEditarAgricultor.setEnabled(true);
                Bas.btnBuscarPlaca.setEnabled(true);
                break;

            case "TiqVarios":
                TiqVarios.btnBuscarConductor.setEnabled(true);
                TiqVarios.btnBuscarPlaca.setEnabled(true);
                break;
        }
    }

    public void crearModeloAgricultor(String claseTiquete) {
        //System.out.println("2");
        modeloagr = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        if (claseTiquete.equals("TiqLiqui")) {
            tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT cedula,nombres,apellidos,direccion,municipios.nombre FROM personalexterno,municipios,tiquete,detalleliquidacion WHERE tiquete.idTiquete=detalleliquidacion.idTiquete AND detalleliquidacion.idliquidaciones IS NULL AND personalexterno.idPersonalExterno=tiquete.idAgricultor AND personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='agricultor' GROUP BY personalexterno.cedula");
            tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
        } else {
            tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT cedula,nombres,apellidos,direccion,municipios.nombre FROM personalexterno,municipios WHERE personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='agricultor'");
            tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
        }
    }

    public void tabla_camposAgricultor(String tiquete) {
        /**
         * int rec = BusTiquete.jTable3.getSelectedRow();
         *
         * System.out.println(tiquete);
         *
         * claseTiquete=tiquete;
         *
         * switch (claseTiquete) { case "TiqPrincipal":
         * Bas.txtAgricultor.setText((BusTiquete.jTable3.getValueAt(rec,
         * 1).toString()) + (" " + BusTiquete.jTable3.getValueAt(rec,
         * 2).toString())); cerrar(claseTiquete); break;
         *
         * case "TiqVarios":
         * TiqVarios.txtConductor.setText((BusTiquete.jTable3.getValueAt(rec,
         * 1).toString()) + (" " + BusTiquete.jTable3.getValueAt(rec,
         * 2).toString())); cerrar(claseTiquete); break; }*
         */

    }

    public static void desactivar_checkboxAgricultor() { // desactiva las checkbox
        BusTiquete.chCedulaAgricultor.setSelected(false);
        BusTiquete.chApellidosAgricultor.setSelected(false);
        BusTiquete.chCiudadAgricultor.setSelected(false);

    }

    public void buscarAgricultor() {
        String cedula = BusTiquete.txtBCedulaVehiculo.getText();
        String apellidos = BusTiquete.txtBApellidosAgricultor.getText();
        String ciudad = BusTiquete.txtBCiudadAgricultor.getText();

        modeloagr = new DefaultTableModel(null, columnasAgri) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (BusTiquete.chCedulaAgricultor.isSelected() == true && BusTiquete.chApellidosAgricultor.isSelected() == true && BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT personalexterno.cedula,nombres,apellidos,direccionmunicipios.Nombre FROM personalexterno,municipios WHERE personalexterno.cedula LIKE '%" + cedula + "%' AND personalexterno.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.idMunicipio=municipios.idMunicipio GROUP BY cedula AND personalexterno.tipo='agricultor'");
                tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaAgricultor.isSelected() == true && BusTiquete.chApellidosAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT personalexterno.cedula,nombres,apellidos,municipios.Nombre,direccion FROM personalexterno,municipios WHERE personalexterno.apellidos LIKE '%" + apellidos + "%' AND personalexterno.cedula LIKE '%" + cedula + "%' AND personalexterno.idMunicipio=municipios.idMunicipio GROUP BY cedula AND personalexterno.tipo='agricultor'");
                tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaAgricultor.isSelected() == true && BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT personalexterno.cedula,nombres,apellidos,municipios.Nombre,direccion FROM personalexterno,municipios WHERE personalexterno.cedula LIKE '%" + cedula + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.idMunicipio=municipios.idMunicipio GROUP BY cedula AND personalexterno.tipo='agricultor'");
                tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosAgricultor.isSelected() == true && BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT personalexterno.cedula,nombres,apellidos,municipios.Nombre,direccion FROM personalexterno,municipios WHERE personalexterno.apellidos LIKE '%" + apellidos + "%' AND municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.idMunicipio=municipios.idMunicipio GROUP BY cedula AND personalexterno.tipo='agricultor'");
                tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaAgricultor.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT personalexterno.cedula,nombres,apellidos,municipios.Nombre,direccion FROM personalexterno,municipios WHERE personalexterno.cedula LIKE '%" + cedula + "%' AND personalexterno.idMunicipio=municipios.idMunicipio GROUP BY cedula AND personalexterno.tipo='agricultor'");
                tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosAgricultor.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT personalexterno.cedula,nombres,apellidos,municipios.Nombre,direccion FROM personalexterno,municipios WHERE personalexterno.apellidos LIKE '%" + apellidos + "%' AND personalexterno.idMunicipio=municipios.idMunicipio GROUP BY cedula AND personalexterno.tipo='agricultor'");
                tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCiudadAgricultor.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable3, modeloagr, columnasAgri.length, "SELECT personalexterno.cedula,nombres,apellidos,municipios.Nombre,direccion FROM personalexterno,municipios WHERE municipios.Nombre LIKE '%" + ciudad + "%' AND personalexterno.idMunicipio=municipios.idMunicipio GROUP BY cedula AND personalexterno.tipo='agricultor'");
                tbl.alinearHeaderTable(BusTiquete.jTable3, headerColumnasAgri);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkboxAgricultor();
    }

    /**
     * Busqueda Conductor
     */
    public void crearModeloConductor() {
        modelCdt = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='conductor'");
        tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
    }

    public void tabla_camposConductor() {
        //int rec = BusTiquete.jTable2.getSelectedRow();

        //  Bas.txtConductor.setText((BusTiquete.jTable2.getValueAt(rec, 2).toString()) + (" " + BusTiquete.jTable2.getValueAt(rec, 1).toString()));
        //Bas.txtConductor.setText((BusTiquete.jTable2.getValueAt(rec, 1).toString()));
        //bas.ccConductor = BusTiquete.jTable2.getValueAt(rec, 0).toString();
        //nomConductor =BusTiquete.jTable2.getValueAt(rec, 1).toString();
        //System.out.println("Log"+nomConductor);
    }

    public void desactivar_checkboxConductor() { // desactiva las checkbox
        BusTiquete.chCedulaConductor.setSelected(false);
        BusTiquete.chApellidosConductor.setSelected(false);
        BusTiquete.chCiudadConductor.setSelected(false);
    }

    public void buscarConductor() {
        String cedula = BusTiquete.txtBCedulaConductor.getText();
        String apellidos = BusTiquete.txtBApellidosConductor.getText();
        String ciudad = BusTiquete.txtBCiudadConductor.getText();

        modelCdt = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (BusTiquete.chCedulaConductor.isSelected() == true && BusTiquete.chApellidosConductor.isSelected() == true && BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE cedula like '%" + cedula + "%' and apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaConductor.isSelected() == true && BusTiquete.chApellidosConductor.isSelected() == true) {
            if (!cedula.equals("") && !apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE apellidos like '%" + apellidos + "%' and cedula like '%" + cedula + "%' and personalexterno.idMunicipio=municipios.idMunicipio ");
                tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaConductor.isSelected() == true && BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!cedula.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE cedula like '%" + cedula + "%' and municipios.Nombre like '%" + ciudad + "%' and  and personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='conductor' ");
                tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosConductor.isSelected() == true && BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!apellidos.equals("") && !ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE apellidos like '%" + apellidos + "%' and municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='conductor' ");
                tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCedulaConductor.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE cedula like '%" + cedula + "%' and personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='conductor' ");
                tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chApellidosConductor.isSelected() == true) {
            if (!apellidos.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE apellidos like '%" + apellidos + "%' and personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='conductor' ");
                tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chCiudadConductor.isSelected() == true) {
            if (!ciudad.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable2, modelCdt, columnas.length, "SELECT cedula,nombres,apellidos,telefono,Direccion,municipios.Nombre FROM personalexterno,municipios WHERE municipios.Nombre like '%" + ciudad + "%' and personalexterno.idMunicipio=municipios.idMunicipio AND personalexterno.tipo='conductor' ");
                tbl.alinearHeaderTable(BusTiquete.jTable2, headerColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkboxConductor();
    }

    /**
     * Busqueda vehiculo
     */
    public void crearModeloVehiculo() {
        modelVhc = new DefaultTableModel(null, columnasVhc) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.idMarca=marca.idMarca");
        tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
    }

    public void desactivar_checkboxVehiculo() { // desactiva las checkbox
        BusTiquete.chPlacaVehiculo.setSelected(false);
        BusTiquete.chModeloVehiculo.setSelected(false);
        BusTiquete.chMarcaVehiculo.setSelected(false);
    }

    public void buscarVehiculo() {
        String placa = BusTiquete.txtBPlacaVehiculo.getText();
        String modelo = BusTiquete.txtBModeloVehiculo.getText();
        String marca = BusTiquete.txtBMarcaVehiculo.getText();

        modelVhc = new DefaultTableModel(null, columnasVhc) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();

        if (BusTiquete.chPlacaVehiculo.isSelected() == true && BusTiquete.chModeloVehiculo.isSelected() == true && BusTiquete.chMarcaVehiculo.isSelected() == true) {
            if (!placa.equals("") && !modelo.equals("") && !marca.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%' and vehiculo.modelo like '%" + modelo + "%' and marca.marca like '%" + marca + "%' and vehiculo.idMarca=marca.idMarca");
                tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chPlacaVehiculo.isSelected() == true && BusTiquete.chModeloVehiculo.isSelected() == true) {
            if (!placa.equals("") && !modelo.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%' and vehiculo.modelo like '%" + modelo + "%'and vehiculo.idMarca=marca.idMarca");
                tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chPlacaVehiculo.isSelected() == true && BusTiquete.chMarcaVehiculo.isSelected() == true) {
            if (!placa.equals("") && !marca.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%' and marca.marca like '%" + marca + "%'and vehiculo.idMarca=marca.idMarca");
                tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chModeloVehiculo.isSelected() == true && BusTiquete.chMarcaVehiculo.isSelected() == true) {
            if (!modelo.equals("") && !marca.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.modelo like '%" + modelo + "%' and marca.marca like '%" + marca + "%'and vehiculo.idMarca=marca.idMarca");
                tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chPlacaVehiculo.isSelected() == true) {
            if (!placa.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.placa like '%" + placa + "%'and vehiculo.idMarca=marca.idMarca");
                tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chModeloVehiculo.isSelected() == true) {
            if (!modelo.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE vehiculo.modelo like '%" + modelo + "%'and vehiculo.idMarca=marca.idMarca");
                tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (BusTiquete.chMarcaVehiculo.isSelected() == true) {
            if (!marca.equals("")) {
                tbl.llenarTabla(BusTiquete.jTable4, modelVhc, columnasVhc.length, "SELECT placa,marca.marca,modelo,color FROM vehiculo,marca WHERE marca.marca like '%" + marca + "%'and vehiculo.idMarca=marca.idMarca");
                tbl.alinearHeaderTable(BusTiquete.jTable4, headerColumnasVhc);
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos de busqueda esta seleccionado");
        }
        //desactivar_checkbox();

    }

}
