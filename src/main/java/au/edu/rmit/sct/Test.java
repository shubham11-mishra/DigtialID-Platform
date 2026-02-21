package au.edu.rmit.sct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Person.addPerson().
 * Test Case template: Test Case (description), Test Data, Expected Result, Test Result, Pass/Fail.
 * Each function has 5 test cases with at least one test data per case.
 */
class PersonAddPersonTest {

    private Path personsPath;
    private Path idsPath;

    @BeforeEach
    void setUp() throws IOException {
        Path dir = Files.createTempDirectory("person-test");
        personsPath = dir.resolve("persons.txt");
        idsPath = dir.resolve("ids.txt");
        Person.setPersonsFilePath(personsPath.toString());
        Person.setIdsFilePath(idsPath.toString());
    }

    // Test Case 1: Check the function with valid inputs
    @Test
    void testAddPerson_validInputs_returnsTrue() {
        Person p1 = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p1.addPerson(), "Valid personID, address and birthdate should be added");

        // Second person: valid 10-char ID (first 2 digits 2-9, ≥2 special in 3-8, last 2 uppercase)
        Person p2 = new Person("34a@b#cDEF", "John", "Smith", "1|Collins St|Melbourne|Victoria|Australia", "01-01-2000");
        assertTrue(p2.addPerson(), "Second valid person should be added");
    }

    // Test Case 2: Check the function with invalid personID
    @Test
    void testAddPerson_invalidPersonID_returnsFalse() {
        // Too short (9 chars)
        Person p1 = new Person("56s_d%&fA", "Jane", "Doe", "32|High St|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(p1.addPerson(), "personID with length 9 should not be added");

        // First two not in [2-9] (0 and 1)
        Person p2 = new Person("10a@b#cDE", "John", "Smith", "32|High St|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(p2.addPerson(), "personID starting with 10 should not be added");

        // Last two not uppercase letters
        Person p3 = new Person("56s_d%&fab", "Jane", "Doe", "32|High St|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(p3.addPerson(), "personID ending with lowercase should not be added");
    }

    // Test Case 3: Check the function with invalid address
    @Test
    void testAddPerson_invalidAddress_returnsFalse() {
        // State not Victoria
        Person p1 = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|NSW|Australia", "15-11-1990");
        assertFalse(p1.addPerson(), "Address with State NSW should not be added");

        // Wrong format (fewer than 5 parts)
        Person p2 = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne", "15-11-1990");
        assertFalse(p2.addPerson(), "Address with wrong format should not be added");
    }

    // Test Case 4: Check the function with invalid birthdate
    @Test
    void testAddPerson_invalidBirthdate_returnsFalse() {
        Person p1 = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "1990-11-15");
        assertFalse(p1.addPerson(), "Birthdate in YYYY-MM-DD format should not be added");

        Person p2 = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "31-13-1990");
        assertFalse(p2.addPerson(), "Invalid date month 13 should not be added");
    }

    // Test Case 5: Check the function with another invalid condition (fewer than 2 special chars in positions 3-8)
    @Test
    void testAddPerson_fewerThanTwoSpecialCharsInMiddle_returnsFalse() {
        // Only one special character between positions 3 and 8
        Person p1 = new Person("56sd%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertFalse(p1.addPerson(), "personID with only one special char in positions 3-8 should not be added");
    }
}
