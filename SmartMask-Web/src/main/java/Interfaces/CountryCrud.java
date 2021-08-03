/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Country;

/**
 * This interface contains the methods of Country
 * @author Michelle
 */
public interface CountryCrud {

    public boolean addCountry(Country country);

    public boolean editCountry(Country country);

    public boolean deleteorresetCountry(Country country);
}
