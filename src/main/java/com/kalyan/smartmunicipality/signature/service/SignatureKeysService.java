package com.kalyan.smartmunicipality.signature.service;

import com.kalyan.smartmunicipality.signature.KeyGenerator.RSAKeyGenerator;
import com.kalyan.smartmunicipality.signature.model.SignatureKeys;
import com.kalyan.smartmunicipality.signature.repository.SignatureKeysRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class SignatureKeysService {

    private static final Logger LOG = LoggerFactory.getLogger(SignatureKeysService.class);
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
    @Cacheable(value = "publicKey")
    public BigInteger getPublicKey(){
        return new BigInteger(getLatestKeys().getPublicKey());
    }

    @Cacheable(value = "modulus")
    public BigInteger getModulus(){
        return new BigInteger(getLatestKeys().getModulus());
    }

    @Cacheable(value = "privateKey")
    public BigInteger getPrivateKey(){
        return new BigInteger(getLatestKeys().getPrivateKey());
    }


}

