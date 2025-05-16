package com.kalyan.smartmunicipality.signature.utils;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

@Service
public class VerifyUtils {

    public static boolean verifySignature(String message, BigInteger signature, BigInteger publicKey, BigInteger modulus) throws NoSuchAlgorithmException {
        byte[] hash = SignatureUtils.sha256(message);
        BigInteger hashed = new BigInteger(1,hash);
        BigInteger decrypted = hashed.modPow(publicKey, modulus);
        return hashed.equals(decrypted);

    }
}
