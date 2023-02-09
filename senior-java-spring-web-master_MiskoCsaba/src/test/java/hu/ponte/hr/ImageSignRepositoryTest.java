package hu.ponte.hr;

import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.domain.SignedImage;
import hu.ponte.hr.repository.SignedImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zoltan
 */
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ImageSignRepositoryTest {

    @Autowired
    private SignedImageRepository signedImageRepository;

    @BeforeEach

    @Test
	public void saveSignedImage()  {
        ImageFile imageFile = new ImageFile();
        imageFile.setMediaType(".jpg");
        imageFile.setFileSize(3000L);
        imageFile.setCategory("Image");
        imageFile.setOriginalFileName("myImage.jpg");
        imageFile.setUploadDateTime(ZonedDateTime.now());
        imageFile.setFilePath("www.fakePath.com");

        List<ImageFile> imageFiles = new ArrayList<>();
        imageFiles.add(imageFile);

        SignedImage signedImage = new SignedImage();
        signedImage.setDigitalSign("fakesign");
        signedImage.setSize(imageFile.getFileSize());
        signedImage.setName(imageFile.getOriginalFileName());
        signedImage.setImageFiles(imageFiles);

        signedImageRepository.save(signedImage);

        List<SignedImage> signedImages = signedImageRepository.findAll();

        Assertions.assertEquals("myImage.jpg", signedImages.get(0).getName());

    }

}
