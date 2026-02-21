package au.edu.rmit.sct;

/**
 * Person class for the Digital ID platform (skeleton).
 * Fields, getters/setters, and constructors only.
 * addPerson, updatePersonalDetails, and addID are implemented by other team members.
 */
public class Person {

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

    // ---------- Validation: personID ----------
    /**
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

    
}
