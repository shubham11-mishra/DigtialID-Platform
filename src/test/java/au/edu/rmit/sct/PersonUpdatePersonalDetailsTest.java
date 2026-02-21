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

    @Test
    void testUpdatePersonalDetails_validUpdate_returnsTrue() {
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertTrue(updater.updatePersonalDetails("56s_d%&fAB", "56s_d%&fAB", "Jane", "Smith",
                "10|New Street|Melbourne|Victoria|Australia", null));
    }

    @Test
    void testUpdatePersonalDetails_under18_addressChange_returnsFalse() {
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-2010");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertFalse(updater.updatePersonalDetails("56s_d%&fAB", null, null, null,
                "10|New Street|Melbourne|Victoria|Australia", null));
    }

    @Test
    void testUpdatePersonalDetails_birthdayChangeWithOtherField_returnsFalse() {
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertFalse(updater.updatePersonalDetails("56s_d%&fAB", null, "Janet", null, null, "16-11-1990"));
    }

    @Test
    void testUpdatePersonalDetails_evenFirstDigitIdChange_returnsFalse() {
        Person p = new Person("68s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertFalse(updater.updatePersonalDetails("68s_d%&fAB", "56s_d%&fAB", null, null, null, null));
    }

    @Test
    void testUpdatePersonalDetails_birthdayOnlyUpdate_returnsTrue() {
        Person p = new Person("56s_d%&fAB", "Jane", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990");
        assertTrue(p.addPerson());
        Person updater = new Person();
        assertTrue(updater.updatePersonalDetails("56s_d%&fAB", null, null, null, null, "16-11-1990"));
    }
}
