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
 * Archives contacts from the address book.
 * Can archive either a single contact by index or all contacts if no index is provided.
 * <p>
 * When executed with an index, this command transfers the specified {@link Person} from the active
 * address book into the archived contacts list. When executed without an index, it transfers all
 * contacts and clears the address book.
 * <p>
 * This command returns a {@link CommandResult} with a message indicating what was archived.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives a contact by the index number used in the displayed person list, "
            + "or archives all contacts if no index is provided.\n"
            + "Parameters: [INDEX] (must be a positive integer)\n"
            + "Examples: " + COMMAND_WORD + " 1\n"
            + COMMAND_WORD;

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";
    public static final String MESSAGE_ARCHIVE_ALL_SUCCESS = "Successfully archived %d contacts.";

    public static final String MESSAGE_EMPTY_LIST =
            "The addressbook is empty and there is nothing to archive!";

    private final Index targetIndex;

    /**
     * Creates an ArchiveCommand to archive all contacts.
     */
    public ArchiveCommand() {
        this.targetIndex = null;
    }

    /**
     * Creates an ArchiveCommand to archive the contact at the specified {@code targetIndex}.
     */
    public ArchiveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the archive command by moving either a single {@link Person} or all {@link Person}
     * objects in the current address book into the archived contacts list.
     *
     * @param model The {@link Model} containing the address book and archive data.
     * @return A {@link CommandResult} containing a success message.
     * @throws CommandException If the address book is empty or the index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        // Archive single person by index
        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToArchive = lastShownList.get(targetIndex.getZeroBased());
            model.addArchivedPersons(List.of(personToArchive));
            model.deletePerson(personToArchive);
            return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS,
                    Messages.format(personToArchive)));
        }

        // Archive all persons
        List<Person> persons = model.getAddressBook().getPersonList();
        int size = persons.size();
        model.addArchivedPersons(persons);
        model.setAddressBook(new AddressBook());
        return new CommandResult(String.format(MESSAGE_ARCHIVE_ALL_SUCCESS, size));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        if (targetIndex == null && otherArchiveCommand.targetIndex == null) {
            return true;
        }
        if (targetIndex == null || otherArchiveCommand.targetIndex == null) {
            return false;
        }
        return targetIndex.equals(otherArchiveCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
