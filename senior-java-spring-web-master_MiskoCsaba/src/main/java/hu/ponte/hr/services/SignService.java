package hu.ponte.hr.services;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignService {
    private static final String ALGORITHM = "SHA256withRSA";

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public List<String> encodeSignature(String signatureData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, InvalidKeySpecException {
        List<String> signatures = new ArrayList<>();

        this.privateKey = loadPrivateKey();
        this.publicKey = loadPublicKey();

        Signature privateSignature = Signature.getInstance(ALGORITHM);
        privateSignature.initSign(privateKey);
        privateSignature.update(signatureData.getBytes());

        byte[] encodedPrivateSignature = privateSignature.sign();

        signatures.add(Base64.getEncoder().encodeToString(encodedPrivateSignature));

        Signature publicSignature = Signature.getInstance(ALGORITHM);
        publicSignature.initVerify(publicKey);
        publicSignature.update(signatureData.getBytes());

        byte[] encodedPublicSignature = privateSignature.sign();

        signatures.add(Base64.getEncoder().encodeToString(encodedPublicSignature));

        return signatures;
    }

    private PrivateKey loadPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (FileReader fileReader = new FileReader("senior-java-spring-web-master_MiskoCsaba/src/main/resources/config/keys/key.private")) {
            StringBuilder privateKeyBuilder = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1) {
                privateKeyBuilder.append((char) c);
            }
            String privateKeyPEM = privateKeyBuilder.toString();

            privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "");
            privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
            privateKeyPEM = privateKeyPEM.replace("\n", "");

            byte[] encodedPrivateKey = Base64.getDecoder().decode(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            return keyFactory.generatePrivate(privateKeySpec);
        }
    }

    private PublicKey loadPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (FileReader fileReader = new FileReader("senior-java-spring-web-master_MiskoCsaba/src/main/resources/config/keys/key.pub")) {
            StringBuilder publicKeyBuilder = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1) {
                publicKeyBuilder.append((char) c);
            }
            String publicKeyPEM = publicKeyBuilder.toString();

            publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
            publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
            publicKeyPEM = publicKeyPEM.replace("\n", "");

            byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }
    }
}
