/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Login;

/**
 * This interface contains the methods of Login
 * @author Michelle
 */
public interface LoginCrud {

    public boolean addLogin(Login login);

    public boolean editLogin(Login login);

    public boolean deleteorresetLogin(Login login);
}
