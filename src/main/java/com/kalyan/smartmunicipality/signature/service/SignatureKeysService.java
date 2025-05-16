package com.kalyan.smartmunicipality.signature.service;

import com.kalyan.smartmunicipality.signature.KeyGenerator.RSAKeyGenerator;
import com.kalyan.smartmunicipality.signature.model.SignatureKeys;
import com.kalyan.smartmunicipality.signature.repository.SignatureKeysRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class SignatureKeysService {

    private final SignatureKeysRepository signatureKeysRepository;

    @PostConstruct
    public void generateAndStoreKeysIfNotExists() {
        if (signatureKeysRepository.count() == 0) {
            RSAKeyGenerator rsa = new RSAKeyGenerator(2048);
            SignatureKeys keys = SignatureKeys.builder()
                    .privateKey(rsa.getPrivateKey())
                    .publicKey(rsa.getPublicKey())
                    .modulus(rsa.getModulus())
                    .build();
            signatureKeysRepository.save(keys);
        }
    }

    private SignatureKeys getLatestKeys(){
        return signatureKeysRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("No signature keys found"));

    }
    public BigInteger getPublicKey(){
        return new BigInteger(getLatestKeys().getPublicKey());
    }

    public BigInteger getModulus(){
        return new BigInteger(getLatestKeys().getModulus());
    }

    public BigInteger getPrivateKey(){
        return new BigInteger(getLatestKeys().getPrivateKey());
    }


}

