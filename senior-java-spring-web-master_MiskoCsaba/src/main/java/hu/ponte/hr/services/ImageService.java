package hu.ponte.hr.services;

import hu.ponte.hr.dto.outgoing.ImageMetaData;
import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.domain.SignedImage;
import hu.ponte.hr.dto.commands.AddImageCommand;
import hu.ponte.hr.repository.FileUploadRepository;
import hu.ponte.hr.repository.SignedImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class ImageService {

    private final CloudinaryService cloudinaryService;
    private final SignService signService;
    private final SignedImageRepository signedImageRepository;
    private final FileUploadRepository fileUploadRepository;
    private static final Logger LOGGER = Logger.getLogger(ImageService.class.getName());

    public void storeImage(AddImageCommand addImageCommand) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, InvalidKeySpecException {

        if (addImageCommand.getImageFile() != null) {
            ImageFile imageFile = cloudinaryService.uploadImage(addImageCommand.getImageFile());
            fileUploadRepository.save(imageFile);
            LOGGER.info("File uploaded");

            List<String> encodedKeys = signService.encodeSignature(addImageCommand.getDigitalSign());
            SignedImage signedImage = new SignedImage(addImageCommand);
            signedImage.setPrivateSign(encodedKeys.get(0));
            signedImage.setPublicSign(encodedKeys.get(1));
            signedImage.setImagePath(imageFile.getFilePath());
            signedImage.setSize(imageFile.getFileSize());
            signedImage.setMimeType(imageFile.getMediaType());

            signedImageRepository.save(signedImage);

        }
    }

    public List<ImageMetaData> getImageMeta(){
        return signedImageRepository.findAll().stream().map(ImageMetaData::new).collect(Collectors.toList());
    }
}
