package hu.ponte.hr.domain;

import hu.ponte.hr.dto.commands.AddImageCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class SignedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;
    private String imagePath;

    @OneToMany(mappedBy = "signedImage")
    private List<ImageFile> imageFiles;

    public SignedImage(AddImageCommand addImageCommand) {
        this.digitalSign = addImageCommand.getSignature();
    }
}
