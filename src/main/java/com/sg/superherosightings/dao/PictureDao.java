/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Picture;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface PictureDao {
    
    public Picture addPicture(Picture picture);
    
    public void deletePicture(long pictureId);
    
    public Picture getPictureById(long pictureId);
    
    public List<Picture> getAllPictures();
}
