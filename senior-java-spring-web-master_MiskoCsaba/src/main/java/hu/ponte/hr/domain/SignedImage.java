package hu.ponte.hr.domain;

import hu.ponte.hr.dto.commands.AddImageCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String privateSign;
    private String publicSign;
    private String imagePath;

    public SignedImage(AddImageCommand addImageCommand) {
        this.digitalSign = addImageCommand.getDigitalSign();
    }
}
