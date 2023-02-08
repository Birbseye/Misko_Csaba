package hu.ponte.hr;

import hu.ponte.hr.dto.outgoing.ImageMetaData;
import hu.ponte.hr.services.ImageService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
public class imageServiceTest {

    @Autowired
    ImageService imageService;

    @Test
    public void testEmptyList(){

        List<ImageMetaData> imageMetaDataList = imageService.getImageMeta();

        Assertions.assertEquals(0, imageMetaDataList.size());

    }

}
