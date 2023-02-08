package hu.ponte.hr.services;

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

@Service
@Transactional
public class SignService {

    private PrivateKey privateKey;

    public SignService() throws Exception {
        byte[] keyBytes = readPrivateKeyFile().getEncoded();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        privateKey = kf.generatePrivate(spec);
    }

    public String sign(String input) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(input.getBytes());
        byte[] signatureBytes = signature.sign();

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
