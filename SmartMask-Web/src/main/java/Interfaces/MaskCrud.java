/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Mask;

/**
 * This interface contains the methods of Mask
 * @author Michelle
 */
public interface MaskCrud {

    public boolean addMask(Mask mask);

    public boolean editMask(Mask mask);

    public boolean deleteorresetMask(Mask mask);
}
