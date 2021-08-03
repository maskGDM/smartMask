/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.City;

/**
 * This interface contains the methods of City
 * @author Michelle
 */
public interface CityCrud {

    public boolean addCity(City city);

    public boolean editCity(City city);

    public boolean deleteorresetCity(City city);
}
