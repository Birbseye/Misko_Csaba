package hu.ponte.hr.repository;

import hu.ponte.hr.domain.SignedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignedImageRepository extends JpaRepository<SignedImage, Long> {
}
