package hu.ponte.hr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;

@Service
@Transactional
public class SignService {

    private final VerifyService verifyService;
    private static final Logger LOGGER = Logger.getLogger(SignService.class.getName());

    @Autowired
    public SignService() {
        this.verifyService = new VerifyService();
    }

    public String encodeSign(String input) throws Exception {
        byte[] keyBytes = readPrivateKeyFile().getEncoded();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(input.getBytes());
        byte[] signatureBytes = signature.sign();

        String encodedSignature = Base64.getEncoder().encodeToString(signatureBytes);
        if (!verifyService.verify(input, encodedSignature)) {
            LOGGER.warning("Signature verification failed at these signature: " + input);
            throw new RuntimeException("Signature verification failed");
        }

        return signShortener(Base64.getEncoder().encodeToString(signatureBytes));
    }

    private PrivateKey readPrivateKeyFile() throws Exception {
        String fileName = "senior-java-spring-web-master_MiskoCsaba/src/main/resources/config/keys/key.private";
        byte[] keyBytes = Files.readAllBytes(Paths.get(fileName));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private String signShortener(String originalSign) {
        int maxLength = 200;

        if (originalSign.length() > maxLength) {
            String shortenedSign = originalSign.substring(0, maxLength);
            byte[] encodedBytes = Base64.getEncoder().encode(shortenedSign.getBytes(StandardCharsets.UTF_8));

            return new String(encodedBytes, StandardCharsets.UTF_8);
        }
        return originalSign;
    }
}
