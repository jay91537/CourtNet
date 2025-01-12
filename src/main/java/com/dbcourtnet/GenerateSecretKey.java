package com.dbcourtnet;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class GenerateSecretKey {

    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secretString = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated Secret Key: " + secretString);
    }

}
