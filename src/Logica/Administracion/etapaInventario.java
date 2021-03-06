/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Administracion;

import java.text.SimpleDateFormat;
import Interfaces.EtapaInventario;
import static Interfaces.EtapaInventario.jfecha;
import java.sql.ResultSet;
import java.sql.Statement;
import Negocio.Conexion;
import java.util.Date;
import javax.swing.JOptionPane;
import Logica.Extras.cargarCombo;
import Logica.Extras.tablas;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.extras;

/**
 *
 * @author uriel
 */
public class etapaInventario {

    public static extras ext;
    public static EtapaInventario Procedimiento;
    public static Statement st, st2;
    public static ResultSet rs, rs2;
    public static Conexion Con;
    public DefaultTableModel modeloemp, modeloemp2, modeloemp3, modeloemp5;
    public static cargarCombo cargar;
    public String idsilo, secadora, numsilo, idsilo2, numsilo2, secadora2;
    //public String columnas[] = new String[]{"N° procedimiento", "N° silo", "Fecha", "Hora", "estado"};
    public String columnas2[] = new String[]{"Etapa", "Fecha", "Hora", "Hum"};
    public String headerEtapasProedimiento[] = new String[]{"10", "30", "30", "30"};
    public String columnas1[] = new String[]{"N°", "Bateria", "Secadora", "Silo", "Fecha", "Hora"};
    public String headerProcedimiento[] = new String[]{"5", "10", "10", "10", "50", "40"};
    public String columnas3[] = new String[]{"N°", "Bateria", "Secadora", "Silo"};
    public String headerSilo[] = new String[]{"10", "20", "35", "10"};
    public String columnas5[] = new String[]{"Etapa", "Fecha", "Hora", "Hum"};
    public String headerEtapaSilo[] = new String[]{"40", "40", "20", "5"};
    public static tablas tbl;
    public static String idSilos, idProcedimiento, idProcedimiento2;

    public etapaInventario() {
        crearModeloProcedimiento();
        crearModelo2();
        crearModeloProcedimentosSecamiento();
        alignHeadersTable();
        ext = new extras();
    }

    public void alignHeadersTable() {
        tbl.alinearHeaderTable(Procedimiento.jtablecreadas, headerProcedimiento);
        tbl.alinearHeaderTable(Procedimiento.jtablependiente, headerEtapasProedimiento);
        tbl.alinearHeaderTable(Procedimiento.jTable1, headerSilo);
        tbl.alinearHeaderTable(Procedimiento.jTable2, headerEtapaSilo);

    }

    public void guardar() {
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd");
        Date Fechainicial = Procedimiento.jfecha.getDate();
        String fecha = formato.format(Fechainicial);
        String hora = Procedimiento.jhora.getFormatedTime();
        String humedad = Procedimiento.txtHumedad.getText();
        String estado = Procedimiento.cmbestado.getSelectedItem().toString();
        if (!humedad.equals("")) {
            insertar_procedimiento(fecha, hora, humedad, estado);
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            crearModeloProcedimentosSecamiento();
            //Procedimiento.cmbestado.setSelectedIndex(0);
            limpiar_campos();
        } else {
            JOptionPane.showMessageDialog(null, "La humedad no puede estar vacia");
        }

        //String almacenamiento = Procedimiento.CmbAlmacenamiento.getSelectedItem().toString();
        String observacion = Procedimiento.TxtObs.getText();

    }

    public void insertar_procedimiento(String fecha, String hora, String humedad, String estado) {
        try {
            Con = new Conexion();

            st = Con.conexion.createStatement();
            System.out.println("idprocedimiento=" + idProcedimiento);
            st.executeUpdate("Insert Into etapa (idHistorialEtapa,idProcedimiento,etapa,fecha,hora,humedad) values (0,'" + idProcedimiento + "','" + estado + "','" + fecha + "','" + hora + "','" + humedad + "')");
            ext.logs("INSERT", "Insert Into etapa (idHistorialEtapa,idProcedimiento,etapa,fecha,hora,humedad) values (0,'" + idProcedimiento + "','" + estado + "','" + fecha + "','" + hora + "','" + humedad + "')");

            st.executeUpdate("UPDATE procedimiento SET tipoAlmacenamiento='trincho' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");
            ext.logs("UPDATE", "UPDATE procedimiento SET tipoAlmacenamiento='trincho' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");

            refrescar();
            crearModeloProcedimiento();

            crearModelo2();

            Con.Desconectar();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void tabla_campos() {
        int rec = Procedimiento.jtablecreadas.getSelectedRow();
        idProcedimiento = "";
        idProcedimiento = Procedimiento.jtablecreadas.getValueAt(rec, 0).toString();

        //  Procedimiento.txtFecha.setText(Procedimiento.jtablecreadas.getValueAt(rec, 2).toString());
        // Procedimiento.txtHora.setText(Procedimiento.jtablecreadas.getValueAt(rec, 3).toString());
//        Procedimiento.txtFecha.setText(Procedimiento.jtablecreadas.getValueAt(rec, 2).toString());
//        Procedimiento.txtHora.setText(Procedimiento.jtablecreadas.getValueAt(rec, 3).toString());
        String silo = Procedimiento.jtablecreadas.getValueAt(rec, 1).toString();
        idsilo = Procedimiento.jtablecreadas.getValueAt(rec, 1).toString();
        numsilo(silo);
    }

    public void numsilo(String idsilo) {
        try {

            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            rs2 = st2.executeQuery("SELECT silos.numero FROM silos WHERE silos.idSilos = '" + idsilo + "'");
            while (rs2.next()) {
                idSilos = rs2.getObject(1) + "";
                Procedimiento.TxtSilo.setText(idSilos);
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refrescar() {
        Date datep = new Date();
        Procedimiento.jfecha.setDate(datep);
        Procedimiento.jhora.setTime(datep);

        Procedimiento.txtHumedad.setText("");

        Procedimiento.txtHumedad.setText("");

    }

    public void crearModeloProcedimiento() {
        modeloemp = new DefaultTableModel(null, columnas1) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablecreadas, modeloemp, columnas1.length, "SELECT procedimiento.idProcedimiento,bateria.nombre,secadora.nombre,silos.numero,procedimiento.fecha,procedimiento.hora from bateria,procedimiento,silos,secadora where procedimiento.idSilos=silos.idSilos and silos.idSecadora=secadora.idSecadora and secadora.idBateria=bateria.idBateria and procedimiento.estado='proceso' and procedimiento.observacion is null");
        tbl.alinearHeaderTable(Procedimiento.jtablecreadas, headerProcedimiento);
    }

    public void Datos_Campos_Procedimientos() {
        int rec = Procedimiento.jtablecreadas.getSelectedRow();
        idProcedimiento = "";
        idProcedimiento = Procedimiento.jtablecreadas.getValueAt(rec, 0).toString();
        //System.out.println("El id del procedimiento  es: " + idProcedimiento);
        Procedimiento.TxtBateria.setText(Procedimiento.jtablecreadas.getValueAt(rec, 1).toString());
        Procedimiento.TxtSecadora.setText(Procedimiento.jtablecreadas.getValueAt(rec, 2).toString());
        Procedimiento.TxtSilo.setText(Procedimiento.jtablecreadas.getValueAt(rec, 3).toString());
        secadora = Procedimiento.jtablecreadas.getValueAt(rec, 2).toString();
        numsilo = Procedimiento.jtablecreadas.getValueAt(rec, 3).toString();
        getIdSilo();

        crearModelo2();

    }

    public void Datos_Campos_Procedimientos2() {
        int rec = Procedimiento.jTable1.getSelectedRow();
        idProcedimiento2 = "";
        idProcedimiento2 = Procedimiento.jTable1.getValueAt(rec, 0).toString();
        crearModeloEtapasSeco(idProcedimiento2);
        llenarTabla(idProcedimiento2);
        secadora2 = Procedimiento.jTable1.getValueAt(rec, 2).toString();
        numsilo2 = Procedimiento.jTable1.getValueAt(rec, 3).toString();
        getIdSilo2();

    }

    public void crearModelo2() {
        modeloemp2 = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jtablependiente, modeloemp2, columnas2.length, "SELECT etapa,fecha,hora,humedad FROM etapa WHERE etapa = 'secamiento' and idProcedimiento='" + idProcedimiento + "' ");
        tbl.alinearHeaderTable(Procedimiento.jtablependiente, headerEtapasProedimiento);
    }

    public void crearModeloProcedimentosSecamiento() {
        modeloemp3 = new DefaultTableModel(null, columnas3) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jTable1, modeloemp3, columnas3.length, "SELECT procedimiento.idProcedimiento,bateria.nombre,secadora.nombre,silos.numero from bateria,procedimiento,silos,secadora,etapa,tiqueteensilos where procedimiento.idSilos=silos.idSilos and silos.idSilos=tiqueteensilos.idSilos and silos.idSecadora=secadora.idSecadora and secadora.idBateria=bateria.idBateria and procedimiento.idProcedimiento=etapa.idProcedimiento and procedimiento.estado='proceso' and tiqueteensilos.estado='seco' GROUP BY procedimiento.idProcedimiento");
        tbl.alinearHeaderTable(Procedimiento.jTable1, headerSilo);
    }

    public void crearModeloEtapasSeco(String id) {
        modeloemp5 = new DefaultTableModel(null, columnas5) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Procedimiento.jTable2, modeloemp5, columnas5.length, "SELECT etapa,fecha,hora,humedad FROM etapa WHERE etapa.idProcedimiento='" + id + "'");
        tbl.alinearHeaderTable(Procedimiento.jTable2, headerEtapaSilo);
    }

    public void llenarTabla(String idPro) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("select observacion from procedimiento where idProcedimiento= '" + idPro + "' ");
            //ResultSetMetaData rsc3md = rsc3.getMetaData();
            while (rs.next()) {

                Procedimiento.TxtObservacion.setText(rs.getString(1));
            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar_campos() {
        // Procedimiento.TxtObs.setText("");
        Procedimiento.TxtObservacion.setText("");
        Procedimiento.TxtSecadora.setText("");
        Procedimiento.TxtSilo.setText("");
        Procedimiento.txtHumedad.setText("");
        Procedimiento.TxtBateria.setText("");

        // crearModeloEtapasSeco("0");
    }

    public void actualizar_procedimiento() {
        String estado = Procedimiento.cmbestado.getSelectedItem().toString();
        String silo = Procedimiento.TxtSilo.getText();

        String observacion = Procedimiento.TxtObs.getText();

        if (estado.equals("Seco")) {

            try {
                Con = new Conexion();

                st = Con.conexion.createStatement();

                st.executeUpdate("UPDATE procedimiento SET observacion='" + observacion + "' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");
                ext.logs("UPDATE", "UPDATE procedimiento SET observacion='" + observacion + "' Where procedimiento.idProcedimiento='" + idProcedimiento + "'");
                st.executeUpdate("UPDATE tiqueteensilos SET estado='seco' Where tiqueteensilos.idSilos='" + idsilo + "' and tiqueteensilos.estado='secamiento'");
                ext.logs("UPDATE", "UPDATE procedimiento SET estado='finalizado'  Where procedimiento.idProcedimiento='" + idProcedimiento + "'");

                JOptionPane.showMessageDialog(null, "El registro ha sido agregado");

                crearModeloProcedimiento();
                crearModeloProcedimentosSecamiento();
                idProcedimiento = "0";
                crearModelo2();
                Procedimiento.cmbestado.setSelectedIndex(0);
                Procedimiento.TxtObs.setText("");
                limpiar_campos();
                Con.Desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo observacion debe contener información");
            limpiar_campos();
        }

    }

    public void getIdSilo() {
        try {

            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            rs2 = st2.executeQuery("SELECT silos.idSilos FROM silos,secadora WHERE silos.numero = '" + numsilo + "' and secadora.nombre = '" + secadora + "' and silos.idSecadora=secadora.idSecadora");
            while (rs2.next()) {
                idsilo = rs2.getObject(1) + "";

            }
            System.out.println("id silo = " + idsilo);

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getIdSilo2() {
        try {

            Con = new Conexion();
            st2 = Con.conexion.createStatement();
            rs2 = st2.executeQuery("SELECT silos.idSilos FROM silos,secadora WHERE silos.numero = '" + numsilo2 + "' and secadora.nombre = '" + secadora2 + "' and silos.idSecadora=secadora.idSecadora");
            while (rs2.next()) {
                idsilo2 = rs2.getObject(1) + "";

            }
            System.out.println("id silo = " + idsilo);

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void guardar_actualizar() {

        String observacion = Procedimiento.TxtObs.getText();
        if (Procedimiento.cmbestado.getSelectedIndex() == 0) {
            guardar();
            idProcedimiento = "";
            crearModelo2();
        } else {
            guardar();
            actualizar_procedimiento();
        }
    }
    
    public void desactivarBtnAgregar(){
        if (!Procedimiento.TxtBateria.getText().equals("")) {
            Procedimiento.btnguardar.setEnabled(true);
        }else{
            Procedimiento.btnguardar.setEnabled(false);
        }
    }

    public void trillar() {

        if (idProcedimiento2 == "") {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un procedimiento a trillar");
        } else {
            int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que desea enviar a trillar", "Confirmacion", JOptionPane.CANCEL_OPTION);
            if (aceptar == JOptionPane.YES_OPTION) {
                try {
                    Con = new Conexion();

                    st = Con.conexion.createStatement();
                    System.out.println("idsilo" + idsilo);
                    System.out.println("idsilo2" + idsilo2);
                    st.executeUpdate("UPDATE procedimiento SET estado='finalizado'  Where procedimiento.idProcedimiento='" + idProcedimiento2 + "'");
                    st.executeUpdate("UPDATE silos SET kilos='0.00', estado='vacio' Where silos.idSilos='" + idsilo2 + "'");
                    st.executeUpdate("UPDATE tiqueteensilos SET estado='trilla' Where tiqueteensilos.idSilos='" + idsilo2 + "' and tiqueteensilos.estado='seco'");

                    JOptionPane.showMessageDialog(null, "El silo ha sido trillado");

                    crearModeloProcedimiento();
                    crearModeloProcedimentosSecamiento();

                    Con.Desconectar();
                    idProcedimiento = "0";
                    crearModeloEtapasSeco(idProcedimiento);
                    crearModelo2();
                    limpiar_campos();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los cambios han sido descartados");
            }
        }

    }

}
