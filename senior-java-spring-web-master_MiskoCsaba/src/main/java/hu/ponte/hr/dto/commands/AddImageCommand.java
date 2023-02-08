package hu.ponte.hr.dto.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class AddImageCommand {

    private String signature;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private List<CommonsMultipartFile> file;
}
