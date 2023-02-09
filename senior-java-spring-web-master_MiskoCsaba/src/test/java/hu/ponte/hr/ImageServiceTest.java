package hu.ponte.hr;

import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.dto.commands.AddImageCommand;
import hu.ponte.hr.dto.outgoing.ImageMetaData;
import hu.ponte.hr.repository.FileUploadRepository;
import hu.ponte.hr.repository.SignedImageRepository;
import hu.ponte.hr.services.FileUploadService;
import hu.ponte.hr.services.ImageService;
import hu.ponte.hr.services.SignatureService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Rollback
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private FileUploadRepository fileUploadRepository;

    @Mock
    private SignatureService signatureService;

    @Mock
    private SignedImageRepository signedImageRepository;

    @InjectMocks
    private ImageService imageService;

    @Test
    public void test_EmptyList() {
        List<ImageMetaData> imageMetaDataList = imageService.getImageMeta();
        Assertions.assertEquals(0, imageMetaDataList.size());
    }

    @Test
    public void storeImage_whenFileAndSignatureNotNull_thenShouldSaveFileAndSignedImage() throws Exception {
        AddImageCommand addImageCommand = new AddImageCommand();
        List<CommonsMultipartFile> imageFiles = new ArrayList<>();

        File file = new File("src/test/resources/images/cat.jpg");
        FileInputStream input = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        input.read(data);
        input.close();

        FileItem fileItem = new DiskFileItem("file", "image/jpeg", false, file.getName(), (int) file.length(), file.getParentFile());
        fileItem.getOutputStream().write(data);

        CommonsMultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        imageFiles.add(multipartFile);
        addImageCommand.setFile(imageFiles);
        addImageCommand.setSignature("signature");

        ImageFile imageFile = new ImageFile();
        imageFile.setFilePath("path");
        imageFile.setFileSize(1L);
        imageFile.setMediaType("image/jpg");
        imageFile.setOriginalFileName("image.jpg");

        when(fileUploadService.processFile(any(), any())).thenReturn(imageFile);

        imageService.storeImage(addImageCommand);

    }

    @Test
    public void storeImage_whenIllegalFileType() throws Exception {
        AddImageCommand addImageCommand = new AddImageCommand();
        List<CommonsMultipartFile> imageFiles = new ArrayList<>();

        File file = new File("src/test/resources/images/cat.jpg");
        FileInputStream input = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        input.read(data);
        input.close();

        FileItem fileItem = new DiskFileItem("file", ".pdf", false, file.getName(), (int) file.length(), file.getParentFile());
        fileItem.getOutputStream().write(data);

        CommonsMultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        imageFiles.add(multipartFile);
        addImageCommand.setFile(imageFiles);
        addImageCommand.setSignature("signature");

        assertThrows(RuntimeException.class, () -> {
            imageService.storeImage(addImageCommand);
        });
    }
}
