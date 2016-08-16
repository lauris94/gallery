import org.junit.Test;
import org.junit.runner.RunWith;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.entities.PictureSource;
import org.lcinga.model.enums.ImageQuality;
import org.lcinga.service.PictureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        "classpath:META-INF/application-context-test.xml")
@Transactional
@Commit
public class ServiceTests {

    @Autowired
    private PictureServiceImpl pictureServiceImpl;

    @Test
    public void checkServiceBean() {
        PictureSource pictureSource = new PictureSource();

        File file = new File("C:\\pele.jpg");

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        byte[] bytes = bos.toByteArray();

        pictureSource.setLargeImage(bytes);
        Picture picture = new Picture();
        picture.setUploadDate(new Date());
        picture.setQuality(ImageQuality.NORMAL);
        picture.setDescription("blabla");
        picture.setEditDate(new Date());
        picture.setPictureSource(pictureSource);
        pictureServiceImpl.createPicture(picture, 200, 200);
    }
}


