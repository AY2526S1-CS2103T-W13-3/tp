package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnarchiveCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnarchiveCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnarchiveCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnarchiveCommandParserTest {

    private UnarchiveCommandParser parser = new UnarchiveCommandParser();

    @Test
    public void parse_validIndex_returnsUnarchiveCommand() {
        assertParseSuccess(parser, "1", new UnarchiveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_emptyArgs_returnsUnarchiveAllCommand() {
        assertParseSuccess(parser, "", new UnarchiveCommand());
    }

    @Test
    public void parse_whitespaceArgs_returnsUnarchiveAllCommand() {
        assertParseSuccess(parser, "   ", new UnarchiveCommand());
    }
}
