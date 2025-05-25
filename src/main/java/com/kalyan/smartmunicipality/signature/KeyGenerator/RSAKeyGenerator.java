package com.kalyan.smartmunicipality.signature.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyGenerator {

    public static final Logger LOGGER = LoggerFactory.getLogger(RSAKeyGenerator.class);
    private static final SecureRandom random = new SecureRandom();

    public BigInteger p; // First prime number
    public BigInteger q; // Second prime number
    public BigInteger n; // Modulus (n = p * q)
    public BigInteger e; // Public exponent
    public BigInteger d; // Private exponent

    public RSAKeyGenerator(int bitLength) {
        // Step 1: Generate two large prime numbers p and q
        p = BigInteger.probablePrime(bitLength / 2, random);
        do {
            q = BigInteger.probablePrime(bitLength / 2, random);
        } while (p.equals(q)); // Ensure p ≠ q

        // Step 2: Compute modulus n = p * q
        n = p.multiply(q);

        // Step 3: Compute Euler's totient phi(n) = (p - 1) * (q - 1)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Step 4 (Option A): Use a fixed public exponent 65537
        // This is a commonly used value for 'e' in RSA.
        // It's fast, secure enough, and ensures compatibility across systems.
        e = BigInteger.valueOf(65537);

        // Step 4 (Option B): Generate a random public exponent dynamically

        // Step 5: Compute the private exponent d such that (d * e) ≡ 1 mod phi(n)
        d = e.modInverse(phi);

        // Log the key components to check
        LOGGER.info("Private key: {}, Public key: {}, Modulus: {}", d, e, n);
    }

    // Returns the public key exponent
    public String getPublicKey() {
        return e.toString();
    }

    // Returns the private key exponent
    public String getPrivateKey() {
        return d.toString();
    }

    // Returns the modulus
    public String getModulus() {
        return n.toString();
    }
}