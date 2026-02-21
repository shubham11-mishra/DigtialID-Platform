package au.edu.rmit.sct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Person.addID().
 * Passport: 8 chars (2 uppercase + 6 digits); Driver's licence: 10 chars (2 uppercase + 8 digits);
 * Medicare: 9 digits; Student card: under 18 only, no other IDs, 12 digits.
 */
class PersonAddIDTest {

    private Path personsPath;
    private Path idsPath;

    @BeforeEach
    void setUp() throws IOException {
        Path dir = Files.createTempDirectory("person-addid-test");
        personsPath = dir.resolve("persons.txt");
        idsPath = dir.resolve("ids.txt");

        Person.setPersonsFilePath(personsPath.toString());
        Person.setIdsFilePath(idsPath.toString());
    }

    // Test Case 1: Valid passport (8 chars: 2 uppercase + 6 digits)
    @Test
    void testAddID_validPassport_returnsTrue() {
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "passport", "AB123456"),
                "Valid passport number AB123456 should be added");
    }

    // Test Case 2: Valid driver's licence (10 chars: 2 uppercase + 8 digits)
    @Test
    void testAddID_validDriversLicence_returnsTrue() {
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "driverslicence", "XY12345678"),
                "Valid driver's licence number should be added");
    }

    // Test Case 3: Valid Medicare card (9 digits)
    @Test
    void testAddID_validMedicare_returnsTrue() {
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "medicare", "123456789"),
                "Valid medicare card number should be added");
    }

    // Test Case 4: Valid student card for under-18 with no other IDs
    @Test
    void testAddID_validStudentCard_under18_noOtherIDs_returnsTrue() {
        Person person = new Person("56s_d%&fAB", "Jane", "Doe", "32|High St|Melbourne|Victoria|Australia", "15-11-2010");
        assertTrue(person.addPerson(), "Add under-18 person first");
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "studentcard", "123456789012"),
                "Valid student card for under-18 with no other IDs should be added");
    }

    // Test Case 5: Invalid ID format
    @Test
    void testAddID_invalidFormat_returnsFalse() {
        Person p = new Person();
        assertFalse(p.addID("56s_d%&fAB", "passport", "A1234567"),
                "Passport with 7 digits (only 1 letter) should not be added");
        assertFalse(p.addID("56s_d%&fAB", "medicare", "12345678"),
                "Medicare with 8 digits should not be added");
        assertFalse(p.addID("56s_d%&fAB", "studentcard", "12345"),
                "Student card with wrong length should not be added");
    }
}
