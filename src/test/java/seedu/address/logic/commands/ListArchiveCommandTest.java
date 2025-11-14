package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ListArchiveCommand}.
 */
public class ListArchiveCommandTest {

    @Test
    public void execute_listArchive_showsAllArchivedPersons() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalAddressBook());
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalAddressBook());
        CommandResult expectedCommandResult = new CommandResult(ListArchiveCommand.MESSAGE_SUCCESS,
                false, false, true);
        assertCommandSuccess(new ListArchiveCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_emptyArchive_showsNoPersons() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), new AddressBook());
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), new AddressBook());
        CommandResult expectedCommandResult = new CommandResult(ListArchiveCommand.MESSAGE_SUCCESS,
                false, false, true);
        assertCommandSuccess(new ListArchiveCommand(), model, expectedCommandResult, expectedModel);
    }
}
