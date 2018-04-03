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
import Logica.Extras.extras;
import Logica.Extras.tablas;
import Negocio.Conexion;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
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
    public static extras ext;
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    String columnasTabla[] = new String[]{"N", "Fecha", "Agricultor", "Kilos Netos"};
    String alineaHeader[] = new String[]{"10", "40", "150", "40"};
    String alineaCampo[] = new String[]{"center", "center", "left", "right"};
    DefaultTableModel modeloTabla;
    String idTiquete = "", movimientos = "";
    int lastIndex;
    double pesoActual = 0;
    double A1[][] = new double[5][4];
    double A2[][] = new double[10][4];
    double A3[][] = new double[5][4];
    double B4[][] = new double[6][4];
    double B5[][] = new double[6][4];
    double B6[][] = new double[6][4];
    double B7[][] = new double[6][4];
    String estados[] = new String[7];

    currencyFormat cu;

    public almacenarInventario() {
        tbl = new tablas();
        cu = new currencyFormat();
        ext = new extras();
        AlmacenarI.rbtnGuiado.setSelected(true);
        crearModelo();
        cargarSilos();
        print();
        cargarSecadoras();
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
            rs = st.executeQuery(" SELECT idSilos,kilos,0,capacidad,capacidad FROM silos,secadora,bateria WHERE bateria.nombre='A' AND secadora.nombre='A1' AND secadora.idBateria=bateria.idBateria AND silos.idSecadora=secadora.idSecadora");
            int contador = 0;
            while (rs.next()) {
                for (int i = 0; i < A1[contador].length; i++) {
                    A1[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0,capacidad,capacidad FROM silos,secadora,bateria WHERE bateria.nombre='A' AND secadora.nombre='A2' AND secadora.idBateria=bateria.idBateria AND silos.idSecadora=secadora.idSecadora");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < A2[contador].length; i++) {
                    A2[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0,capacidad,capacidad FROM silos,secadora,bateria WHERE bateria.nombre='A' AND secadora.nombre='A3' AND secadora.idBateria=bateria.idBateria AND silos.idSecadora=secadora.idSecadora");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < A3[contador].length; i++) {
                    A3[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0,capacidad,capacidad FROM silos,secadora,bateria WHERE bateria.nombre='B' AND secadora.nombre='B4' AND secadora.idBateria=bateria.idBateria AND silos.idSecadora=secadora.idSecadora");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < B4[contador].length; i++) {
                    B4[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0,capacidad,capacidad FROM silos,secadora,bateria WHERE bateria.nombre='B' AND secadora.nombre='B5' AND secadora.idBateria=bateria.idBateria AND silos.idSecadora=secadora.idSecadora");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < B5[contador].length; i++) {
                    B5[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0,capacidad FROM silos,secadora,bateria WHERE bateria.nombre='B' AND secadora.nombre='B6' AND secadora.idBateria=bateria.idBateria AND silos.idSecadora=secadora.idSecadora");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < B6[contador].length; i++) {
                    B6[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            rs = st.executeQuery(" SELECT idSilos,kilos,0,capacidad FROM silos,secadora,bateria WHERE bateria.nombre='B' AND secadora.nombre='B7' AND secadora.idBateria=bateria.idBateria AND silos.idSecadora=secadora.idSecadora");
            contador = 0;
            while (rs.next()) {
                for (int i = 0; i < B7[contador].length; i++) {
                    B7[contador][i] = Double.parseDouble(rs.getString(i + 1).toString());
                }
                contador++;
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cargarSecadoras() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery(" SELECT estado FROM secadora");
            int contador = 0;
            while (rs.next()) {
                estados[contador] = rs.getString(1).toString();
                contador++;
            }
            Con.Desconectar();
            for (int i = 0; i < estados.length; i++) {
                switch (i) {
                    case 0:
                        if (estados[i].equals("apagado")) {
                            AlmacenarI.lblLedA1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                        } else {
                            AlmacenarI.lblLedA1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                        }
                        break;
                    case 1:
                        if (estados[i].equals("apagado")) {
                            AlmacenarI.lblLedA2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                        } else {
                            AlmacenarI.lblLedA2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                        }
                        break;
                    case 2:
                        if (estados[i].equals("apagado")) {
                            AlmacenarI.lblLedA3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                        } else {
                            AlmacenarI.lblLedA3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                        }
                        break;
                    case 3:
                        if (estados[i].equals("apagado")) {
                            AlmacenarI.lblLedB4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                        } else {
                            AlmacenarI.lblLedB4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                        }
                        break;
                    case 4:
                        if (estados[i].equals("apagado")) {
                            AlmacenarI.lblLedB5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                        } else {
                            AlmacenarI.lblLedB5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                        }
                        break;
                    case 5:
                        if (estados[i].equals("apagado")) {
                            AlmacenarI.lblLedB6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                        } else {
                            AlmacenarI.lblLedB6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                        }
                        break;
                    case 6:
                        if (estados[i].equals("apagado")) {
                            AlmacenarI.lblLedB7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                        } else {
                            AlmacenarI.lblLedB7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValueSilo(JProgressBar progressBar, double silo[][], int fila) {
        if (silo[fila][2] == 0) {
            progressBar.setString(String.valueOf(silo[fila][1]));
            double percent = ((silo[fila][1] * 100) / silo[fila][3]);
            progressBar.setValue((int) percent);
        } else {
            progressBar.setString(String.valueOf(silo[fila][2]));
            double percent = ((silo[fila][2] * 100) / silo[fila][3]);
            progressBar.setValue((int) percent);
        }
    }

    public void print() {
        //---- secadora A2 ---\\\
        setValueSilo(AlmacenarI.A11, A1, 0);
        setValueSilo(AlmacenarI.A12, A1, 1);
        setValueSilo(AlmacenarI.A13, A1, 2);
        setValueSilo(AlmacenarI.A14, A1, 3);
        setValueSilo(AlmacenarI.A15, A1, 4);

        //---- secadora A2 ---\\\
        setValueSilo(AlmacenarI.A26, A2, 0);
        setValueSilo(AlmacenarI.A27, A2, 1);
        setValueSilo(AlmacenarI.A28, A2, 2);
        setValueSilo(AlmacenarI.A29, A2, 3);
        setValueSilo(AlmacenarI.A210, A2, 4);
        setValueSilo(AlmacenarI.A211, A2, 5);
        setValueSilo(AlmacenarI.A212, A2, 6);
        setValueSilo(AlmacenarI.A213, A2, 7);
        setValueSilo(AlmacenarI.A214, A2, 8);
        setValueSilo(AlmacenarI.A215, A2, 9);

        //---- secadora A3 ---\\\
        setValueSilo(AlmacenarI.A316, A3, 0);
        setValueSilo(AlmacenarI.A317, A3, 1);
        setValueSilo(AlmacenarI.A318, A3, 2);
        setValueSilo(AlmacenarI.A319, A3, 3);
        setValueSilo(AlmacenarI.A320, A3, 4);

        //---- secadora B4 ---\\\
        setValueSilo(AlmacenarI.B419, B4, 0);
        setValueSilo(AlmacenarI.B420, B4, 1);
        setValueSilo(AlmacenarI.B421, B4, 2);
        setValueSilo(AlmacenarI.B422, B4, 3);
        setValueSilo(AlmacenarI.B423, B4, 4);
        setValueSilo(AlmacenarI.B424, B4, 5);

        //---- secadora B5 ---\\\
        setValueSilo(AlmacenarI.B513, B5, 0);
        setValueSilo(AlmacenarI.B514, B5, 1);
        setValueSilo(AlmacenarI.B515, B5, 2);
        setValueSilo(AlmacenarI.B516, B5, 3);
        setValueSilo(AlmacenarI.B517, B5, 4);
        setValueSilo(AlmacenarI.B518, B5, 5);

        //---- secadora B6 ---\\\
        setValueSilo(AlmacenarI.B67, B6, 0);
        setValueSilo(AlmacenarI.B68, B6, 1);
        setValueSilo(AlmacenarI.B69, B6, 2);
        setValueSilo(AlmacenarI.B610, B6, 3);
        setValueSilo(AlmacenarI.B611, B6, 4);
        setValueSilo(AlmacenarI.B612, B6, 5);

        //---- secadora B7 ---\\\
        setValueSilo(AlmacenarI.B71, B7, 0);
        setValueSilo(AlmacenarI.B72, B7, 1);
        setValueSilo(AlmacenarI.B73, B7, 2);
        setValueSilo(AlmacenarI.B74, B7, 3);
        setValueSilo(AlmacenarI.B75, B7, 4);
        setValueSilo(AlmacenarI.B76, B7, 5);

    }

    public void tablaCampos() {
        int rec = AlmacenarI.tblMateriaPrima.getSelectedRow();
        String idtiquete = AlmacenarI.tblMateriaPrima.getValueAt(rec, 0).toString();
        if (!idTiquete.equals("") && !movimientos.equals("")) {
            if (!idTiquete.equals(idtiquete)) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿desea cambiar de tiquete?", "Confirmación", JOptionPane.CANCEL_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    seleccionarFila(rec, idtiquete);
                } else {
                    AlmacenarI.tblMateriaPrima.setRowSelectionInterval(lastIndex, lastIndex);
                }
            }
        } else {
            seleccionarFila(rec, idtiquete);
        }
    }

    public void seleccionarFila(int rec, String idtiquete) {
        limpiar();
        idTiquete = idtiquete;
        AlmacenarI.lblPesoActual.setText(AlmacenarI.tblMateriaPrima.getValueAt(rec, 3).toString());
        pesoActual = Double.parseDouble(cu.notThousandsFormat(AlmacenarI.lblPesoActual.getText()));
        cargarSilos();
        print();
        lastIndex = rec;
    }

    public void seleccionarSilo(JProgressBar progressBar) {
        String silo = progressBar.getName();
        llenarSilo(progressBar, getSilo(silo), getRowSilo(silo));
    }

    public boolean verificarContenidoSecadoras(int secadora) {
        double silos[][] = new double[0][0];
        switch (secadora) {
            case 0:
                silos = getSilo("A1");
                break;
            case 1:
                silos = getSilo("A2");
                break;
            case 2:
                silos = getSilo("A3");
                break;
            case 3:
                silos = getSilo("B4");
                break;
            case 4:
                silos = getSilo("B5");
                break;
            case 5:
                silos = getSilo("B6");
                break;
            case 6:
                silos = getSilo("B7");
                break;

        }
        for (int i = 0; i < silos.length; i++) {
            if (silos[i][1] != 0 || silos[i][2] != 0) {
                return false;
            }
        }
        return true;
    }

    public void switchSecadora(JLabel label) {
        int secadora = getNumberSecadora(label.getName(), 1);
        if (pesoActual > 0) {
            if (estados[secadora].equals("apagado")) {
                int respuesta = JOptionPane.showConfirmDialog(null, "La secadora esta apagada, ¿Desea encenderla? \nRecuerde el consumo energetico", "Confirmación", JOptionPane.CANCEL_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    estados[secadora] = "encendido";
                    label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
            } else if (verificarContenidoSecadoras(secadora)) {
                estados[secadora] = "apagado";
                label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
            } else {
                JOptionPane.showMessageDialog(null, "No se puede apagar la secadora porque los silos tienen contenido");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe tener kilos para manipular la secadora");
        }
    }

    public void llenarSilo(JProgressBar progressBar, double silo[][], int fila) {
        String nameSilo = progressBar.getName();
        int secadora = getNumberSecadora(nameSilo, 2);
        if (silo[fila][1] < silo[fila][3]) {
            if (silo[fila][2] == 0) {
                if (pesoActual > 0) {
                    if (!estados[secadora].equals("encendido")) {
                        JOptionPane.showMessageDialog(null, "La secadora esta apagado por favor enciendala dando clic en el indicador");
                    } else if (AlmacenarI.rbtnGuiado.isSelected()) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "¿desea llenar este silo?", "Confirmación", JOptionPane.CANCEL_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            double espacio = silo[fila][3] - silo[fila][1];
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
                    } else {
                        String respuesta = JOptionPane.showInputDialog("Digite los kilos a ingresar al silo");
                        if (!respuesta.equals("") && respuesta != null) {
                            if (ext.isNumeric(respuesta)) {
                                double espacio = silo[fila][3] - silo[fila][1];
                                double kilos = Double.parseDouble(respuesta);
                                if (kilos <= pesoActual) {
                                    if (kilos <= espacio) {
                                        silo[fila][2] = silo[fila][1] + kilos;
                                        pesoActual -= kilos;
                                        movimientos = movimientos + "," + nameSilo;
                                        progressBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 0), new java.awt.Color(255, 51, 0), new java.awt.Color(255, 51, 0), new java.awt.Color(255, 51, 0)));
                                        AlmacenarI.lblPesoActual.setText(String.valueOf(pesoActual));
                                        print();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Los kilos ingresados son menores al peso actual");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Los kilos deben ser datos numericos");
                            }
                        }
                    }
                }
            } else {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿desea revertir cambios en este silo?", "Confirmación", JOptionPane.CANCEL_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    pesoActual += silo[fila][2] - silo[fila][1];
                    silo[fila][2] = 0;
                    movimientos = movimientos.replace("," + nameSilo + ",", "");
                    progressBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
                    AlmacenarI.lblPesoActual.setText(cu.thousandsFormat(pesoActual));
                    if (verificarContenidoSecadoras(secadora)) {
                        estados[secadora] = "apagado";
                        printLabelLed(secadora, 2);
                    }
                    print();
                }
            }
        }

    }

    private void printLabelLed(int secadora, int opc) {
        switch (secadora) {
            case 0:
                if (opc == 2) {
                    AlmacenarI.lblLedA1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                } else {
                    AlmacenarI.lblLedA1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
                break;
            case 1:
                if (opc == 2) {
                    AlmacenarI.lblLedA2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                } else {
                    AlmacenarI.lblLedA2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
                break;
            case 2:
                if (opc == 2) {
                    AlmacenarI.lblLedA3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                } else {
                    AlmacenarI.lblLedA3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
                break;
            case 3:
                if (opc == 2) {
                    AlmacenarI.lblLedB4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                } else {
                    AlmacenarI.lblLedB4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
                break;
            case 4:
                if (opc == 2) {
                    AlmacenarI.lblLedB5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                } else {
                    AlmacenarI.lblLedB5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
                break;
            case 5:
                if (opc == 2) {
                    AlmacenarI.lblLedB6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                } else {
                    AlmacenarI.lblLedB6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
                break;
            case 6:
                if (opc == 2) {
                    AlmacenarI.lblLedB7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/red circle.png")));
                } else {
                    AlmacenarI.lblLedB7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/green circle.png")));
                }
                break;
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
            guardarEstadoSecadora();
            crearModelo();
            limpiar();
            JOptionPane.showMessageDialog(null, "Almacenamiento exitoso");
        }
    }

    private void guardarEstadoSecadora() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            for (int i = 0; i < estados.length; i++) {
                int idSecadora = i + 1;
                if (!verificarContenidoSecadoras(idSecadora)) {
                    st.executeUpdate("UPDATE secadora SET estado='" + estados[i] + "' WHERE idSecadora='" + idSecadora + "';");
                }else{
                    
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guadar");
            e.printStackTrace();
        }
    }

    private void insert(double silo[][], int fila) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            String idSilo = String.valueOf((int) (silo[fila][0]));
            double kilos = silo[fila][2] - silo[fila][1];
            String estadoSilo = "vacio";
            if (silo[fila][2] == silo[fila][3]) {
                estadoSilo = "lleno";
            } else {
                estadoSilo = "contenido";
            }
            st.executeUpdate("INSERT INTO tiqueteensilos (idTiqueteSilos, idTiquete, idSilos, kilos, estado) VALUES(0,'" + idTiquete + "','" + idSilo + "','" + kilos + "','secamiento')");
            st.executeUpdate("UPDATE silos SET kilos='" + silo[fila][2] + "',estado='" + estadoSilo + "' WHERE idSilos='" + idSilo + "';");
            ext.logs("INSERT INTO tiqueteensilos (idTiqueteSilos, idTiquete, idSilos, kilos, estado) VALUES(0,'" + idTiquete + "','" + idSilo + "','" + kilos + "','secamiento')");
            ext.logs("UPDATE silos SET kilos='" + silo[fila][2] + "',estado='" + estadoSilo + "' WHERE idSilos='" + idSilo + "';");

            Con.Desconectar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guadar");
            e.printStackTrace();
        }
    }

// retorna la matriz segun el silo
    public double[][] getSilo(String silo) {
        String secadora = silo.substring(0, 2);
        switch (secadora) {
            case "A1":
                return A1;
            case "A2":
                return A2;
            case "A3":
                return A3;
            case "B4":
                return B4;
            case "B5":
                return B5;
            case "B6":
                return B6;
            case "B7":
                return B7;
        }
        return null;
    }
// retorna el entero perteneciente a la secadora donde se aloja el silo

    public int getNumberSecadora(String silo, int opc) {
        if (opc == 1) {
            String secadora = silo.substring(6, 8);
            switch (secadora) {
                case "A1":
                    return 0;
                case "A2":
                    return 1;
                case "A3":
                    return 2;
                case "B4":
                    return 3;
                case "B5":
                    return 4;
                case "B6":
                    return 5;
                case "B7":
                    return 6;
            }
        } else {
            String secadora = silo.substring(0, 2);
            switch (secadora) {
                case "A1":
                    return 0;
                case "A2":
                    return 1;
                case "A3":
                    return 2;
                case "B4":
                    return 3;
                case "B5":
                    return 4;
                case "B6":
                    return 5;
                case "B7":
                    return 6;
            }
        }
        return 99999;
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
            case "A211":
                return 5;
            case "A212":
                return 6;
            case "A213":
                return 7;
            case "A214":
                return 8;
            case "A215":
                return 9;
            case "A316":
                return 0;
            case "A317":
                return 1;
            case "A318":
                return 2;
            case "A319":
                return 3;
            case "A320":
                return 4;
            //Secadora B4 \\\
            case "B419":
                return 0;
            case "B420":
                return 1;
            case "B421":
                return 2;
            case "B422":
                return 3;
            case "B423":
                return 4;
            case "B424":
                return 5;
            //Secadora B5 \\\
            case "B513":
                return 0;
            case "B514":
                return 1;
            case "B515":
                return 2;
            case "B516":
                return 3;
            case "B517":
                return 4;
            case "B518":
                return 5;
            //Secadora B6 \\\
            case "B67":
                return 0;
            case "B68":
                return 1;
            case "B69":
                return 2;
            case "B610":
                return 3;
            case "B611":
                return 4;
            case "B612":
                return 5;
            //Secadora B7 \\\
            case "B71":
                return 0;
            case "B72":
                return 1;
            case "B73":
                return 2;
            case "B74":
                return 3;
            case "B75":
                return 4;
            case "B76":
                return 5;
        }
        return 9999;
    }

    public void limpiar() {
        pesoActual = 0;
        movimientos = "";
        idTiquete = "";
        cargarSecadoras();
        // Bateria A
        AlmacenarI.A11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A210.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A211.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A212.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A213.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A214.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A215.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A316.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A317.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A318.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A319.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.A320.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        // Bateria B
        AlmacenarI.B419.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B420.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B421.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B422.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B423.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B424.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B513.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B514.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B515.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B516.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B517.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B518.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B67.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B68.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B69.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B610.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B611.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B612.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B71.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B72.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B73.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B74.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B75.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
        AlmacenarI.B76.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));
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
