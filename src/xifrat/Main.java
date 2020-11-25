package xifrat;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E:\\textamagat");
        byte[] arxiu = Files.readAllBytes(path);

        //txt de claus
        File claus = new File("E:\\clausA4.txt");
        FileReader frClaus = new FileReader(claus);
        BufferedReader brClaus = new BufferedReader(frClaus);

        String s = brClaus.readLine();

        while(s != null){

            System.out.println("lectura: " + s);

            SecretKey myKey = Xifrar.passwordKeyGeneration(s, 256);
            Xifrar.decryptData(arxiu, myKey);

            s = brClaus.readLine();
        }

    }
}
