package hu.ponte.hr.cloudinary;

import com.cloudinary.Cloudinary;
import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.repository.FileUploadRepository;
import hu.ponte.hr.services.CloudinaryFileUploadImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloudinaryFileUploadImpTest {

    @Mock
    private Cloudinary cloudinary;
    @Mock
    private FileUploadRepository fileUploadRepositoryMock;
    private CloudinaryFileUploadImp cloudinaryFileUploadImp;

    @BeforeEach
    void setUp() {
        cloudinaryFileUploadImp = new CloudinaryFileUploadImp(fileUploadRepositoryMock, cloudinary);
    }

    @Test
    void test_findById() {
        String filePath = "https://res.cloudinary.com/dnn3zzp6u/image/authenticated/s--guGX4PTU--/v1672743929/products/adi-goldstein-mDinBvq1Sfg-unsplash_vvvnpx.jpg";
        String fileName = "java.jpg";
        Long fileSize = 85385L;
        String mediaType = "image/jpeg";

        ImageFile imageFile = new ImageFile();
        imageFile.setFilePath(filePath);
        imageFile.setOriginalFileName(fileName);
        imageFile.setCategory("products");
        imageFile.setFileSize(fileSize);
        imageFile.setMediaType(mediaType);

        when(fileUploadRepositoryMock.save(any(ImageFile.class))).thenAnswer(returnsFirstArg());
        when(fileUploadRepositoryMock.findById(anyLong())).thenReturn(Optional.of(imageFile));

        fileUploadRepositoryMock.save(imageFile);
        ImageFile result = cloudinaryFileUploadImp.findById(1L);
        assertNotNull(result);

        verify(fileUploadRepositoryMock, times(1)).save(any(ImageFile.class));
        verify(fileUploadRepositoryMock, times(1)).findById(anyLong());
        verifyNoMoreInteractions(fileUploadRepositoryMock);
    }

}
