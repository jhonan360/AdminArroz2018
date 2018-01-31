/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Gerente;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Interfaces.Gerente;

/**
 *
 * @author uriel
 */
public class gerente {

    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public DefaultTableModel modelo;
    public static Gerente Ger;
    public String columnas[] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    public tablas tbl;
    public String tiquete, valor;

    public gerente() {
        crearModeloTabla();
    }

    public void crearModeloTabla() {// crea los modelos de las tablas
        modelo = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl = new tablas();
        tbl.llenarTabla(Ger.TablaPendiente, modelo, columnas.length, "SELECT tiquete.idTiquete, personalexterno.cedula, personalexterno.nombres,personalexterno.apellidos,tipodearroz.nombre,tiquete.fecha,tiquete.kilosBrutos,tiquete.destare,tiquete.kilosNetos,tiquete.observacion,tiquete.empaque,tiquete.humedadUno,tiquete.impurezaUno FROM tiquete,personalexterno,tipodearroz WHERE tiquete.idAgricultor= personalexterno.idPersonalExterno and tipodearroz.idTipoDeArroz=tiquete.idTipoDeArroz");
    }

    public void enviarInformacionALosCampos() {
        int rec = Ger.TablaPendiente.getSelectedRow();// devuelve un entero con la posicion de la seleccion en la tabla
        Ger.TxtNumTiquete.setText(Ger.TablaPendiente.getValueAt(rec, 0).toString());
        Ger.TxtCedula.setText(Ger.TablaPendiente.getValueAt(rec, 1).toString());
        Ger.TxtNombre.setText(Ger.TablaPendiente.getValueAt(rec, 2).toString() + Ger.TablaPendiente.getValueAt(rec, 3).toString());
        Ger.TxtTipo.setText(Ger.TablaPendiente.getValueAt(rec, 4).toString());
        Ger.TxtFecha.setText(Ger.TablaPendiente.getValueAt(rec, 5).toString());
        Ger.TxtKilosBrutos.setText(Ger.TablaPendiente.getValueAt(rec, 6).toString());
        Ger.TxtDestare.setText(Ger.TablaPendiente.getValueAt(rec, 7).toString());
        Ger.TxtKilosNetos.setText(Ger.TablaPendiente.getValueAt(rec, 8).toString());
        Ger.TxtObservacion.setText(Ger.TablaPendiente.getValueAt(rec, 9).toString());
        Ger.TxtEmpaque.setText(Ger.TablaPendiente.getValueAt(rec, 10).toString());
        Ger.TxtHumedad.setText(Ger.TablaPendiente.getValueAt(rec, 11).toString());
        Ger.TxtImpureza.setText(Ger.TablaPendiente.getValueAt(rec, 12).toString());

    }
    
    public void limpiarCampos(){
        Ger.TxtNumTiquete.setText("");
        Ger.TxtCedula.setText("");
        Ger.TxtNombre.setText("");
        Ger.TxtTipo.setText("");
        Ger.TxtFecha.setText("");
        Ger.TxtKilosBrutos.setText("");
        Ger.TxtDestare.setText("");
        Ger.TxtKilosNetos.setText("");
        Ger.TxtObservacion.setText("");
        Ger.TxtEmpaque.setText("");
        Ger.TxtHumedad.setText("");
        Ger.TxtImpureza.setText("");
        Ger.TxtValor.setText("");
        crearModeloTabla();
    }

    public void TomarValoresTiquete() {
        tiquete = Ger.TxtNumTiquete.getText();
        valor = Ger.TxtValor.getText();

        if (!tiquete.equals("") && !valor.equals("")) {
            iniciarTiquete(tiquete, valor);
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede quedar vacio");
        }
    }

    public void iniciarTiquete(String tiquete, String valor) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            st.executeUpdate("Insert Into detalleliquidacion (idDetalleLiquidacion,idTiquete,valorCarga) Values (0,'" + tiquete + "','" + valor + "')");
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            limpiarCampos();
            crearModeloTabla();
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}