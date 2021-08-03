/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.User_information;

/**
 * This interface contains the methods of User_information
 * @author Michelle
 */
public interface User_informationCrud {

    public boolean addUser_information(User_information user_information);

    public boolean editUser_information(User_information user_information);

    public boolean deleteorresetUser_information(User_information user_information);
}
