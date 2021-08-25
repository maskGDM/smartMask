/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.DataCrud;
import Model.Data;
import javax.swing.table.DefaultTableModel;

/**
 *
 * This DAO model class contains the attributes and methods of Data
 *
 * @authors Brito,Solano,Velez
 * @version 1.0
 */
public class DataDAO implements DataCrud {

    Conection conex = new Conection();
    Data categ = new Data();

    /**
     * Method to obtain a json-chain with the information of a Data
     *
     * @param data Receive the model of the data
     * @return Returns Data Information
     */
    public String jsonData(Data data) {
        return conex.getRecordsInJson("select * from data where user_informationid =" + data.getUser_informationid());
    }

    /**
     * Method to obtain a json-chain with the information of a Data
     *
     * @return Returns default information
     */
    public String jsonData() {
        return conex.getRecordsInJson("select * from default_data order by defaultdataid desc limit 1;");
    }

    /**
     * Method for aggregating data
     *
     * @param data Receive the Data model
     * @return Return a Boolean if the information was inserted
     */
    @Override
    public boolean addData(Data data) {
        return conex.modifyBD("select addData('" + data.getJson2Xml() + "')");
    }

    /**
     * Method for editing data
     *
     * @param data Receive the Data model
     * @return Return a Boolean if the information was edit
     */
    @Override
    public boolean editData(Data data) {
        return false;
    }

    /**
     * Method for delete data
     *
     * @param data Receive the Data model
     * @return Return a Boolean if the information was delet
     */
    @Override
    public boolean deleteorresetData(Data data) {
        return false;
    }

    public String[] selectData(String type_query, Data data) {
        String query = String.format("select * from listdefaultdata(%s, %s)", type_query, data.getUser_informationid());
        System.out.println(query);
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            return new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
        } else {
            return new String[]{"4", ""};
        }
    }

    public String[] saveData(Data data) {
        String query = String.format("select * from adddata('%s')", data.returnXml());
        System.out.println(query);
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            return new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
        } else {
            return new String[]{"4", ""};
        }
    }

    public String[] restoreData(Data data) {
        String query = String.format("select * from deletedata(%s)", data.getUser_informationid());
        System.out.println(query);
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            return new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
        } else {
            return new String[]{"4", ""};
        }
    }
}
