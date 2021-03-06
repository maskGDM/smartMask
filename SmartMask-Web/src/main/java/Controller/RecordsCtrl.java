/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAOmodel.RecordsDAO;
import java.io.File;

/**
 *
 * @author geova
 */
public class RecordsCtrl {

    RecordsDAO recordsDao;

    public RecordsCtrl() {
        recordsDao = new RecordsDAO();
    }

    public String[] listRecords(
            String type,
            String id_user
    ) {
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = recordsDao.listRecords(type, id_user);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        }
        return new String[]{status, message, data};
    }

    public String[] listRecordsInterval(
            String id_user,
            String stardate,
            String enddate
    ) {
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = recordsDao.listRecordsInterval("1", id_user, stardate, enddate);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        }
        return new String[]{status, message, data};
    }
}
