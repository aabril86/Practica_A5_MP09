package xifrat;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        //EXERCICI 1.5 Xifrar i desxifrar un text en clar amb una clau generada

        /*System.out.println("Frase a xifrar:");
        String s = scanner.nextLine();

        byte[] msg = s.getBytes("UTF8");
        SecretKey myKey = Xifrar.keygenKeyGeneration(192);

        byte[] msgXifrat = Xifrar.encryptData(msg, myKey);
        System.out.println(msgXifrat);

        byte[] msgDesxifrat = Xifrar.decryptData(msgXifrat, myKey);

        String text = new String(msgDesxifrat, StandardCharsets.UTF_8);
        System.out.println(text);*/






        //EXERCICI 1.6 Xifrar i desxifrar un text en clar amb una clau generada a partir de paraula de pas

        /*System.out.println("Frase a xifrar: ");
        String s = scanner.nextLine();

        System.out.println("Introdueix la contrasenya:");
        String password = scanner.nextLine();

        SecretKey myKey = Xifrar.passwordKeyGeneration(password, 256);

        byte[] msg = s.getBytes("UTF8");

        byte[] msgXifrat = Xifrar.encryptData(msg, myKey);
        System.out.println("xifrat: " + msgXifrat);

        byte[] msgDesxifrat = Xifrar.decryptData(msgXifrat, myKey);

        String text = new String(msgDesxifrat, StandardCharsets.UTF_8);
        System.out.println("desxifrat: " + text);*/





        //EXERCICI 1.7 Provar metodes de la classe SecretKey

        /*SecretKey myKey = Xifrar.keygenKeyGeneration(192);

        System.out.println(myKey.getFormat());
        System.out.println(myKey.getEncoded());
        System.out.println(myKey.getAlgorithm());*/






        //EXERCICI 1.8 BadPaddingException

        /*SecretKey anotherKey = Xifrar.keygenKeyGeneration(256);

        System.out.println("Frase a xifrar: ");
        String s = scanner.nextLine();

        System.out.println("Introdueix la contrasenya: ");
        String password = scanner.nextLine();
        SecretKey myKey = Xifrar.passwordKeyGeneration(password, 256);

        byte[] msg = s.getBytes("UTF8");

        byte[] msgXifrat = Xifrar.encryptData(msg, myKey);
        System.out.println("Xifrat: " + msgXifrat);

        byte[] msgDesxifrat = Xifrar.decryptData(msgXifrat, anotherKey);

        String text = new String(msgDesxifrat, StandardCharsets.UTF_8);
        System.out.println("Desxifrat: " + text);*/








        //EXERCICI 2 (HE MODIFICAT LA FUNCIÓ PASSWORDKEYGENERATION, PER A QUE MOSTRI EL TEXT DINS DE LA PROPIA FUNCIÓ (sino donava errors xungos))
        /*Path path = Paths.get("E:\\textamagat");
        byte[] arxiu = Files.readAllBytes(path);

        //txt de claus
        File claus = new File("E:\\clausA4.txt");
        FileReader frClaus = new FileReader(claus);
        BufferedReader brClaus = new BufferedReader(frClaus);

        String s = brClaus.readLine();

        while(s != null){

            System.out.println("lectura: " + s);

            SecretKey myKey = Xifrar.passwordKeyGeneration(s, 128);
            Xifrar.decryptData(arxiu, myKey);

            s = brClaus.readLine();
        }*/

    }
}
