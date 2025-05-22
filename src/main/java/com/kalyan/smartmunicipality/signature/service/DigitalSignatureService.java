package com.kalyan.smartmunicipality.signature.service;

import com.kalyan.smartmunicipality.signature.utils.SignatureUtils;
import com.kalyan.smartmunicipality.signature.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Service

public class DigitalSignatureService {

    private static final Logger LOG = LoggerFactory.getLogger(DigitalSignatureService.class);
    private final SignatureKeysService signatureKeysService;

    // New version: sign raw bytes
    public String signCertificate(byte[] certificateBytes) throws NoSuchAlgorithmException {
        BigInteger message = new BigInteger(1, certificateBytes); // Avoid negative numbers
        BigInteger signature = SignatureUtils.sign(message.toByteArray(), signatureKeysService.getPrivateKey(), signatureKeysService.getModulus());
        return signature.toString();
    }

    // New version: verify raw bytes
    public boolean verifyCertificate(byte[] uploadedBytes, String signature) throws NoSuchAlgorithmException {
        BigInteger sig = new BigInteger(signature);
        return VerifyUtils.verifySignature(uploadedBytes, sig, signatureKeysService.getPublicKey(), signatureKeysService.getModulus());
    }


    
    public DigitalSignatureService(SignatureKeysService signatureKeysService) {
        this.signatureKeysService = signatureKeysService;
        LOG.info("\n Private Keys: {}, \nPublic: {}, \nModulus: {}",
                signatureKeysService.getPrivateKey(),
                signatureKeysService.getPublicKey(),
                signatureKeysService.getModulus());
    }



}
