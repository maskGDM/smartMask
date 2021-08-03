/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.imageio.ImageIO;

/**
 *
 * @author geova
 */
public class Util {

    public Util() {
    }

    public void zonaHoraria() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
        //TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
    }

    public Date parseData(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public String normalizerRegex(String original) {
        String cadenaNormalize = Normalizer.normalize(original, Normalizer.Form.NFD);
        //System.out.println("Resultado: " + cadenaSinAcentos);
        return cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
    }

    public int[] ObtenerFechaHora()// Método para poder obtener Fecha y Hora que devuelve un Array 
    {
        int[] FechaHora = new int[7];
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        int millisegundo = fecha.get(Calendar.MILLISECOND);

        FechaHora[0] = año;
        FechaHora[1] = mes;
        FechaHora[2] = dia;
        FechaHora[3] = hora;
        FechaHora[4] = minuto;
        FechaHora[5] = segundo;
        FechaHora[6] = millisegundo;

        return FechaHora;
    }

    public Date getFechaHoraActual() throws ParseException {
        //java.sql.Date now = new java.sql.Date(System.currentTimeMillis());// obtengo fecha-hora actual, por eso lo cargo aqí para jalar
        int[] Date = this.ObtenerFechaHora();
        String Anio = Integer.toString(Date[0]);
        String Mes = Integer.toString(Date[1]);
        String Dia = Integer.toString(Date[2]);
        java.util.Date fechaParseada;
        String Fecha = "" + Anio + "-" + Mes + "-" + Dia;
        fechaParseada = new SimpleDateFormat("yyyy-MM-dd").parse(Fecha);
        return fechaParseada;
    }

    public String getFechaHoraActualString() throws ParseException {
        //java.sql.Date now = new java.sql.Date(System.currentTimeMillis());// obtengo fecha-hora actual, por eso lo cargo aqí para jalar
        int[] Date = this.ObtenerFechaHora();
        String Anio = Integer.toString(Date[0]);
        String Mes = Integer.toString(Date[1]);
        String Dia = Integer.toString(Date[2]);
        String Fecha = "" + Anio + "-" + Mes + "-" + Dia;
        return Fecha;
    }

    public boolean writeOutputStream(String value, File outputStream) {
        String[] partes = value.split(",");
        try {
            //byte[] imgBytes = decoder.decodeBuffer(partes[1]);
            byte[] imgBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(partes[1]);

            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
            ImageIO.write(bufImg, "png", outputStream);
            return true;
        } catch (IOException e) {
            System.out.println("Error creating image: " + e.getMessage());
            return false;
        }
    }

}
