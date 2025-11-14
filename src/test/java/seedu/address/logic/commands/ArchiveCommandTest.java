package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

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
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model emptyModelStub = new ModelManager(new AddressBook(), new UserPrefs(), new AddressBook());
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new AddressBook());

    @Test
    public void execute_emptyAddressBook_throwsCommandException() {
        ArchiveCommand archiveCommand = new ArchiveCommand();
        assertCommandFailure(archiveCommand, emptyModelStub, ArchiveCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_emptyAddressBookWithIndex_throwsCommandException() {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(archiveCommand, emptyModelStub, ArchiveCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_nonEmptyAddressBook_archivesAllContacts() {
        ArchiveCommand archiveCommand = new ArchiveCommand();
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalAddressBook());
        assertCommandSuccess(archiveCommand, model, String.format(ArchiveCommand.MESSAGE_ARCHIVE_ALL_SUCCESS, 7),
                expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(personToArchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getArchive());
        expectedModel.addArchivedPersons(List.of(personToArchive));
        expectedModel.deletePerson(personToArchive);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_PERSON);
        ArchiveCommand archiveAllCommand = new ArchiveCommand();

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_PERSON);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // both archive all -> returns true
        ArchiveCommand archiveAllCommandCopy = new ArchiveCommand();
        assertTrue(archiveAllCommand.equals(archiveAllCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));

        // archive single vs archive all -> returns false
        assertFalse(archiveFirstCommand.equals(archiveAllCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(targetIndex);
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, archiveCommand.toString());
    }

}
