package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ArchiveCommand object
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveCommand
     * and returns an ArchiveCommand object for execution.
     * Returns an ArchiveCommand with no index if arguments are empty (archives all).
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArchiveCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // If no arguments provided, archive all contacts
        if (trimmedArgs.isEmpty()) {
            return new ArchiveCommand();
        }

        // Otherwise, parse the index
        Index index = ParserUtil.parseIndex(trimmedArgs);
        return new ArchiveCommand(index);
    }

}
