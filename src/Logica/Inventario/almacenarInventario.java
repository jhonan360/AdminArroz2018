/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Inventario;

import Interfaces.AlmacenarInventario;
import Interfaces.EtapaInventario;
import Interfaces.Login;
import Interfaces.ProcedimientosInventario;
import Interfaces.VerProcedimientoInventario;
import Logica.Extras.currencyFormat;
import Logica.Extras.tablas;
import Negocio.Conexion;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lizeth
 */
public class almacenarInventario {

    public static Login Login;
    public static AlmacenarInventario AlmacenarI;
    public static ProcedimientosInventario ProcedI;
    public static EtapaInventario EtapaI;
    public static VerProcedimientoInventario VerProcedI;
    public static tablas tbl;
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    String columnasTabla[] = new String[]{"N", "Fecha", "Agricultor", "Kilos Netos"};
    String alineaHeader[] = new String[]{"10", "default", "default", "default"};
    String alineaCampo[] = new String[]{"center", "center", "left", "right"};
    DefaultTableModel modeloTabla;
    String idTiquete, movimientos = "";
    double pesoActual = 0;
    double A1[][] = new double[5][3];
    double A2[][] = new double[5][3];
    double A3[][] = new double[5][3];
    double A4[][] = new double[5][3];
    
    currencyFormat cu;

    public almacenarInventario() {
        tbl = new tablas();
        cu = new currencyFormat();
        crearModelo();
        cargarSilos();
        print();
    }

    public static void abrirProcedimiento() {
        if (!(ProcedI instanceof ProcedimientosInventario)) {
            ProcedI = new ProcedimientosInventario();
            ProcedI.setVisible(true);
        } else {
            ProcedI.setVisible(true);
        }
    }

    public static void abrirEtapa() {
        if (!(EtapaI instanceof EtapaInventario)) {
            EtapaI = new EtapaInventario();
            EtapaI.setVisible(true);
        } else {
            EtapaI.setVisible(true);
        }
    }

    public static void abrirVerProcedimiento() {
        if (!(VerProcedI instanceof VerProcedimientoInventario)) {
            VerProcedI = new VerProcedimientoInventario();
            VerProcedI.setVisible(true);
        } else {
            VerProcedI.setVisible(true);
        }
    }

    public static void salir() {
        Login = new Login();
        Login.setVisible(true);
    }

    public void crearModelo() {
        modeloTabla = new DefaultTableModel(null, columnasTabla) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(AlmacenarI.tblMateriaPrima, modeloTabla, columnasTabla.length, "SELECT tiquete.idTiquete, tiquete.fecha, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos), tiquete.kilosNetos FROM personalexterno,tiquete  WHERE tiquete.idTiquete NOT IN (SELECT idTiquete FROM tiqueteensilos) AND personalexterno.idPersonalExterno=tiquete.idAgricultor AND tiquete.kilosNetos>0.00");
        tbl.alinearHeaderTable(AlmacenarI.tblMateriaPrima, alineaHeader);
        tbl.alinearCamposTable(AlmacenarI.tblMateriaPrima, alineaCampo);
        formatoTabla();
    }

    public void cargarSilos() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery(" SELECT idSilos,kilos,0 FROM silos,seccion,bateria WHERE bateria.nombre='A' AND seccion.nombre='A1' AND seccion.idBateria=bateria.idBateria AND silos.idSeccion=seccion.idSeccion");
            int contador = 0;
            while (rs.next()) {
                for (int i = 0; i < A1[contador].length; i++) {
                    A1[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0 FROM silos,seccion,bateria WHERE bateria.nombre='A' AND seccion.nombre='A2' AND seccion.idBateria=bateria.idBateria AND silos.idSeccion=seccion.idSeccion");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < A2[contador].length; i++) {
                    A2[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0 FROM silos,seccion,bateria WHERE bateria.nombre='A' AND seccion.nombre='A3' AND seccion.idBateria=bateria.idBateria AND silos.idSeccion=seccion.idSeccion");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < A3[contador].length; i++) {
                    A3[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0 FROM silos,seccion,bateria WHERE bateria.nombre='A' AND seccion.nombre='A4' AND seccion.idBateria=bateria.idBateria AND silos.idSeccion=seccion.idSeccion");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < A4[contador].length; i++) {
                    A4[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setValueSilo(JProgressBar progressBar, double silo[][], int fila) {
        if (silo[fila][2] == 0) {
            progressBar.setString(String.valueOf(silo[fila][1]));
            double percent = ((silo[fila][1] * 100) / 12000);
            progressBar.setValue((int) percent);
        } else {
            progressBar.setString(String.valueOf(silo[fila][2]));
            double percent = ((silo[fila][2] * 100) / 12000);
            progressBar.setValue((int) percent);
        }
    }

    public void print() {
        //---- sección A2 ---\\\
        setValueSilo(AlmacenarI.A11, A1, 0);
        setValueSilo(AlmacenarI.A12, A1, 1);
        setValueSilo(AlmacenarI.A13, A1, 2);
        setValueSilo(AlmacenarI.A14, A1, 3);
        setValueSilo(AlmacenarI.A15, A1, 4);

        //---- sección A2 ---\\\
        setValueSilo(AlmacenarI.A26, A2, 0);
        setValueSilo(AlmacenarI.A27, A2, 1);
        setValueSilo(AlmacenarI.A28, A2, 2);
        setValueSilo(AlmacenarI.A29, A2, 3);
        setValueSilo(AlmacenarI.A210, A2, 4);

        //---- sección A3 ---\\\
        setValueSilo(AlmacenarI.A311, A3, 0);
        setValueSilo(AlmacenarI.A312, A3, 1);
        setValueSilo(AlmacenarI.A313, A3, 2);
        setValueSilo(AlmacenarI.A314, A3, 3);
        setValueSilo(AlmacenarI.A315, A3, 4);

        //---- sección A4 ---\\\
        setValueSilo(AlmacenarI.A416, A4, 0);
        setValueSilo(AlmacenarI.A417, A4, 1);
        setValueSilo(AlmacenarI.A418, A4, 2);
        setValueSilo(AlmacenarI.A419, A4, 3);
        setValueSilo(AlmacenarI.A420, A4, 4);

    }

    public void tablaCampos() {
        limpiar();
        int rec = AlmacenarI.tblMateriaPrima.getSelectedRow();
        idTiquete = AlmacenarI.tblMateriaPrima.getValueAt(rec, 0).toString();
        AlmacenarI.lblPesoActual.setText(AlmacenarI.tblMateriaPrima.getValueAt(rec, 3).toString());
        pesoActual = Double.parseDouble(AlmacenarI.lblPesoActual.getText());
        cargarSilos();
        print();
    }

    public void seleccionarSilo(JProgressBar progressBar) {
        String silo = progressBar.getName();
        llenarSilo(progressBar, getSilo(silo), getRowSilo(silo));
    }

    public void llenarSilo(JProgressBar progressBar, double silo[][], int fila) {
        String nameSilo = progressBar.getName();
        if (silo[fila][1] < 12000) {
            if (silo[fila][2] == 0) {
                if (pesoActual > 0) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿desea llenar este silo?", "Confirmación", JOptionPane.CANCEL_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        double espacio = 12000 - silo[fila][1];
                        if (espacio >= pesoActual) {
                            silo[fila][2] = silo[fila][1] + pesoActual;
                            pesoActual = 0;
                        } else {
                            silo[fila][2] = silo[fila][1] + espacio;
                            pesoActual = pesoActual - espacio;
                        }
                        movimientos = movimientos + "," + nameSilo;
                        progressBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 0), new java.awt.Color(255, 51, 0), new java.awt.Color(255, 51, 0), new java.awt.Color(255, 51, 0)));
                        AlmacenarI.lblPesoActual.setText(String.valueOf(pesoActual));
                        print();
                    }
                }
            } else {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿desea revertir cambios en este silo?", "Confirmación", JOptionPane.CANCEL_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    pesoActual += silo[fila][2] - silo[fila][1];
                    silo[fila][2] = 0;
                    movimientos = movimientos.replace("," + nameSilo + ",", "");
                    progressBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
                    AlmacenarI.lblPesoActual.setText(String.valueOf(pesoActual));
                    print();
                }
            }
        }
    }

    public void almacenar() {
        if (pesoActual == 0) {
            String[] silos = movimientos.split(",");
            for (int i = 0; i < silos.length; i++) {
                if (!silos[i].equals("")) {
                    insert(getSilo(silos[i]), getRowSilo(silos[i]));
                }
            }
            JOptionPane.showMessageDialog(null, "Almacenamiento exitoso");
        }
    }

    public void insert(double silo[][], int fila) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            String idSilo = String.valueOf((int) (silo[fila][0]));
            double kilos = silo[fila][2] - silo[fila][1];
            String estadoSilo = "vacio";
            if (silo[fila][2] == 12000) {
                estadoSilo = "lleno";
            } else {
                estadoSilo = "contenido";
            }
            st.executeUpdate("INSERT INTO tiqueteensilos (idTiqueteSilos, idTiquete, idSilos, kilos, estado) VALUES(0,'" + idTiquete + "','" + idSilo + "','" + kilos + "','secamiento')");
            st.executeUpdate("UPDATE silos SET kilos='" + silo[fila][2] + "',estado='" + estadoSilo + "' WHERE idSilos='" + idSilo + "';");
            crearModelo();
            limpiar();
            Con.Desconectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guadar");
            e.printStackTrace();
        }

    }

// retorna la matriz segun el silo
    public double[][] getSilo(String silo) {
        String seccion = silo.substring(0, 2);
        switch (seccion) {
            case "A1":
                return A1;
            case "A2":
                return A2;
            case "A3":
                return A3;
            case "A4":
                return A4;
        }
        return null;
    }

    // retorna el numero de fila de la matriz segun el silo
    public int getRowSilo(String silo) {
        switch (silo) {
            case "A11":
                return 0;
            case "A12":
                return 1;
            case "A13":
                return 2;

            case "A14":
                return 3;

            case "A15":
                return 4;

            case "A26":
                return 0;

            case "A27":
                return 1;

            case "A28":
                return 2;

            case "A29":
                return 3;

            case "A210":
                return 4;

            case "A311":
                return 0;

            case "A312":
                return 1;

            case "A313":
                return 2;

            case "A314":
                return 3;

            case "A315":
                return 4;

            case "A416":
                return 0;

            case "A417":
                return 1;

            case "A418":
                return 2;

            case "A419":
                return 3;

            case "A420":
                return 4;
        }
        return 0;
    }

    public void limpiar() {
        pesoActual = 0;
        movimientos = "";
        // Bateria A
        AlmacenarI.A11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A210.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A311.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A312.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A313.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A314.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A315.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A416.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A417.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A418.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A419.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A420.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));

        // Bateria B
    }

    public void formatoTabla() {
        int row = AlmacenarI.tblMateriaPrima.getRowCount();
        for (int i = 0; i < row; i++) {
            String fecha = cu.dateNotTime(AlmacenarI.tblMateriaPrima.getValueAt(i, 1).toString());
            AlmacenarI.tblMateriaPrima.setValueAt(fecha, i, 1);
            String pesoNeto = AlmacenarI.tblMateriaPrima.getValueAt(i, 3).toString();
            pesoNeto = cu.thousandsFormat(Double.parseDouble(pesoNeto));
            AlmacenarI.tblMateriaPrima.setValueAt(pesoNeto, i, 3);
        }
    }
}
