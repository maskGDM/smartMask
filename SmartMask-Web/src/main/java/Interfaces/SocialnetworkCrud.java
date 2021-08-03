/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Socialnetwork;

/**
 * This interface contains the methods of Social network
 * @author Michelle
 */
public interface SocialnetworkCrud {

    public boolean addSocialnetwork(Socialnetwork socialnetwork);

    public boolean editSocialnetwork(Socialnetwork socialnetwork);

    public boolean deleteorresetSocialnetwork(Socialnetwork socialnetwork);
}
