package au.edu.rmit.sct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Person.updatePersonalDetails().
 * Conditions: (1) Under 18 cannot change address; (2) If birthday changed, no other detail can change;
 * (3) If first digit of ID is even, ID cannot be changed.
 */
class PersonUpdatePersonalDetailsTest {

    private Path personsPath;
    private Path idsPath;

    @BeforeEach
    void setUp() throws IOException {
        Path dir = Files.createTempDirectory("person-update-test");
        personsPath = dir.resolve("persons.txt");
        idsPath = dir.resolve("ids.txt");
        Person.setPersonsFilePath(personsPath.toString());
        Person.setIdsFilePath(idsPath.toString());
    }

    // Test Case 1: Valid update (person 18+, odd first digit ID) – change name/address
    @Test
    void testUpdatePersonalDetails_validUpdate_returnsTrue() {
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertTrue(updater.updatePersonalDetails("56s_d%&fAB", "56s_d%&fAB", "Jane", "Smith",
                "10|New Street|Melbourne|Victoria|Australia", null),
                "Valid name and address update for adult with odd first digit ID should succeed");
    }

    // Test Case 2: Under-18 person – address change not allowed
    @Test
    void testUpdatePersonalDetails_under18_addressChange_returnsFalse() {
        // Person born 2010 -> under 18
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-2010");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertFalse(updater.updatePersonalDetails("56s_d%&fAB", null, null, null,
                "10|New Street|Melbourne|Victoria|Australia", null),
                "Under-18 person should not be able to change address");
    }

    // Test Case 3: Birthday change with another field change – not allowed
    @Test
    void testUpdatePersonalDetails_birthdayChangeWithOtherField_returnsFalse() {
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertFalse(updater.updatePersonalDetails("56s_d%&fAB", null, "Janet", null, null, "16-11-1990"),
                "When birthday is changed, no other detail (e.g. firstName) may be changed");
    }

    // Test Case 4: Person with even first digit of ID – ID change not allowed
    @Test
    void testUpdatePersonalDetails_evenFirstDigitIdChange_returnsFalse() {
        Person p = new Person("68s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertFalse(updater.updatePersonalDetails("68s_d%&fAB", "56s_d%&fAB", null, null, null, null),
                "Person with even first digit (6) should not be able to change ID");
    }

    // Test Case 5: Valid birthday-only update
    @Test
    void testUpdatePersonalDetails_birthdayOnlyUpdate_returnsTrue() {
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertTrue(updater.updatePersonalDetails("56s_d%&fAB", null, null, null, null, "16-11-1990"),
                "Birthday-only update should succeed");
    }
}
