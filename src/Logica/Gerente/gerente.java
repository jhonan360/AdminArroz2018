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
import Interfaces.GerenteApruebaLiquidaciones;
import Interfaces.LiquidacionesAprobadas;
import Interfaces.Login;
import Logica.Extras.currencyFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author uriel
 */
public class gerente {

    public static LiquidacionesAprobadas LiqAprobada;
    public static Login Login;
    public static GerenteApruebaLiquidaciones GApruebaL;
    public static Conexion Con;
    public static ResultSet rs;
    public static Statement st;
    public DefaultTableModel modelo, modelobuscar;
    public static Gerente Ger;
    public String columnas[] = new String[]{"N° Tiquete", "Agricultor", "Fecha Creación"};
    public String headerColumnas[] = new String[]{"30", "170", "30"};
    public String camposColumnas[] = new String[]{"center", "left", "center"};
    public tablas tbl;
    public String tiquete, valor;
    public static currencyFormat cu;

    public gerente() {
        tbl = new tablas();
        cu = new currencyFormat();
        crearModeloTabla();

    }

    public static void mnAbrirLiqPorAprobar() {
        if (!(GApruebaL instanceof GerenteApruebaLiquidaciones)) {
            GApruebaL = new GerenteApruebaLiquidaciones();
            GApruebaL.setVisible(true);
        } else {
            GApruebaL.setVisible(true);
        }
    }

    public static void mnGenerarLiquidacion() {
        if (!(LiqAprobada instanceof LiquidacionesAprobadas)) {
            LiqAprobada = new LiquidacionesAprobadas();
            LiqAprobada.setVisible(true);
        } else {
            LiqAprobada.setVisible(true);
        }
    }

    public static void salir() {
        Login = new Login();
        Login.setVisible(true);
    }

    public void crearModeloTabla() {// crea los modelos de las tablas
        modelo = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tbl.llenarTabla(Ger.TablaPendiente, modelo, columnas.length, "SELECT tiquete.idTiquete,concat(personalexterno.nombres,' ',personalexterno.apellidos),tiquete.fecha FROM tiquete,personalexterno,tipodearroz WHERE tiquete.idTiquete NOT IN(SELECT detalleliquidacion.idTiquete from detalleliquidacion) and tiquete.kilosBrutos<>0.00 and tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 and tiquete.idAgricultor= personalexterno.idPersonalExterno and tipodearroz.idTipoDeArroz=tiquete.idTipoDeArroz order by tiquete.idTiquete ASC");
        tbl.alinearHeaderTable(Ger.TablaPendiente, headerColumnas);
        tbl.alinearCamposTable(Ger.TablaPendiente, camposColumnas);
        formatoTbl();
    }

    public void formatoTbl() {
        int row = Ger.TablaPendiente.getRowCount();
        for (int i = 0; i < row; i++) {
            String fecha = cu.dateNotTime(Ger.TablaPendiente.getValueAt(i, 2).toString());
            Ger.TablaPendiente.setValueAt(fecha, i, 2);
        }
    }

    public void tablaCamposLiquidacion() {
        // limpiarCampos();
        int rec = Ger.TablaPendiente.getSelectedRow();
        String idTiquete = Ger.TablaPendiente.getValueAt(rec, 0).toString();
        Ger.TxtNumTiquete.setText(Ger.TablaPendiente.getValueAt(rec, 0).toString());
        Ger.TxtNombre.setText(Ger.TablaPendiente.getValueAt(rec, 1).toString());
        //Ger.TxtNumTiquete.setText(Ger.TablaPendiente.getValueAt(rec, 0).toString());
        // campos_habilitados();
        //System.out.println("id tiquete pendiente =" + idTiquete);
        // idLiquidacion = LiqAprobadas.tblLiquidaciones.getValueAt(rec, 0).toString();
        //LiqAprobadas.lblNumLiquidacion.setText(idLiquidacion);
        //LiqAprobadas.lblNomAgricultor.setText(LiqAprobadas.tblLiquidaciones.getValueAt(rec, 3).toString());

        try {
            Con = new Conexion();
            st = Con.conexion.createStatement();
            rs = st.executeQuery("SELECT tiquete.idTiquete, personalexterno.cedula,personalexterno.nombres,personalexterno.apellidos,CONCAT(tipodearroz.nombre,'-',variedad.nombre),tiquete.fecha,tiquete.kilosBrutos,tiquete.destare,tiquete.kilosNetos,tiquete.observacion,tiquete.empaque,tiquete.humedadUno,tiquete.impurezaUno,concat(personalexterno.nombres,' ',personalexterno.apellidos),lote.nombre FROM tiquete,personalexterno,tipodearroz,variedad,lote WHERE tiquete.idConductor= personalexterno.idPersonalExterno and tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad and tiquete.idLote=lote.idLote and tiquete.idTiquete='" + idTiquete + "'");

            while (rs.next()) {
                //Ger.TxtNumTiquete.setText(rs.getString(1));
                // Ger.TxtNumTiquete.setText(rs.getString(0));        
                //Ger.TxtCedula.setText(rs.getString(2));
                //Ger.TxtNombre.setText(rs.getString(3) + rs.getString(4));
                Ger.TxtTipo.setText(rs.getString(5));
                Ger.TxtFecha.setText(cu.dateNotTime(rs.getString(6)));
                Ger.TxtKilosBrutos.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(7))));
                Ger.TxtDestare.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(8))));
                Ger.TxtKilosNetos.setText(cu.thousandsFormat(Double.parseDouble(rs.getString(9))));
                Ger.TxtObservacion.setText(rs.getString(10));
                Ger.TxtEmpaque.setText(rs.getString(11));
                Ger.TxtHumedad.setText(rs.getString(12));
                Ger.TxtImpureza.setText(rs.getString(13));
                Ger.txtConductor.setText(rs.getString(14));
                Ger.txtLote.setText(rs.getString(15));
            }
            Con.Desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarInformacionALosCampos() {
        int rec = Ger.TablaPendiente.getSelectedRow();

// devuelve un entero con la posicion de la seleccion en la tabla
        Ger.TxtNumTiquete.setText(Ger.TablaPendiente.getValueAt(rec, 0).toString());
        //Ger.TxtCedula.setText(Ger.TablaPendiente.getValueAt(rec, 1).toString());
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

    public void limpiarCampos() {
        Ger.TxtNumTiquete.setText("");
        //Ger.TxtCedula.setText("");
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
        Ger.txtConductor.setText("");
        Ger.txtLote.setText("");
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

    public void BuscarTiquetes() {
        String FechaIni, FechaFin;
        Date Fechainicial,FechaFinal;
        
        SimpleDateFormat formatoI = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat formato = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        Fechainicial = Ger.jDateinicial.getDate();
        FechaFinal = Ger.jDatefinal.getDate();

        if (Fechainicial == null && FechaFinal == null) {
            FechaIni = "";
            FechaFin = "";
        } else {
            FechaIni = formatoI.format(Fechainicial);
            FechaFin = formato.format(FechaFinal);
        }
        
        String cedula = Ger.cedula.getText();

        modelobuscar = new DefaultTableModel(null, columnas) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        if (Ger.chfecha.isSelected() == true && Ger.chcedula.isSelected() == true) {
            if (!FechaIni.equals("") && !FechaFin.equals("") && !cedula.equals("")) {
                tbl.llenarTabla(Ger.TablaPendiente, modelobuscar, columnas.length, "SELECT tiquete.idTiquete,concat(personalexterno.nombres,' ',personalexterno.apellidos),tiquete.fecha FROM tiquete,personalexterno,tipodearroz WHERE tiquete.idTiquete NOT IN(SELECT detalleliquidacion.idTiquete from detalleliquidacion) and tiquete.kilosBrutos<>0.00 and tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 and tiquete.idAgricultor= personalexterno.idPersonalExterno and tipodearroz.idTipoDeArroz=tiquete.idTipoDeArroz and personalexterno.cedula = '" + cedula + "' AND tiquete.fecha >= '" + FechaIni + "' AND tiquete.fecha <='" + FechaFin + "' order by tiquete.idTiquete ASC");
                tbl.alinearHeaderTable(Ger.TablaPendiente, headerColumnas);
                tbl.alinearCamposTable(Ger.TablaPendiente, camposColumnas);
                formatoTbl();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Ger.chfecha.isSelected() == true) {
            if (!FechaIni.equals("") && !FechaFin.equals("")) {
                tbl.llenarTabla(Ger.TablaPendiente, modelobuscar, columnas.length, "SELECT tiquete.idTiquete,concat(personalexterno.nombres,' ',personalexterno.apellidos),tiquete.fecha FROM tiquete,personalexterno,tipodearroz WHERE tiquete.idTiquete NOT IN(SELECT detalleliquidacion.idTiquete from detalleliquidacion) and tiquete.kilosBrutos<>0.00 and tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 and tiquete.idAgricultor= personalexterno.idPersonalExterno and tipodearroz.idTipoDeArroz=tiquete.idTipoDeArroz AND tiquete.fecha >= '" + FechaIni + "' AND tiquete.fecha <='" + FechaFin + "' order by tiquete.idTiquete ASC");
                tbl.alinearHeaderTable(Ger.TablaPendiente, headerColumnas);
                tbl.alinearCamposTable(Ger.TablaPendiente, camposColumnas);
                formatoTbl();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        } else if (Ger.chcedula.isSelected() == true) {
            if (!cedula.equals("")) {
                tbl.llenarTabla(Ger.TablaPendiente, modelobuscar, columnas.length, "SELECT tiquete.idTiquete,concat(personalexterno.nombres,' ',personalexterno.apellidos),tiquete.fecha FROM tiquete,personalexterno,tipodearroz WHERE tiquete.idTiquete NOT IN(SELECT detalleliquidacion.idTiquete from detalleliquidacion) and tiquete.kilosBrutos<>0.00 and tiquete.destare<>0.00 AND tiquete.kilosNetos<>0.00 and tiquete.idAgricultor= personalexterno.idPersonalExterno and tipodearroz.idTipoDeArroz=tiquete.idTipoDeArroz and personalexterno.cedula = '" + cedula + "' order by tiquete.idTiquete ASC");
                tbl.alinearHeaderTable(Ger.TablaPendiente, headerColumnas);
                tbl.alinearCamposTable(Ger.TablaPendiente, camposColumnas);
                formatoTbl();
            } else {
                JOptionPane.showMessageDialog(null, "Uno de los campos que selecciono para la busqueda esta vacio");
            }
        }
    }
}
