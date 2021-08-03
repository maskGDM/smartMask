/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOmodel;

import Config.Conection;
import Interfaces.RecordsCrud;
import Model.Records;
import javax.swing.table.DefaultTableModel;

/**
 * This DAO model class contains the attributes and methods of a Records
 *
 * @author Brito,Solano,Velez
 * @version 1.0
 */
public class RecordsDAO implements RecordsCrud {

    Conection conex = new Conection();
    Records records = new Records();

    /**
     * Method to save all data
     *
     * @param records Receive of the model Records
     * @return Return a boolean if the query was successful
     */
    @Override
    public boolean addRecords(Records records) {
        System.out.println("select insertrecords('" + records.getJson2Xml() + "')");
        return conex.modifyBD("select insertrecords('" + records.getJson2Xml() + "')");
    }

    public String[] listRecords(String type, String id_user) {
        String query = String.format("select * from listrecords(%s, %s)", type, id_user);
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

    @Override
    public boolean editRecords(Records records) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteorresetRecords(Records records) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
