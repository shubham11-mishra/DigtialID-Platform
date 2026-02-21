# Assignment 4 (Team-based) – Report

## Part (a) Test Cases for Testing the Functions

### addPerson – 5 test cases

### updatePersonalDetails – 5 test cases
| Test Case | Test Data | Expected Result | Test Result | Pass/Fail |
|-----------|-----------|-----------------|-------------|-----------|
| 1. Valid update (adult, odd first digit ID) | Existing person "56s_d%&fAB", update lastName to "Smith" and address | Person information should be updated in TXT. updatePersonalDetails returns true. | Returns true; file updated | Pass |
| 2. Under-18 person – address change | Person with birthday 15-11-2010; attempt to change address | Address cannot be changed. updatePersonalDetails returns false. | Returns false | Pass |
| 3. Birthday change with another field change | Change birthday and firstName together | When birthday is changed, no other detail may change. updatePersonalDetails returns false. | Returns false | Pass |
| 4. Person with even first digit of ID – ID change | Person "68s_d%&fAB"; attempt to change ID to "56s_d%&fAB" | ID cannot be changed. updatePersonalDetails returns false. | Returns false | Pass |
| 5. Valid birthday-only update | Change only birthday to "16-11-1990" | Person information should be updated. updatePersonalDetails returns true. | Returns true | Pass |

### addID – 5 test cases


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

