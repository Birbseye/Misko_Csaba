package hu.ponte.hr.controller;

import hu.ponte.hr.dto.commands.AddImageCommand;
import hu.ponte.hr.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("api/file")
@AllArgsConstructor
public class UploadController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<HttpStatus> handleFormUpload(@ModelAttribute AddImageCommand addImageCommand) throws Exception {
        imageService.storeImage(addImageCommand);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
