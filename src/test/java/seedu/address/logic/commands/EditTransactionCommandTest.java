package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTransactionCommand.
 */
public class EditTransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new AddressBook());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Transaction transactionToEdit = personToEdit.getTransactions().get(0);
        EditTransactionCommand.EditTransactionDescriptor descriptor =
                new EditTransactionDescriptorBuilder(transactionToEdit)
                .withName("New Name").withAmount(100.0).build();
        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(INDEX_FIRST_PERSON,
                Index.fromZeroBased(0), descriptor);

        Transaction editedTransaction = new Transaction("New Name", 100.0);
        PersonBuilder personInList = new PersonBuilder(personToEdit);
        Person editedPerson = personInList.withTransactions(Arrays.asList(editedTransaction)).build();

        String expectedMessage = String.format(EditTransactionCommand.MESSAGE_EDIT_TRANSACTION_SUCCESS,
                Messages.format(editedPerson), editedTransaction);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                model.getArchive());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(editTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditTransactionCommand.EditTransactionDescriptor descriptor =
                new EditTransactionDescriptorBuilder().withName("New Name").build();
        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(outOfBoundIndex,
                Index.fromZeroBased(0), descriptor);

        assertCommandFailure(editTransactionCommand, model, Messages.MESSAGE_PERSON_INDEX_OUT_OF_BOUNDS);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditTransactionCommand.EditTransactionDescriptor descriptor =
                new EditTransactionDescriptorBuilder().withName("New Name").build();

        EditTransactionCommand editTransactionCommand = new EditTransactionCommand(outOfBoundIndex,
                Index.fromZeroBased(0), descriptor);

        assertCommandFailure(editTransactionCommand, model, Messages.MESSAGE_PERSON_INDEX_OUT_OF_BOUNDS);
    }

    @Test
    public void equals() {
        final EditTransactionCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withName("Test").withAmount(1.0).build();
        final EditTransactionCommand standardCommand = new EditTransactionCommand(INDEX_FIRST_PERSON,
                Index.fromZeroBased(0), descriptor);

        // same values -> returns true
        EditTransactionCommand.EditTransactionDescriptor copyDescriptor =
                new EditTransactionCommand.EditTransactionDescriptor(descriptor);
        EditTransactionCommand commandWithSameValues = new EditTransactionCommand(INDEX_FIRST_PERSON,
                Index.fromZeroBased(0), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTransactionCommand(INDEX_SECOND_PERSON,
                Index.fromZeroBased(0), descriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTransactionCommand(INDEX_FIRST_PERSON,
                Index.fromZeroBased(0), new EditTransactionDescriptorBuilder()
                .withName("Different").withAmount(2.0).build())));
    }
}
