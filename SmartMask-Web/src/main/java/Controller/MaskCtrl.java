/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Config.WeEncoder;
import DAOmodel.MaskDAO;
import DAOmodel.RecordsDAO;
import DAOmodel.UserDAO;
import Model.Mask;
import Model.User;
import Model.User_information;
import java.io.File;

/**
 *
 * @author geova
 */
public class MaskCtrl {

    MaskDAO maskDAO;
    WeEncoder weEncoder;

    public MaskCtrl() {
        maskDAO = new MaskDAO();
        weEncoder = new WeEncoder();
    }

    public String[] saveMask(
            String mask_code,
            String user_informationid
    ) {
        Mask mask = new Mask();
        mask.setMask_code(mask_code);
        mask.setUser_informationid(user_informationid);
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = maskDAO.saveMask(mask);

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

    public String[] selectMask(
            String user_informationid
    ) {
        Mask mask = new Mask();
        mask.setUser_informationid(user_informationid);
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = maskDAO.selectMask(mask);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        } 
        
        return new String[]{status, message, data};
    }

    public String[] deleteMask(
            String mask_code
    ) {
        Mask mask = new Mask();
        mask.setMask_code(mask_code);
        String status = "4", message = "Error al retornar datos", data = "[]";

        String[] resp = maskDAO.deleteMask(mask);

        if (resp[0].equals("2")) {
            status = resp[0];
            data = resp[1];
            message = "Los datos se han retornado correctamente";
        } else if (resp[0].equals("3")) {
            status = resp[0];
            message = "La mascarilla se encuentra ya en uso";
        }
        return new String[]{status, message, data};
    }
}
