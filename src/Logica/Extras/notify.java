/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import Negocio.Conexion;
import ds.desktop.notify.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import Logica.Extras.currencyFormat;
import Interfaces.*;
import javax.swing.table.DefaultTableModel;
import Logica.Extras.tablas;

/**
 *
 * @author jhonansmith
 */
public class notify {

    private hilo hilo;

    public notify(String privilegio, String user) {
        hilo = new hilo();
        hilo.privilegio = privilegio;
        hilo.user = user;
        hilo.start();
    }

    public void stop() {
        hilo.desactivar();

    }

    private class hilo extends Thread {

        private boolean active;
        private Conexion Con;
        private Statement st, stupdate;
        private ResultSet rs;
        public String privilegio, user;
        private currencyFormat cu;
        private Bascula Bas;
        private tablas tbl;

        public hilo() {
            active = true;
            cu = new currencyFormat();
            tbl = new tablas();
        }

        public void desactivar() {
            active = false;
        }

        @Override
        public void run() {
            while (active) {

                try {
                    try {
                        Con = new Conexion();
                        st = Con.conexion.createStatement();
                        rs = st.executeQuery("SELECT  idNotificacion, privilegio, usuario, titulo, texto, tipo, fechaCreacion, fechaVisualizacion, origen FROM notificaciones WHERE (fechaVisualizacion IS NULL AND privilegio='" + privilegio + "') OR (fechaVisualizacion IS NULL AND usuario='" + user + "')");
                        while (rs.next()) {
                            showNotify(rs);
                        }
                        Con.Desconectar();
                        Thread.sleep(5000);
                    } catch (SQLException ex) {
                        Logger.getLogger(notify.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

        private void showNotify(ResultSet rs) throws SQLException {
            String id = rs.getString(1);
            String titulo = rs.getString(4);
            String texto = rs.getString(5);
            String tipo = rs.getString(6);
            int type = 0;
            int time = 15000;
            switch (tipo) {
                case "info":
                    type = DesktopNotify.INFORMATION;
                    break;
                case "help":
                    type = DesktopNotify.HELP;
                    break;
                case "tip":
                    type = DesktopNotify.TIP;
                    break;
                case "error":
                    type = DesktopNotify.ERROR;
                    break;
                case "fail":
                    type = DesktopNotify.FAIL;
                    break;
                case "success":
                    type = DesktopNotify.SUCCESS;
                    break;
                case "warning":
                    type = DesktopNotify.WARNING;
                    break;
            }
            switch (privilegio) {
                case "basculista":
                    if (titulo.indexOf("Tiquete") != -1) {
                        String[] split = texto.split(" ");
                        String idTiquete = split[2];
                        if (!tbl.searchColumnTable(Bas.tblEspera, 0, idTiquete)) {
                            DefaultTableModel model = (DefaultTableModel) Bas.tblEspera.getModel();
                            tbl.llenarTabla(Bas.tblEspera, model, Bas.bascula.columEspera.length, "SELECT tiquete.idTiquete, CONCAT(personalexterno.nombres,' ',personalexterno.apellidos), CONCAT(tipodearroz.nombre,' - ',variedad.nombre) FROM tiquete,personalexterno,tipodearroz,variedad WHERE tiquete.idTiquete='" + idTiquete + "' AND tiquete.idAgricultor=personalexterno.idPersonalExterno AND tiquete.idTipoDeArroz=tipodearroz.idTipoDeArroz AND tipodearroz.idVariedad=variedad.idVariedad");
                        }
                    }
                    DesktopNotify.showDesktopMessage(titulo, texto, type, time);
                    break;
                case "laboratorista":
                    DesktopNotify.showDesktopMessage(titulo, texto, type, time);
                    break;
                case "contador":
                    DesktopNotify.showDesktopMessage(titulo, texto, type, time);
                    break;
                case "gerente":
                    DesktopNotify.showDesktopMessage(titulo, texto, type, time);
                    break;
                case "molinero":
                    DesktopNotify.showDesktopMessage(titulo, texto, type, time);
                    break;
            }
            Con = new Conexion();
            stupdate = Con.conexion.createStatement();
            java.util.Date date = new Date();
            stupdate.executeUpdate("UPDATE notificaciones SET fechaVisualizacion='" + cu.DateTime(date) + "' WHERE idNotificacion='" + id + "'");
            Con.Desconectar();
        }

    }
}
