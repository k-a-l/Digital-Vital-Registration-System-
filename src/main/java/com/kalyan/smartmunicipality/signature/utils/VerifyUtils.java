package com.kalyan.smartmunicipality.signature.utils;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class VerifyUtils {

    public static boolean verifySignature(byte[] data, BigInteger signature, BigInteger publicKey, BigInteger modulus) throws NoSuchAlgorithmException {
        byte[] hash = SignatureUtils.sha256(data);
        BigInteger hashed = new BigInteger(1, hash);

        BigInteger decryptedSignature = signature.modPow(publicKey, modulus);
        return hashed.equals(decryptedSignature);
    }

    public static boolean verifySignature(String message, BigInteger signature, BigInteger publicKey, BigInteger modulus) throws NoSuchAlgorithmException {
        byte[] hash = SignatureUtils.sha256(message);
        BigInteger hashed = new BigInteger(1, hash);

        BigInteger decryptedSignature = signature.modPow(publicKey, modulus);
        return hashed.equals(decryptedSignature);
    }

    // Optional: directly verify a BigInteger message (e.g., already hashed)
    public static boolean verifySignature(BigInteger messageHash, BigInteger signature, BigInteger publicKey, BigInteger modulus) {
        BigInteger decryptedSignature = signature.modPow(publicKey, modulus);
        return messageHash.equals(decryptedSignature);
    }
}
