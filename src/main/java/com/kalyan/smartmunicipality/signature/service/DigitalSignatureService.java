package com.kalyan.smartmunicipality.signature.service;

import com.kalyan.smartmunicipality.signature.KeyGenerator.RSAKeyGenerator;
import com.kalyan.smartmunicipality.signature.utils.SignatureUtils;
import com.kalyan.smartmunicipality.signature.utils.VerifyUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class DigitalSignatureService {
    private static final Logger LOG = LoggerFactory.getLogger(DigitalSignatureService.class);

    private final SignatureKeysService signatureKeysService;
    public String signCertificate(String certificate) throws NoSuchAlgorithmException {
        BigInteger signature = SignatureUtils.sign(certificate, signatureKeysService.getPrivateKey(), signatureKeysService.getModulus());
        return signature.toString();

    }

    public boolean verifyCertificate(String certificate, String signature) throws NoSuchAlgorithmException {
        BigInteger sig = new BigInteger(signature);
        return VerifyUtils.verifySignature(certificate, sig,signatureKeysService.getPublicKey(), signatureKeysService.getModulus());

    }
    public void keys(){
        LOG.info("Private Keys , {} , Public , {}, Modulus , {}", signatureKeysService.getPrivateKey(), signatureKeysService.getPublicKey(), signatureKeysService.getModulus());

    }

}
