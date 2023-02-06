package hu.ponte.hr.dto.outgoing;

import hu.ponte.hr.domain.SignedImage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @author zoltan
 */
@Data
public class ImageMeta
{
	private String id;
	private String name;
	private String mimeType;
	private long size;
	private String digitalSign;
	private String imageFilePath;

	public ImageMeta(SignedImage signedImage) {
		this.id = String.valueOf(signedImage.getId());
		this.name = signedImage.getName();
		this.mimeType = signedImage.getMimeType();
		this.size = signedImage.getSize();
		this.digitalSign = signedImage.getPublicSign();
		this.imageFilePath = signedImage.getImagePath();
	}
}
