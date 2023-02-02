package hu.ponte.hr.services;

import hu.ponte.hr.domain.ImageFile;
import hu.ponte.hr.repository.FileUploadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@AllArgsConstructor
public abstract class FileUploadService {

    private final FileUploadRepository fileUploadRepository;

    public ImageFile processFile(CommonsMultipartFile commonsMultipartFile) throws IOException {
        ImageFile imageFile = storeFile(commonsMultipartFile);
        Long id = fileUploadRepository.save(imageFile).getId();
        return findById(id);
    }

    protected abstract ImageFile storeFile(CommonsMultipartFile commonsMultipartFile) throws IOException;
    public ImageFile findById(Long id) {
        return fileUploadRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
