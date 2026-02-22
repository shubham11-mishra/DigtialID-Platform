package au.edu.rmit.sct;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Demo for video: writes to data/persons.txt and data/ids.txt so you can show the TXT files.
 * Run from the DigtialID-Platform folder (set "Working directory" to project root in your IDE run config).
 */
public class DemoTxtFiles {
    public static void main(String[] args) {
        try {
            // Use "data" folder under current working directory; create it if needed
            String base = System.getProperty("user.dir");
            Path dataDir = Paths.get(base).resolve("data");
            Files.createDirectories(dataDir);

            Path personsPath = dataDir.resolve("persons.txt").toAbsolutePath();
            Path idsPath = dataDir.resolve("ids.txt").toAbsolutePath();
            // Start with clean TXT files so the generated files are readable (assignment: "data should be readable")
            Files.deleteIfExists(personsPath);
            Files.deleteIfExists(idsPath);

            Person.setPersonsFilePath(personsPath.toString());
            Person.setIdsFilePath(idsPath.toString());

            System.out.println("Working directory: " + base);
            System.out.println("Writing to: " + personsPath + " and " + idsPath);

            Person p = new Person("56s_d%&fAB", "Jane", "Doe",
                    "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
            boolean ok1 = p.addPerson();
            System.out.println("addPerson: " + (ok1 ? "OK" : "FAILED"));

            Person p2 = new Person();
            boolean ok2 = p2.addID("56s_d%&fAB", "passport", "AB123456");
            System.out.println("addID: " + (ok2 ? "OK" : "FAILED"));

            if (ok1 && ok2) {
                System.out.println("Done. Open this folder and show persons.txt, ids.txt: " + dataDir.toAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
