package seedu.address.logic.commands.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.NewCommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.NewCommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.NewCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.NewCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.NewCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.NewCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.NewCommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientRecords;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.patient.EditPatientCommand.EditPatientDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientRecords;
import seedu.address.model.RoomList;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.task.TaskList;
import seedu.address.testutil.EditPatientDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.TypicalPatients;

/**
 * Contains integration tests and unit tests for EditPatientCommand.
 */
public class EditPatientCommandTest {

    private Model model = new ModelManager(getTypicalPatientRecords(), new UserPrefs(), new RoomList(), new TaskList());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient editedPatient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(new Name("Alice Pauline"), descriptor);

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientRecords(model.getPatientRecords()), new UserPrefs(),
                new RoomList(), new TaskList());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        PatientBuilder patientInList = new PatientBuilder(TypicalPatients.GEORGE);
        Patient editedPatient = patientInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditPatientCommand editCommand = new EditPatientCommand(new Name("George Best"), descriptor);

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientRecords(model.getPatientRecords()), new UserPrefs(),
                new RoomList(), new TaskList());
        expectedModel.setPatient(TypicalPatients.GEORGE, editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPatientCommand editCommand = new EditPatientCommand(new Name("Alice Pauline"), new EditPatientDescriptor());
        Patient editedPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientRecords(model.getPatientRecords()), new UserPrefs(),
                new RoomList(), new TaskList());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient patientInFilteredList = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList).withName(VALID_NAME_BOB).build();
        EditPatientCommand editCommand = new EditPatientCommand(new Name("Alice Pauline"),
                new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientRecords(model.getPatientRecords()), new UserPrefs(),
                new RoomList(), new TaskList());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePatientUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(firstPatient).build();
        EditPatientCommand editCommand = new EditPatientCommand(new Name("Benson Meier"), descriptor);

        assertCommandFailure(editCommand, model, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_duplicatePatientFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        // Edit patient in filtered list into a duplicate in address book
        Patient patientInList = model.getPatientRecords().getPatientList().get(INDEX_SECOND_PATIENT.getZeroBased());
        EditPatientCommand editCommand = new EditPatientCommand(new Name("Alice Pauline"),
                new EditPatientDescriptorBuilder(patientInList).build());

        assertCommandFailure(editCommand, model, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidPatientNameUnfilteredList_failure() {
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditPatientCommand editCommand = new EditPatientCommand(new Name("Unknown"), descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PATIENT_NAME);
    }

    @Test
    public void equals() {
        final EditPatientCommand standardCommand = new EditPatientCommand(new Name("Amy Bee"), DESC_AMY);

        // Same values -> returns true
        EditPatientDescriptor copyDescriptor = new EditPatientDescriptor(DESC_AMY);
        EditPatientCommand commandWithSameValues = new EditPatientCommand(new Name("Amy Bee"), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false
        assertFalse(standardCommand.equals(new EditPatientCommand(new Name("Bob Choo"), DESC_AMY)));

        // Different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPatientCommand(new Name("Amy Bee"), DESC_BOB)));
    }

}
