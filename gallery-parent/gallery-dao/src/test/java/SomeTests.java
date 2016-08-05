import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/application-context-test-dao.xml")
@Transactional
@Commit
public class SomeTests {
    //    @Autowired
//    private PictureDao imageDao;
//    @Autowired
//    private PictureSourceDao imageSourceDao;

//    public void testCreateImage() {
//        PictureSource imageSource = new PictureSource();
//        imageSource.setLargeImage(ByteBuffer.allocate(4).putInt(54).array());
//        imageSourceDao.create(imageSource);
//        Picture image = new Picture();
//        image.setPictureSource(imageSource);
//        image.setDescription("aprasymas");
//        image.setEditDate(new Date());
//        image.setQuality(ImageQuality.BAD);
//        image.setSmallImage(ByteBuffer.allocate(4).putInt(54).array());
//        image.setUploadDate(new Date());
//        imageDao.create(image);
//    }

    @Test
    public void checkImage() {
        //testCreateImage();
        //testCreateImage();

        // Picture image = imageDao.find(63L);

        //Assert.assertNotNull("Tokio image nera", image);
    }
}

