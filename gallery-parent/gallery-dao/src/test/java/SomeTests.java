import org.junit.Test;
import org.junit.runner.RunWith;
import org.lcinga.dao.ImageDao;
import org.lcinga.model.entities.Image;
import org.lcinga.model.entities.ImageSource;
import org.lcinga.model.enums.ImageQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.Date;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/application-context.xml")
public class SomeTests {
//    @Autowired
//    private ImageDao imageDao;
//
//    @Test
//    @Transactional
//    public void testCreateImage() {
//        ImageSource imageSource = new ImageSource();
//        imageSource.setId(1);
//        imageSource.setLargeImage(ByteBuffer.allocate(4).putInt(54).array());
//        imageSource.setVersion(0);
//        Image image = new Image();
//        image.setImageSource(imageSource);
//        image.setVersion(0);
//        image.setId(1);
//        image.setDescription("aprasymas");
//        image.setEditDate(new Date());
//        image.setQuality(ImageQuality.BAD);
//        image.setSmallImage(ByteBuffer.allocate(4).putInt(54).array());
//        image.setUploadDate(new Date());
//        imageDao.create(image);
//   }
//
//    @Test
//    public void testSampleServiceGetOrder() {
//
//        Image existingImage = imageDao.find(1L);
//
//        if (existingImage != null) {
//
//            assertThat(imageDao.find(1L), instanceOf(Image.class));
//
//            assertNotNull("Security isn't null",
//                    existingImage.getDescription());
//        }
//
//        assertNotNull("Object is not null", existingImage);
//
//    }

}

