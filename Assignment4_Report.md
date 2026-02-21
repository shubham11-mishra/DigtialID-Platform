# Assignment 4 (Team-based) – Report

## Part (a) Test Cases for Testing the Functions


### addPerson – 5 test cases

| Test Case | Test Data | Expected Result | Test Result | Pass/Fail |
|-----------|-----------|-----------------|-------------|-----------|
| 1. Check the function with valid inputs | ("56s_d%&fAB", "Jane", "Doe", "32\|Highland Street\|Melbourne\|Victoria\|Australia", "15-11-1990"); ("34a@b#cDE", "John", "Smith", "1\|Collins St\|Melbourne\|Victoria\|Australia", "01-01-2000") | The person information should be added to the TXT file. addPerson returns true. | addPerson returns true; record written to file | Pass |
| 2. Check the function with invalid personID | ("56s_d%&fA", ...); ("10a@b#cDE", ...); ("56s_d%&fab", ...) | The person information should not be added. addPerson returns false. | addPerson returns false | Pass |
| 3. Check the function with invalid address | Address with State NSW; address with wrong format (fewer than 5 parts) | The person information should not be added. addPerson returns false. | addPerson returns false | Pass |
| 4. Check the function with invalid birthdate | "1990-11-15"; "31-13-1990" | The person information should not be added. addPerson returns false. | addPerson returns false | Pass |
| 5. Check the function with fewer than 2 special chars in personID (positions 3–8) | ("56sd%&fAB", ...) – only one special character in middle | The person information should not be added. addPerson returns false. | addPerson returns false | Pass |


### updatePersonalDetails – 5 test cases

| Test Case | Test Data | Expected Result | Test Result | Pass/Fail |
|-----------|-----------|-----------------|-------------|-----------|
| 1. Valid update (adult, odd first digit ID) | Existing person "56s_d%&fAB", update lastName to "Smith" and address | Person information should be updated in TXT. updatePersonalDetails returns true. | Returns true; file updated | Pass |
| 2. Under-18 person – address change | Person with birthday 15-11-2010; attempt to change address | Address cannot be changed. updatePersonalDetails returns false. | Returns false | Pass |
| 3. Birthday change with another field change | Change birthday and firstName together | When birthday is changed, no other detail may change. updatePersonalDetails returns false. | Returns false | Pass |
| 4. Person with even first digit of ID – ID change | Person "68s_d%&fAB"; attempt to change ID to "56s_d%&fAB" | ID cannot be changed. updatePersonalDetails returns false. | Returns false | Pass |
| 5. Valid birthday-only update | Change only birthday to "16-11-1990" | Person information should be updated. updatePersonalDetails returns true. | Returns true | Pass |


### addID – 5 test cases

| Test Case | Test Data | Expected Result | Test Result | Pass/Fail |
|-----------|-----------|-----------------|-------------|-----------|
| 1. Valid passport | personID "56s_d%&fAB", type "passport", number "AB123456" | ID information should be added to TXT. addID returns true. | Returns true | Pass |
| 2. Valid driver's licence | type "driverslicence", number "XY12345678" | ID information should be added. addID returns true. | Returns true | Pass |
| 3. Valid Medicare card | type "medicare", number "123456789" | ID information should be added. addID returns true. | Returns true | Pass |
| 4. Valid student card (under 18, no other IDs) | Person with birthday 15-11-2010 added first; then addID "studentcard", "123456789012" | ID information should be added. addID returns true. | Returns true | Pass |
| 5. Invalid ID format | Passport "A1234567"; Medicare "12345678"; student card "12345" | ID information should not be added. addID returns false. | Returns false | Pass |


## Part (b) User Stories and Acceptance Criteria

Template: **As a** `<user role>`, **I want** `<goal>` **so that** `<benefit>`.

### User Story 1
**As a** Citizen, **I want** to register and create a Digital ID account **so that** I can use government and other services online securely.

**Acceptance criteria:**
1. The system shall validate all required registration fields (e.g. full name, date of birth, email, password) and their formats before submission.
2. The system shall initiate identity verification with the Identity Verification Agency using the details provided by the citizen.
3. The system shall create a new Digital ID account and send a registration confirmation to the citizen when verification succeeds.

### User Story 2
**As a** Citizen, **I want** to update my personal details (name, address, date of birth where allowed) **so that** my Digital ID information stays current.

**Acceptance criteria:**
1. The system shall allow address updates only for citizens who are 18 years or older.
2. The system shall allow a change of date of birth only when no other personal detail (ID, name, address) is changed in the same request.
3. The system shall prevent a change of person ID when the first character of the current ID is an even digit.

### User Story 3
**As a** Citizen, **I want** to view my Digital ID details **so that** I can confirm what information is stored and linked to my account.

**Acceptance criteria:**
1. The system shall display the citizen's personal information (e.g. name, date of birth, address) only after successful authentication.
2. The system shall display linked identity documents (e.g. passport, driver's licence, Medicare) if they have been added.
3. The system shall not display sensitive data (e.g. full document numbers) in an unmasked form unless the user explicitly requests it and re-authenticates.

### User Story 4
**As a** Citizen, **I want** to book an appointment (e.g. for in-person verification or support) **so that** I can complete processes that require a face-to-face visit.

**Acceptance criteria:**
1. The system shall allow the citizen to select appointment type, location, and an available time slot from the Appointment System.
2. The system shall confirm the booking and send a notification (e.g. email or in-app) with appointment details.
3. The system shall require the citizen to be logged in before booking an appointment.

### User Story 5
**As a** Citizen, **I want** to renew my Digital ID **so that** I can continue to use it after it expires or when required by policy.

**Acceptance criteria:**
1. The system shall initiate identity verification (e.g. with the Identity Verification Agency) as part of the renewal process.
2. The system shall update the validity period of the Digital ID upon successful renewal.
3. The system shall notify the citizen when renewal is complete and when their Digital ID is due for renewal.

### User Story 6
**As a** Citizen, **I want** to view my notifications and manage my notification preferences **so that** I receive important updates in the way I prefer.

**Acceptance criteria:**
1. The system shall list all notifications relevant to the citizen (e.g. appointment reminders, account alerts) when they choose to view notifications.
2. The system shall allow the citizen to set preferences for how and when they receive notifications (e.g. email, in-app, SMS).
3. The system shall persist the citizen's notification preferences and apply them to future notifications.

### User Story 7
**As a** Citizen, **I want** to link my Digital ID to an external service (e.g. a government or partner service) **so that** I can sign in or prove my identity to that service without creating another account.

**Acceptance criteria:**
1. The system shall require the citizen to authenticate before linking an external service.
2. The system shall allow the citizen to select the external service and complete the linking flow with the External Service Provider.
3. The system shall record the link and allow the citizen to view and, where supported, revoke linked services.

### User Story 8
**As a** Citizen, **I want** to report my Digital ID or linked identity documents as lost or stolen **so that** my account can be secured and misuse can be prevented.

**Acceptance criteria:**
1. The system shall allow the citizen to report loss or theft from their account (e.g. from the View Digital ID Details flow) and shall record the report.
2. The system shall suspend or flag the affected Digital ID and linked documents as appropriate and prevent further use until the citizen is re-verified.
3. The system shall notify the citizen and, where applicable, relevant authorities or services in line with policy.

---

*This report contains (a) the test cases for addPerson, updatePersonalDetails, and addID, and (b) eight user stories with three acceptance criteria each for the Digital ID platform.*

