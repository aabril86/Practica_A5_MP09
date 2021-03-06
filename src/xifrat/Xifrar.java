package xifrat;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class Xifrar {
        public static SecretKey keygenKeyGeneration (int keySize){
            SecretKey sKey = null;
            if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
                try {

                    KeyGenerator kgen = KeyGenerator.getInstance("AES");
                    kgen.init(keySize);
                    sKey = kgen.generateKey();

                } catch (NoSuchAlgorithmException ex) {
                    System.out.println("Generador no disponible.");
                }
            }
            return sKey;
        }

        public static SecretKey passwordKeyGeneration(String text, int keySize) {
            SecretKey sKey = null;
            if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
                try {
                    byte[] data = text.getBytes("UTF-8");
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] hash = md.digest(data);
                    byte[] key = Arrays.copyOf(hash, keySize/8);
                    sKey = new SecretKeySpec(key, "AES");
                } catch (Exception ex) {
                    System.err.println("Error generant la clau:" + ex);
                }
            }
            return sKey;
        }

        public static byte[] encryptData(byte[] data, PublicKey key) {
            byte[] encryptedData = null;
            try{
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding","SunJCE");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                encryptedData = cipher.doFinal(data);
            }catch(Exception ex){
                System.out.println("Error xifrant les dades " + ex);
            }
            return encryptedData;
        }

    public static byte[] decryptData(byte[] data, PrivateKey key) {
        byte[] decryptedData = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decryptedData = cipher.doFinal(data);

        }catch(Exception ex){
            System.out.println("Error desxifrant les dades " + ex);
        }
        return decryptedData;
    }

    public static KeyPair randomGenerate(int len) {
            KeyPair keys = null;
            try{
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(len);
                keys = keyGen.genKeyPair();
            }catch (Exception e){
                System.out.println("Generador no disponible");
            }
            return keys;
    }

    public static KeyStore loadKeystore(String ksFile, String ksPwd) throws Exception {
            KeyStore ks =KeyStore.getInstance("PKCS12");
            File f = new File(ksFile);
            if(f.isFile()){
                FileInputStream in = new FileInputStream(f);
                ks.load(in, ksPwd.toCharArray());
            }
            return ks;
    }


    public static PublicKey getPublicKey(String fitxer) throws FileNotFoundException, CertificateException {
        PublicKey publicKey;

        FileInputStream fin = new FileInputStream(fitxer);
        CertificateFactory f = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
        publicKey = certificate.getPublicKey();

        return publicKey;
    }


    public static PublicKey getPublicKey(KeyStore ks, String alias, String pwMyKey) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {

            FileInputStream f = new FileInputStream("E:\\mykeystore.ks");

            ks.load(f, pwMyKey.toCharArray());
            Key mykey = ks.getKey(alias, pwMyKey.toCharArray());

            if (mykey instanceof PrivateKey) {
                X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
                PublicKey publicKey = certificate.getPublicKey();
                return publicKey;
            }

            else return null;
    }


    public static byte[] signData(byte[] data, PrivateKey priv) {
            byte[] signature = null;
            try {
                Signature signer = Signature.getInstance("SHA1withRSA");
                signer.initSign(priv);
                signer.update(data);
                signature = signer.sign();
            } catch (Exception ex) {
                System.err.println("Error signant les dades: " + ex);
            }
            return signature;
    }


    public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) {
        boolean isValid = false;
        try {
        Signature signer = Signature.getInstance("SHA1withRSA");
        signer.initVerify(pub);
        signer.update(data);
        isValid = signer.verify(signature);
        } catch (Exception ex) {
            System.err.println("Error validant les dades: " + ex);
        }
        return isValid;
    }

    //METODES EXERCICI 2

    public static byte[][] encryptWrappedData(byte[] data, PublicKey pub) {
        byte[][] encWrappedData = new byte[2][];
        try {

            //Genera clau (amb AES)
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKey sKey = kgen.generateKey();

            //Encripta amb la clau que s'ha generat abans
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            byte[] encMsg = cipher.doFinal(data);

            //Embolcallar la clau amb RSA
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.WRAP_MODE, pub);
            byte[] encKey = cipher.wrap(sKey);
            encWrappedData[0] = encMsg;
            encWrappedData[1] = encKey;
            } catch (Exception ex) {
                System.err.println("Ha succeït un error xifrant: " + ex);
            }
        return encWrappedData;
    }

    public static byte[] decryptWrappedData(byte[][] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] decWrappedData = null;
        byte[] encData = data[0];

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.UNWRAP_MODE, privateKey);


        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        SecretKey secretKey = (SecretKey) cipher.unwrap(data[1], "AES", Cipher.SECRET_KEY);

        cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        decWrappedData = cipher.doFinal(encData);

        return decWrappedData;

    }


}
