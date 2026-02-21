package au.edu.rmit.sct;

import java.util.List;
import java.util.Arrays;

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
    /*
    updatePersonalDetails: updates a person's record if rules allow.
    */
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

}
