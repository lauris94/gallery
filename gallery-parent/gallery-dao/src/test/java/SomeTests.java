import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lcinga.dao.ImageDao;
import org.lcinga.dao.ImageSourceDao;
import org.lcinga.model.entities.Image;
import org.lcinga.model.entities.ImageSource;
import org.lcinga.model.enums.ImageQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/application-context-test.xml")
@Transactional
@Commit
public class SomeTests {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ImageSourceDao imageSourceDao;

    public void testCreateImage() {
        ImageSource imageSource = new ImageSource();
        imageSource.setLargeImage(ByteBuffer.allocate(4).putInt(54).array());
        imageSourceDao.create(imageSource);
        Image image = new Image();
        image.setImageSource(imageSource);
        image.setDescription("aprasymas");
        image.setEditDate(new Date());
        image.setQuality(ImageQuality.BAD);
        image.setSmallImage(ByteBuffer.allocate(4).putInt(54).array());
        image.setUploadDate(new Date());
        imageDao.create(image);
    }

    @Test
    public void checkImage() {

       //testCreateImage();
        testCreateImage();

        Image image = imageDao.find(63L);

        Assert.assertNotNull("Tokio image nera", image);
    }
}

