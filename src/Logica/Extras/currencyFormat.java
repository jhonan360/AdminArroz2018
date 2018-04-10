/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Extras;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jhonansmith
 */
public class currencyFormat {

    DecimalFormatSymbols simboloDinero = new DecimalFormatSymbols();
    DecimalFormat ftDinero;
    DecimalFormatSymbols simboloMiles = new DecimalFormatSymbols();
    DecimalFormat ftMiles;
    SimpleDateFormat ftDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ftDateTime = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");

    public currencyFormat() {
        simboloDinero.setDecimalSeparator('.');
        simboloDinero.setGroupingSeparator(',');
        ftDinero = new DecimalFormat("$ ###,###.##", simboloDinero);
        simboloMiles.setDecimalSeparator('.');
        simboloMiles.setGroupingSeparator(',');
        ftMiles = new DecimalFormat("###,###.##", simboloMiles);
    }

    public String moneyFormat(double money) {
        return ftDinero.format(money);
    }

    public String notMoneyFormat(String money) {
        return money.replace("$", "").replace(",", "");
    }

    public String thousandsFormat(double number) {
        return ftMiles.format(number);
    }

    public String notThousandsFormat(String money) {
        return money.replace(",", "");
    }

    public String dateNotTime(String date) {
        try {
            Date Date = ftDate.parse(date);
            return ftDate.format(Date);
        } catch (Exception e) {
        }
        return "";
    }

    public String DateTime(Date dateTime) {
        try {
            return ftDateTime.format(dateTime);
        } catch (Exception e) {
            System.err.println(e);
        }
        return "";
    }

    public String getDateTimeNow() {
        java.util.Date dateTime = new Date();
        return DateTime(dateTime);
    }
}
