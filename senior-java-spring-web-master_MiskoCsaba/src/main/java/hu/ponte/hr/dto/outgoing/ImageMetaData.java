package hu.ponte.hr.dto.outgoing;

import hu.ponte.hr.domain.SignedImage;
import lombok.Data;

/**
 * @author zoltan
 */
@Data
public class ImageMetaData {
    private String id;
    private String name;
    private String mimeType;
    private long size;
    private String digitalSign;
    private String imageFilePath;

    public ImageMetaData(SignedImage signedImage) {
        this.id = String.valueOf(signedImage.getId());
        this.name = signedImage.getName();
        this.mimeType = signedImage.getMimeType();
        this.size = signedImage.getSize();
        this.digitalSign = signedImage.getDigitalSign();
        this.imageFilePath = signedImage.getImagePath();
    }
}
