package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Restores archived contacts back into the active address book.
 * Can restore either a single contact by index or all contacts if no index is provided.
 * <p>
 * When executed with an index, this command transfers the specified {@link Person} from the archive
 * back into the active address book. When executed without an index, it transfers all contacts and
 * clears the archive.
 * <p>
 * This command returns a {@link CommandResult} with a message indicating what was unarchived.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores an archived contact by the index number used in the displayed archived person list, "
            + "or restores all archived contacts if no index is provided.\n"
            + "Parameters: [INDEX] (must be a positive integer)\n"
            + "Examples: " + COMMAND_WORD + " 1\n"
            + COMMAND_WORD;

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_UNARCHIVE_ALL_SUCCESS = "Successfully unarchived %d contacts.";

    public static final String MESSAGE_EMPTY_LIST =
            "The archive is empty and there is nothing to unarchive!";

    private final Index targetIndex;

    /**
     * Creates an UnarchiveCommand to restore all archived contacts.
     */
    public UnarchiveCommand() {
        this.targetIndex = null;
    }

    /**
     * Creates an UnarchiveCommand to restore the archived contact at the specified {@code targetIndex}.
     */
    public UnarchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the unarchive command by moving either a single {@link Person} or all {@link Person}
     * objects from the archive back into the active address book.
     *
     * @param model The {@link Model} containing the address book and archive data.
     * @return A {@link CommandResult} containing a success message.
     * @throws CommandException If the archive is empty or the index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownArchivedList = model.getFilteredArchivedPersonList();

        if (lastShownArchivedList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        // Unarchive single person by index
        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownArchivedList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToUnarchive = lastShownArchivedList.get(targetIndex.getZeroBased());
            model.addPerson(personToUnarchive);
            // Remove from archive
            AddressBook updatedArchive = new AddressBook(model.getArchive());
            updatedArchive.removePerson(personToUnarchive);
            model.setArchive(updatedArchive);
            return new CommandResult(String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                    Messages.format(personToUnarchive)));
        }

        // Unarchive all persons
        List<Person> persons = model.getArchive().getPersonList();
        int size = persons.size();
        model.addPersons(persons);
        model.setArchive(new AddressBook());
        return new CommandResult(String.format(MESSAGE_UNARCHIVE_ALL_SUCCESS, size));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        if (targetIndex == null && otherUnarchiveCommand.targetIndex == null) {
            return true;
        }
        if (targetIndex == null || otherUnarchiveCommand.targetIndex == null) {
            return false;
        }
        return targetIndex.equals(otherUnarchiveCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
