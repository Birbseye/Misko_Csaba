package hu.ponte.hr.controller;


import hu.ponte.hr.dto.outgoing.ImageMetaData;
import hu.ponte.hr.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("api/images")
@AllArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    @GetMapping("meta")
    public List<ImageMetaData> listImages() {
        return imageService.getImageMeta();
    }
}
