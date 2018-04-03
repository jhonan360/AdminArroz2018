/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Laboratorio;

import Negocio.Conexion;
import Interfaces.LaboratorioTiquete;
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
import Logica.Extras.extras;

/**
 *
 * @author uriel
 */
public class laboratorioTiquete {

    public static LaboratorioTiquete Laboratorio;
    public static BusquedasTiqueteInicial BusInicial;
    public static tablas tbl;
    public DefaultTableModel modeloemp, modeloestufa, modelo3;
    public login login;
    public String columnas[] = new String[]{"N° Tiq Lab", "N° Tiq Entrada", "Fecha", "Humedad", "Impureza", "Integral", "Cascarilla", "Blanco", "Partido", "Entero", "Yeso", "Dañado", "Ip"};
    public String headerColumnas[] = new String[]{"default", "default", "default", "default", "default", "default", "default", "default", "default", "default", "default", "default", "default"};
    public String camposColumnas[] = new String[]{"center", "center", "center", "right", "right", "right", "right", "right", "right", "right", "right", "right", "right"};
    public String columnas2[] = new String[]{"Muestreo", "Hora", "Humedad"};
    public String headerColumnas2[] = new String[]{"default", "default", "default"};
    public String camposColumnas2[] = new String[]{"center", "center", "center"};
    public String columnas3[] = new String[]{"N° Tiq Bascula", "Agricultor", "Tipo de Arroz", "Fecha"};
    public String headerColumnas3[] = new String[]{"30", "170", "130", "50"};
    public String camposColumnas3[] = new String[]{"center", "left", "left", "center"};
    public static currencyFormat cu;
    public String user;
    public static Statement st, st3, st4, st5;
    public static ResultSet rs, rs3, rsconsecutivo, rscantidaad;
    public static Conexion Con;
    public String IDLaboratorio, muestreo, hora, humedadEstufa, res;
    public static String maxMuestreo, idTiquete, fecha, humedad, impureza, integralRes, cascarillaRes, blancoRes, partidoRes, enteroRes, yeso, danado, ip, consecutivo;
    public int cantEstado;
    public extras ext;

    public laboratorioTiquete() {
        tbl = new tablas();
        ext = new extras();
        cu = new currencyFormat();
        Laboratorio.lblFecha.setText(cu.dateNotTime(ext.fecha()));
        Laboratorio.txtIdTiquete.setText(String.valueOf(ext.getNextIndex("laboratorio")));
        crearModelo();
        crearModelo3();
        //consecutivo();
        campos_desabilitados();
    }

    /*
    Tiquete laboratorio
     */
    public void crearModelo3() {
        modelo3 = new DefaultTableModel(null, columnas3) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(Laboratorio.jTable3, modelo3, columnas3.length, "select idTiquete,CONCAT(personalexterno.nombres,personalexterno.apellidos),CONCAT(tipodearroz.nombre,' - ',variedad.nombre),fecha from tiquete,personalexterno,tipodearroz,variedad where idTiquete not in (select idTiquete from laboratorio group by idTiquete) and tiquete.idAgricultor = personalexterno.idPersonalExterno AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad ORDER BY idTiquete asc");
        tbl.alinearHeaderTable(Laboratorio.jTable3, headerColumnas3);
        tbl.alinearCamposTable(Laboratorio.jTable3, camposColumnas3);
        tblFormatoTiqBascula();
    }

    public void tblFormatoTiqBascula() {
        int row = Laboratorio.jTable3.getRowCount();
        for (int i = 0; i < row; i++) {
            String fecha = cu.dateNotTime(Laboratorio.jTable3.getValueAt(i, 3).toString());
            Laboratorio.jTable3.setValueAt(fecha, i, 3);
        }
    }

    //seleccionar tabla y mostrar campos en formulario
    public void idTiquete_campo() {
        int rec = Laboratorio.jTable3.getSelectedRow();
        Laboratorio.TxtIdTiquete.setText(Laboratorio.jTable3.getValueAt(rec, 0).toString());
        campos_habilitados();
    }

    //se supone que esto hace lo mismo que el metodo anterior?
    public void tablaCamposLiquidacion() {
        //  limpiar_campos();
        int rec = Laboratorio.jTable3.getSelectedRow();
        Laboratorio.TxtIdTiquete.setText(Laboratorio.jTable3.getValueAt(rec, 0).toString());
        campos_habilitados();
        String idlaboratorio = Laboratorio.jTable3.getValueAt(rec, 0).toString();
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

    //calcular cascarilla
    public void calculos() {
        double integral = 0.0;
        if (!Laboratorio.TxtIntegral.getText().equals("")) {
            integral = Double.parseDouble(Laboratorio.TxtIntegral.getText());
            double cascarilla = 1000 - integral;
            Laboratorio.TxtCascarilla.setText(String.valueOf(cu.thousandsFormat(cascarilla)));
        } else {
            JOptionPane.showMessageDialog(null, "El campo Integral no puede quedar vacio");
        }
    }

    //calcular entero
    public void calculo_entero() {
        double partido = 0.0;
        if (!Laboratorio.TxtPartido.getText().equals("")) {
            partido = Double.parseDouble(Laboratorio.TxtPartido.getText());
            double entero = 100 - partido;
            Laboratorio.enteroRes.setText(String.valueOf(cu.thousandsFormat((entero))));
            calculo_ip(entero);
        } else {
            JOptionPane.showMessageDialog(null, "El campo partido no se puede encontrar vacio");
        }
    }

    //calcular indice de pilado(ip)
    public void calculo_ip(double entero) {
        double ip = 0.0;
        double blanco = 0.0;
        blanco = Double.parseDouble(Laboratorio.TxtBlanco.getText());
        ip = (entero * blanco) / 1000;
        Laboratorio.TxtIp.setText(String.valueOf(ip));
    }

    public void campos_desabilitados() {
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

    public void limpiar_campos() {
        Laboratorio.lblFecha.setText("");
        Laboratorio.TxtYeso.setText("");
        Laboratorio.TxtDanado.setText("");
        Laboratorio.TxtIp.setText("");
        Laboratorio.TxtIdTiquete.setText("");
        Laboratorio.TxtHumedad.setText("");
        Laboratorio.TxtImpureza.setText("");
        Laboratorio.TxtIntegral.setText("");
        Laboratorio.TxtCascarilla.setText("");
        Laboratorio.TxtBlanco.setText("");
        Laboratorio.TxtPartido.setText("");
        Laboratorio.enteroRes.setText("");
        //consecutivo();
        Laboratorio.lblFecha.setText(cu.dateNotTime(ext.fecha()));
        Laboratorio.txtIdTiquete.setText(String.valueOf(ext.getNextIndex("laboratorio")));
    }

    //abre interfaz para buscar tiquetes de laboratorio finalizados
    public void busqueda() {
        BusInicial = new BusquedasTiqueteInicial();
        BusInicial.setVisible(true);
    }

    //Crea el tiquete de laboratorio
    public void guardar_inicial() {
        idTiquete = Laboratorio.TxtIdTiquete.getText();
        fecha = Laboratorio.lblFecha.getText();
        humedad = Laboratorio.TxtHumedad.getText();
        impureza = Laboratorio.TxtImpureza.getText();
        user = login.enviarUsuario();
        integralRes = Laboratorio.TxtIntegral.getText();
        cascarillaRes = Laboratorio.TxtCascarilla.getText();
        blancoRes = Laboratorio.TxtBlanco.getText();
        partidoRes = Laboratorio.TxtPartido.getText();
        enteroRes = Laboratorio.enteroRes.getText();
        yeso = Laboratorio.TxtYeso.getText();
        danado = Laboratorio.TxtDanado.getText();
        ip = Laboratorio.TxtIp.getText();

        if (!user.equals("")&&!fecha.equals("") && !idTiquete.equals("") && !humedad.equals("") && !impureza.equals("") && !integralRes.equals("") && !cascarillaRes.equals("") && !blancoRes.equals("") && !partidoRes.equals("") && !enteroRes.equals("") && !ip.equals("")) {
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
            st.executeUpdate("Insert Into laboratorio (idLaboratorio,idTiquete,user,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip) values (0,'" + idTiquete + "','" + user + "','" + fecha + "','" + humedad + "','" + impureza + "','" + integralRes + "','" + cascarillaRes + "','" + blancoRes + "','" + partidoRes + "','" + enteroRes + "','" + yeso + "','" + danado + "','" + ip + "')");
           ext.logs("Insert Into laboratorio (idLaboratorio,idTiquete,user,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip) values (0,'" + idTiquete + "','" + user + "','" + fecha + "','" + humedad + "','" + impureza + "','" + integralRes + "','" + cascarillaRes + "','" + blancoRes + "','" + partidoRes + "','" + enteroRes + "','" + yeso + "','" + danado + "','" + ip + "')");

            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestras estufa
     */
    public void crearModelo() {
        modeloemp = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tbl.llenarTabla(Laboratorio.jTable1, modeloemp, columnas.length, "SELECT idLaboratorio,idTiquete,fecha,humedad,impureza,integralRes,cascarillaRes,blancoRes,partidoRes,enteroRes,yeso,danado,ip FROM laboratorio WHERE laboratorio.estado='abierto'");
        tbl.alinearHeaderTable(Laboratorio.jTable1, headerColumnas);
        tbl.alinearCamposTable(Laboratorio.jTable1, camposColumnas);
        tbl.alinearHeaderTable(Laboratorio.jTable4, headerColumnas2);
        tblFormatoTiqLab();
    }

    public void tblFormatoTiqLab() {
        int row = Laboratorio.jTable1.getRowCount();
        for (int i = 0; i < row; i++) {
            Laboratorio.jTable1.setValueAt(cu.dateNotTime(Laboratorio.jTable1.getValueAt(i, 2).toString()), i, 2);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 3).toString())), i, 3);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 4).toString())), i, 4);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 5).toString())), i, 5);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 6).toString())), i, 6);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 7).toString())), i, 7);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 8).toString())), i, 8);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 9).toString())), i, 9);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 10).toString())), i, 10);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 11).toString())), i, 11);
            Laboratorio.jTable1.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable1.getValueAt(i, 12).toString())), i, 12);
        }
    }

    //Al seleccionar la tabla muestra n tiq de lab en seccion humedad estufa
    public void tabla_consecutivo_campo() {
        tbl.alinearCamposTable(Laboratorio.jTable4, camposColumnas2);
        int rec = Laboratorio.jTable1.getSelectedRow();
        Laboratorio.TxtIDTiqueteEstufa.setText(Laboratorio.jTable1.getValueAt(rec, 0).toString());
        String id = Laboratorio.jTable1.getValueAt(rec, 0).toString();
        //crearModelo2();
        maxMuestreo(id);
        tbl.llenarTabla(Laboratorio.jTable4, modeloestufa, columnas2.length, "SELECT muestreo,hora,humedad FROM muetraestufa where idLaboratorio ='" + id + "' ");
        tblFormatoMuestras();
    }

    //crea modelo tiquetes de laboratorio con resultado de laboratorio incluyendo IP
    public void crearModelo2() {
        modeloestufa = new DefaultTableModel(null, columnas2) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        Laboratorio.jTable4.setModel(modeloestufa);
    }

    public void tblFormatoMuestras() {
        int row = Laboratorio.jTable4.getRowCount();
        for (int i = 0; i < row; i++) {
            Laboratorio.jTable4.setValueAt(cu.thousandsFormat(Double.parseDouble(Laboratorio.jTable4.getValueAt(i, 2).toString())), i, 2);
        }
    }

    public void maxMuestreo(String id) {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT max(muetraestufa.muestreo) FROM muetraestufa WHERE muetraestufa.idLaboratorio='" + id + "'");
            while (rs.next()) {
                maxMuestreo = rs.getString(1);
                if (maxMuestreo == null) {
                    Laboratorio.TxtMuestreo.setText(Integer.toString(1));
                } else {
                    //System.out.println("MAX MUESTREO = " + maxMuestreo);
                    int numEntero = Integer.parseInt(maxMuestreo) + 1;
                    Laboratorio.TxtMuestreo.setText(Integer.toString(numEntero));
                }
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void consecutivo() {
        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rsconsecutivo = st.executeQuery("SELECT MAX(idLaboratorio) FROM laboratorio");
            while (rsconsecutivo.next()) {
                String res = rsconsecutivo.getString(1);
                consecutivo = (Integer.parseInt(res) + 1) + "";
            }
            Laboratorio.txtIdTiquete.setText(consecutivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    //cierra un tiquete de laboratorio
    public void estado() {
        int aceptar = JOptionPane.showConfirmDialog(null, "Esta seguro que quiere cerrar el tiquete", "Confirmacion", JOptionPane.CANCEL_OPTION);
        if (aceptar == JOptionPane.YES_OPTION) {
            try {
                String id = Laboratorio.TxtIDTiqueteEstufa.getText();
                Con = new Conexion();
                st = Con.conexion.createStatement();
                st.executeUpdate("UPDATE laboratorio SET estado = 'cerrado' WHERE idLaboratorio ='" + id + "'");
                ext.logs("UPDATE laboratorio SET estado = 'cerrado' WHERE idLaboratorio ='" + id + "'");
         
                JOptionPane.showMessageDialog(null, "El tiquete fue cerrado");
                actualizar();
                crearModelo2();
                Con.Desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los cambios han sido descartados");
        }
    }

    //se supone que hace lo mismo que el anterior?
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
                crearModelo2();
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //limpiar campos muestra estufa
    public void actualizar() {
        Laboratorio.TxtIDTiqueteEstufa.setText("");
        Laboratorio.TxtHumedadEstufa.setText("");
        Laboratorio.TxtMuestreo.setText("");
        crearModelo();
        crearModelo2();
    }

    //guardar humedad estufa
    public void guardar_estufa() {
        IDLaboratorio = Laboratorio.TxtIDTiqueteEstufa.getText();
        muestreo = Laboratorio.TxtMuestreo.getText();
        hora = Laboratorio.jTimeChooser1.getFormatedTime();
        humedadEstufa = Laboratorio.TxtHumedadEstufa.getText();

        if (!IDLaboratorio.equals("") && !muestreo.equals("") && !hora.equals("") && !humedadEstufa.equals("")) {
            if (!hora.equals("00:00")) {
            estado_tiquete();
            tabla_consecutivo_campo();
            Laboratorio.TxtIDTiqueteEstufa.setText(IDLaboratorio);
            Laboratorio.TxtHumedadEstufa.setText("");
            Laboratorio.TxtMuestreo.setText("");
            maxMuestreo(IDLaboratorio);
            }else{
                JOptionPane.showMessageDialog(null, "Establezca una hora diferente a 00:00");
            }
        }
    }

    public void insertar_humedad_estufa(String IDLaboratorio, String muestreo, String hora, String humedadEstufa) {
        try {
            Con = new Conexion();
            st4 = Con.conexion.createStatement();
            st4.executeUpdate("Insert Into muetraestufa (idmuetraestufa,idLaboratorio,muestreo,hora,humedad) values (0,'" + IDLaboratorio + "','" + muestreo + "','" + hora + "','" + humedadEstufa + "')");
            ext.logs("Insert Into muetraestufa (idmuetraestufa,idLaboratorio,muestreo,hora,humedad) values (0,'" + IDLaboratorio + "','" + muestreo + "','" + hora + "','" + humedadEstufa + "')");
     
            JOptionPane.showMessageDialog(null, "El registro ha sido agregado");
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
