package hu.ponte.hr.services;

import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.domain.SignedImage;
import hu.ponte.hr.dto.commands.AddImageCommand;
import hu.ponte.hr.dto.outgoing.ImageMetaData;
import hu.ponte.hr.repository.FileUploadRepository;
import hu.ponte.hr.repository.SignedImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class ImageService {

    private final FileUploadService fileUploadService;
    private final SignService signService;
    private final SignedImageRepository signedImageRepository;
    private final FileUploadRepository fileUploadRepository;
    private static final Logger LOGGER = Logger.getLogger(ImageService.class.getName());

    public void storeImage(AddImageCommand addImageCommand) throws Exception {
        if (addImageCommand.getFile() != null && addImageCommand.getSignature() != null) {
            List<CommonsMultipartFile> imageFiles = addImageCommand.getFile();
            List<String> imageTypes = new ArrayList<>(List.of("image/jpg", "image/png", "image/jpeg"));
            for (CommonsMultipartFile imageFile : imageFiles) {
                if (imageTypes.contains(imageFile.getContentType())) {
                    ImageFile file = fileUploadService.processFile(imageFile, "image");
                    fileUploadRepository.save(file);
                    LOGGER.info("File uploaded.");
                    SignedImage signedImage = new SignedImage(addImageCommand);
                    signedImage.setDigitalSign(signService.encodeSign(addImageCommand.getSignature()));
                    file.setSignedImage(signedImage);
                    signedImage.setImagePath(file.getFilePath());
                    signedImage.setSize(file.getFileSize());
                    signedImage.setMimeType(file.getMediaType());
                    signedImage.setName(file.getOriginalFileName());
                    signedImageRepository.save(signedImage);
                    LOGGER.info("SignedImage saved.");
                }
            }
        }
    }
    public List<ImageMetaData> getImageMeta() {
        return signedImageRepository.findAll().stream().map(ImageMetaData::new).collect(Collectors.toList());
    }
}
