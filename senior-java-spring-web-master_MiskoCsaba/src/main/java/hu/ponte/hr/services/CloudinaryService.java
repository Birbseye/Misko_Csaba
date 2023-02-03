package hu.ponte.hr.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.dto.outgoing.UploadResponse;
import hu.ponte.hr.exception.CloudinaryUploadException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class CloudinaryService {

    private final Cloudinary CLOUDINARY;

    public ImageFile uploadImage(CommonsMultipartFile image) {
        Map params = ObjectUtils.asMap(
                "access_mode", "authenticated",
                "overwrite", false,
                "type", "authenticated",
                "resource_type", "auto",
                "use_filename", true
        );

        UploadResponse response;
        File fileToUpload = new File(System.getProperty("java.io.tmpdir") + '/' + image.getOriginalFilename());
        try {
            image.transferTo(fileToUpload);
            response = new ObjectMapper()
                    .convertValue(CLOUDINARY.uploader().upload(fileToUpload, params), UploadResponse.class);
        } catch (IOException e) {
            throw new CloudinaryUploadException();
        }

        return new ImageFile(response, image);
    }
}
