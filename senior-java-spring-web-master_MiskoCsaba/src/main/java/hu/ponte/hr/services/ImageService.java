package hu.ponte.hr.services;

import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.domain.SignedImage;
import hu.ponte.hr.dto.commands.AddImageCommand;
import hu.ponte.hr.dto.outgoing.ImageMetaData;
import hu.ponte.hr.repository.FileUploadRepository;
import hu.ponte.hr.repository.SignedImageRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Transactional
@Service
@NoArgsConstructor
public class ImageService {

    private FileUploadService fileUploadService;
    private SignatureService signatureService;
    private SignedImageRepository signedImageRepository;
    private FileUploadRepository fileUploadRepository;
    private static final Logger LOGGER = Logger.getLogger(ImageService.class.getName());

    @Autowired
    public ImageService(FileUploadService fileUploadService, SignatureService signatureService, SignedImageRepository signedImageRepository, FileUploadRepository fileUploadRepository) {
        this.fileUploadService = fileUploadService;
        this.signatureService = signatureService;
        this.signedImageRepository = signedImageRepository;
        this.fileUploadRepository = fileUploadRepository;
    }

    public void storeImage(AddImageCommand addImageCommand) throws Exception {

        List<String> imageTypes = new ArrayList<>(List.of("image/jpg", "image/png", "image/jpeg", "image/webp"));
        if (addImageCommand.getFile() != null && addImageCommand.getSignature() != null) {
            List<CommonsMultipartFile> imageFiles = addImageCommand.getFile();
            for (CommonsMultipartFile imageFile : imageFiles) {
                if (imageTypes.contains(imageFile.getContentType())) {
                    ImageFile file = fileUploadService.processFile(imageFile, "image");
                    fileUploadRepository.save(file);
                    LOGGER.info("File uploaded.");
                    SignedImage signedImage = new SignedImage();
                    signedImage.setDigitalSign(signatureService.encodeSign(addImageCommand.getSignature()));
                    file.setSignedImage(signedImage);
                    signedImage.setImagePath(file.getFilePath());
                    signedImage.setSize(file.getFileSize());
                    signedImage.setMimeType(file.getMediaType());
                    signedImage.setName(file.getOriginalFileName());
                    signedImageRepository.save(signedImage);
                    LOGGER.info("SignedImage saved.");
                } else {
                    LOGGER.warning("There is an illegal file type in the input field: " + imageFile.getContentType());
                    throw new RuntimeException("Illegal file type!");
                }
            }
        }
    }

    public List<ImageMetaData> getImageMeta() {
        return signedImageRepository.findAll().stream().map(ImageMetaData::new).collect(Collectors.toList());
    }
}
