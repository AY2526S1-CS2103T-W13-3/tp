package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model emptyArchiveModelStub = new ModelManager(new AddressBook(), new UserPrefs(), new AddressBook());
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalAddressBook());

    @Test
    public void execute_emptyArchive_throwsCommandException() {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand();
        assertCommandFailure(unarchiveCommand, emptyArchiveModelStub, UnarchiveCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_emptyArchiveWithIndex_throwsCommandException() {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(unarchiveCommand, emptyArchiveModelStub, UnarchiveCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_nonEmptyArchive_unarchivesAllContacts() {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new AddressBook());
        assertCommandSuccess(unarchiveCommand, model,
                String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_ALL_SUCCESS, 7), expectedModel);
    }

    @Test
    public void execute_validIndexArchivedList_success() {
        Person personToUnarchive = model.getFilteredArchivedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getArchive());
        expectedModel.addPerson(personToUnarchive);
        AddressBook updatedArchive = new AddressBook(expectedModel.getArchive());
        updatedArchive.removePerson(personToUnarchive);
        expectedModel.setArchive(updatedArchive);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexArchivedList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArchivedPersonList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_PERSON);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_PERSON);
        UnarchiveCommand unarchiveAllCommand = new UnarchiveCommand();

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // both unarchive all -> returns true
        UnarchiveCommand unarchiveAllCommandCopy = new UnarchiveCommand();
        assertTrue(unarchiveAllCommand.equals(unarchiveAllCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));

        // unarchive single vs unarchive all -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveAllCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(targetIndex);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }

}
