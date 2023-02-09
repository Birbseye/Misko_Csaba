package hu.ponte.hr.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.dto.outgoing.UploadResponse;
import hu.ponte.hr.exception.CloudinaryUploadException;
import hu.ponte.hr.repository.FileUploadRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Transactional
public class CloudinaryFileUploadImp extends FileUploadService {

    private final Cloudinary cloudinary;

    public CloudinaryFileUploadImp(FileUploadRepository fileUploadRepository, Cloudinary cloudinary) {
        super(fileUploadRepository);
        this.cloudinary = cloudinary;
    }

    @Override
    protected ImageFile storeFile(CommonsMultipartFile commonsMultipartFile, String category) {
        Map params = ObjectUtils.asMap(
                "folder", category,
                "access_mode", "authenticated",
                "overwrite", false,
                "type", "authenticated",
                "resource_type", "auto",
                "use_filename", true,
                "transformation", new Transformation<>().width(600).height(400).crop("fill"));
        UploadResponse uploadResponse;
        File fileToUpload = new File(System.getProperty("java.io.tmpdir") + '/' + commonsMultipartFile.getOriginalFilename());
        try {
            commonsMultipartFile.transferTo(fileToUpload);
            uploadResponse = new ObjectMapper()
                    .convertValue(cloudinary.uploader().upload(fileToUpload, params), UploadResponse.class);
        } catch (IOException e) {
            throw new CloudinaryUploadException();
        }

        return new ImageFile(uploadResponse, commonsMultipartFile);
    }
}
