package hu.ponte.hr.controller;


import hu.ponte.hr.dto.outgoing.ImageMeta;
import hu.ponte.hr.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping("api/images")
@AllArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    @GetMapping("meta")
    public List<ImageMeta> listImages() {

        return imageService.getImageMeta();
    }

    @GetMapping("preview/{id}")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) {
	}

}
