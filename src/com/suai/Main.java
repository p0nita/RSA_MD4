package com.suai;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        RSA user1 = new RSA(256);
        RSA user2 = new RSA(256);
        BigInteger m = BigInteger.valueOf(322);
        BigInteger c = user1.encrypt(m, user2.getE(), user2.getN());
        System.out.println("DECRYPTED: " + user2.decrypt(c));

        m = new BigInteger("It's me, Mario!".getBytes());
        BigInteger m1 = new BigInteger("It's me, Morio!".getBytes());
        BigInteger hashedm = user1.getHash(m);
        BigInteger signature = user1.getSignature(m);
        if (user2.checkSignature(signature, hashedm, user1.getE(), user1.getN())){
            System.out.println("Authentication was successful");
        }
        else System.out.println("Authentication error");
    }
}
