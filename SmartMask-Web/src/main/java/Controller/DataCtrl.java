/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Config.WeEncoder;
import DAOmodel.DataDAO;
import DAOmodel.MaskDAO;
import DAOmodel.RecordsDAO;
import DAOmodel.UserDAO;
import Model.Data;
import Model.Mask;
import Model.User;
import Model.User_information;
import java.io.File;

/**
 *
 * @author geova
 */
public class DataCtrl {

    DataDAO dataDAO;
    WeEncoder weEncoder;

    public DataCtrl() {
        dataDAO = new DataDAO();
        weEncoder = new WeEncoder();
    }

    public String[] saveData(
            String user_informationid,
            Double maxvalueco2,
            Double minvalueco2,
            Double maxvalueco,
            Double minvalueco,
            Double maxvaluec4h10,
            Double mainvaluec4h10,
            Double maxvaluenh3,
            Double minvaluenh3
    ) {
        Data dataMask = new Data();
        dataMask.setUser_informationid(user_informationid);
        dataMask.setMaxvalueco2(maxvalueco2);
        dataMask.setMinvalueco2(minvalueco2);
        dataMask.setMaxvalueco(maxvalueco);
        dataMask.setMinvalueco(minvalueco);
        dataMask.setMaxvaluec4h10(maxvaluec4h10);
        dataMask.setMainvaluec4h10(mainvaluec4h10);

        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = dataDAO.saveData(dataMask);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        } else if (resp[0].equals("3")) {
            status = resp[0];
            message = "No se puede agregar esta mascarilla";
        }
        return new String[]{status, message, data};
    }

    public String[] selectData(
            String user_informationid
    ) {
        Data dataMask = new Data();
        dataMask.setUser_informationid(user_informationid);
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = dataDAO.selectData("1", dataMask);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        }

        return new String[]{status, message, data};
    }

    public String[] restoreData(
            String user_informationid
    ) {
        Data dataMask = new Data();
        dataMask.setUser_informationid(user_informationid);
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = dataDAO.restoreData(dataMask);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        }

        return new String[]{status, message, data};
    }

}
