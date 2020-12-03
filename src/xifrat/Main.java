package xifrat;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.sql.SQLOutput;
import java.util.Enumeration;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        //EXERCICI 1

        /*KeyPair myKeys = Xifrar.randomGenerate(1024);
        System.out.println(myKeys.getPublic());
        System.out.println(myKeys.getPrivate());

        System.out.println("Introdueix el missatge a xifrar: ");
        String ms = scanner.nextLine();

        byte[] msg = ms.getBytes();

        byte[] msgEncriptat = Xifrar.encryptData(msg, myKeys.getPublic());
        System.out.println("Xifrat: " + msgEncriptat);

        byte[] msgDesencriptat = Xifrar.decryptData(msgEncriptat, myKeys.getPrivate());
        String text = new String(msgDesencriptat, StandardCharsets.UTF_8);

        System.out.println("Desxifrat: " + text);*/



        //EXERCICI 2.i.1-2-3-4-5

        /*KeyStore myKs = Xifrar.loadKeystore("/home/dam2a/.keystore", "2dama4328");
        System.out.println("Tipus: " + myKs.getType());
        System.out.println("Mida: " + myKs.size());
        System.out.print("Alies de les claus: ");

        Enumeration<String> alias = myKs.aliases();

        while(alias.hasMoreElements()){
            System.out.print(alias.nextElement() + " ");
            System.out.println();
        }

        System.out.println("Certificat de la clau: " + myKs.getCertificate("mykey"));

        System.out.println("Algoritme de xifrat de la clau: " + myKs.getCertificate("mykey").getPublicKey().getAlgorithm());
        */





        //EXERCICI 2.ii

        SecretKey mykey2 = Xifrar.keygenKeyGeneration(128);

        String s = "2dama4328";
        char[] pass = s.toCharArray();

        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(pass);
        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(mykey2);

        KeyStore myKs = Xifrar.loadKeystore("/home/dam2a/.keystore", "2dama4328");
        myKs.setEntry("mykey2", skEntry ,protectionParameter );

        java.io.FileOutputStream fos = null;
        try {
            fos = new java.io.FileOutputStream("newKeyStoreName");
            myKs.store(fos, pass);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }

        Enumeration<String> alias = myKs.aliases();

        while(alias.hasMoreElements()){
            System.out.print(alias.nextElement() + " ");
            System.out.println();
        }


    }
}
