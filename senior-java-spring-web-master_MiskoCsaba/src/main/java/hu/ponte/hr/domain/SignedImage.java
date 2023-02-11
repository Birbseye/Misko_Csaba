package hu.ponte.hr.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class SignedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "media_type")
    private String mimeType;

    private long size;

    @Column(columnDefinition = "TEXT", length = 100000)
    private String digitalSign;

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "signedImage")
    private List<ImageFile> imageFiles;

    @Column(name = "date_created")
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "deleted_status")
    private boolean isDeleted = false;

    @Column(name = "date_deleted")
    private LocalDateTime dateDeleted;

}
