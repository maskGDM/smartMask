/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Records;

/**
 * This interface contains the methods of Records
 * @author Michelle
 */
public interface RecordsCrud {

    public boolean addRecords(Records records);

    public boolean editRecords(Records records);

    public boolean deleteorresetRecords(Records records);
}
