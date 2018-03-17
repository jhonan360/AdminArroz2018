/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;

import Negocio.Conexion;
import Interfaces.Laboratorio;
import static Logica.Administracion.empleado.Con;
import static Logica.Administracion.empleado.Emp;
import Logica.Extras.login;
import Logica.Extras.tablas;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Interfaces.BusquedasTiqueteInicial;
import lu.tudor.santec.jtimechooser.JTimeChooser;
import Logica.Extras.currencyFormat;

/**
 *
 * @author uriel
 */
public class laboratorio {

    public static Laboratorio Laboratorio;
    public static BusquedasTiqueteInicial BusInicial;
    public static tablas tbl;
    public DefaultTableModel modeloemp, modeloestufa, modelo3;
    public login login;
    public String columnas[] = new String[]{"N° Laboratorio", "N° Tiquete", "Fecha", "Humedad", "Impureza", "Integral", "Cascarilla", "Blanco", "Partido", "Entero", "Yeso", "Dañado", "IP"};
    public String columnas2[] = new String[]{"Muestreo", "Hora", "Humedad"};
    public String columnas3[] = new String[]{"N° Tiquete", "Agricultor", "Fecha"};
    public static currencyFormat cu;
    public String user;
    public static Statement st, st3, st4, st5;
    public static ResultSet rs, rs3, rsconsecutivo, rscantidaad;
    public static Conexion Con;
    public String IDLaboratorio, muestreo, hora, humedadEstufa, res;
    public String maxMuestreo, idTiquete, fecha, humedad, impureza, integralRes, cascarillaRes, blancoRes, partidoRes, enteroRes, yeso, danado, ip, consecutivo;
    public int cantEstado;

    public laboratorio() {
        fecha();
        crearModelo();
        crearModelo3();
        consecutivo();
        campos_desabilitados();
        // time();
    }

    public void busqueda() {
        BusInicial = new BusquedasTiqueteInicial();
        BusInicial.setVisible(true);
    }

    public void crearModelo() {
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Laboratorio.jTable1, modeloemp, columnas.length, "SELECT idLaboratorio,idTiquete,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio WHERE laboratorio.estado='abierto'");
        // formatoTbFecha();
    }

    public void crearModelo2(String id) {
        modeloestufa = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Laboratorio.jTable4, modeloestufa, columnas2.length, "SELECT muestreo,hora,humedad FROM muetraestufa where idLaboratorio ='" + id + "' ");

    }

    public void crearModelo3() {
        modelo3 = new DefaultTableModel(null, columnas3) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl = new tablas();
        tbl.llenarTabla(Laboratorio.jTable3, modelo3, columnas3.length, "select idTiquete, CONCAT(personalexterno.nombres, personalexterno.apellidos),fecha from tiquete,personalexterno where idTiquete not in (select idTiquete from laboratorio group by idTiquete) and tiquete.idAgricultor = personalexterno.idPersonalExterno ORDER BY idTiquete asc");

    }

    public void maxMuestreo(String id) {

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT max(muetraestufa.muestreo) FROM muetraestufa WHERE muetraestufa.idLaboratorio='"+id+"'");
            //ResultSetMetaData rsc3md = rsc3.getMetaData();
            while (rs.next()) {

                maxMuestreo = rs.getString(1);
                if(maxMuestreo==null){
                    Laboratorio.TxtMuestreo.setText(Integer.toString(1));
                }else{
                     System.out.println("MAX MUESTREO = "+maxMuestreo);
                int numEntero = Integer.parseInt(maxMuestreo) + 1;

                Laboratorio.TxtMuestreo.setText(Integer.toString(numEntero));
                }
               

            }

            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void estado_tiquete() {
        try {
            String id = Laboratorio.TxtIDTiqueteEstufa.getText();
            Con = new Conexion();

            st5 = Con.conexion.createStatement();
            rscantidaad = st5.executeQuery("SELECT estado FROM laboratorio WHERE idLaboratorio = '" + id + "'");
            while (rscantidaad.next()) {
                res = rscantidaad.getString(1);
                System.out.println("res+" + res);

            }
            if (res.equals("cerrado")) {
                JOptionPane.showMessageDialog(null, "No se pueden agregar mas registros");

            } else {
                insertar_humedad_estufa(IDLaboratorio, muestreo, hora, humedadEstufa);
                crearModelo2(IDLaboratorio);
            }

            Con.Desconectar();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void formatoTbFecha() {
        int row = Laboratorio.jTable1.getRowCount();
        System.out.println("Row " + row);
        for (int i = 0; i < row; i++) {
            System.out.println(Laboratorio.jTable1.getValueAt(i, 2));
            String fecha = cu.dateNotTime(Laboratorio.jTable1.getValueAt(i, 2) + "");
            Laboratorio.jTable1.setValueAt(fecha, i, 2);

        }
    }

    public void estado() {
        int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere cerrar el tiquete", "Confirmacion", JOptionPane.CANCEL_OPTION);
        if (aceptar == JOptionPane.YES_OPTION) {
            try {
                String id = Laboratorio.TxtIDTiqueteEstufa.getText();
                Con = new Conexion();
                st = Con.conexion.createStatement();
                st.executeUpdate("UPDATE laboratorio SET estado = 'cerrado' WHERE idLaboratorio ='" + id + "'");
                JOptionPane.showMessageDialog(null, "El tiquete fue cerrado");
                actualizar();
                crearModelo2(IDLaboratorio);

                Con.Desconectar();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los cambios han sido descartados");
        }

    }

    public void calculos() {
        double integral = 0.0;
        if (!Laboratorio.TxtIntegral.getText().equals("")) {
            integral = Double.parseDouble(Laboratorio.TxtIntegral.getText());
            double cascarilla = 1000 - integral;
            Laboratorio.TxtCascarilla.setText(String.valueOf(cascarilla));
        } else {
            JOptionPane.showMessageDialog(null, "El campo Integral no puede quedar vacio");
        }

    }

    public void calculo_entero() {
        double partido = 0.0;
        if (!Laboratorio.TxtPartido.getText().equals("")) {
            partido = Double.parseDouble(Laboratorio.TxtPartido.getText());
            double entero = 100 - partido;
            Laboratorio.enteroRes.setText(String.valueOf(entero));
            calculo_ip(entero);
        } else {
            JOptionPane.showMessageDialog(null, "El campo partido no se puede encontrar vacio");
        }

    }

    public void calculo_ip(double entero) {
        double ip = 0.0;
        double blanco = 0.0;
        blanco = Double.parseDouble(Laboratorio.TxtBlanco.getText());
        ip = (entero * blanco) / 1000;
        Laboratorio.TxtIp.setText(String.valueOf(ip));
    }

    public void campos_desabilitados() {
        //Laboratorio.lblFecha.setEnabled(false);
        Laboratorio.TxtHumedad.setEnabled(false);
        Laboratorio.TxtImpureza.setEnabled(false);
        Laboratorio.TxtIntegral.setEnabled(false);
        Laboratorio.TxtBlanco.setEnabled(false);
        Laboratorio.TxtPartido.setEnabled(false);
        Laboratorio.TxtYeso.setEnabled(false);
        Laboratorio.TxtIp.setEnabled(false);
        Laboratorio.TxtIntegral.setEnabled(false);
        Laboratorio.TxtBlanco.setEnabled(false);
        Laboratorio.enteroRes.setEnabled(false);
        Laboratorio.TxtIdTiquete.setEnabled(false);
        Laboratorio.TxtCascarilla.setEnabled(false);
        Laboratorio.TxtDanado.setEnabled(false);

    }

    public void campos_habilitados() {

        Laboratorio.TxtHumedad.setEnabled(true);
        Laboratorio.TxtImpureza.setEnabled(true);
        Laboratorio.TxtIntegral.setEnabled(true);
        Laboratorio.TxtBlanco.setEnabled(true);
        Laboratorio.TxtPartido.setEnabled(true);
        Laboratorio.TxtYeso.setEnabled(true);
        Laboratorio.TxtIp.setEnabled(true);
        Laboratorio.TxtIntegral.setEnabled(true);
        Laboratorio.TxtBlanco.setEnabled(true);
        Laboratorio.enteroRes.setEnabled(true);
        Laboratorio.TxtIdTiquete.setEnabled(true);
        Laboratorio.TxtCascarilla.setEnabled(true);
        Laboratorio.TxtDanado.setEnabled(true);

    }

    public void idTiquete_campo() {
        int rec = Laboratorio.jTable3.getSelectedRow();
        Laboratorio.TxtIdTiquete.setText(Laboratorio.jTable3.getValueAt(rec, 0).toString());
        campos_habilitados();
    }

    public void fecha() {
        Calendar c;
        c = Calendar.getInstance();
        int d = c.get(Calendar.DATE), m = 1 + (c.get(Calendar.MONTH)), a = c.get(Calendar.YEAR);
        Laboratorio.lblFecha.setText(a + "/" + m + "/" + d);

    }

    public void tablaCamposLiquidacion() {
        //  limpiar_campos();
        int rec = Laboratorio.jTable3.getSelectedRow();
        Laboratorio.TxtIdTiquete.setText(Laboratorio.jTable3.getValueAt(rec, 0).toString());
        campos_habilitados();
        String idlaboratorio = Laboratorio.jTable3.getValueAt(rec, 0).toString();
        // idLiquidacion = LiqAprobadas.tblLiquidaciones.getValueAt(rec, 0).toString();
        //LiqAprobadas.lblNumLiquidacion.setText(idLiquidacion);
        //LiqAprobadas.lblNomAgricultor.setText(LiqAprobadas.tblLiquidaciones.getValueAt(rec, 3).toString());
        /**
         * try { Con = new Conexion(); st = Con.conexion.createStatement(); rs =
         * st.executeQuery("SELECT
         * laboratorio.integralRes,laboratorio.cascarillaRes,laboratorio.blancoRes,laboratorio.partidoRes,laboratorio.enteroRes,laboratorio.yeso,laboratorio.danado,laboratorio.ip
         * from laboratorio WHERE laboratorio.idLaboratorio='" + idlaboratorio +
         * "'");
         *
         * while (rs.next()) { Laboratorio.TxtIntegral.setText(rs.getString(1));
         * Laboratorio.TxtCascarilla.setText(rs.getString(2));
         * Laboratorio.TxtBlanco.setText(rs.getString(3));
         * Laboratorio.TxtPartido.setText(rs.getString(4));
         * Laboratorio.enteroRes.setText(rs.getString(5));
         * Laboratorio.TxtYeso.setText(rs.getString(6));
         * Laboratorio.TxtDanado.setText(rs.getString(7));
         * Laboratorio.TxtIp.setText(rs.getString(8));
         *
         *
         * }
         *
         * Con.Desconectar(); } catch (Exception e) { e.printStackTrace();
        }
         */
    }

    public void actualizar() {
        Laboratorio.TxtIDTiqueteEstufa.setText("");

        Laboratorio.TxtHumedadEstufa.setText("");
        Laboratorio.TxtMuestreo.setText("");

        crearModelo();
        crearModelo2(ip);
    }

    public void guardar_inicial() {
        idTiquete = Laboratorio.TxtIdTiquete.getText();
        fecha = Laboratorio.lblFecha.getText();
        //variedad = Laboratorio.TxtVariedad.getText();
        humedad = Laboratorio.TxtHumedad.getText();
        impureza = Laboratorio.TxtImpureza.getText();
        //humedadEst = Laboratorio.TxtHumedadRes.getText();
        // estado = Laboratorio.CmbEstado.getSelectedItem().toString();
        //  System.out.println("hola "+estado);
        user = login.enviarUsuario();
        integralRes = Laboratorio.TxtIntegral.getText();
        cascarillaRes = Laboratorio.TxtCascarilla.getText();
        blancoRes = Laboratorio.TxtBlanco.getText();
        partidoRes = Laboratorio.TxtPartido.getText();
        enteroRes = Laboratorio.enteroRes.getText();
        yeso = Laboratorio.TxtYeso.getText();
        danado = Laboratorio.TxtDanado.getText();
        ip = Laboratorio.TxtIp.getText();

        if (!fecha.equals("") && !idTiquete.equals("") && !humedad.equals("") && !impureza.equals("") && !integralRes.equals("") && !cascarillaRes.equals("") && !blancoRes.equals("") && !partidoRes.equals("") && !enteroRes.equals("") && !ip.equals("")) {
            if (yeso.equals("")) {
                yeso = "0.00";
            }
            if (danado.equals("")) {
                danado = "0.00";
            }
            insertar_inicial(idTiquete, user, fecha, humedad, impureza, integralRes, cascarillaRes, blancoRes, partidoRes, enteroRes, yeso, danado, ip);
            limpiar_campos();
            crearModelo3();
            campos_desabilitados();
            actualizar();
        } else {
            JOptionPane.showMessageDialog(null, "Ninguno de los campos puede estar vacio");
        }
    }

    public void insertar_inicial(String idTiquete, String user, String fecha, String humedad, String impureza, String integralRes, String cascarillaRes, String blancoRes, String partidoRes, String enteroRes, String yeso, String danado, String ip) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            System.out.println("Insert Into laboratorio (idLaboratorio,idTiquete,user,estado,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip) values (0,'" + idTiquete + "','" + user + "','" + fecha + "','" + humedad + "','" + impureza + "','" + integralRes + "','" + cascarillaRes + "','" + blancoRes + "','" + partidoRes + "','" + enteroRes + "','" + yeso + "','" + danado + "','" + ip + "')");
            st.executeUpdate("Insert Into laboratorio (idLaboratorio,idTiquete,user,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip) values (0,'" + idTiquete + "','" + user + "','" + fecha + "','" + humedad + "','" + impureza + "','" + integralRes + "','" + cascarillaRes + "','" + blancoRes + "','" + partidoRes + "','" + enteroRes + "','" + yeso + "','" + danado + "','" + ip + "')");
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consecutivo() {// Recibe la cedula del empleado y retorna el nombre del departamento de recidencia 
        try {
            Con = new Conexion();

            st = Con.conexion.createStatement();
            rsconsecutivo = st.executeQuery("SELECT MAX(idLaboratorio) FROM laboratorio");
            while (rsconsecutivo.next()) {
                String res = rsconsecutivo.getString(1);
                consecutivo = (Integer.parseInt(res) + 1) + "";
            }

            Laboratorio.lblTiquete.setText(consecutivo);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void limpiar_campos() {

        Laboratorio.lblFecha.setText("");
        Laboratorio.TxtYeso.setText("");
        Laboratorio.TxtDanado.setText("");
        Laboratorio.TxtIp.setText("");
        Laboratorio.TxtIdTiquete.setText("");
        // Laboratorio.TxtVariedad.setText("");
        Laboratorio.TxtHumedad.setText("");
        Laboratorio.TxtImpureza.setText("");
        //  Laboratorio.TxtHumedadRes.setText("");
        Laboratorio.TxtIntegral.setText("");
        Laboratorio.TxtCascarilla.setText("");
        Laboratorio.TxtBlanco.setText("");
        Laboratorio.TxtPartido.setText("");
        Laboratorio.enteroRes.setText("");
        consecutivo();
        fecha();
    }

    public void tabla_consecutivo_campo() {
        int rec = Laboratorio.jTable1.getSelectedRow();
        Laboratorio.TxtIDTiqueteEstufa.setText(Laboratorio.jTable1.getValueAt(rec, 0).toString());
        String id = Laboratorio.jTable1.getValueAt(rec, 0).toString();
        crearModelo2(id);
        maxMuestreo(id);
    }

    public void guardar_estufa() {
        IDLaboratorio = Laboratorio.TxtIDTiqueteEstufa.getText();
        muestreo = Laboratorio.TxtMuestreo.getText();
        hora = Laboratorio.jTimeChooser1.getFormatedTime();
        humedadEstufa = Laboratorio.TxtHumedadEstufa.getText();

        if (!IDLaboratorio.equals("") && !muestreo.equals("") && !hora.equals("") && !humedadEstufa.equals("")) {
            estado_tiquete();
            crearModelo2(IDLaboratorio);
            Laboratorio.TxtIDTiqueteEstufa.setText(IDLaboratorio);

            Laboratorio.TxtHumedadEstufa.setText("");
            Laboratorio.TxtMuestreo.setText("");
            maxMuestreo(IDLaboratorio);

        }

    }

    public void insertar_humedad_estufa(String IDLaboratorio, String muestreo, String hora, String humedadEstufa) {
        try {
            Con = new Conexion();
            st4 = Con.conexion.createStatement();

            st4.executeUpdate("Insert Into muetraestufa (idmuetraestufa,idLaboratorio,muestreo,hora,humedad) values (0,'" + IDLaboratorio + "','" + muestreo + "','" + hora + "','" + humedadEstufa + "')");
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
