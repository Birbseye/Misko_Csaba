package hu.ponte.hr.dto.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Data
@NoArgsConstructor
public class AddImageCommand {

    private String digitalSign;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private CommonsMultipartFile imageFile;
}
