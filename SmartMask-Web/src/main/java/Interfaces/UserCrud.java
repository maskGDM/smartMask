/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.User;

/**
 * This interface contains the methods of User
 * @author Michelle
 */
public interface UserCrud {

    public boolean addUser(User user);

    public boolean editUser(User user);

    public boolean deleteorresetUser(User user);
}
