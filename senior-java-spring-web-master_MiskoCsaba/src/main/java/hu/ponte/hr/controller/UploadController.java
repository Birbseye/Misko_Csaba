package hu.ponte.hr.controller;

import hu.ponte.hr.dto.commands.AddImageCommand;
import hu.ponte.hr.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Component
@RequestMapping("api/file")
@AllArgsConstructor
public class UploadController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<HttpStatus> handleFormUpload(@ModelAttribute AddImageCommand addImageCommand) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, InvalidKeySpecException {
        System.out.println("STEPPED IN THE CONTROLLER -------------------");
        imageService.storeImage(addImageCommand);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
