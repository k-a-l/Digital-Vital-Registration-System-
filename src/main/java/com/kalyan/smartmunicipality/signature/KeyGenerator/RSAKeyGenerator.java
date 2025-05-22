package com.kalyan.smartmunicipality.signature.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyGenerator {
    public static final Logger LOGGER = LoggerFactory.getLogger(RSAKeyGenerator.class);
    private static final SecureRandom random = new SecureRandom();
    public BigInteger p; //prime factor p
    public BigInteger q; //prime factor q
    public BigInteger n; //modulus
    public BigInteger e; //public exponent
    public BigInteger d; //private exponent


    public RSAKeyGenerator(int bitLength) {
        //Step 1: Generate two large prime numbers p and q with bitLength bits such that p != q.
         p = BigInteger.probablePrime(bitLength/2, random);
         do {
             q = BigInteger.probablePrime(bitLength / 2, random);
         }while (p.equals(q)); // Ensure p !=q
         //Step 2: Compute n = p x q.

    n = p.multiply(q);



    //Step 3: Compute phi(n) = (p-1) x (q-1)
             BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));




    //Step 4: Select an integer such that 1 < e < phi(n) and gcd(e, phi(n)) = 1.
             do {
                 e = BigInteger.probablePrime(bitLength / 2, random);
             } while (e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE));
//Static
             //e=BigInteger.valueOf(65537);
             /*if(!phi.gcd(e).compareTo(BigInteger.ONE)){
                 e=BigInteger.probablePrime(bitLength/2, random);
             }*/



             //Step 5: Compute d = e^-1 mod phi(n).
             d = e.modInverse(phi);


    LOGGER.info("Private key:, {}, Public key, {} , Modulus: {}",d,e,n);
    }
    public String getPublicKey(){

        return e.toString();
    }

    public String getPrivateKey(){

        return d.toString();
    }

    public String getModulus(){
        return n.toString();
    }
}
