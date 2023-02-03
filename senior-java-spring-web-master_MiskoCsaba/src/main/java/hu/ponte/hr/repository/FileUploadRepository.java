package hu.ponte.hr.repository;

import hu.ponte.hr.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<ImageFile, Long> {
}
