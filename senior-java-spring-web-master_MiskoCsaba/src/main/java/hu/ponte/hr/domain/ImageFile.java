package hu.ponte.hr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import hu.ponte.hr.dto.outgoing.UploadResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "image_file")
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "category")
    private String category = "default";
    @Column(name = "upload_datetime")
    @JsonFormat(locale = "hu", shape = JsonFormat.Shape.STRING, pattern = "yyyy. MM. dd. HH:mm:ss (Z)")
    private ZonedDateTime uploadDateTime = ZonedDateTime.now();

    @ManyToOne
    private SignedImage signedImage;

    public ImageFile(UploadResponse uploadResponse, CommonsMultipartFile commonsMultipartFile) {
        this.filePath = uploadResponse.getSecureUrl();
        this.originalFileName = commonsMultipartFile.getFileItem().getName();
        this.fileSize = uploadResponse.getBytes();
        this.mediaType = commonsMultipartFile.getContentType();
    }

}
