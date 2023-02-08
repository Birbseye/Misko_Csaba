package hu.ponte.hr.services;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
@Transactional
public class VerifyService {

    private PublicKey publicKey;

    public VerifyService() throws Exception {
        byte[] keyBytes = readPublicKeyFile();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publicKey = kf.generatePublic(spec);
    }

    public boolean verify(String input, String signature) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(input.getBytes());
        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return sig.verify(signatureBytes);
    }

    private byte[] readPublicKeyFile() throws Exception {
        String fileName = "senior-java-spring-web-master_MiskoCsaba/src/main/resources/config/keys/key.pub";
        return Files.readAllBytes(Paths.get(fileName));
    }
}
