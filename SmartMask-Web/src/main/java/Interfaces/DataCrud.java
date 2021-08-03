/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Data;

/**
 * This interface contains the methods of Data 
 * @author geova
 */
public interface DataCrud {

    public boolean addData(Data data);

    public boolean editData(Data data);

    public boolean deleteorresetData(Data data);
}
