package com.suai;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class RSA {
    private BigInteger n = BigInteger.valueOf(0);
    private BigInteger e = BigInteger.valueOf(65537);
    private BigInteger d;

    public RSA(int length){
        BigInteger p = BigInteger.valueOf(0);
        BigInteger q = BigInteger.valueOf(0);
        while (n.compareTo(e) == -1) {
            p = BigInteger.probablePrime(length/2, new Random());
            q = BigInteger.probablePrime(length/2, new Random());
            n = p.multiply(q);
        }
        BigInteger phi = (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
        Euclid(phi);
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("phi = " + phi);
        System.out.println("n = " + n);
        System.out.println("e = " + e);
        System.out.println("d = " + d);
        System.out.println(e.multiply(d).mod(phi));
    }

    private void Euclid(BigInteger phi){
        BigInteger a = BigInteger.valueOf(65537);
        BigInteger mod;
        BigInteger div;
        BigInteger x;
        BigInteger xpred = BigInteger.valueOf(0);
        BigInteger xpredpred = BigInteger.valueOf(1);
        BigInteger b = phi;
        BigInteger zero = BigInteger.valueOf(0);
        while (true){
            div = a.divide(b);
            mod = a.mod(b);
            x = xpredpred.subtract(xpred.multiply(div));
            if (mod.compareTo(zero) == 0) break;
            a = b;
            b = mod;
            xpredpred = xpred;
            xpred = x;
        }
        d = xpred.mod(phi);
    }

    private BigInteger pow(BigInteger a, BigInteger b, BigInteger n){
        BigInteger res = a;
        String tmp = b.toString(2);
        byte[] bb = tmp.getBytes();
        for (int i = 1; i < bb.length; i++){
            if (bb[i] == 48){
                res = res.pow(2);
            }
            else{
                res = res.pow(2).multiply(a);
            }
            res = res.mod(n);
        }
        return res;
    }

    public BigInteger decrypt(BigInteger c){
        return pow(c, d, n);
    }

    public BigInteger encrypt(BigInteger m, BigInteger e, BigInteger n){
        return pow(m, e, n);
    }

    public BigInteger getSignature(BigInteger m){
        return pow(m, d, n);
    }

    public BigInteger getHash(BigInteger m){
        BigInteger res = BigInteger.valueOf(0);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(m.toByteArray());
            res = new BigInteger(md.digest());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public boolean checkSignature(BigInteger s, BigInteger mhash, BigInteger e, BigInteger n){
        BigInteger m1 = pow(s, e, n);
        if (mhash.compareTo(getHash(m1)) == 0) return true;
        return false;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getE() {
        return e;
    }
}