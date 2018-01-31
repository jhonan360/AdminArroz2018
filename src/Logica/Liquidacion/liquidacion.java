/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Liquidacion;

import Interfaces.Liquidacion;
import Logica.Extras.tablas;
import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;
import Logica.Extras.extras;
/**
 *
 * @author jhonansmith
 */
public class liquidacion {
   public static Liquidacion Liqui;
   public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public static extras ext;
    public DefaultTableModel modeloemp, modeloUsu;
    public String columnas[] = new String[]{"N", "Agricultor", "Valor Carga", "Kilos Netos"};
    public String columnasUsu[] = new String[]{"N", "Humedad", "Impureza", "Castigo Humedad", "Castigo Impureza", "Peso Compra", "Valor Kilo","Valor Total"};
    public tablas tbl;
    public String idEmpleado, user, cedula, nombres, apellidos, ciudad, direccion, telefono, departamento;
    
    public liquidacion() {
        ext = new extras();
        tbl = new tablas();
        
    }
}
