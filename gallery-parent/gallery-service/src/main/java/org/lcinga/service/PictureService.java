package org.lcinga.service;

import org.apache.log4j.Logger;
import org.lcinga.dao.PictureDao;
import org.lcinga.model.entities.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PictureService implements IPictureService{

    private final Logger logger = Logger.getLogger(PictureService.class);

    private final PictureDao pictureDao;

    @Autowired
    public PictureService(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }

    public void createPicture(Picture picture) {
        pictureDao.create(picture);
    }

    public void createPicture(Picture picture, int width, int height) {
        BufferedImage bufferedImage = convertPictureToBuffered(picture.getPictureSource().getLargeImage());

        if (bufferedImage != null) {
            BufferedImage bufferedThumbnail = createThumb(bufferedImage, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedThumbnail, "jpg", baos);
                byte[] resizedImageBytes = baos.toByteArray();
                picture.setSmallImage(resizedImageBytes);
            } catch (IOException e) {
                logger.error("Thumbnail was not created.");
            }
            createPicture(picture);
        } else {
            logger.error("Picture was not created.");
        }
    }

    public Picture getPicture(long id){
        return pictureDao.find(id);
    }

    public List<Picture> getAllPictures(){
        return pictureDao.getAll();
    }

    private BufferedImage convertPictureToBuffered(byte[] largeImage) {
        InputStream in = new ByteArrayInputStream(largeImage);
        try {
            return ImageIO.read(in);
        } catch (IOException e) {
            logger.error("Large image not found!");
            return null;
        }
    }

    private BufferedImage createThumb(BufferedImage in, int w, int h) {
        // scale w, h to keep aspect constant
        double outputAspect = 1.0 * w / h;
        double inputAspect = 1.0 * in.getWidth() / in.getHeight();
        if (outputAspect < inputAspect) {
            // width is limiting factor; adjust height to keep aspect
            h = (int) (w / inputAspect);
        } else {
            // height is limiting factor; adjust width to keep aspect
            w = (int) (h * inputAspect);
        }
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(
                java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(in, 0, 0, w, h, null);
        g2.dispose();
        return bi;
    }

}
