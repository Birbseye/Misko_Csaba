package hu.ponte.hr.dto.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class AddImageCommand {

    private List<CommonsMultipartFile> imageFiles;

}
