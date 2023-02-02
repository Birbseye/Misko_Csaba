package hu.ponte.hr.services;

import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.dto.commands.AddImageCommand;
import hu.ponte.hr.repository.FileUploadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Transactional
@Service
@AllArgsConstructor
public class ImageStoreService {

    private final CloudinaryService CLOUDINARY_SERVICE;
    private final FileUploadRepository FILE_UPLOAD_REPOSITORY;
    private static final Logger LOGGER = Logger.getLogger(ImageStoreService.class.getName());

    public void storeImage(AddImageCommand addImageCommand) {

        if (addImageCommand.getImageFile() != null) {
            ImageFile imageFile = CLOUDINARY_SERVICE.uploadImage(addImageCommand.getImageFile());
            FILE_UPLOAD_REPOSITORY.save(imageFile);
            LOGGER.info("File uploaded");
        }
    }

}
