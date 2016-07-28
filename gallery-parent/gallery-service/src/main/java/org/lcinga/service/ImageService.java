package org.lcinga.service;

import org.lcinga.dao.ImageDao;
import org.lcinga.model.entities.Image;
import org.lcinga.model.entities.ImageSource;
import org.lcinga.model.enums.ImageQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    public void addImage(byte[] largeImage, byte[] smallImage, String description, Date editDate, ImageQuality imageQuality){
        ImageSource imageSource = new ImageSource();
        imageSource.setLargeImage(largeImage);
        Image image = new Image();
        image.setImageSource(imageSource);
        image.setDescription(description);
        image.setEditDate(editDate);
        image.setQuality(imageQuality);
        image.setSmallImage(smallImage);
        image.setUploadDate(new Date());
        imageDao.create(image);
    }
}
