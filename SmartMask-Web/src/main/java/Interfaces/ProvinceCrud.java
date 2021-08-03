/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Province;

/**
 * This interface contains the methods of Province
 * @author Michelle
 */
public interface ProvinceCrud {

    public boolean addProvince(Province province);

    public boolean editProvince(Province province);

    public boolean deleteorresetProvince(Province province);
}
