package hu.ponte.hr.repository;

import hu.ponte.hr.domain.SignedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignedImageRepository extends JpaRepository<SignedImage, Long> {
}
