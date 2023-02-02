package hu.ponte.hr.services;

import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.dto.commands.AddImageCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.logging.Logger;

@Transactional
@Service
@AllArgsConstructor
public class ImageStoreService {

    private final FileUploadService fileUploadService;
    private static final Logger LOGGER = Logger.getLogger(ImageStoreService.class.getName());

    public void storeImage(AddImageCommand addImageCommand) {
        addImageCommand.getImageFiles()
                .forEach(imageFile -> {
                    try {
                        ImageFile imageFileToUpload = fileUploadService.processFile(imageFile);
                    } catch (IOException e) {
                        LOGGER.severe("Error while processing image file: " + e.getMessage());
                    }
                });
    }

}
