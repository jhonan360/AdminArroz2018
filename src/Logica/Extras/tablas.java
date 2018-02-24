/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import Negocio.Conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author uriel
 */
public class tablas {

    Conexion Con;
    public static Statement st;
    public static ResultSet rs;

    public void llenarTabla(JTable tabla, DefaultTableModel modelo, int columnas, String sentencia) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery(sentencia);
            //ResultSetMetaData rsc3md = rsc3.getMetaData();
            while (rs.next()) {
                String datos[] = new String[columnas];
                for (int i = 0; i < columnas; i++) {
                    datos[i] = rs.getString(i + 1);
                }
                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alinearHeaderTable(JTable tabla, String columnas[]) {
        TableColumnModel columnModel = tabla.getColumnModel();
        for (int i = 0; i < columnas.length; i++) {
            if (!columnas[i].equals("default")) {
                columnModel.getColumn(i).setPreferredWidth(Integer.parseInt(columnas[i]));
            }
        }
        TableCellRenderer rendererFromHeaderLiqui = tabla.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeaderLiqui;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    public void alinearCamposTable(JTable tabla, String columnas[]) {
        DefaultTableCellRenderer Right = new DefaultTableCellRenderer();
        Right.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer Center = new DefaultTableCellRenderer();
        Center.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer Left = new DefaultTableCellRenderer();
        Left.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < columnas.length; i++) {
            switch (columnas[i]) {
                case "right":
                      tabla.getColumnModel().getColumn(i).setCellRenderer(Right);
                    break;
                case "center":
                    tabla.getColumnModel().getColumn(i).setCellRenderer(Center);
                    break;
                case "left":
                    tabla.getColumnModel().getColumn(i).setCellRenderer(Left);
                    break;
            }
        }
    }
}
