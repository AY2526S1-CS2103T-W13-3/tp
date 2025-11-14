package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnarchiveCommand object
 */
public class UnarchiveCommandParser implements Parser<UnarchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnarchiveCommand
     * and returns an UnarchiveCommand object for execution.
     * Returns an UnarchiveCommand with no index if arguments are empty (unarchives all).
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnarchiveCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // If no arguments provided, unarchive all contacts
        if (trimmedArgs.isEmpty()) {
            return new UnarchiveCommand();
        }

        // Otherwise, parse the index
        Index index = ParserUtil.parseIndex(trimmedArgs);
        return new UnarchiveCommand(index);
    }

}
