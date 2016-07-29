package org.lcinga.service;

import org.lcinga.dao.PictureSourceDao;
import org.lcinga.dao.PictureDao;
import org.lcinga.model.entities.PictureSource;
import org.lcinga.model.entities.Picture;
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
    private final int RESIZED_PICTURE_HEIGHT = 200;
    private final int RESIZED_PICTURE_WIDTH = 200;

    @Autowired
    private PictureSourceDao pictureSourceDao;

    @Autowired
    private PictureDao pictureDao;

//    @PostConstruct
//    public void init() {
//        System.out.println("Veikiaaaa!!!");
//    }

    public void addImageSource(PictureSource imagesource){
        pictureSourceDao.create(imagesource);
    }

    public void addImage(Picture picture) {
        if (picture.getSmallImage() == null) {
            BufferedImage bufferedImage = convertPictureToBuffered(picture.getPictureSource().getLargeImage());
            BufferedImage resizedBufferedImage = createThumb(bufferedImage, RESIZED_PICTURE_WIDTH, RESIZED_PICTURE_HEIGHT);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(resizedBufferedImage, "jpg", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] resizedImageBytes = baos.toByteArray();

            picture.setSmallImage(resizedImageBytes);
        }
        pictureDao.create(picture);
    }

    private BufferedImage convertPictureToBuffered(byte[] smallImage) {
        InputStream in = new ByteArrayInputStream(smallImage);
        try {
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;                    //sutvarkyti
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
