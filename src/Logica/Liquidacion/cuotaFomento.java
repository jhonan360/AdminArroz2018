/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Liquidacion;
import Interfaces.CuotaFomento;
import Reportes.logicaReportes;

/**
 *
 * @author Lizeth
 */
public class cuotaFomento {
    public static CuotaFomento Fomento;
    public static logicaReportes reportes;
    
    public cuotaFomento(){
        reportes =new logicaReportes();
    }
    
    public void capturar(){
        int mes=Fomento.jMonthChooser.getMonth()+1;
        int anio=Fomento.jYearChooser.getYear();
        reportes.reporteCuotaFomento(mes,anio);
        System.out.println(mes);
        System.out.println(anio);
    }
}
