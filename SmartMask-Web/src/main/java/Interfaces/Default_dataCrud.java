/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Default_data;

/**
 * This interface contains the methods of default_data
 * @author Michelle
 */
public interface Default_dataCrud {

    public boolean addDefault_data(Default_data default_data);

    public boolean editDefault_data(Default_data default_data);

    public boolean deleteorresetDefault_data(Default_data default_data);
}
