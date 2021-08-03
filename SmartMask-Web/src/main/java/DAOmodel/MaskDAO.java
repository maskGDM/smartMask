/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.MaskCrud;
import Model.Mask;
import java.util.Date;

/**
 * This DAO model class contains the attributes and methods of a mask
 *
 * @author Brito,Solano,Velez
 * @version 1.0
 */
public class MaskDAO implements MaskCrud {

    Conection conex = new Conection();
    Mask city = new Mask();

    /**
     * Method to obtain a json-chain with the information of a mask
     *
     * @param mask Receive the model Mask
     * @return Return one json string
     */
    public String jsonMask(Mask mask) {
        return conex.getRecordsInJson("SELECT * FROM mask WHERE user_informationid=" + mask.getUser_id());
    }

    /**
     * Method to obtain a json-chain with the information of daily data of a
     * mask
     *
     * @param mask Receive the model of Mask
     * @return Return a string in json format with the information of the daily
     * data
     */
    public String jsonMaskDailyData(Mask mask) {
        return conex.getRecordsInJson("select (r.datetimegistration)::time, r.alert,  r.ppminternal,  pressure, temperature, altitude, humidity, m.mask_code, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= (date_trunc('day', CURRENT_DATE)) and m.user_informationid = " + mask.getUser_id() + "\n"
                + "order by r.datetimegistration asc;");
    }

    /**
     * Method to obtain a json-chain with the information of daily data of a
     * mask with a limit range
     *
     * @param mask Receive the model Mask
     * @param limit Receive an integer type of data
     * @return Return a string in json format with the information of the daily
     * data with a limit range data
     */
    public String jsonMaskDailyDataLimit(Mask mask, int limit) {
        return conex.getRecordsInJson("select * from (select (r.datetimegistration)::time, r.alert,  r.ppminternal, pressure, temperature, altitude, humidity, m.mask_code, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= (date_trunc('day', CURRENT_DATE)) and m.user_informationid = " + mask.getUser_id() + "\n"
                + "order by r.datetimegistration desc limit " + limit + ") as X order by datetimegistration  asc;");
    }

    /**
     * Method to obtain a json-chain with the information of monthly data
     *
     * @param mask Receive the model Mask
     * @return Return a string in json format with the information of the
     * monthly data
     */
    public String jsonMask3MonthData(Mask mask) {
        return conex.getRecordsInJson("select (r.datetimegistration), r.alert,  r.ppminternal, pressure, temperature, altitude, humidity, m.mask_code, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= (date_trunc('month', CURRENT_DATE - INTERVAL '3 month')) and m.user_informationid = " + mask.getUser_id() + "\n"
                + "order by r.datetimegistration asc;");
    }

    /**
     * Method to obtain a json-chain with the information of one month data
     *
     * @param mask Receive the model Mask
     * @return Return a string json format with the information of the one month
     * data
     */
    public String jsonMask1MonthData(Mask mask) {
        return conex.getRecordsInJson("select (r.datetimegistration), r.alert,  r.ppminternal, pressure, temperature, altitude, humidity, m.mask_code, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= (date_trunc('month', CURRENT_DATE - INTERVAL '1 month')) and m.user_informationid = " + mask.getUser_id() + "\n"
                + "order by r.datetimegistration asc;");
    }

    /**
     * Method to obtain a json-chain with the information of a one month data
     * with a limit range
     *
     * @param mask Receive the model Mask
     * @param limit Receive an integer type of data
     * @return Returns information from a month's range of data
     */
    public String jsonMask1MonthDataLimit(Mask mask, int limit) {
        return conex.getRecordsInJson("select * from (select (r.datetimegistration), r.alert,  r.ppminternal, m.mask_code, pressure, temperature, altitude, humidity, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= (date_trunc('month', CURRENT_DATE - INTERVAL '1 month')) and m.user_informationid = " + mask.getUser_id() + "\n"
                + "order by r.datetimegistration desc limit " + limit + ") as X order by datetimegistration asc;");
    }

    /**
     * Method to obtain a string in json format with the monthly data for the
     * admin
     *
     * @return Returns a string in json format with the monthly data
     */
    public String jsonMask1MonthDataAdmin() {
        return conex.getRecordsInJson("select (r.datetimegistration), r.alert,  r.ppminternal, pressure, temperature, altitude, humidity, m.mask_code from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= (date_trunc('month', CURRENT_DATE - INTERVAL '1 month'))\n"
                + "order by r.datetimegistration asc;");
    }

    /**
     * Method to obtain a string in json format of the monthly data with a limit
     * range
     *
     * @param limit Receive an integer type of data
     * @return Returns a json-type string with the information of the monthly
     * data with a defined range
     */
    public String jsonMask1MonthDataAdminLimit(int limit) {
        return conex.getRecordsInJson("select * from (select (r.datetimegistration), r.alert,  r.ppminternal, pressure, temperature, altitude, humidity, m.mask_code from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= (date_trunc('month', CURRENT_DATE - INTERVAL '1 month'))\n"
                + "order by r.datetimegistration desc limit " + limit + ") as x order by datetimegistration asc;");
    }

    /**
     * Method to obtain a string in json format of the monthly data with a star
     * and end date
     *
     * @param mask Receive the model Mask
     * @param init Receive an integer type of data
     * @param end Receive an integer type data
     * @return Returns a json-type string with the information of the monthly
     * data depending on the start and end date
     */
    public String jsonMaskReport(Mask mask, String init, String end) {
        System.out.println("select (r.datetimegistration), r.alert,  r.ppminternal, pressure, temperature, altitude, humidity, m.mask_code, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= '" + init + "' and r.datetimegistration < '" + end + " 23:59:59.999999' and m.user_informationid = " + mask.getUser_id() + " \n"
                + "order by r.datetimegistration asc");
        return conex.getRecordsInJson("select (r.datetimegistration), r.alert,  r.ppminternal, pressure, temperature, altitude, humidity, m.mask_code, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= '" + init + "' and r.datetimegistration < '" + end + " 23:59:59.999999' and m.user_informationid = " + mask.getUser_id() + " \n"
                + "order by r.datetimegistration asc");
    }

    /**
     * Method for obtaining a json-type chain with start and end dates
     *
     * @param init Receive an string type of data
     * @param end Receive an string type of data
     * @return Returns a json-type string with the information
     */
    public String jsonMaskReportAdmin(String init, String end) {
        return conex.getRecordsInJson("select (r.datetimegistration), r.alert,  r.ppminternal, m.mask_code, pressure, temperature, altitude, humidity, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= '" + init + "' and r.datetimegistration < '" + end + "' \n"
                + "order by r.datetimegistration asc");
    }

    /**
     * Method for obtaining a json-type chain with start and end dates with
     * limit range
     *
     * @param init Receive an string type of data
     * @param limit Receive an integer type of data
     * @param end Receive an string type of data
     * @return Return a json-type string with the information
     */
    public String jsonMaskReportAdminLimit(String init, int limit, String end) {
        return conex.getRecordsInJson("select * from (select (r.datetimegistration), r.alert,  r.ppminternal, m.mask_code, pressure, temperature, altitude, humidity, m.user_informationid from records as r \n"
                + "inner join mask as m on m.mask_code = r.mask_code \n"
                + "WHERE r.datetimegistration >= '" + init + "' and r.datetimegistration < '" + end + "' \n"
                + "order by r.datetimegistration desc limit " + limit + ") as x order by datetimegistration asc;");
    }

    /**
     * Method for displaying active masks
     *
     * @param mask Receive of model Mask
     * @return Return a Boolean if the query was successful
     */
    public boolean activeMask(Mask mask) {
        return (conex.fillString("select active from mask_produced where mask_code = " + mask.getMask_code() + ";")).equals("t");
    }

    /**
     * Method for adding a new mask
     *
     * @param mask Receive of model Mask
     * @return Return a boolean if the query was successful
     */
    @Override
    public boolean addMask(Mask mask) {
        return conex.modifyBD("SELECT addmask('" + mask.getJson2Xml() + "');");
    }

    @Override
    public boolean editMask(Mask mask) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteorresetMask(Mask mask) {
        return conex.modifyBD("delete from data where user_informationid=" + mask.getUser_id());
    }

}
