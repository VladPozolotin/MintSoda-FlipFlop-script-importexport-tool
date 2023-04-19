package ru.whitepolarfoxxx;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    protected static String version;
    protected static HashMap<String, String> files = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter version number");
            setVersion(scanner.nextLine());
            initHashMap(getVersion());
            System.out.println("Put folders .import and stories to folder " + getVersion() + " and file " + getVersion() + ".csv to root");
            System.out.println("Choose operation:");
            System.out.println("1. From tres to flfl");
            System.out.println("2. From flfl to tres");
            System.out.println("3. Back");
            int com = Integer.parseInt(scanner.nextLine());
            switch (com) {
                case 1:
                    tresToflfl();
                    continue;
                case 2:
                    flflTotres();
                    continue;
                case 3:
                    break;
            }
        }

    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String vers) {
        version = vers;
    }

    public static void initHashMap(String version) {
        try (FileReader csv = new FileReader(version + ".csv")) {
            BufferedReader in = new BufferedReader(csv);
            String inline;
            in.readLine();
            while ((inline = in.readLine()) != null) {
                String[] read = inline.split(";");
                String flfl = read[0];
                String tres = read[1];
                if (!flfl.equals("") && !tres.equals(""))
                    files.put(flfl, tres);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void tresToflfl() {
        int count = 0;
        for (Map.Entry<String, String> entry : files.entrySet()) {
            File tres = new File("./" + getVersion() + entry.getValue());
            File flfl = new File("./" + getVersion() + entry.getKey());
            try (FileInputStream fis = new FileInputStream(tres)) {
                byte[] bytes = new byte[(int) tres.length()];
                fis.read(bytes);
                flfl.createNewFile();
                try (FileOutputStream outputStream = new FileOutputStream(flfl);){
                    outputStream.write(bytes);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage() + flfl.getPath());
                    continue;
                }
                count++;
            } catch (IOException ex) {
                System.out.println(ex.getMessage() + " " + tres.getPath());
                continue;
            }
        }
        System.out.println("Files converted: " + count);
    }

    public static void flflTotres() {
        int count = 0;
        for (Map.Entry<String, String> entry : files.entrySet()) {
            File tres = new File("./" + getVersion() + entry.getValue());
            File flfl = new File("./" + getVersion() + entry.getKey());
            try (FileInputStream fis = new FileInputStream(flfl)) {
                byte[] bytes = new byte[(int) flfl.length()];
                fis.read(bytes);
                tres.createNewFile();
                try(FileOutputStream outputStream = new FileOutputStream(tres);) {
                outputStream.write(bytes);} catch (IOException ex) {
                    System.out.println(ex.getMessage() + tres.getPath());
                    continue;
                }
                count++;
            } catch (IOException ex) {
                System.out.println(ex.getMessage() + " " + flfl.getPath());
                continue;
            }
        }
        System.out.println("Files converted: " + count);
    }
}