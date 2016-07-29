package org.lcinga.service;

import org.apache.log4j.Logger;
import org.lcinga.dao.PictureDao;
import org.lcinga.dao.impl.GenericDaoImpl;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.entities.PictureSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    private final Logger logger = Logger.getLogger(ImageService.class);

    @Autowired
    private PictureDao pictureDao;

    public void createPicture(Picture picture) {
        picture.setSmallImage(picture.getPictureSource().getLargeImage());
        pictureDao.create(picture);
    }

    public void createPicture(Picture picture, int width, int height) {
        BufferedImage bufferedImage = convertPictureToBuffered(picture.getPictureSource().getLargeImage());
        BufferedImage bufferedThumbnail;

        if (bufferedImage != null) {
            bufferedThumbnail = createThumb(bufferedImage, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(bufferedThumbnail, "jpg", baos);
                byte[] resizedImageBytes = baos.toByteArray();
                picture.setSmallImage(resizedImageBytes);
            } catch (IOException e) {
                logger.error("Thumbnail was not created. Saving large image for thumbnail...");
                picture.setSmallImage(picture.getPictureSource().getLargeImage());
            }
            pictureDao.create(picture);
        } else {
            logger.error("Picture was not created.");
        }
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
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(in, 0, 0, w, h, null);
        g2.dispose();
        return bi;
    }

}
