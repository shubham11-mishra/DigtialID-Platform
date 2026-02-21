package au.edu.rmit.sct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 import java.io.IOException;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
 import java.nio.file.StandardOpenOption;
 import java.util.List; /**
 * Person class for the Digital ID platform (skeleton).
 * Fields, getters/setters, and constructors only.
 * addPerson, updatePersonalDetails, and addID are implemented by other team members.
 */
public class Person {

    /** Default path for persons data file (StreetNumber|Street|City|State|Country). */
    private static String personsFilePath = "data/persons.txt";

    /** Default path for IDs data file. */
    private static String idsFilePath = "data/ids.txt";

    /** Format for birthdate: DD-MM-YYYY. */
    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    /** Address must have State = Victoria. Format: StreetNumber|Street|City|State|Country. */
    private static final String REQUIRED_STATE = "Victoria";

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthday;

    public Person() {}

    public Person(String personID, String firstName, String lastName, String address, String birthday) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    /*
    updatePersonalDetails: updates a person's record if rules allow.
    */

    private List<String> readPersonsFile() {
        try {
            Path path = Paths.get(personsFilePath);
            if (!Files.exists(path)) return new ArrayList<>();
            return new ArrayList<>(Files.readAllLines(path));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private boolean writePersonsFile(List<String> lines) {
        try {
            Path path = Paths.get(personsFilePath);
            if (path.getParent() != null) Files.createDirectories(path.getParent());
            Files.write(path, String.join("\n", lines).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private int findLineByPersonID(List<String> lines, String personID) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(personID + "|")) return i;
        }
        return -1;
    }

    public boolean updatePersonalDetails(String existingPersonID, String newPersonID, String newFirstName,
                                         String newLastName, String newAddress, String newBirthday) {
        List<String> lines = readPersonsFile();
        int idx = findLineByPersonID(lines, existingPersonID);
        if (idx < 0) return false;

        // Line format: personID|firstName|lastName|StreetNumber|Street|City|State|Country|birthday (address has 5 parts with |)
        String[] existing = lines.get(idx).split("\\|", -1);
        String existingAddress = existing.length > 5 ? String.join("|", java.util.Arrays.copyOfRange(existing, 3, existing.length - 1)) : (existing.length > 3 ? existing[3] : "");
        String existingBirthday = existing.length > 4 ? existing[existing.length - 1] : "";
        int age = getAgeFromBirthday(existingBirthday);

        // Condition 1: Under 18 cannot change address
        if (age >= 0 && age < 18 && newAddress != null && !newAddress.equals(existingAddress)) {
            return false;
        }

        // Condition 2: If birthday is being changed, no other detail can change
        boolean birthdayChanging = newBirthday != null && !newBirthday.equals(existingBirthday);
        if (birthdayChanging) {
            if (!existingPersonID.equals(newPersonID != null ? newPersonID : existing[0])) return false;
            if (newFirstName != null && !newFirstName.equals(existing[1])) return false;
            if (newLastName != null && !newLastName.equals(existing[2])) return false;
            if (newAddress != null && !newAddress.equals(existingAddress)) return false;
        }

        // Condition 3: If first digit of existing ID is even, ID cannot be changed
        char firstChar = existing[0].length() > 0 ? existing[0].charAt(0) : ' ';
        if (Character.isDigit(firstChar) && (firstChar - '0') % 2 == 0) {
            if (newPersonID != null && !newPersonID.equals(existing[0])) return false;
        }

        // Apply updates: use new values or keep existing
        String id = (newPersonID != null && !newPersonID.isEmpty()) ? newPersonID : existing[0];
        String first = (newFirstName != null && !newFirstName.isEmpty()) ? newFirstName : existing[1];
        String last = (newLastName != null && !newLastName.isEmpty()) ? newLastName : existing[2];
        String addr = (newAddress != null && !newAddress.isEmpty()) ? newAddress : existingAddress;
        String bday = (newBirthday != null && !newBirthday.isEmpty()) ? newBirthday : existingBirthday;

        if (!isValidPersonID(id) || !isValidAddress(addr) || !isValidBirthday(bday)) return false;

        lines.set(idx, id + "|" + first + "|" + last + "|" + addr + "|" + bday);
        return writePersonsFile(lines);
    }


    //Validation: personID
    /*
     * Condition 1: personID exactly 10 chars; first two digits in [2-9];
     * at least two special characters between positions 3 and 8 (inclusive); last two uppercase A-Z.
     * Example: "56s_d%&fAB"
     */
    public static boolean isValidPersonID(String id) {
        if (id == null || id.length() != 10)
            return false;
        // First two characters must be digits between 2 and 9
        char c0 = id.charAt(0), c1 = id.charAt(1);
        if (!Character.isDigit(c0) || c0 < '2' || c0 > '9')
            return false;
        if (!Character.isDigit(c1) || c1 < '2' || c1 > '9')
            return false;
        // Positions 2-7 (indices 2 to 7): at least two special characters
        int specialCount = 0;
        for (int i = 2; i <= 7; i++) {
            char c = id.charAt(i);
            if (!Character.isLetterOrDigit(c))
                specialCount++;
        }
        if (specialCount < 2) return false;
        // Last two characters must be uppercase A-Z
        return Character.isUpperCase(id.charAt(8)) && Character.isLetter(id.charAt(8))
                && Character.isUpperCase(id.charAt(9)) && Character.isLetter(id.charAt(9));
    }

    // ---------- Validation: address ----------
    /**
     * Condition 2: Format StreetNumber|Street|City|State|Country. State must be Victoria.
     */
    public static boolean isValidAddress(String address) {
        if (address == null)
            return false;
        String[] parts = address.split("\\|", -1);
        if (parts.length != 5)
            return false;
        return REQUIRED_STATE.equals(parts[3].trim());
    }

    // ---------- Validation: birthdate ----------
    /**
     * Condition 3: Format DD-MM-YYYY.
     */
    public static boolean isValidBirthday(String birthday) {
        if (birthday == null) return false;
        try {
            LocalDate.parse(birthday, BIRTHDAY_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns age in years from birthday string (DD-MM-YYYY). Returns -1 if invalid.
     */
    public static int getAgeFromBirthday(String birthday) {
        if (!isValidBirthday(birthday)) return -1;
        LocalDate birth = LocalDate.parse(birthday, BIRTHDAY_FORMAT);
        return java.time.Period.between(birth, LocalDate.now()).getYears();
    }

    /**
     * addPerson: validates personID, address, birthday; if valid appends record to TXT and returns true.
     */
    public boolean addPerson() {
        if (!isValidPersonID(personID) || !isValidAddress(address) || !isValidBirthday(birthday)) {
            return false;
        }
        try {
            Path path = Paths.get(personsFilePath);
            if (path.getParent() != null) Files.createDirectories(path.getParent());
            String line = personID + "|" + firstName + "|" + lastName + "|" + address + "|" + birthday + "\n";
            Files.write(path, line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // validating here passport, driving licence and meddicare number and etc
    private static boolean isValidPassportNumber(String s) {
        if (s == null || s.length() != 8) return false;
        for (int i = 0; i < 2; i++) if (!Character.isUpperCase(s.charAt(i)) || !Character.isLetter(s.charAt(i))) return false;
        for (int i = 2; i < 8; i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    private static boolean isValidDriversLicenceNumber(String s) {
        if (s == null || s.length() != 10) return false;
        for (int i = 0; i < 2; i++) if (!Character.isUpperCase(s.charAt(i)) || !Character.isLetter(s.charAt(i))) return false;
        for (int i = 2; i < 10; i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    private static boolean isValidMedicareNumber(String s) {
        if (s == null || s.length() != 9) return false;
        for (int i = 0; i < 9; i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    private static boolean isValidStudentCardNumber(String s) {
        if (s == null || s.length() != 12) return false;
        for (int i = 0; i < 12; i++) if (!Character.isDigit(s.charAt(i))) return false;
        return true;
    }

    private boolean personHasAnyID(String pid) {
        try {
            Path path = Paths.get(idsFilePath);
            if (!Files.exists(path)) return false;
            for (String line : Files.readAllLines(path)) {
                if (line.startsWith(pid + "|")) return true;
            }
        } catch (IOException e) {
            // ignore
        }
        return false;
    }

    private int getAgeForPerson(String pid) {
        List<String> lines = readPersonsFile();
        int idx = findLineByPersonID(lines, pid);
        if (idx < 0) return -1;
        String[] parts = lines.get(idx).split("\\|", -1);
        // Birthday is last field (address may contain |)
        String birthday = parts.length > 0 ? parts[parts.length - 1] : "";
        return getAgeFromBirthday(birthday);
    }

    /**
     * addID: stores an ID document (passport, driverslicence, medicare, studentcard) if valid.
     * Passport: 8 chars, first 2 uppercase A-Z, rest 0-9.
     * Driver's licence: 10 chars, first 2 uppercase A-Z, rest 0-9.
     * Medicare: 9 chars, all 0-9.
     * Student card: only for person under 18 with no passport/drivers/medicare; 12 chars, all 0-9.
     */

    public boolean addID(String personID, String idType, String idNumber) {
        if (personID == null || idType == null || idNumber == null) return false;
        idType = idType.trim().toLowerCase();

        if ("passport".equals(idType)) {
            if (!isValidPassportNumber(idNumber)) return false;
        } else if ("driverslicence".equals(idType) || "drivers licence".equals(idType)) {
            if (!isValidDriversLicenceNumber(idNumber)) return false;
        } else if ("medicare".equals(idType)) {
            if (!isValidMedicareNumber(idNumber)) return false;
        } else if ("studentcard".equals(idType) || "student card".equals(idType)) {
            if (!isValidStudentCardNumber(idNumber)) return false;
            // Student card only if person under 18 and has no passport, drivers licence, medicare
            int age = getAgeForPerson(personID);
            if (age < 0 || age >= 18) return false;
            if (personHasAnyID(personID)) return false;
        } else {
            return false;
        }

        try {
            Path path = Paths.get(idsFilePath);
            if (path.getParent() != null) Files.createDirectories(path.getParent());
            String line = personID + "|" + idType + "|" + idNumber + "\n";
            Files.write(path, line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

