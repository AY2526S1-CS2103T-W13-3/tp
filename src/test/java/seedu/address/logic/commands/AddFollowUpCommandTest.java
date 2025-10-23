package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.followUp.FollowUp;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code AddFollowUpCommand}.
 */
public class AddFollowUpCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FollowUp followUp = new FollowUp("Get price estimate for aircon repair",
                "HIGH");
        AddFollowUpCommand addFollowUpCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, followUp);

        List<FollowUp> updatedFollowUps= new ArrayList<>(personToModify.getFollowUps());
        updatedFollowUps.add(followUp);
        Person modifiedPerson = new Person(personToModify.getName(), personToModify.getPhone(), personToModify.getEmail(), personToModify.getAddress(), personToModify.getTags(), personToModify.getTransactions(), updatedFollowUps);

        String expectedMessage = String.format(AddFollowUpCommand.MESSAGE_SUCCESS,
                Messages.format(modifiedPerson), followUp);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToModify, modifiedPerson);

        assertCommandSuccess(addFollowUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FollowUp followUp = new FollowUp("Ask about bread prices",
                "MEDIUM");
        AddFollowUpCommand addFollowUpCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, followUp);

        List<FollowUp> updatedFollowUps= new ArrayList<>(personToModify.getFollowUps());
        updatedFollowUps.add(followUp);
        Person modifiedPerson = new Person(personToModify.getName(), personToModify.getPhone(), personToModify.getEmail(), personToModify.getAddress(), personToModify.getTags(), personToModify.getTransactions(), updatedFollowUps);

        String expectedMessage = String.format(AddFollowUpCommand.MESSAGE_SUCCESS,
                Messages.format(modifiedPerson), followUp);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToModify, modifiedPerson);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(addFollowUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_lowUrgencyFollowUp_success() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FollowUp followUp = new FollowUp("Schedule monthly check-in",
                "LOW");
        AddFollowUpCommand addFollowUpCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, followUp);


        List<FollowUp> updatedFollowUps= new ArrayList<>(personToModify.getFollowUps());
        updatedFollowUps.add(followUp);
        Person modifiedPerson = new Person(personToModify.getName(), personToModify.getPhone(), personToModify.getEmail(), personToModify.getAddress(), personToModify.getTags(), personToModify.getTransactions(), updatedFollowUps);

        String expectedMessage = String.format(AddFollowUpCommand.MESSAGE_SUCCESS,
                Messages.format(modifiedPerson), followUp);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToModify, modifiedPerson);

        assertCommandSuccess(addFollowUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mediumUrgencyFollowUp_success() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FollowUp followUp = new FollowUp("Schedule monthly check-in",
                "MEDIUM");
        AddFollowUpCommand addFollowUpCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, followUp);


        List<FollowUp> updatedFollowUps= new ArrayList<>(personToModify.getFollowUps());
        updatedFollowUps.add(followUp);
        Person modifiedPerson = new Person(personToModify.getName(), personToModify.getPhone(), personToModify.getEmail(), personToModify.getAddress(), personToModify.getTags(), personToModify.getTransactions(), updatedFollowUps);

        String expectedMessage = String.format(AddFollowUpCommand.MESSAGE_SUCCESS,
                Messages.format(modifiedPerson), followUp);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToModify, modifiedPerson);

        assertCommandSuccess(addFollowUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleFollowUps_success() {
        // First add a follow-up
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FollowUp firstFollowUp = new FollowUp("First follow-up task",
                "HIGH");

        AddFollowUpCommand firstCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, firstFollowUp);
        Model intermediateModel = new ModelManager(model.getAddressBook(), new UserPrefs());


        List<FollowUp> updatedFollowUps= new ArrayList<>(personToModify.getFollowUps());
        updatedFollowUps.add(firstFollowUp);
        Person intermediateModifiedPerson = new Person(personToModify.getName(), personToModify.getPhone(), personToModify.getEmail(), personToModify.getAddress(), personToModify.getTags(), personToModify.getTransactions(), updatedFollowUps);
        intermediateModel.setPerson(personToModify, intermediateModifiedPerson);

        // Now add a second follow-up
        Person personWithOneFollowUp = intermediateModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FollowUp secondFollowUp = new FollowUp("Second follow-up task",
                "MEDIUM");

        AddFollowUpCommand secondCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, secondFollowUp);

        List<FollowUp> finalUpdatedFollowups = new ArrayList<>(intermediateModifiedPerson.getFollowUps());
        finalUpdatedFollowups.add(firstFollowUp);
        Person finalModifiedPerson = new Person(intermediateModifiedPerson.getName(), intermediateModifiedPerson.getPhone(), intermediateModifiedPerson.getEmail(), intermediateModifiedPerson.getAddress(), intermediateModifiedPerson.getTags(), intermediateModifiedPerson.getTransactions(), updatedFollowUps);

        String expectedMessage = String.format(AddFollowUpCommand.MESSAGE_SUCCESS,
                Messages.format(finalModifiedPerson), secondFollowUp);

        Model expectedModel = new ModelManager(intermediateModel.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithOneFollowUp, finalModifiedPerson);

        assertCommandSuccess(secondCommand, intermediateModel, expectedMessage, expectedModel);

        // Verify the person now has 2 follow-ups
        Person finalPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(2, finalPerson.getFollowUps().size());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FollowUp followUp = new FollowUp("Invalid index follow-up",
                "HIGH");
        AddFollowUpCommand addFollowUpCommand = new AddFollowUpCommand(outOfBoundIndex, followUp);

        assertCommandFailure(addFollowUpCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FollowUp followUp = new FollowUp("Invalid index follow-up",
                "MEDIUM");
        AddFollowUpCommand addFollowUpCommand = new AddFollowUpCommand(outOfBoundIndex, followUp);

        assertCommandFailure(addFollowUpCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FollowUp followUp1 = new FollowUp("First follow-up",
                "HIGH");
        FollowUp followUp2 = new FollowUp("Second follow-up",
                "LOW");

        AddFollowUpCommand addFirstCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, followUp1);
        AddFollowUpCommand addSecondCommand = new AddFollowUpCommand(INDEX_SECOND_PERSON, followUp2);
        AddFollowUpCommand duplicateFirstCommand = new AddFollowUpCommand(INDEX_FIRST_PERSON, followUp1);

        // same object -> returns true
        assertEquals(addFirstCommand, addFirstCommand);

        // same values -> returns true
        assertEquals(addFirstCommand, duplicateFirstCommand);

        // different types -> returns false
        assertNotEquals(1, addFirstCommand);

        // null -> returns false
        assertNotEquals(null, addFirstCommand);

        // different index -> returns false
        assertNotEquals(addFirstCommand, new AddFollowUpCommand(INDEX_SECOND_PERSON, followUp1));

        // different followUp -> returns false
        assertNotEquals(addFirstCommand, new AddFollowUpCommand(INDEX_FIRST_PERSON, followUp2));

        // different index and followUp -> returns false
        assertNotEquals(addFirstCommand, addSecondCommand);
    }
}
