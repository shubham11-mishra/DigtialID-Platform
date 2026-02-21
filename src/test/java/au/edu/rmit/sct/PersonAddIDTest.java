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

    @Test
    void testAddID_validPassport_returnsTrue() {
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "passport", "AB123456"));
    }

    @Test
    void testAddID_validDriversLicence_returnsTrue() {
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "driverslicence", "XY12345678"));
    }

    @Test
    void testAddID_validMedicare_returnsTrue() {
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "medicare", "123456789"));
    }

    @Test
    void testAddID_validStudentCard_under18_noOtherIDs_returnsTrue() {
        Person person = new Person("56s_d%&fAB", "Jane", "Doe", "32|High St|Melbourne|Victoria|Australia", "15-11-2010");
        assertTrue(person.addPerson());
        Person p = new Person();
        assertTrue(p.addID("56s_d%&fAB", "studentcard", "123456789012"));
    }

    @Test
    void testAddID_invalidFormat_returnsFalse() {
        Person p = new Person();
        assertFalse(p.addID("56s_d%&fAB", "passport", "A1234567"));
        assertFalse(p.addID("56s_d%&fAB", "medicare", "12345678"));
        assertFalse(p.addID("56s_d%&fAB", "studentcard", "12345"));
    }
}
