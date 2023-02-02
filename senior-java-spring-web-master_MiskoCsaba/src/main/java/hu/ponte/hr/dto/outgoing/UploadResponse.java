package hu.ponte.hr.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadResponse {
    @JsonAlias("public_id")
    private String publicId;

    private String signature;
    private String format;

    @JsonAlias("resource_type")
    private String resourceType;

    @JsonAlias("created_at")
    private String createdAt;
    private String type;
    private Long version;
    private Long bytes;
    private String etag;

    private String url;
    @JsonAlias("secure_url")
    private String secureUrl;
    private String[] tags;
    private Long width;
    private Long height;
    @JsonAlias("original_filename")
    private String originalFileName;
}
