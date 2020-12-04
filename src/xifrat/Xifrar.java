package xifrat;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


    //public static PublicKey getPublicKey(KeyStore ks, String alias, String pwMyKey){}

}
